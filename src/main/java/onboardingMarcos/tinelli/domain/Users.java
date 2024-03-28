package onboardingMarcos.tinelli.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Users {

  @Id
  private long id;

  private String name;

  private long cpf;

  private String password;

  private String userType;

  public Users(long id, String name, long cpf, String password, String userType) {
    this.id = id;
    this.name = name;
    this.cpf = cpf;
    this.password = password;
    this.userType = userType;
  }

  public Users() {
    
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getCpf() {
    return cpf;
  }

  public void setCpf(long cpf) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Users users = (Users) o;
    return id == users.id && cpf == users.cpf && Objects.equals(name, users.name) && Objects.equals(
        password, users.password) && Objects.equals(userType, users.userType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, cpf, password, userType);
  }

  @Override
  public String toString() {
    return "Users{" + "id=" + id + ", name='" + name + '\'' + ", cpf=" + cpf + ", password='"
        + password + '\'' + ", userType='" + userType + '\'' + '}';
  }
}
