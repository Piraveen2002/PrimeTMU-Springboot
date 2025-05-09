package org.primetmu.primetmu_api.client;

import java.util.List;
import java.util.UUID;

public record ClientDTO(
                UUID id,
                String firstName,
                String lastName,
                String username,
                List<String> roles) {
}
