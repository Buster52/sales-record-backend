package com.buster.backend.service;

import com.buster.backend.dto.AuthenticationResponse;
import com.buster.backend.dto.LoginRequest;
import com.buster.backend.dto.RefreshTokenRequest;
import com.buster.backend.dto.RegisterRequest;
import com.buster.backend.exceptions.CustomException;
import com.buster.backend.model.NotificationEmail;
import com.buster.backend.model.Usuario;
import com.buster.backend.model.VerificationToken;
import com.buster.backend.repository.UsuarioRepository;
import com.buster.backend.repository.VerificationTokenRepository;
import com.buster.backend.security.JwtProvider;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  UsuarioRepository usuarioRepository;
    @Autowired
    private  VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private  MailService mailService;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  JwtProvider jwtProvider;
    @Autowired
    private  RefreshTokenService refreshTokenService;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        Usuario usuario = new Usuario();
        usuario.setFullName(registerRequest.getFullName());
        usuario.setUsername(registerRequest.getUsername());
        usuario.setEmail(registerRequest.getEmail());
        usuario.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        usuario.setCreated(Instant.now());
        usuario.setEnabled(false);

        usuarioRepository.save(usuario);

        String token = generateVerificationToken(usuario);
        mailService.sendMail(new NotificationEmail("Please activate your account.",
                usuario.getEmail(),
                "Thank you for signing up to Spring Reddit" +
                        "please click on the below url to activate your account: " +
                        "http://localhost:8080/api/v1/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(Usuario usuario) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUsuario(usuario);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new CustomException("Invalid token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUsuario().getUsername();
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() ->
                new CustomException("User not found with name - " + username)
        );
        usuario.setEnabled(true);
        usuarioRepository.save(usuario);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
	return AuthenticationResponse.builder()
	  .authenticationToken(token)
	  .refreshToken(refreshTokenService.generateRefreshToken().getToken())
	  .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
	  .username(loginRequest.getUsername())
	  .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    @Transactional(readOnly = true)
    public Usuario getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
        return usuarioRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new CustomException("User name not found - " + principal.getUsername()));
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
