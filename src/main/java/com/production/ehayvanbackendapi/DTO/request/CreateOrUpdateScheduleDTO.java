package com.production.ehayvanbackendapi.DTO.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.production.ehayvanbackendapi.DTO.ScheduleDTO;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

public class CreateOrUpdateScheduleDTO {

    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate beginningDate;
    private Integer doseFrequency;
    private Integer doseCount;

    public CreateOrUpdateScheduleDTO() {

    }

    public CreateOrUpdateScheduleDTO(ScheduleDTO scheduleDTO) {
        this.beginningDate = scheduleDTO.getBeginningDate();
        this.doseFrequency = scheduleDTO.getDoseFrequency();
        this.doseCount = scheduleDTO.getDoseCount();
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
