package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "Alerts")
public class AlertRecord {

    private String medicationName;
    private String alertId;
    private String dosage;
    private String alertTime;
    private List<DayOfWeek> alertDays;

    @DynamoDBHashKey(attributeName = "AlertId")
    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    @DynamoDBAttribute(attributeName = "Name")
    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    @DynamoDBAttribute(attributeName = "Dosage")
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

    @DynamoDBTypeConverted(converter = DayOfWeekConverter.class)
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
