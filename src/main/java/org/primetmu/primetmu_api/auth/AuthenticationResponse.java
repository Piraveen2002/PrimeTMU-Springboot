package org.primetmu.primetmu_api.auth;

import org.primetmu.primetmu_api.client.ClientDTO;

public record AuthenticationResponse(
        String token,
        ClientDTO clientDTO) {
}
