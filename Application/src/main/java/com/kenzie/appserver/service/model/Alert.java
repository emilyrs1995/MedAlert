package com.kenzie.appserver.service.model;

import java.time.LocalDateTime;

public class Alert {

    private String alertId;
    private String medicationName;
    private String medicationId;
    private String dosage;
    private LocalDateTime alertTime;

    public Alert() {
    }

    public Alert(String alertId, String medicationName, String medicationId, String dosage, LocalDateTime alertTime) {
        this.alertId = alertId;
        this.medicationName = medicationName;
        this.medicationId = medicationId;
        this.dosage = dosage;
        this.alertTime = alertTime;
    }

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
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

    public LocalDateTime getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(LocalDateTime alertTime) {
        this.alertTime = alertTime;
    }
}
