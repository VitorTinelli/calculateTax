package onboardingMarcos.tinelli.requests;

public class TaxesPostRequestBody {

  private String name;
  private double aliquot;

  public TaxesPostRequestBody(String name, double aliquot) {
    this.name = name;
    this.aliquot = aliquot;
  }

  public TaxesPostRequestBody() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getAliquot() {
    return aliquot;
  }

  public void setAliquot(Double aliquot) {
    this.aliquot = aliquot;
  }

}
