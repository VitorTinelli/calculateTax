package onboardingMarcos.tinelli.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsersRepositoryTest {

  Users user;
  UUID id;

  @Autowired
  private UsersRepository usersRepository;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    user = new Users(
        id,
        "Marcos",
        12345678910L,
        "12345678910",
        "123456",
        "contador");
  }

  @Test
  @DisplayName("findById returns all registered Users when successful")
  void findById_ReturnUsers_WhenSuccessful() {
    usersRepository.save(user);
    Users foundUsers = usersRepository.findById(id).get();
    assertThat(foundUsers).isNotNull();
    assertThat(foundUsers.getName()).isEqualTo(user.getName());
    assertThat(foundUsers.getAuthorities()).isEqualTo(user.getAuthorities());
  }

  @Test
  @DisplayName("findByCpf returns all registered Users when successful")
  void findByCpf_ReturnUsers_WhenSuccessful() {
    usersRepository.save(user);
    Users foundUsers = usersRepository.findBycpf(user.getCpf()).get();
    assertThat(foundUsers).isNotNull();
    assertThat(foundUsers.getName()).isEqualTo(user.getName());
    assertThat(foundUsers.getAuthorities()).isEqualTo(user.getAuthorities());
  }

  @Test
  @DisplayName("save persists Users when successful")
  void save_persists_whenSuccessful() {
    Users savedUsers = usersRepository.save(user);
    assertThat(usersRepository.findById(id)).isNotNull();
    assertThat(savedUsers).isNotNull();
    assertThat(savedUsers.getId()).isNotNull();
  }

  @Test
  @DisplayName("delete removes Users when successful")
  void delete_removesUsers_WhenSuccessful() {
    usersRepository.save(user);
    assertThat(usersRepository.findById(id)).isNotNull();
    usersRepository.delete(user);
    assertThat(usersRepository.findById(id)).isEmpty();
  }

  @Test
  @DisplayName("put updates Users when successful")
  void put_updatesUsers_WhenSuccessful() {
    usersRepository.save(user);
    Users newUser = new Users(
        id,
        "Gilberto",
        12345678920L,
        "12345678920L",
        "123",
        "gerente");
    usersRepository.save(newUser);
    Users foundUsers = usersRepository.findById(id).get();
    assertThat(foundUsers).isNotNull();
    assertThat(foundUsers.getName()).isEqualTo(newUser.getName());
    assertThat(foundUsers.getPassword()).isEqualTo(newUser.getPassword());
    assertThat(foundUsers.getAuthorities()).isEqualTo(newUser.getAuthorities());
  }
}
