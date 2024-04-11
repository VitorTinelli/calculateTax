package onboardingMarcos.tinelli.domain;

import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UsersTest {

  UUID id;
  @InjectMocks
  private Users users;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
  }

  @Test
  void testGettersAndSetters() {
    Assertions.assertAll(
        () -> {
          users.setId(id);
          Assertions.assertEquals(id, users.getId());
        },
        () -> {
          users.setName("Marcos");
          Assertions.assertEquals("Marcos", users.getName());
        },
        () -> {
          users.setUsername("123812318238");
          Assertions.assertEquals("123812318238", users.getUsername());
        },
        () -> {
          users.setCpf(12345678910L);
          Assertions.assertEquals(12345678910L, users.getCpf());
        },
        () -> {
          users.setPassword("123456");
          Assertions.assertEquals("123456", users.getPassword());
        },
        () -> {
          users.setAuthorities("gerente");
          Assertions.assertDoesNotThrow(() -> users.getAuthorities());
        }

    );
  }
}