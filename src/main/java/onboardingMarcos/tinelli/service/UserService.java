package onboardingMarcos.tinelli.service;

import java.util.List;
import onboardingMarcos.tinelli.domain.Users;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.UsersRepository;
import onboardingMarcos.tinelli.requests.UserPostRequestBody;
import onboardingMarcos.tinelli.requests.UserPutRequestBody;
import onboardingMarcos.tinelli.util.Verifications;
import org.springframework.stereotype.Service;


@Service
public class UserService {

  private final UsersRepository usersRepository;

  public UserService(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;

  }

  public List<Users> listAll() {
    return usersRepository.findAll();
  }

  public Users findByIdOrThrowBadRequestException(Long id) {
    return usersRepository.findById(id)
        .orElseThrow(() -> new BadRequestException(
            "User not Found, Please verify the provided ID"));
  }

  public Users findByCPForReturnNull(Long cpf) {
    return usersRepository.findBycpf(cpf)
        .orElse(null);
  }

  public Long lastUserID() {
    List<Users> users = usersRepository.findAll();
    Users lastUser = users.get(users.size() - 1);
    return lastUser.getId();
  }


  public Users save(UserPostRequestBody user) {
    if (findByCPForReturnNull(user.getCpf()) != null) {
      throw new BadRequestException("CPF already registered");
    }
    Verifications.verificationUserPOST(user);
    try {
      return usersRepository.save(
          new Users(
              lastUserID() + 1,
              user.getName(),
              user.getCpf(),
              user.getPassword(),
              user.getUserType().toLowerCase()
          )
      );
    } catch (Exception e) {
      throw new BadRequestException(
          "Error saving user, please try again later or contact the administrator");
    }
  }

  public void delete(Long id) {
    findByIdOrThrowBadRequestException(id);
    usersRepository.deleteById(id);
  }

  public void replace(UserPutRequestBody userPutRequestBody) {
    Verifications.verificationUserPUT(userPutRequestBody);
    Users savedUser = findByIdOrThrowBadRequestException(userPutRequestBody.getId());
    if (findByCPForReturnNull(userPutRequestBody.getCpf()) != null
        && savedUser.getId() != findByCPForReturnNull(userPutRequestBody.getCpf()).getId()) {
      throw new BadRequestException("CPF already registered");
    }
    try {
      usersRepository.save(
          new Users(
              savedUser.getId(),
              userPutRequestBody.getName(),
              userPutRequestBody.getCpf(),
              userPutRequestBody.getPassword(),
              userPutRequestBody.getUserType().toLowerCase()
          )
      );
    } catch (Exception e) {
      throw new BadRequestException(
          "Error updating user, please try again later or contact the administrator");
    }
  }
}
