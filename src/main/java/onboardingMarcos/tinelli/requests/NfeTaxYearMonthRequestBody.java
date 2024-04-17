package onboardingMarcos.tinelli.requests;

public class NfeTaxYearMonthRequestBody {

  private String month;
  private Long year;

  public NfeTaxYearMonthRequestBody(String month, Long year) {
    this.month = month;
    this.year = year;
  }

  public NfeTaxYearMonthRequestBody() {
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public Long getYear() {
    return year;
  }

  public void setYear(Long year) {
    this.year = year;
  }
}
