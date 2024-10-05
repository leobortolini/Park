package br.com.park.authentication.repository;

import br.com.park.authentication.model.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<AuthEntity, Long> {
    AuthEntity findByUsername(String username);
}
