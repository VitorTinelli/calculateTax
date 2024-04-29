package onboardingMarcos.tinelli.repository;


import java.util.Optional;
import onboardingMarcos.tinelli.domain.UserAuthorities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserAuthoritiesRepositoryTest {

  UserAuthorities userAuthorities;

  @Autowired
  private UserAuthoritiesRepository userAuthoritiesRepository;

  @BeforeEach
  void setUp() {
    userAuthorities = new UserAuthorities("gerente");
  }

  @Test
  @DisplayName("findById returns all registered UserAuthorities when successful")
  void findByAuthorities_returnAllRegisteredAuthorities_whenSuccessful() {
    userAuthoritiesRepository.save(userAuthorities);
    Assertions.assertTrue(userAuthoritiesRepository.findById("gerente").isPresent());
  }

  @Test
  @DisplayName("save persists UserAuthorities when successful")
  void save_savesUserAuthorities_whenSuccessful() {
    Assertions.assertTrue(
        userAuthoritiesRepository.save(userAuthorities).getAuthorities().equals("gerente"));
    Assertions.assertFalse(userAuthoritiesRepository.findAll().isEmpty());
  }

  @Test
  @DisplayName("delete removes UserAuthorities when successful")
  void delete_deletesUserAuthorities_whenSuccessful() {
    userAuthoritiesRepository.save(userAuthorities);
    Assertions.assertFalse(userAuthoritiesRepository.findAll().isEmpty());
    userAuthoritiesRepository.delete(userAuthorities);
    Optional<UserAuthorities> auth = userAuthoritiesRepository.findById("gerente");
    Assertions.assertTrue(auth.isEmpty());
  }

}
