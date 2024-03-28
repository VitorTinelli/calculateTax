package onboardingMarcos.tinelli.service;

import java.util.List;
import onboardingMarcos.tinelli.domain.Users;
import onboardingMarcos.tinelli.repository.UsersRepository;
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

}
