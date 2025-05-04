package org.primetmu.primetmu_api.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientUserDetailsService implements UserDetailsService {

    private final ClientDao clientDao;

    public ClientUserDetailsService(@Qualifier("clientJpa") ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return clientDao.selectClientByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Username " + username + " not found"));
    }

}
