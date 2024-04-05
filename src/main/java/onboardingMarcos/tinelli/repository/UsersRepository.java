package onboardingMarcos.tinelli.repository;

import java.util.Optional;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, UUID> {
  Optional<Users> findBycpf(Long cpf);
}
