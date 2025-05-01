package org.primetmu.primetmu_api.client;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("clientJdbc")
public class ClientJDBCDataAccessService implements ClientDao {
    private final JdbcTemplate jdbcTemplate;
    private final ClientRowMapper clientRowMapper;

    public ClientJDBCDataAccessService(JdbcTemplate jdbcTemplate,
            ClientRowMapper clientRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.clientRowMapper = clientRowMapper;
    }

    @Override
    public List<Client> selectAllClients() {
        var sql = """
                select id, first_name, last_name, username, password
                from client
                LIMIT 1000
                """;
        return jdbcTemplate.query(sql, clientRowMapper);
    }

    @Override
    public Optional<Client> selectClientById(UUID id) {
        var sql = """
                SELECT id, first_name, last_name, username, password
                FROM client
                WHERE id = ?
                LIMIT 1000
                """;
        return jdbcTemplate.query(sql, clientRowMapper, id)
                .stream().findFirst();
    }

    @Override
    public Optional<Client> selectClientByUsername(String username) {
        var sql = """
                SELECT id, first_name, last_name, username, password
                From client
                Where username = ?
                LIMIT 1000
                """;

        return jdbcTemplate.query(sql, clientRowMapper, username)
                .stream().findFirst();
    }

    @Override
    public void insertClient(Client c) {
        var sql = """
                INSERT INTO client(id, first_name, last_name, username, password)
                VALUES (?, ?, ?, ?, ?)
                """;

        int result = jdbcTemplate.update(
                sql,
                c.getId(),
                c.getFirstName(),
                c.getLastName(),
                c.getUsername(),
                c.getPassword());

        System.out.println("insertClient result " + result);
    }

    @Override
    public boolean existsClientByUsername(String username) {
        var sql = """
                SELECT count(id)
                FROM client
                where username = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    public boolean existsClientById(UUID id) {
        var sql = """
                SELECT count(id)
                from client
                where id = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteClientById(UUID id) {
        var sql = """
                DELETE
                FROM client
                WHERE id = ?
                """;

        int result = jdbcTemplate.update(sql, id);
        System.out.println("deleteClientById result = " + result);
    }

    @Override
    public void updateClient(Client c) {
        if (c.getFirstName() != null) {
            String sql = "UPDATE client SET first_name=? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    c.getFirstName(),
                    c.getId());
            System.out.println("update client firstname result = " + result);
        }

        if (c.getLastName() != null) {
            String sql = "UPDATE client SET last_name = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    c.getLastName(),
                    c.getId());

            System.out.println("update client lastname result = " + result);
        }

        if (c.getUsername() != null) {
            String sql = "Update client SET username = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    c.getUsername(),
                    c.getId());

            System.out.println("update client username result = " + result);
        }

    }

}
