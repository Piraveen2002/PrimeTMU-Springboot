package org.primetmu.primetmu_api.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientDao clientDao;
    private final ClientDTOMapper clientDTOMapper;
    private final PasswordEncoder passwordEncoder;

    public ClientService(
            @Qualifier("clientJdbc") ClientDao clientDao,
            ClientDTOMapper clientDTOMapper,
            PasswordEncoder passwordEncoder) {
        this.clientDao = clientDao;
        this.clientDTOMapper = clientDTOMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<ClientDTO> getAllClients() {
        return clientDao
                .selectAllClients()
                .stream()
                .map(clientDTOMapper)
                .collect(Collectors.toList());
    }

    public ClientDTO getClient(UUID id) {
        return clientDao.selectClientById(id)
                .map(clientDTOMapper)
                .orElseThrow(() -> new ResourceAccessException("Client with id [%s] not found".formatted(id)));
    }

    public void addClient(ClientRegistrationRequest crr) {
        String username = crr.username();
        System.out.println(username);

        if (clientDao.existsClientByUsername(username)) {
            throw new ResourceAccessException("Client already exists!");
        }

        UUID id = UUID.randomUUID();

        Client temp = new Client(id, crr.firstName(), crr.lastName(), crr.username(),
                passwordEncoder.encode(crr.password()));

        try {
            clientDao.insertClient(temp);
        } catch (Exception e) {
            // Log the exception for debugging
            System.out.println("Error inserting client: " + e.getMessage());
            throw new RuntimeException("Error inserting client", e);
        }
    }

    public void deleteClientById(UUID id) {
        Optional<Client> user = clientDao.selectClientById(id);

        if (user == null)
            throw new ResourceAccessException("id [%s] not found".formatted(id));

        clientDao.deleteClientById(id);
    }

    public void updateClient(UUID id, ClientUpdateRequest cur) {
        Client user = clientDao.selectClientById(id)
                .orElseThrow(() -> new ResourceAccessException("id not found!"));

        user.setFirstName(cur.firstName());
        user.setLastName(cur.lastName());

        clientDao.updateClient(user);
    }
}
