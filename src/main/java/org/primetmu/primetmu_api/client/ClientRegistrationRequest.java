package org.primetmu.primetmu_api.client;

public record ClientRegistrationRequest(
        String firstName,
        String lastName,
        String username,
        String password) {
}
