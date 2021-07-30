package com.buster.backend.dto;


import javax.validation.constraints.NotBlank;

public class RefreshTokenRequest {
    @NotBlank
    private String username;
    private String refreshToken;


    public RefreshTokenRequest(String refreshToken, String username) {
      this.refreshToken = refreshToken;
      this.username = username;
    }

    public RefreshTokenRequest() {
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getUsername() {
      return username;
    }

    public void setRefreshToken(String refreshToken) {
      this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
      return refreshToken;
    }
}
