package com.buster.backend.service;

import com.buster.backend.dto.AuthenticationResponse;
import com.buster.backend.dto.LoginRequest;
import com.buster.backend.dto.RegisterRequest;
import com.buster.backend.exceptions.CustomException;
import com.buster.backend.model.NotificationEmail;
import com.buster.backend.model.Usuario;
import com.buster.backend.model.VerificationToken;
import com.buster.backend.repository.UsuarioRepository;
import com.buster.backend.repository.VerificationTokenRepository;
import com.buster.backend.security.JwtProvider;
import lombok.AllArgsConstructor;
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

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

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
                        "http://localhost:8080/api/auth/accountVerification/" + token));
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
        return new AuthenticationResponse(token, loginRequest.getUsername());
    }
}
