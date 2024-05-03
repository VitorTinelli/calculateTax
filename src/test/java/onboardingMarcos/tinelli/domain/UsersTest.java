package onboardingMarcos.tinelli.domain;

import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UsersTest {

  UUID id;
  @InjectMocks
  private Users users;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
  }

  @Test
  @DisplayName("Test all getters and setters methods")
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

  @Test
  @DisplayName("Test the constructor method")
  void testConstructor() {
    Assertions.assertDoesNotThrow(
        () -> new Users(
            id,
            "Marcos",
            12345678910L,
            "12345678910",
            "senhasecreta",
            "gerente"
        )
    );
  }
}
