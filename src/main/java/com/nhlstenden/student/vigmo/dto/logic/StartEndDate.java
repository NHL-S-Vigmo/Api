package com.nhlstenden.student.vigmo.dto.logic;

// Ik zou denk ik, gezien jullie toch al met reflectie werken, eerder de annotaties @Before en @After maken waarin je de naam van het veld plaatst waar het voor of na moet zijn o.i.d.
//@Before("end")
//LocalDateTime start;
//@After("start")
//LocalDateTime end;
public interface StartEndDate {
    String getStartDate();
    void setStartDate(String startDate);
    String getEndDate();
    void setEndDate(String endDate);
}
