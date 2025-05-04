package org.primetmu.primetmu_api.auth;

public record AuthenticationRequest(
        String username,
        String password) {
}
