package org.primetmu.primetmu_api.client;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/clients")
public class ClientController {
    private final ClientService cs;

    public ClientController(ClientService clientService) {
        this.cs = clientService;
    }

    @GetMapping
    public List<ClientDTO> getClients() {
        return cs.getAllClients();
    }

    @GetMapping("{clientId}")
    public ClientDTO getClient(@PathVariable("clientId") UUID clientId) {
        return cs.getClient(clientId);
    }

    @PostMapping
    public void registerClient(@RequestBody ClientRegistrationRequest cr) {
        cs.addClient(cr);
    }

    @DeleteMapping("{clientId}")
    public void deleteClient(@PathVariable("clientId") UUID id) {
        cs.deleteClientById(id);
    }

    @PutMapping("{clientId}")
    public void updateClient(
            @PathVariable("clientId") UUID id,
            @RequestBody ClientUpdateRequest request) {

        cs.updateClient(id, request);
    }
}
