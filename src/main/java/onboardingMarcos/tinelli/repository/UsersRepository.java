package onboardingMarcos.tinelli.repository;

import java.util.Optional;
import onboardingMarcos.tinelli.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

  Optional<Users> findBycpf(Long cpf);
}
