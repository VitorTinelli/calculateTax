package onboardingMarcos.tinelli.service;

import java.util.List;
import java.util.Objects;
import onboardingMarcos.tinelli.domain.Users;
import onboardingMarcos.tinelli.repository.UsersRepository;
import onboardingMarcos.tinelli.requests.UserPostRequestBody;
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
        .orElseThrow(() -> new RuntimeException(
            "User not Found, Please verify the provided ID"));
  }

  public Users findByIDOrReturnNull(Long cpf) {
    return usersRepository.findBycpf(cpf)
        .orElse(null);
  }

  public Long lastUserID() {
    List<Users> users = usersRepository.findAll();
    Users lastUser = users.get(users.size() - 1);
    return lastUser.getId();
  }


  public Users save(UserPostRequestBody user) {
    if (findByIDOrReturnNull(user.getCpf()) != null) {
      throw new RuntimeException("CPF already registered");
    }
    if (user.getName() == null || user.getPassword() == null || user.getUser_type() == null
        || user.getName().isBlank() || user.getPassword().isBlank() || user.getUser_type()
        .isBlank()) {
      throw new RuntimeException("You have to fill all fields");
    }
    if (user.getCpf().toString().length() != 11) {
      throw new RuntimeException("CPF don't have 11 digits");
    }
    if (!Objects.equals(user.getUser_type(), "contador") && !Objects.equals(user.getUser_type(),
        "gerente")) {
      throw new RuntimeException("User type must be 'contador' or 'gerente'");
    }

    try {
      return usersRepository.save(
          new Users(
              lastUserID() + 1,
              user.getName(),
              user.getCpf(),
              user.getPassword(),
              user.getUser_type().toLowerCase()
          )
      );
    } catch (Exception e) {
      throw new RuntimeException(
          "Error saving user, please try again later or contact the administrator");
    }
  }
}
