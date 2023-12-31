package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;


import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@DynamoDBTable(tableName = "Alerts")
public class AlertRecord {

    // Emily S. 12/21 - Alert Record for saving individual alerts in a table
    private String medicationName;
    private String alertId;
    private String dosage;
    private String alertTime;
    private List<DayOfWeek> alertDays;
    @DynamoDBAttribute(attributeName = "Name")
    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }
    @DynamoDBHashKey(attributeName = "AlertId")
    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }
    @DynamoDBHashKey(attributeName = "Dosage")
    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
    @DynamoDBAttribute(attributeName = "AlertTime")
    public String getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
    }
    @DynamoDBAttribute(attributeName = "AlertDays")
    public List<DayOfWeek> getAlertDays() {
        return alertDays;
    }

    public void setAlertDays(List<DayOfWeek> alertDays) {
        this.alertDays = alertDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlertRecord that = (AlertRecord) o;
        return Objects.equals(alertId, that.alertId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alertId);
    }
}
