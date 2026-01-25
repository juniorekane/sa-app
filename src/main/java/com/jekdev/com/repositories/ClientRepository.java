package com.jekdev.com.repositories;

import com.jekdev.com.entities.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

  Optional<Client> findByEmail(String email);
}
