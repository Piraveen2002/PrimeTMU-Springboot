package org.primetmu.primetmu_api.client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository("clientJpa")
public class ClientJPADataAccessService implements ClientDao {
    private final ClientRepository clientRepo;

    public ClientJPADataAccessService(ClientRepository clientRepository) {
        this.clientRepo = clientRepository;
    }

    @Override
    public List<Client> selectAllClients() {
        Page<Client> page = clientRepo.findAll(Pageable.ofSize(1000));
        return page.getContent();
    }

    @Override
    public Optional<Client> selectClientById(UUID id) {
        return clientRepo.findById(id);
    }

    @Override
    public Optional<Client> selectClientByUsername(String username) {
        return clientRepo.findClientByUsername(username);
    }

    @Override
    public void insertClient(Client c) {
        clientRepo.save(c);
    }

    @Override
    public boolean existsClientByUsername(String username) {
        return clientRepo.existsClientByUsername(username);
    }

    public boolean existsClientById(UUID id) {
        return clientRepo.existsClientById(id);
    }

    @Override
    public void deleteClientById(UUID id) {
        clientRepo.deleteById(id);
    }

    @Override
    public void updateClient(Client c) {
        clientRepo.save(c);
    }
}
