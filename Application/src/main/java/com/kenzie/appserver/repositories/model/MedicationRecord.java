package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;
import java.util.Objects;

/* Emily S (12/15/23)*/
@DynamoDBTable(tableName = "Medication")
public class MedicationRecord {

    private String name; // name of medication, partition key
    private String id; // optional sort key
    private String timeOfDay; // Morning, Afternoon, or Evening
    private String dosage; // ex. 1 pill
    private String alertTime; // ex. 8:00 am
    private List<String> alertDays; // Days of week for alert to be repeated, ex. Every Monday and Wednesday

    @DynamoDBHashKey(attributeName = "Name")
    public String getName() {
        return name;
    }

    @DynamoDBRangeKey(attributeName = "Id")
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
