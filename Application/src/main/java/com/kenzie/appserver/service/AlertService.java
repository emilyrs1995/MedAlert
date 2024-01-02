package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.AlertRepository;
import com.kenzie.appserver.repositories.model.AlertRecord;
import com.kenzie.appserver.service.model.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AlertService {
    private AlertRepository alertRepository;
    private Map<DayOfWeek, Map<String, Alert>> alertMap;
    @Autowired
    public AlertService(AlertRepository alertRepository){
        this.alertRepository = alertRepository;
        // Check for null here?
        alertMap = makeAlertMap(makeAlertList(alertRepository.findAll()));
    }
    public void addAlert(Alert alert){
        AlertRecord alertRecord = makeAlertRecord(alert);
        alertRepository.save(alertRecord);
        addAlertToMap(alert);
    }
    public void updateAlert(Alert alert){
        if(alertRepository.existsById(alert.getMedicationName())){
            AlertRecord alertRecord = makeAlertRecord(alert);
            alertRepository.save(alertRecord);
            addAlertToMap(alert);
        }
    }

//    public void deleteAlert(){}

    public List<String> checkForAlert(){
        // Returns alarm with messages
        List<String> alertStatus = new ArrayList<>();

        // Set current DayOfWeek
        LocalDate date = LocalDate.now();
        DayOfWeek day = date.getDayOfWeek();
        Map<String, Alert> alerts = alertMap.get(day);
        // Iterate through map, and check if alertTime == current time
        for(Alert alert: alerts.values()){
            if(LocalTime.now().toString().contains(alert.getAlertTime())){
                alertStatus.add(String.format("It is time for you to take %s of your medication [%s]", alert.getDosage(), alert.getMedicationName()));
            }
        }

        return alertStatus;
    }
    private AlertRecord makeAlertRecord(Alert alert){
        AlertRecord alertRecord = new AlertRecord();
        alertRecord.setMedicationName(alert.getMedicationName());
        alertRecord.setAlertId(alert.getAlertId());
        alertRecord.setDosage(alert.getDosage());
        alertRecord.setAlertTime(alert.getAlertTime());
        alertRecord.setAlertDays(alert.getAlertDays());
        return alertRecord;
    }
    private Map<DayOfWeek, Map<String, Alert>> makeAlertMap(List<Alert> alertList){
        Map<DayOfWeek, Map<String, Alert>> returnMap = makeAlertKeys();
        Map<String, Alert> valueMap;

        if(alertList != null){
            for(Alert alert: alertList){
                for(DayOfWeek day: alert.getAlertDays()){
                    valueMap = returnMap.get(day);
                    switch (day){
                        case MONDAY: valueMap.put(alert.getAlertId(), alert);
                        case TUESDAY: valueMap.put(alert.getAlertId(), alert);
                        case WEDNESDAY: valueMap.put(alert.getAlertId(), alert);
                        case THURSDAY: valueMap.put(alert.getAlertId(), alert);
                        case FRIDAY: valueMap.put(alert.getAlertId(), alert);
                        case SATURDAY: valueMap.put(alert.getAlertId(), alert);
                        case SUNDAY: valueMap.put(alert.getAlertId(), alert);
                    }
                    returnMap.put(day,valueMap);
                }
            }
        }


        return returnMap;
    }
    private Map<DayOfWeek, Map<String, Alert>> makeAlertKeys(){
        Map<DayOfWeek, Map<String, Alert>> returnMap = new HashMap<>();

        returnMap.put(DayOfWeek.MONDAY, new HashMap<>());
        returnMap.put(DayOfWeek.TUESDAY, new HashMap<>());
        returnMap.put(DayOfWeek.WEDNESDAY, new HashMap<>());
        returnMap.put(DayOfWeek.THURSDAY, new HashMap<>());
        returnMap.put(DayOfWeek.FRIDAY, new HashMap<>());
        returnMap.put(DayOfWeek.SATURDAY, new HashMap<>());
        returnMap.put(DayOfWeek.SUNDAY, new HashMap<>());

        return  returnMap;
    }
    private void addAlertToMap(Alert alert){
        Map<String, Alert> valueMap;

        for(DayOfWeek day: alert.getAlertDays()){
            valueMap = alertMap.get(day);
            switch (day){
                case MONDAY: valueMap.put(alert.getAlertId(), alert);
                case TUESDAY: valueMap.put(alert.getAlertId(), alert);
                case WEDNESDAY: valueMap.put(alert.getAlertId(), alert);
                case THURSDAY: valueMap.put(alert.getAlertId(), alert);
                case FRIDAY: valueMap.put(alert.getAlertId(), alert);
                case SATURDAY: valueMap.put(alert.getAlertId(), alert);
                case SUNDAY: valueMap.put(alert.getAlertId(), alert);
            }
            alertMap.put(day,valueMap);
        }

    }
    private List<Alert> makeAlertList(Iterable<AlertRecord> alertRecords){
        List<Alert> alerts = new ArrayList<>();

        if( alertRecords != null) {
            for (AlertRecord record : alertRecords) {
                alerts.add(new Alert(record.getMedicationName(), record.getAlertId(), record.getDosage(), record.getAlertTime(), record.getAlertDays()));
            }
        }

        return alerts;
    }
}
