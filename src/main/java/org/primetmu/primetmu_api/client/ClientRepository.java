package org.primetmu.primetmu_api.client;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

@Transactional
public interface ClientRepository extends JpaRepository<Client, UUID> {
    boolean existsClientById(UUID id);

    boolean existsClientByUsername(String username);

    Optional<Client> findClientByUsername(String username);
}
