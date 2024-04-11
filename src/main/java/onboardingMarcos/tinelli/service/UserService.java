package onboardingMarcos.tinelli.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Users;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.UsersRepository;
import onboardingMarcos.tinelli.requests.UserPostRequestBody;
import onboardingMarcos.tinelli.requests.UserPutRequestBody;
import onboardingMarcos.tinelli.util.Verifications;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

  private final UsersRepository usersRepository;

  public UserService(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;

  }

  public List<Users> listAll() {
    return usersRepository.findAll();
  }

  public Users findByIdOrThrowBadRequestException(UUID id) {
    return usersRepository.findById(id)
        .orElseThrow(() -> new BadRequestException(
            "User not Found, Please verify the provided ID"));
  }

  public Users findByCPForReturnNull(Long cpf) {
    return usersRepository.findBycpf(cpf)
        .orElse(null);
  }

  public Users save(UserPostRequestBody user) {
    try {
      if (findByCPForReturnNull(user.getCpf()) != null) {
        throw new BadRequestException("CPF already registered");
      }
      Verifications.verificationUserPOST(user);
      return usersRepository.save(
          new Users(
              UUID.randomUUID(),
              user.getName(),
              user.getCpf(),
              user.getUsername(),
              new BCryptPasswordEncoder().encode(user.getPassword()),
              user.getUserType().toLowerCase()
          )
      );
    } catch (Exception e) {
      throw new BadRequestException(
          "Error saving user, please try again later or contact the administrator");
    }
  }

  public void delete(UUID id) {
    findByIdOrThrowBadRequestException(id);
    try {
      usersRepository.deleteById(id);
    } catch (Exception exception) {
      throw new BadRequestException("User not found, please verify the provided ID");
    }
  }

  public void replace(UserPutRequestBody userPutRequestBody) {
    try {
      Verifications.verificationUserPUT(userPutRequestBody);
      Users savedUser = findByIdOrThrowBadRequestException(userPutRequestBody.getId());
      if (findByCPForReturnNull(userPutRequestBody.getCpf()) != null
          && savedUser.getId() != findByCPForReturnNull(userPutRequestBody.getCpf()).getId()) {
        throw new BadRequestException("CPF already registered");
      }
      usersRepository.save(
          new Users(
              savedUser.getId(),
              userPutRequestBody.getName(),
              userPutRequestBody.getCpf(),
              savedUser.getUsername(),
              new BCryptPasswordEncoder().encode(userPutRequestBody.getPassword()),
              userPutRequestBody.getUserType().toLowerCase()
          )
      );
    } catch (Exception exception) {
      throw new BadRequestException(
          "Error updating user, please try again later or contact the administrator");
    }
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return Optional.ofNullable(usersRepository.findByUsername(username))
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
