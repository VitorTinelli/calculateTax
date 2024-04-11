package onboardingMarcos.tinelli.requests;

import java.util.UUID;

public class TaxesPutRequestBody {

  private UUID id;
  private String name;
  private Double aliquot;

  public TaxesPutRequestBody(UUID id, String name, Double aliquot) {
    this.id = id;
    this.name = name;
    this.aliquot = aliquot;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getAliquot() {
    return aliquot;
  }

  public void setAliquot(Double aliquot) {
    this.aliquot = aliquot;
  }

}
