package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.AlertRepository;
import com.kenzie.appserver.repositories.model.AlertRecord;
import com.kenzie.appserver.service.model.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


@Service
public class AlertService {
    private AlertRepository alertRepository;
    // The alert is stored by alertId to avoid mixing up to different times of the same medication if they  are on the same day
    private AlertMap alertMap;
    @Autowired
    public AlertService(AlertRepository alertRepository, AlertMap alertMap){
        this.alertRepository = alertRepository;
        // Check for null here?
        this.alertMap = alertMap;
    }

    public void addAlert(Alert alert){
        AlertRecord alertRecord = makeAlertRecord(alert);
        alertMap.addAlertToMap(alert);
        alertRepository.save(alertRecord);
    }

    public void updateAlert(Alert alert){
        if(alertRepository.existsById(alert.getAlertId())){
            AlertRecord alertRecord = makeAlertRecord(alert);
            alertRepository.save(alertRecord);
            alertMap.addAlertToMap(alert);
        }
    }

    public void deleteAlert(String Id){
        Optional<AlertRecord> alertRecord = alertRepository.findById(Id);
        if(alertRecord.isPresent()){
            AlertRecord deleteRecord = alertRecord.get();
            alertRepository.delete(deleteRecord);
            alertMap.removeAlertFromMap(new Alert(deleteRecord.getMedicationName(), deleteRecord.getAlertId(),
                    deleteRecord.getDosage(), deleteRecord.getAlertTime(), deleteRecord.getAlertDays()));
        }
    }

    public List<String> checkForAlert(){
        // Returns alarm with messages
        List<String> alertStatus = new ArrayList<>();

        // Set current DayOfWeek
        LocalDate date = LocalDate.now();
        DayOfWeek day = date.getDayOfWeek();
        Map<String, Alert> alerts = alertMap.getAlertMap().get(day);
        String currentTime = LocalTime.now().toString();

        // Iterate through map, and check if alertTime == current time
        // Emily S. 1/3 - added null check
        if (alerts != null) {
            for (Alert alert : alerts.values()) {
                if (currentTime.substring(0, 5).contains(alert.getAlertTime())) {
                    alertStatus.add(String.format("It is time for you to take %s of your medication [%s]",
                            alert.getDosage(), alert.getMedicationName()));
                }
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

    // May use as calling point from frontend
//    private Map<DayOfWeek, Map<String, Alert>> makeAlertMap(List<Alert> alertList){
//    }

}
