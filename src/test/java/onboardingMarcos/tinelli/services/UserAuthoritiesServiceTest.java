package onboardingMarcos.tinelli.services;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.UserAuthorities;
import onboardingMarcos.tinelli.domain.Users;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.UserAuthoritiesRepository;
import onboardingMarcos.tinelli.repository.UsersRepository;
import onboardingMarcos.tinelli.service.UserAuthoritiesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserAuthoritiesServiceTest {

  Users user;
  UserAuthorities userAuthorities;

  @InjectMocks
  private UserAuthoritiesService userAuthoritiesService;

  @Mock
  private UserAuthoritiesRepository userAuthoritiesRepository;

  @Mock
  private UsersRepository usersRepository;

  @BeforeEach
  void setUp() {
    userAuthorities = new UserAuthorities("gerente");
    user = new Users(
        UUID.randomUUID(),
        "Marcos",
        12345678910L,
        "12345678910",
        "123456",
        "contador"
    );
  }

  @Test
  @DisplayName("List all user authorities")
  void listAll_ReturnsListOfUserAuthorities_WhenSuccessful() {
    when(userAuthoritiesRepository.findAll()).thenReturn(List.of(userAuthorities));

    Assertions.assertTrue(userAuthoritiesService.listAll().contains(userAuthorities));
    verify(userAuthoritiesRepository).findAll();
  }

  @Test
  @DisplayName("Find by userAuthorities throws BadRequest exception when user authorities not found")
  void findByUserAuthorities_ThrowsBadRequestException_WhenUserAuthoritiesNotFound() {
    when(userAuthoritiesRepository.findById(userAuthorities.getAuthorities())).thenReturn(
        Optional.empty());

    Assertions.assertThrows(
        BadRequestException.class,
        () -> userAuthoritiesService.findByAuthoritiesOrThrowBadRequestException(
            userAuthorities.getAuthorities()));
  }

  @Test
  @DisplayName("Save successfully user authorities")
  void save_savesUserAuthorities_WhenSuccessful() {
    when(userAuthoritiesRepository.save(any())).thenReturn(userAuthorities);
    UserAuthorities userAuthoritiesSaved = userAuthoritiesService.save("gerente");
    Assertions.assertEquals(userAuthorities.getAuthorities(),
        userAuthoritiesSaved.getAuthorities());
    verify(userAuthoritiesRepository).save(any());
  }

  @Test
  @DisplayName("Save throw BadRequest exception when user authorities already exists")
  void save_ThrowsException_WhenUserAuthoritiesAlreadyExists() {
    when(userAuthoritiesRepository.findById(userAuthorities.getAuthorities())).thenReturn(
        Optional.of(userAuthorities));

    Assertions.assertThrows(
        BadRequestException.class,
        () -> userAuthoritiesService.save(userAuthorities.getAuthorities()));
  }

  @Test
  @DisplayName("Delete one selected user authorities")
  void delete_DeletesUser_WhenSuccessful() {
    when(userAuthoritiesRepository.findById(userAuthorities.getAuthorities())).thenReturn(
        Optional.of(userAuthorities));

    Assertions.assertDoesNotThrow(
        () -> userAuthoritiesService.delete(userAuthorities.getAuthorities()));
  }

  @Test
  @DisplayName("Delete throw BadRequest exception when user authorities is being used by a user")
  void delete_ThrowsBadRequestException_WhenUserAuthoritiesIsBeingUsedByAUser() {
    when(usersRepository.findByAuthorities(userAuthorities.getAuthorities())).thenReturn(
        List.of(user));

    Assertions.assertThrows(
        BadRequestException.class,
        () -> userAuthoritiesService.delete(userAuthorities.getAuthorities()));
  }
}
