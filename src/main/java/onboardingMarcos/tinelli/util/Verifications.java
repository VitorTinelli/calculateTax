package onboardingMarcos.tinelli.util;

import java.time.LocalDate;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.NfeRepository;
import onboardingMarcos.tinelli.requests.*;

@Log4j2
public class Verifications {

  private Verifications(NfeRepository nfeRepository) {
  }

  public static void verificationUserPOST(UserPostRequestBody user) {
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

  public static void verificationUserPUT(UserPutRequestBody user) {
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

  public static void verificationTaxesPOST(TaxesPostRequestBody taxes) {
    if (taxes.getName().isBlank()) {
      throw new BadRequestException("You have to fill all fields");
    }
    if (taxes.getAliquot() < 0) {
      throw new BadRequestException("Aliquot must be greater than 0");
    }
  }

  public static void verificationTaxesPUT(TaxesPutRequestBody taxes) {
    if (taxes.getName().isBlank()) {
      throw new BadRequestException("You have to fill all fields");
    }
    if (taxes.getAliquot() < 0) {
      throw new BadRequestException("Aliquot must be greater than 0");
    }
  }

  public static void verificationNFEPOST(NfePostRequestBody nfe) {
    if (nfe.getNumber().toString().isBlank()) {
      throw new BadRequestException("You have to fill all fields");
    }
    if (nfe.getDate().isAfter(LocalDate.now())) {
      throw new BadRequestException("Date must be before or today");
    }
    if (nfe.getValue() < 0) {
      throw new BadRequestException("Value must be greater than 0");
    }
  }

  public static void verificationNFEPUT(NfePutRequestBody nfePutRequestBody) {
    if (nfePutRequestBody.getNumber().toString().isBlank()) {
      throw new BadRequestException("You have to fill all fields");
    }
    if (nfePutRequestBody.getDate().isAfter(LocalDate.now())) {
      throw new BadRequestException("Date must be before or today");
    }
    if (nfePutRequestBody.getValue() < 0) {
      throw new BadRequestException("Value must be greater than 0");
    }
    if (nfePutRequestBody.getId() == null) {
      throw new BadRequestException("You have to fill all fields");
    }

  }
}