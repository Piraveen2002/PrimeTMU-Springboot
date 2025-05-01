package org.primetmu.primetmu_api.client;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "client", uniqueConstraints = {
        @UniqueConstraint(name = "user_id_unique", columnNames = "id"),
        @UniqueConstraint(name = "username_unique", columnNames = "username")
})

public class Client {
    @Id
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    public Client(UUID id, String firstname, String lastname, String username, String password) {
        this.id = id;
        this.firstName = firstname;
        this.lastName = lastname;
        this.username = username;
        this.password = password;
    }

    public Client(String firstname, String lastname, String username, String password) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.username = username;
        this.password = password;
    }

    public Client() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean equals(Object c) {
        if (this == c)
            return true;

        if (c == null || getClass() != c.getClass())
            return false;

        Client client = (Client) c;
        return Objects.equals(id, client.id)
                && Objects.equals(username, client.username)
                && Objects.equals(password, client.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }

    @Override
    public String toString() {
        return "Client {" +
                "id=" + id +
                ", firstname='" + firstName + "\'" +
                ", lastname='" + lastName + "\'" +
                ", username='" + username;
    }
}
