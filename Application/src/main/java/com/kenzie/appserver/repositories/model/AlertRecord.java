package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;


import java.time.LocalDateTime;
import java.util.Objects;

@DynamoDBTable(tableName = "Alerts")
public class AlertRecord {

    // Emily S. 12/21 - Alert Record for saving individual alerts in a table
    private String alertId;
    private String medicationName;
    private String medicationId;
    private String dosage;
    private LocalDateTime alertTime;

    public AlertRecord(String alertId, String medicationName, String medicationId, String dosage, LocalDateTime alertTime) {
        this.alertId = alertId;
        this.medicationName = medicationName;
        this.medicationId = medicationId;
        this.dosage = dosage;
        this.alertTime = alertTime;
    }

    @DynamoDBHashKey(attributeName = "alertId")
    public String getAlertId() {
        return alertId;
    }

    @DynamoDBAttribute(attributeName = "medicationName")
    public String getMedicationName() {
        return medicationName;
    }

    @DynamoDBAttribute(attributeName = "medicationId")
    public String getMedicationId() {
        return medicationId;
    }

    @DynamoDBAttribute(attributeName = "dosage")
    public String getDosage() {
        return dosage;
    }

    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    @DynamoDBAttribute(attributeName = "alertTime")
    public LocalDateTime getAlertTime() {
        return alertTime;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public void setMedicationId(String medicationId) {
        this.medicationId = medicationId;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setAlertTime(LocalDateTime alertTime) {
        this.alertTime = alertTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AlertRecord that = (AlertRecord) o;
        return Objects.equals(alertId, that.alertId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alertId);
    }
}
