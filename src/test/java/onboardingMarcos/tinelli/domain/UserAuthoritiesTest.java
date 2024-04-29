package onboardingMarcos.tinelli.domain;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class UserAuthoritiesTest {

  @InjectMocks
  private UserAuthorities userAuthorities;

  @Test
  void testGetAuthorities() {
    userAuthorities.getAuthorities();
  }

  @Test
  void testSetAuthorities() {
    String authorities = "authorities";
    userAuthorities.setAuthorities(authorities);
  }

  @Test
  void testConstructor() {
    UserAuthorities userAuthorities = new UserAuthorities("authorities");
  }

  @Test
  void testNoArgsContructor() {
    UserAuthorities userAuthorities = new UserAuthorities();
  }

}
