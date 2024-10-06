package br.com.park.parkCheck.repository;

import br.com.park.parkCheck.model.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<AuthEntity, Long> {
    AuthEntity findByUsername(String username);
}
