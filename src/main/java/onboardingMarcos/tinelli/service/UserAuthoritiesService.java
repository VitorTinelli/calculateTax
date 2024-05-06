package onboardingMarcos.tinelli.service;

import java.util.List;
import onboardingMarcos.tinelli.domain.UserAuthorities;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.UserAuthoritiesRepository;
import onboardingMarcos.tinelli.repository.UsersRepository;
import onboardingMarcos.tinelli.util.Verifications;
import org.springframework.stereotype.Service;

@Service
public class UserAuthoritiesService {

  private final UsersRepository usersRepository;
  private final UserAuthoritiesRepository userAuthoritiesRepository;

  public UserAuthoritiesService(UsersRepository usersRepository,
      final UserAuthoritiesRepository userAuthoritiesRepository) {
    this.usersRepository = usersRepository;
    this.userAuthoritiesRepository = userAuthoritiesRepository;
  }

  public List<UserAuthorities> listAll() {
    return userAuthoritiesRepository.findAll();
  }

  public UserAuthorities findByAuthoritiesOrThrowBadRequestException(String authorities) {
    return userAuthoritiesRepository.findById(authorities)
        .orElseThrow(() -> new BadRequestException(
            "User Authorities not Found, Please verify the provided Authorities"));
  }

  public UserAuthorities save(String authorities) {
    Verifications.verificationUserAuthoritiesPOST(authorities);
    if (userAuthoritiesRepository.findById(authorities).isPresent()) {
      throw new BadRequestException("User Authorities already exists");
    }
    userAuthoritiesRepository.save(
        new UserAuthorities(authorities)
    );
    return (new UserAuthorities(authorities));
  }

  public void delete(String authorities) {
    if (usersRepository.findByAuthorities(authorities).isEmpty()) {
      userAuthoritiesRepository.delete(findByAuthoritiesOrThrowBadRequestException(authorities));
    } else {
      throw new BadRequestException(
          "User Authorities is being used by a user, please remove the user first");

    }
  }
}
