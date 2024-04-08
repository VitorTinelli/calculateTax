package onboardingMarcos.tinelli.requests;

import java.util.Objects;

public class UserPostRequestBody {

  private String name;
  private Long cpf;
  private String username;
  private String password;
  private String userType;

  public UserPostRequestBody(String name, Long cpf, String password, String userType) {
    this.name = name;
    this.cpf = cpf;
    this.password = password;
    this.userType = userType;
  }

  public UserPostRequestBody() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getCpf() {
    return cpf;
  }

  public void setCpf(Long cpf) {
    this.cpf = cpf;
  }

  public String getUsername() {
    return cpf.toString();
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserPostRequestBody that = (UserPostRequestBody) o;
    return Objects.equals(name, that.name) && Objects.equals(cpf, that.cpf)
        && Objects.equals(username, that.username) && Objects.equals(password,
        that.password) && Objects.equals(userType, that.userType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, cpf, username, password, userType);
  }
}
