package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "Medication")
public class MedicationRecord {

    private String name;
    private String id;
    private String timeOfDay;
    private String dosage;
    private String alertTime;
    private List<String> alertDays;

    @DynamoDBHashKey(attributeName = "Name")
    public String getName() {
        return name;
    }

    @DynamoDBAttribute(attributeName = "Id")
    public String getId() {
        return id;
    }

    @DynamoDBAttribute(attributeName = "TimeOfDay")
    public String getTimeOfDay() {
        return timeOfDay;
    }

    @DynamoDBAttribute(attributeName = "Dosage")
    public String getDosage() {
        return dosage;
    }

    @DynamoDBAttribute(attributeName = "AlertTime")
    public String getAlertTime() {
        return alertTime;
    }

    @DynamoDBAttribute(attributeName = "AlertDays")
    public List<String> getAlertDays() {
        return alertDays;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
    }

    public void setAlertDays(List<String> alertDays) {
        this.alertDays = alertDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MedicationRecord that = (MedicationRecord) o;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return  Objects.hash(name, id);
    }

}
