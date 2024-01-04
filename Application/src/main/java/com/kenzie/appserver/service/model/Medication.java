package com.kenzie.appserver.service.model;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Medication {
    private final String name; // name of medication, partition key
    private final String id; // optional sort key
    private final String timeOfDay; // Morning, Afternoon, or Evening
    private final String dosage; // ex. 1 pill
    private final String alertTime; // ex. 8:00 am
    private final List<String> alertDays; // Days of week for alert to be repeated, ex. Every Monday and Wednesday
    private final Alert alert;

    public Medication(String name, String id, String timeOfDay, String dosage, String alertTime, List<String> alertDays) {
        this.name = name;
        this.id = id;
        this.timeOfDay = timeOfDay;
        this.dosage = dosage;
        this.alertTime = alertTime;
        this.alertDays = alertDays;

        if (alertDays != null) {
            this.alert = new Alert(name, id, dosage, alertTime, convertDays(alertDays));
        } else {
            this.alert = new Alert(name, id, dosage, alertTime, convertDays(new ArrayList<>()));
        }
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

    public Alert getAlert() {
        return alert;
    }

    private List<DayOfWeek> convertDays(List<String> days){
        List<DayOfWeek> newDays = new ArrayList<>();

        for (String day: days){
            switch (day.toLowerCase()){
                case "mon": newDays.add(DayOfWeek.MONDAY); break;
                case "tues": newDays.add(DayOfWeek.TUESDAY); break;
                case "wed": newDays.add(DayOfWeek.WEDNESDAY); break;
                case "thu": newDays.add(DayOfWeek.THURSDAY); break;
                case "fri": newDays.add(DayOfWeek.FRIDAY); break;
                case "sat": newDays.add(DayOfWeek.SATURDAY); break;
                case "sun": newDays.add(DayOfWeek.SUNDAY); break;
            }
        }
        Collections.sort(newDays);
        return newDays;
    }
}
