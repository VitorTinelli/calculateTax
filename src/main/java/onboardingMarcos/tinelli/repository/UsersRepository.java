package onboardingMarcos.tinelli.repository;

import onboardingMarcos.tinelli.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

}
