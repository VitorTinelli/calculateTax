package onboardingMarcos.tinelli.requests;

import java.time.LocalDate;

public class DateGapRequestBody {
  private LocalDate startDate;
  private LocalDate endDate;

  public DateGapRequestBody(LocalDate startDate, LocalDate endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public DateGapRequestBody() {
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
