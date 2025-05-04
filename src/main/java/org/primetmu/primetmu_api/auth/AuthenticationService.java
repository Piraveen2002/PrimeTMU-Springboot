package org.primetmu.primetmu_api.auth;

import org.primetmu.primetmu_api.client.Client;
import org.primetmu.primetmu_api.client.ClientDTO;
import org.primetmu.primetmu_api.client.ClientDTOMapper;
import org.primetmu.primetmu_api.jwt.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final ClientDTOMapper clientDTOMapper;
    private final JWTUtil jwtUtil;

    public AuthenticationService(AuthenticationManager authenticationManager,
            ClientDTOMapper clientDTOMapper, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.clientDTOMapper = clientDTOMapper;
        this.jwtUtil = jwtUtil;
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        Client principal = (Client) authentication.getPrincipal();
        ClientDTO clientDTO = clientDTOMapper.apply(principal);
        String token = jwtUtil.issueToken(clientDTO.username(), clientDTO.roles());
        return new AuthenticationResponse(token, clientDTO);
    }

}
