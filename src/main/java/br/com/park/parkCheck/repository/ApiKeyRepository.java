package br.com.park.parkCheck.repository;

import br.com.park.parkCheck.model.ApiKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ApiKeyRepository extends JpaRepository<ApiKeyEntity, Long> {
    ApiKeyEntity findByKey(String apiKey);
    @Query("SELECT ap FROM ApiKeyEntity ap WHERE CAST(ap.validUntil AS TIMESTAMP) < :now")
    List<ApiKeyEntity> findByValidUntilBefore(LocalDateTime now);
}
