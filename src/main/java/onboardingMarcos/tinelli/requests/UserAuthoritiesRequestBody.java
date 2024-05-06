package onboardingMarcos.tinelli.requests;

public class UserAuthoritiesRequestBody {

  private String ActualAuthorities;
  private String newAuthorities;

  public UserAuthoritiesRequestBody(String actualAuthorities, String newAuthorities) {
    ActualAuthorities = actualAuthorities;
    this.newAuthorities = newAuthorities;
  }

  public UserAuthoritiesRequestBody() {
  }

  public String getActualAuthorities() {
    return ActualAuthorities;
  }

  public void setActualAuthorities(String actualAuthorities) {
    ActualAuthorities = actualAuthorities;
  }

  public String getNewAuthorities() {
    return newAuthorities;
  }

  public void setNewAuthorities(String newAuthorities) {
    this.newAuthorities = newAuthorities;
  }
}
