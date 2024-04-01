package onboardingMarcos.tinelli.exceptions;


import lombok.experimental.SuperBuilder;

@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails {

  private String fields;
  private String fieldsMessage;

  public String getFields() {
    return fields;
  }

  public String getFieldsMessage() {
    return fieldsMessage;
  }
}
