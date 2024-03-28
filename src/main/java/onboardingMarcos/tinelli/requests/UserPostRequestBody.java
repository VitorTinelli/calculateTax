package onboardingMarcos.tinelli.requests;

import java.util.Objects;

public class UserPostRequestBody {

  private String name;
  private Long cpf;
  private String password;
  private String user_type;

  public UserPostRequestBody(String name, Long cpf, String password, String user_type) {
    this.name = name;
    this.cpf = cpf;
    this.password = password;
    this.user_type = user_type;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUser_type() {
    return user_type;
  }

  public void setUser_type(String user_type) {
    this.user_type = user_type;
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
        && Objects.equals(password, that.password) && Objects.equals(user_type,
        that.user_type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, cpf, password, user_type);
  }

  @Override
  public String toString() {
    return "UserPostRequestBody{" +
        "name='" + name + '\'' +
        ", cpf=" + cpf +
        ", password='" + password + '\'' +
        ", user_type='" + user_type + '\'' +
        '}';
  }
}
