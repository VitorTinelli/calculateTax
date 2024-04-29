package onboardingMarcos.tinelli.repository;

import onboardingMarcos.tinelli.domain.UserAuthorities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthoritiesRepository extends JpaRepository<UserAuthorities, String> {

}
