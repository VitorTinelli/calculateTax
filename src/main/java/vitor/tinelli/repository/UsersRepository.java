package vitor.tinelli.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import vitor.tinelli.domain.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {

  Optional<Users> findByCpf(Long cpf);

  UserDetails findByUsername(String username);
}
