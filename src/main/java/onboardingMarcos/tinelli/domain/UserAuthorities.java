package onboardingMarcos.tinelli.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserAuthorities {

  @Id
  String authorities;

  public UserAuthorities(String authorities) {
    this.authorities = authorities;
  }

  public UserAuthorities() {
  }

  public String getAuthorities() {
    return authorities;
  }

  public void setAuthorities(String authorities) {
    this.authorities = authorities;
  }


}
