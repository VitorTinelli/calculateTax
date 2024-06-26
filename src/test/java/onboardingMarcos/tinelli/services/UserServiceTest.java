package onboardingMarcos.tinelli.services;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Users;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.UsersRepository;
import onboardingMarcos.tinelli.requests.UserAuthoritiesRequestBody;
import onboardingMarcos.tinelli.requests.UserPostRequestBody;
import onboardingMarcos.tinelli.requests.UserPutRequestBody;
import onboardingMarcos.tinelli.service.UserAuthoritiesService;
import onboardingMarcos.tinelli.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  Users user;
  UserPostRequestBody userPostRequestBody;
  UserPutRequestBody userPutRequestBody;
  UserAuthoritiesRequestBody userAuthoritiesRequestBody;

  @InjectMocks
  private UserService userService;

  @Mock
  private UsersRepository usersRepository;

  @Mock
  private UserAuthoritiesService usersAuthoritiesService;

  @BeforeEach
  void setUp() {
    UUID id = UUID.randomUUID();
    user = new Users(
        id,
        "Marcos",
        12345678910L,
        "12345678910",
        "123456",
        "contador"
    );
    userAuthoritiesRequestBody = new UserAuthoritiesRequestBody("gerente", "contador");
    userPostRequestBody = new UserPostRequestBody(
        "Marcos",
        12345678910L,
        "123456",
        "contador"
    );
    userPutRequestBody = new UserPutRequestBody(
        id,
        "Gilberto",
        12345678920L,
        "123",
        "gerente"
    );
  }

  @Test
  @DisplayName("List all returns a list of USERS when successful")
  void listAll_ReturnAllUsers_WhenSuccessful() {
    when(usersRepository.findAll()).thenReturn(List.of(user));
    List<Users> users = userService.listAll();

    Assertions.assertTrue(users.contains(user));
  }

  @Test
  @DisplayName("List all returns an EMPTY list when NO USERS found")
  void listAll_ReturnEmptyList_WhenUsersNotFound() {
    when(usersRepository.findAll()).thenReturn(List.of());
    List<Users> users = userService.listAll();

    Assertions.assertTrue(users.isEmpty());
    verify(usersRepository).findAll();
  }

  @Test
  @DisplayName("Find by id returns a USER when successful")
  void findById_ReturnUser_WhenSuccessful() {
    when(usersRepository.findById(user.getId())).thenReturn(Optional.of(user));
    Users userFound = userService.findByIdOrThrowBadRequestException(user.getId());

    Assertions.assertEquals(user, userFound);
    verify(usersRepository).findById(user.getId());
  }

  @Test
  @DisplayName("Find by id throws BadRequestException when NO USER found")
  void findById_ThrowBadRequestException_WhenUserNotFound() {
    when(usersRepository.findById(user.getId())).thenReturn(Optional.empty());

    Assertions.assertThrows(BadRequestException.class,
        () -> userService.findByIdOrThrowBadRequestException(user.getId()));
    verify(usersRepository).findById(user.getId());
  }

  @Test
  @DisplayName("Find by authorities returns a list of USERS when successful")
  void findByAuthorities_ReturnUsers_WhenSuccessful() {
    when(usersRepository.findByAuthorities("gerente")).thenReturn(List.of(user));
    List<Users> users = userService.findByAuthorities("gerente");

    Assertions.assertTrue(users.contains(user));
    verify(usersRepository).findByAuthorities("gerente");
  }

  @Test
  @DisplayName("Save returns and save a USER when successful")
  void save_ReturnUser_WhenSuccessful() {
    when(usersRepository.save(any(Users.class))).thenReturn(user);
    when(usersRepository.findBycpf(userPostRequestBody.getCpf())).thenReturn(Optional.empty());
    when(usersAuthoritiesService.findByAuthoritiesOrThrowBadRequestException(
        userPostRequestBody.getUserType())).thenReturn(null);
    Users userSaved = userService.save(userPostRequestBody);

    Assertions.assertEquals(user, userSaved);
    verify(usersRepository).save(any(Users.class));
    verifyNoMoreInteractions(usersRepository);
  }

  @Test
  @DisplayName("Save throws BadRequestException when CPF already registered")
  void save_ThrowBadRequestException_WhenCpfAlreadyRegistered() {
    when(usersRepository.findBycpf(userPostRequestBody.getCpf())).thenReturn(Optional.of(user));

    Assertions.assertThrows(BadRequestException.class, () -> userService.save(userPostRequestBody));
    verify(usersRepository).findBycpf(userPostRequestBody.getCpf());
    verify(usersRepository, never()).save(any(Users.class));
  }

  @Test
  @DisplayName("Save throws BadRequestException when any field is blank or null")
  void save_ThrowBadRequestException_WhenAnyFieldIsBlankOrNull() {
    when(usersRepository.findBycpf(userPostRequestBody.getCpf())).thenReturn(Optional.empty());
    Assertions.assertAll(
        () -> {
          userPostRequestBody.setName("  ");
          Assertions.assertThrows(BadRequestException.class,
              () -> userService.save(userPostRequestBody));
        },
        () -> {
          userPostRequestBody.setName("Marcos");
          userPostRequestBody.setPassword("  ");
          Assertions.assertThrows(BadRequestException.class,
              () -> userService.save(userPostRequestBody));
        },
        () -> {
          userPostRequestBody.setPassword("123456");
          userPostRequestBody.setUserType("  ");
          Assertions.assertThrows(BadRequestException.class,
              () -> userService.save(userPostRequestBody));
        },
        () -> {
          userPostRequestBody.setUserType("contador");
          userPostRequestBody.setCpf(0L);
          Assertions.assertThrows(BadRequestException.class,
              () -> userService.save(userPostRequestBody));
        }
    );
  }

  @Test
  @DisplayName("Save throws BadRequestException when CPF not have 11 digits")
  void save_TrowBadRequestException_WhenCpfNotHave11Digits() {
    userPostRequestBody.setCpf(123456L);

    Assertions.assertThrows(BadRequestException.class, () -> userService.save(userPostRequestBody));
    verify(usersRepository, never()).save(any(Users.class));
  }

  @Test
  @DisplayName("Delete deletes a USER when successful")
  void delete_DeletesUser_WhenSuccessful() {
    when(usersRepository.findById(user.getId())).thenReturn(Optional.of(user));

    Assertions.assertDoesNotThrow(() -> userService.delete(user.getId()));
    verify(usersRepository).delete(user);
  }

  @Test
  @DisplayName("Delete throws BadRequestException when NO USER found")
  void delete_ThrowBadRequestException_WhenUserNotFound() {
    when(usersRepository.findById(user.getId())).thenReturn(Optional.empty());

    Assertions.assertThrows(BadRequestException.class, () -> userService.delete(user.getId()));
    verify(usersRepository, never()).deleteById(user.getId());
  }

  @Test
  @DisplayName("Replace replaces a USER when successful")
  void replace_ReplaceUser_WhenSuccessful() {
    when(usersRepository.findById(userPutRequestBody.getId())).thenReturn(Optional.of(user));

    Assertions.assertDoesNotThrow(() -> userService.replace(userPutRequestBody));
    verify(usersRepository).save(any(Users.class));
  }

  @Test
  @DisplayName("Replace throws BadRequestException when NO USER found")
  void replace_ThrowBadRequestException_WhenUserNotFound() {
    when(usersRepository.findById(userPutRequestBody.getId())).thenReturn(Optional.empty());

    Assertions.assertThrows(BadRequestException.class,
        () -> userService.replace(userPutRequestBody));
    verify(usersRepository, never()).save(any(Users.class));
  }

  @Test
  @DisplayName("Replace throws BadRequestException when CPF is already registered")
  void replace_ThrowBadRequestException_WhenNewCpfAlreadyRegistered() {
    Users user2 = new Users(
        UUID.randomUUID(),
        "Gilberto",
        12345678920L,
        "12345678920",
        "123",
        "gerente"
    );
    when(usersRepository.findById(userPutRequestBody.getId())).thenReturn(Optional.of(user));
    when(usersRepository.findBycpf(userPutRequestBody.getCpf())).thenReturn(Optional.of(user2));

    Assertions.assertThrows(BadRequestException.class,
        () -> userService.replace(userPutRequestBody));
    verify(usersRepository, never()).save(any(Users.class));
  }

  @Test
  @DisplayName("Replace throws BadRequestException when any verification fails")
  void replace_ThrowBadRequestException_WhenAnyFieldIsBlankOrNull() {
    Assertions.assertAll(
        () -> {
          userPutRequestBody.setName("  ");
          Assertions.assertThrows(BadRequestException.class,
              () -> userService.replace(userPutRequestBody));
        },
        () -> {
          userPutRequestBody.setName("Gilberto");
          userPutRequestBody.setPassword("  ");
          Assertions.assertThrows(BadRequestException.class,
              () -> userService.replace(userPutRequestBody));
        },
        () -> {
          userPutRequestBody.setPassword("123");
          userPutRequestBody.setUserType("  ");
          Assertions.assertThrows(BadRequestException.class,
              () -> userService.replace(userPutRequestBody));
        },
        () -> {
          userPutRequestBody.setUserType("gerente");
          userPutRequestBody.setCpf(0L);
          Assertions.assertThrows(BadRequestException.class,
              () -> userService.replace(userPutRequestBody));
        }
    );
  }

  @Test
  @DisplayName("Replace throws BadRequestException when user type is different of contador or gerente")
  void replace_ThrowBadRequestException_WhenUserTypeIsNotContadorOrGerente() {
    userPutRequestBody.setUserType("admin");

    Assertions.assertThrows(BadRequestException.class,
        () -> userService.replace(userPutRequestBody));
    verify(usersRepository, never()).save(any(Users.class));
  }

  @Test
  @DisplayName("Load by username returns a USER when successful")
  void loadUserByUsername_ReturnUser_WhenSuccessful() {
    when(usersRepository.findByUsername(user.getUsername())).thenReturn(user);

    Assertions.assertEquals(user, userService.loadUserByUsername(user.getUsername()));
    verify(usersRepository).findByUsername(user.getUsername());
  }

  @Test
  @DisplayName("Load by username throws UsernameNotFoundException when NO USER found")
  void loadUserByUsername_ThrowUsernameNotFoundException_WhenUserNotFound() {
    when(usersRepository.findByUsername(user.getUsername())).thenReturn(null);

    Assertions.assertThrows(UsernameNotFoundException.class,
        () -> userService.loadUserByUsername(user.getUsername()));
    verify(usersRepository).findByUsername(user.getUsername());
  }

  @Test
  @DisplayName("Replace users authorities replaces all USERS authorities when successful")
  void replaceUsersAuthorities_ReplaceUserAuthorities_WhenSuccessful() {
    when(usersAuthoritiesService.findByAuthoritiesOrThrowBadRequestException(
        any(String.class))).thenReturn(null);
    when(usersRepository.findByAuthorities(any()))
        .thenReturn(List.of(user));

    Assertions.assertDoesNotThrow(
        () -> userService.replaceUsersAuthorities(userAuthoritiesRequestBody));
  }

  @Test
  @DisplayName("Replace users authorities throws BadRequestError when authority does not exist")
  void replaceUsersAuthorities_ThrowBadRequestException_WhenAuthorityDoesNotExist() {
    when(usersAuthoritiesService.findByAuthoritiesOrThrowBadRequestException(
        any(String.class))).thenThrow(new BadRequestException("Authority not found."));

    Assertions.assertThrows(BadRequestException.class,
        () -> userService.replaceUsersAuthorities(userAuthoritiesRequestBody));
    verify(usersRepository, never()).save(any(Users.class));
  }

  @Test
  @DisplayName("replace users authorities throws BadRequestError when no user has the authority")
  void replaceUsersAuthorities_ThrowBadExceptionError_WhenNoUserHasTheAuthority() {
    when(usersAuthoritiesService.findByAuthoritiesOrThrowBadRequestException(
        any(String.class))).thenReturn(null);

    Assertions.assertThrows(BadRequestException.class,
        () -> userService.replaceUsersAuthorities(userAuthoritiesRequestBody));
    verify(usersRepository, never()).save(any(Users.class));
  }
}
