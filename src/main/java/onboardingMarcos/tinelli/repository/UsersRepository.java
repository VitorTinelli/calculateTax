package onboardingMarcos.tinelli.repository;

import java.util.Optional;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsersRepository extends JpaRepository<Users, UUID> {

  Optional<Users> findBycpf(Long cpf);

  UserDetails findByUsername(String username);
}
