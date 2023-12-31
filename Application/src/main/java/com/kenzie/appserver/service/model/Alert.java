package com.kenzie.appserver.service.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Alert {
    private String medicationName;
    private String medicationId;
    private String dosage;
    private String alertTime;
    private List<DayOfWeek> alertDays;

    public Alert() {
    }

    public Alert(String medicationName, String medicationId, String dosage, String alertTime, List<String> alertDays) {
        this.medicationName = medicationName;
        this.medicationId = medicationId;
        this.dosage = dosage;
        this.alertTime = alertTime;;
        this.alertDays = convertDays(alertDays);
    }
    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getMedicationId() {
        return medicationId;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setMedicationId(String medicationId) {
        this.medicationId = medicationId;
    }

    public String getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
    }

    public List<DayOfWeek> getAlertDays() {
        return alertDays;
    }

    public void setAlertDays(List<DayOfWeek> alertDays) {
        this.alertDays = alertDays;
    }

    private List<DayOfWeek> convertDays(List<String> days){
        List<DayOfWeek> newDays = new ArrayList<>();

        for (String day: days){
            switch (day.toLowerCase()){
                case "mon": newDays.add(DayOfWeek.MONDAY);
                case "tues": newDays.add(DayOfWeek.TUESDAY);
                case "wed": newDays.add(DayOfWeek.WEDNESDAY);
                case "thurs": newDays.add(DayOfWeek.THURSDAY);
                case "fri": newDays.add(DayOfWeek.FRIDAY);
                case "sat": newDays.add(DayOfWeek.SATURDAY);
                case "sun": newDays.add(DayOfWeek.SUNDAY);
            }
        }
        Collections.sort(newDays);
        return newDays;
    }
}
