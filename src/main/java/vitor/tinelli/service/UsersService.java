package vitor.tinelli.service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vitor.tinelli.domain.Users;
import vitor.tinelli.exceptions.BadRequestException;
import vitor.tinelli.repository.UsersRepository;
import vitor.tinelli.requests.UsersPostRequestBody;
import vitor.tinelli.requests.UsersPutRequestBody;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {

  private final UsersRepository usersRepository;

  public List<Users> findAll() {
    return usersRepository.findAll();
  }

  public Users findByIdOrThrowBadRequestException(UUID id) {
    return usersRepository.findById(id).orElseThrow(
        () -> new BadRequestException("User not found, please verify the provided ID"));
  }

  @Transactional
  public Users save(UsersPostRequestBody user) {
    verifyIfCpfExistAndAuthoritiesAreValid(user.getAuthorities(), user.getCpf());
    return usersRepository.save(Users.builder()
        .name(user.getName())
        .cpf(user.getCpf())
        .username(user.getCpf().toString())
        .password(new BCryptPasswordEncoder().encode(user.getPassword()))
        .authorities(user.getAuthorities().toLowerCase())
        .build());
  }

  @Transactional
  public void delete(UUID id) {
    usersRepository.deleteById(id);
  }

  @Transactional
  public void replace(UsersPutRequestBody user) {
    verifyIfCpfExistAndAuthoritiesAreValid(user.getAuthorities(), user.getCpf());
    Users savedUser = findByIdOrThrowBadRequestException(user.getId());
    Users userToReplace = Users.builder()
        .id(savedUser.getId())
        .name(user.getName())
        .cpf(user.getCpf())
        .username(user.getCpf().toString())
        .password(new BCryptPasswordEncoder().encode(user.getPassword()))
        .authorities(user.getAuthorities().toLowerCase())
        .build();
    usersRepository.save(userToReplace);
  }


  //Utilities
  public void verifyIfCpfExistAndAuthoritiesAreValid(String authorities, Long cpf){
    if (!Objects.equals(authorities.toLowerCase(), "gerente") && !Objects.equals(
        authorities.toLowerCase(), "contador")) {
      throw new BadRequestException("Authorities cannot be different of 'contador' or 'gerente'.");
    }
    if (usersRepository.findByCpf(cpf).isPresent()) {
      throw new BadRequestException("CPF already registered");
    }
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return Optional.ofNullable(usersRepository.findByUsername(username))
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
