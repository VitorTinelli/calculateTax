package onboardingMarcos.tinelli.requests;

import java.time.LocalDate;

public class DatePeriodRequestBody {
  private LocalDate startDate;
  private LocalDate endDate;

  public DatePeriodRequestBody(LocalDate startDate, LocalDate endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public DatePeriodRequestBody() {
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }
}
