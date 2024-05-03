package onboardingMarcos.tinelli.requests;

import java.util.UUID;

public class UserPutRequestBody {

  private UUID id;
  private String name;
  private Long cpf;
  private String password;
  private String userType;

  public UserPutRequestBody(UUID id, String name, Long cpf, String password,
      String userType) {
    this.id = id;
    this.name = name;
    this.cpf = cpf;
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
