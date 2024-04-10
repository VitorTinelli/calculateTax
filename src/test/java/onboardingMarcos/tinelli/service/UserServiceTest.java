package onboardingMarcos.tinelli.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Users;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.UsersRepository;
import onboardingMarcos.tinelli.requests.UserPostRequestBody;
import onboardingMarcos.tinelli.requests.UserPutRequestBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  Users user;
  UserPostRequestBody userPostRequestBody;
  UserPutRequestBody userPutRequestBody;

  @InjectMocks
  private UserService userService;

  @Mock
  private UsersRepository usersRepository;


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
  void listAll_ReturnAllUsers_WhenSuccessful() {
    when(usersRepository.findAll()).thenReturn(List.of(user));
    List<Users> users = userService.listAll();

    Assertions.assertTrue(userService.listAll().contains(user));
  }

  @Test
  void listAll_ReturnEmptyList_WhenUsersNotFound() {
    when(usersRepository.findAll()).thenReturn(List.of());
    List<Users> users = userService.listAll();

    Assertions.assertTrue(users.isEmpty());
    verify(usersRepository).findAll();
  }

  @Test
  void findById_ReturnUser_WhenSuccessful() {
    when(usersRepository.findById(user.getId())).thenReturn(Optional.of(user));
    Users userFound = userService.findByIdOrThrowBadRequestException(user.getId());

    Assertions.assertEquals(user, userFound);
    verify(usersRepository).findById(user.getId());
  }

  @Test
  void findById_ThrowBadRequestException_WhenUserNotFound() {
    when(usersRepository.findById(user.getId())).thenReturn(Optional.empty());

    Assertions.assertThrows(BadRequestException.class,
        () -> userService.findByIdOrThrowBadRequestException(user.getId()));
    verify(usersRepository).findById(user.getId());
  }

  @Test
  void save_ReturnUser_WhenSuccessful() {
    when(usersRepository.save(any(Users.class))).thenReturn(user);
    when(usersRepository.findBycpf(userPostRequestBody.getCpf())).thenReturn(Optional.empty());
    Users userSaved = userService.save(userPostRequestBody);

    Assertions.assertEquals(user, userSaved);
    verify(usersRepository).save(any(Users.class));
    verifyNoMoreInteractions(usersRepository);
  }

  @Test
  void save_ThrowBadRequestException_WhenCpfAlreadyRegistered() {
    when(usersRepository.findBycpf(userPostRequestBody.getCpf())).thenReturn(Optional.of(user));

    Assertions.assertThrows(BadRequestException.class, () -> userService.save(userPostRequestBody));
    verify(usersRepository).findBycpf(userPostRequestBody.getCpf());
    verify(usersRepository, never()).save(any(Users.class));
  }

  @Test
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
  void save_ThrowBadRequestException_WhenUserTypeIsNotContadorOrGerente() {
    when(usersRepository.findBycpf(userPostRequestBody.getCpf())).thenReturn(Optional.empty());
    userPostRequestBody.setUserType("admin");

    Assertions.assertThrows(BadRequestException.class, () -> userService.save(userPostRequestBody));
    verify(usersRepository, never()).save(any(Users.class));
  }

  @Test
  void save_TrowBadRequestException_WhenCpfNotHave11Digits() {
    userPostRequestBody.setCpf(123456L);

    Assertions.assertThrows(BadRequestException.class, () -> userService.save(userPostRequestBody));
    verify(usersRepository, never()).save(any(Users.class));
  }

  @Test
  void delete_DeletesUser_WhenSuccessful() {
    when(usersRepository.findById(user.getId())).thenReturn(Optional.of(user));

    Assertions.assertDoesNotThrow(() -> userService.delete(user.getId()));
    verify(usersRepository).deleteById(user.getId());
  }

  @Test
  void delete_ThrowBadRequestException_WhenUserNotFound() {
    when(usersRepository.findById(user.getId())).thenReturn(Optional.empty());

    Assertions.assertThrows(BadRequestException.class, () -> userService.delete(user.getId()));
    verify(usersRepository, never()).deleteById(user.getId());
  }

  @Test
  void replace_ReplaceUser_WhenSuccessful() {
    when(usersRepository.findById(userPutRequestBody.getId())).thenReturn(Optional.of(user));

    Assertions.assertDoesNotThrow(() -> userService.replace(userPutRequestBody));
    verify(usersRepository).save(any(Users.class));
  }

  @Test
  void replace_ThrowBadRequestException_WhenUserNotFound() {
    when(usersRepository.findById(userPutRequestBody.getId())).thenReturn(Optional.empty());

    Assertions.assertThrows(BadRequestException.class,
        () -> userService.replace(userPutRequestBody));
    verify(usersRepository, never()).save(any(Users.class));
  }

  @Test
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
  void replace_ThrowBadRequestException_WhenUserTypeIsNotContadorOrGerente() {
    userPutRequestBody.setUserType("admin");

    Assertions.assertThrows(BadRequestException.class,
        () -> userService.replace(userPutRequestBody));
    verify(usersRepository, never()).save(any(Users.class));
  }


}
