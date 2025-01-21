package ma.tr.docnearme.repository;

import ma.tr.docnearme.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String username);
    boolean existsByEmail(String email);
}
