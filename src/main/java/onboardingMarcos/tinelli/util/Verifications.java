package onboardingMarcos.tinelli.util;

import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.NfeRepository;
import onboardingMarcos.tinelli.requests.TaxesPostRequestBody;
import onboardingMarcos.tinelli.requests.TaxesPutRequestBody;
import onboardingMarcos.tinelli.requests.UserPostRequestBody;
import onboardingMarcos.tinelli.requests.UserPutRequestBody;

@Log4j2
public class Verifications {

  private Verifications(NfeRepository nfeRepository) {
  }

  public static void VerificationUserPOST(UserPostRequestBody user) {
    if (user.getName() == null || user.getPassword() == null || user.getUserType() == null
        || user.getName().isBlank() || user.getPassword().isBlank() || user.getUserType()
        .isBlank()) {
      throw new BadRequestException("You have to fill all fields");
    }
    if (user.getCpf().toString().length() != 11) {
      throw new BadRequestException("CPF don't have 11 digits");
    }
    if (!Objects.equals(user.getUserType(), "contador") && !Objects.equals(user.getUserType(),
        "gerente")) {
      throw new BadRequestException("User type must be 'contador' or 'gerente'");
    }
  }

  public static void VerificationUserPUT(UserPutRequestBody user) {
    if (user.getName() == null || user.getPassword() == null || user.getUserType() == null
        || user.getName().isBlank() || user.getPassword().isBlank() || user.getUserType()
        .isBlank()) {
      throw new BadRequestException("You have to fill all fields");
    }
    if (user.getCpf().toString().length() != 11) {
      throw new BadRequestException("CPF don't have 11 digits");
    }
    if (!Objects.equals(user.getUserType(), "contador") && !Objects.equals(user.getUserType(),
        "gerente")) {
      throw new BadRequestException("User type must be 'contador' or 'gerente'");
    }
  }

  public static void VerificationTaxesPOST(TaxesPostRequestBody taxes) {
    if (taxes.getName().isBlank()) {
      throw new BadRequestException("You have to fill all fields");
    }
    if (taxes.getAliquot() < 0) {
      throw new BadRequestException("Aliquot must be greater than 0");
    }
  }

  public static void VerificationTaxesPUT(TaxesPutRequestBody taxes) {
    if (taxes.getName().isBlank()) {
      throw new BadRequestException("You have to fill all fields");
    }
    if (taxes.getAliquot() < 0) {
      throw new BadRequestException("Aliquot must be greater than 0");
    }

  }

}
