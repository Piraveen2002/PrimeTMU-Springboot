package org.primetmu.primetmu_api.client;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class ClientDTOMapper implements Function<Client, ClientDTO> {
    @Override
    public ClientDTO apply(Client c) {
        return new ClientDTO(
                c.getId(),
                c.getFirstName(),
                c.getLastName(),
                c.getUsername(),
                c.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));
    }
}
