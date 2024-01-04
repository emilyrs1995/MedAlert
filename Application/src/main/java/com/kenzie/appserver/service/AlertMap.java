package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.model.AlertRecord;
import com.kenzie.appserver.service.model.Alert;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlertMap {
    private Map<DayOfWeek, Map<String, Alert>> alertMap = new HashMap<>();

    public AlertMap(){
        alertMap.put(DayOfWeek.MONDAY, new HashMap<>());
        alertMap.put(DayOfWeek.TUESDAY, new HashMap<>());
        alertMap.put(DayOfWeek.WEDNESDAY, new HashMap<>());
        alertMap.put(DayOfWeek.THURSDAY, new HashMap<>());
        alertMap.put(DayOfWeek.FRIDAY, new HashMap<>());
        alertMap.put(DayOfWeek.SATURDAY, new HashMap<>());
        alertMap.put(DayOfWeek.SUNDAY, new HashMap<>());
    }
    public void addAlertToMap(Alert alert){
        Map<String, Alert> valueMap;

        for(DayOfWeek day: alert.getAlertDays()){
            valueMap = alertMap.get(day);
            switch (day){
                case MONDAY: valueMap.put(alert.getAlertId(), alert); break;
                case TUESDAY: valueMap.put(alert.getAlertId(), alert); break;
                case WEDNESDAY: valueMap.put(alert.getAlertId(), alert); break;
                case THURSDAY: valueMap.put(alert.getAlertId(), alert); break;
                case FRIDAY: valueMap.put(alert.getAlertId(), alert); break;
                case SATURDAY: valueMap.put(alert.getAlertId(), alert); break;
                case SUNDAY: valueMap.put(alert.getAlertId(), alert); break;
            }
            alertMap.put(day,valueMap);
        }

    }
    public void removeAlertFromMap(Alert alert){
        Map<String, Alert> valueMap;

        for(DayOfWeek day: alert.getAlertDays()){
            valueMap = alertMap.get(day);
            switch (day){
                case MONDAY: valueMap.remove(alert.getAlertId(), alert); break;
                case TUESDAY: valueMap.remove(alert.getAlertId(), alert); break;
                case WEDNESDAY: valueMap.remove(alert.getAlertId(), alert); break;
                case THURSDAY: valueMap.remove(alert.getAlertId(), alert); break;
                case FRIDAY: valueMap.remove(alert.getAlertId(), alert); break;
                case SATURDAY: valueMap.remove(alert.getAlertId(), alert); break;
                case SUNDAY: valueMap.remove(alert.getAlertId(), alert); break;
            }
            alertMap.put(day,valueMap);
        }

    }
    public void makeAlertMap(List<Alert> alertList){
        Map<String, Alert> valueMap;

        if(alertList != null){
            for(Alert alert: alertList){
                for(DayOfWeek day: alert.getAlertDays()){
                    valueMap = alertMap.get(day);
                    switch (day){
                        case MONDAY: valueMap.put(alert.getAlertId(), alert); break;
                        case TUESDAY: valueMap.put(alert.getAlertId(), alert); break;
                        case WEDNESDAY: valueMap.put(alert.getAlertId(), alert); break;
                        case THURSDAY: valueMap.put(alert.getAlertId(), alert); break;
                        case FRIDAY: valueMap.put(alert.getAlertId(), alert); break;
                        case SATURDAY: valueMap.put(alert.getAlertId(), alert); break;
                        case SUNDAY: valueMap.put(alert.getAlertId(), alert); break;
                    }
                    alertMap.put(day,valueMap);
                }
            }
        }
    }
    public List<Alert> makeAlertList(Iterable<AlertRecord> alertRecords){
        List<Alert> alerts = new ArrayList<>();

        if( alertRecords != null) {
            for (AlertRecord record : alertRecords) {
                alerts.add(new Alert(record.getMedicationName(), record.getAlertId(), record.getDosage(), record.getAlertTime(), record.getAlertDays()));
            }
        }

        return alerts;
    }

    public Map<DayOfWeek, Map<String, Alert>> getAlertMap() {
        return alertMap;
    }

}
