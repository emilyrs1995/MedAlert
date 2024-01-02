package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.AlertRepository;
import com.kenzie.appserver.repositories.model.AlertRecord;
import com.kenzie.appserver.service.model.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AlertService {
    private AlertRepository alertRepository;
    @Autowired
    public AlertService(AlertRepository alertRepository){
        this.alertRepository = alertRepository;
    }
    public void addAlert(Alert alert){
        AlertRecord alertRecord = makeAlertRecord(alert);
        alertRepository.save(alertRecord);
    }
    public void updateAlert(Alert alert){
        if(alertRepository.existsById(alert.getAlertId())){
            AlertRecord alertRecord = makeAlertRecord(alert);
            alertRepository.save(alertRecord);
        }
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
}
