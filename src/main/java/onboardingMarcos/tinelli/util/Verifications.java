package onboardingMarcos.tinelli.util;

import java.time.LocalDate;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.requests.*;


public class Verifications {

  private static final String FIELDS_MESSAGE = "You have to fill all fields";

  private Verifications() {
  }

  public static void verificationUserPOST(UserPostRequestBody user) {
    if (user.getName() == null || user.getPassword() == null || user.getUserType() == null
        || user.getName().isBlank() || user.getPassword().isBlank() || user.getUserType()
        .isBlank()) {
      throw new BadRequestException(FIELDS_MESSAGE);
    }
    if (user.getCpf().toString().length() != 11) {
      throw new BadRequestException("CPF don't have 11 digits");
    }
  }

  public static void verificationUserPUT(UserPutRequestBody user) {
    if (user.getName() == null || user.getPassword() == null || user.getUserType() == null
        || user.getName().isBlank() || user.getPassword().isBlank() || user.getUserType()
        .isBlank()) {
      throw new BadRequestException(FIELDS_MESSAGE);
    }
    if (user.getCpf().toString().length() != 11) {
      throw new BadRequestException("CPF don't have 11 digits");
    }
  }

  public static void verificationTaxesPOST(TaxesPostRequestBody taxes) {
    if (taxes.getAliquot() == 0.0D || taxes.getName() == null) {
      throw new BadRequestException(FIELDS_MESSAGE);
    }
    if (taxes.getName().isBlank()) {
      throw new BadRequestException(FIELDS_MESSAGE);
    }
    if (taxes.getAliquot() < 0) {
      throw new BadRequestException("Aliquot must be greater than 0");
    }
  }

  public static void verificationTaxesPUT(TaxesPutRequestBody taxes) {
    if (taxes.getName().isBlank()) {
      throw new BadRequestException(FIELDS_MESSAGE);
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
      throw new BadRequestException(FIELDS_MESSAGE);
    }
  }

  public static void verificationNFEPUT(NfePutRequestBody nfe) {
    if (nfe.getNumber().toString().isBlank()) {
      throw new BadRequestException(FIELDS_MESSAGE);
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

  public static void verificationUserAuthoritiesPOST(String authorities) {
    if (authorities.isBlank()) {
      throw new BadRequestException(FIELDS_MESSAGE);
    }
  }
}