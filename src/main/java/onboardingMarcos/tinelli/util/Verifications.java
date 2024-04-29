package onboardingMarcos.tinelli.util;

import java.time.LocalDate;
import java.util.Objects;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.requests.*;


public class Verifications {
  
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
    if (taxes.getAliquot() == 0.0D || taxes.getName() == null) {
      throw new BadRequestException("You have to fill all fields");
    }
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
    if (nfe.getDate().isAfter(LocalDate.now())) {
      throw new BadRequestException("Date must be before or today");
    }
    if (nfe.getValue() < 0) {
      throw new BadRequestException("Value must be greater than 0");
    }
    if (nfe.getNumber().toString().isBlank()) {
      throw new BadRequestException("You have to fill all fields");
    }
  }

  public static void verificationNFEPUT(NfePutRequestBody nfe) {
    if (nfe.getNumber().toString().isBlank()) {
      throw new BadRequestException("You have to fill all fields");
    }
    if (nfe.getDate().isAfter(LocalDate.now())) {
      throw new BadRequestException("Date must be before or today");
    }
    if (nfe.getValue() < 0) {
      throw new BadRequestException("Value must be greater than 0");
    }
    if (nfe.getId() == null) {
      throw new BadRequestException("You have to inform the NFE ID");
    }

  }
}