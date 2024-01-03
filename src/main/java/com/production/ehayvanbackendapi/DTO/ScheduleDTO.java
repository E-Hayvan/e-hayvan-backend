package com.production.ehayvanbackendapi.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ScheduleDTO {
    private Integer scheduleID;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate beginningDate;
    private Integer doseFrequency;
    private Integer doseCount;
    public ScheduleDTO(){

    }
    public ScheduleDTO(Integer scheduleID, List<MedicationDTO> medications,
                       LocalDate beginningDate, Integer doseFrequency, Integer doseCount) {
        this.scheduleID = scheduleID;
        this.beginningDate = beginningDate;
        this.doseFrequency = doseFrequency;
        this.doseCount = doseCount;
    }

    public Integer getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(Integer scheduleID) {
        this.scheduleID = scheduleID;
    }

    public LocalDate getBeginningDate() {
        return beginningDate;
    }

    public void setBeginningDate(LocalDate beginningDate) {
        this.beginningDate = beginningDate;
    }

    public Integer getDoseFrequency() {
        return doseFrequency;
    }

    public void setDoseFrequency(Integer doseFrequency) {
        this.doseFrequency = doseFrequency;
    }

    public Integer getDoseCount() {
        return doseCount;
    }

    public void setDoseCount(Integer doseCount) {
        this.doseCount = doseCount;
    }

}

