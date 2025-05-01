package org.primetmu.primetmu_api.client;

import org.springframework.beans.factory.annotation.Qualifier;
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

    public ClientService(
            @Qualifier("clientJdbc") ClientDao clientDao,
            ClientDTOMapper clientDTOMapper) {
        this.clientDao = clientDao;
        this.clientDTOMapper = clientDTOMapper;
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

        Client temp = new Client(id, crr.firstName(), crr.lastName(), crr.username(), crr.password());

        clientDao.insertClient(temp);
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
        user.setUsername(cur.username());

        clientDao.updateClient(user);
    }
}
