package onboardingMarcos.tinelli.requests;

import java.util.Objects;
import java.util.UUID;

public class UserPutRequestBody {

  private UUID id;
  private String name;
  private Long cpf;
  private String username;
  private String password;
  private String userType;

  public UserPutRequestBody(UUID id, String name, Long cpf, String username, String password,
      String userType) {
    this.id = id;
    this.name = name;
    this.cpf = cpf;
    this.username = username;
    this.password = password;
    this.userType = userType;
  }

  public UserPutRequestBody() {

  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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
    return username;
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
    UserPutRequestBody that = (UserPutRequestBody) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name)
        && Objects.equals(cpf, that.cpf) && Objects.equals(username,
        that.username) && Objects.equals(password, that.password)
        && Objects.equals(userType, that.userType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, cpf, username, password, userType);
  }

}
