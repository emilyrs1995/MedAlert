package com.kenzie.appserver.service.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Medication {
    private final String name; // name of medication, partition key
    private final String id; // optional sort key
    private final String timeOfDay; // Morning, Afternoon, or Evening
    private final String dosage; // ex. 1 pill
    private final String alertTime; // ex. 8:00 am
    private final List<String> alertDays; // Days of week for alert to be repeated, ex. Every Monday and Wednesday
    private final Alert alert;

    // Emily S. 12/21 - Saving in case we go back to using LocalDateTime
//    private final List<LocalDateTime> alertDays; // list of alert days/times

    public Medication(String name, String id, String timeOfDay, String dosage, String alertTime, List<String> alertDays) {
        this.name = name;
        this.id = id;
        this.timeOfDay = timeOfDay;
        this.dosage = dosage;
        this.alertTime = alertTime;
        this.alertDays = alertDays;
        this.alert = new Alert(name, id, dosage, alertTime, alertDays);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public String getDosage() {
        return dosage;
    }

    public String getAlertTime() {
        return alertTime;
    }

    public List<String> getAlertDays() {
        return alertDays;
    }

    // Emily S. 12/21 - Saving in case we go back to using LocalDateTime
//    public List<LocalDateTime> getAlertDays() {
//        return alertDays;
//    }
}
