package org.primetmu.primetmu_api.client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientDao {
    List<Client> selectAllClients();

    Optional<Client> selectClientByUsername(String username);

    Optional<Client> selectClientById(UUID id);

    void insertClient(Client c);

    boolean existsClientByUsername(String username);

    boolean existsClientById(UUID id);

    void deleteClientById(UUID id);

    void updateClient(Client c);
}
