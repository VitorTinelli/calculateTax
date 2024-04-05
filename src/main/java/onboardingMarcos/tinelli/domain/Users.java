package onboardingMarcos.tinelli.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Users {

  @Id
  private UUID id;

  private String name;

  private long cpf;

  private String password;

  private String authorities;

  public Users(UUID id, String name, long cpf, String password, String authorities) {
    this.id = id;
    this.name = name;
    this.cpf = cpf;
    this.password = password;
    this.authorities = authorities;
  }

  public Users() {
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


  public String getAuthorities() {
    return authorities;
  }

  public void setAuthorities(String authorities) {
    this.authorities = authorities;
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
        password, users.password) && Objects.equals(authorities, users.authorities);
  }

}
