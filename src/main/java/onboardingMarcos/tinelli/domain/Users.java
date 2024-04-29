package onboardingMarcos.tinelli.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Users implements UserDetails {

  @Id
  private UUID id;

  private String name;

  private String username;

  private Long cpf;

  private String password;

  private String authorities;

  public Users(UUID id, String name, long cpf, String username, String password,
      String authorities) {
    this.id = id;
    this.name = name;
    this.username = username;
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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(authorities.toLowerCase());
    return Arrays.asList(authority);
  }

  public void setAuthorities(String authorities) {
    this.authorities = authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
