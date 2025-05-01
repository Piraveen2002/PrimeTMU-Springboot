package org.primetmu.primetmu_api.client;

import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class ClientDTOMapper implements Function<Client, ClientDTO> {
    @Override
    public ClientDTO apply(Client c) {
        return new ClientDTO(
                c.getId(),
                c.getFirstName(),
                c.getLastName(),
                c.getUsername());
    }
}
