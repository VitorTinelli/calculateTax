package onboardingMarcos.tinelli.requests;

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

}
