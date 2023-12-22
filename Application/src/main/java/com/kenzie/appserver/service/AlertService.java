package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.AlertRepository;
import com.kenzie.appserver.repositories.model.AlertRecord;
import com.kenzie.appserver.service.model.Alert;
import com.kenzie.appserver.service.model.Medication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AlertService {

    // Emily S. 12/21 - none of this is functional until I write some kind of converter class so all the logic is commented out for now
    // trying to avoid mass amounts of errors when running BootRunDev

    private AlertRepository alertRepository;
    private final List<Alert> alertList;

    @Autowired
    public AlertService(AlertRepository alertRepository, List<Alert> alertList) {
        this.alertRepository = alertRepository;
        this.alertList = alertList;
    }

    public void addAlerts(Medication medication) {
//        for(LocalDateTime alert : medication.getAlertDays()) {
//            Alert newAlert = this.createAlert(medication.getName(), medication.getId(), medication.getDosage(), alert);
//            alertList.add(newAlert);
//            AlertRecord alertRecord = this.createAlertRecord(newAlert);
//            alertRepository.save(alertRecord);
//        }
    }

    public void updateAlerts(Medication medication) {
//        for(Alert oldAlert : alertList) {
//            if(oldAlert.getMedicationName().equals(medication.getName()) && oldAlert.getMedicationId().equals(medication.getId())) {
//                alertList.remove(oldAlert);
//            }
//        }
//
//        for(LocalDateTime alert : medication.getAlertDays()) {
//            Alert newAlert = this.createAlert(medication.getName(), medication.getId(), medication.getDosage(), alert);
//            alertList.add(newAlert);
//            AlertRecord alertRecord = this.createAlertRecord(newAlert);
//            alertRepository.save(alertRecord);
//        }
    }

    public void deleteAlerts(String medication) {
//        for (Alert alert : alertList) {
//            if(alert.getMedicationId().equals(medication)) {
//                alertList.remove(alert);
//
//                Optional<AlertRecord> alertRecord = alertRepository.findById(alert.getAlertId());
//                alertRecord.ifPresent(record -> alertRepository.delete(record));
//            }
//        }
    }

//    @Bean
//    public List<Alert> getAlertList() {
//        return this.alertList;
//    }

    private Alert createAlert(String medicationName, String medicationId, String dosage, LocalDateTime makeAlert) {
        Alert alert = new Alert();
        alert.setAlertId(UUID.randomUUID().toString());
        alert.setMedicationName(medicationName);
        alert.setMedicationId(medicationId);
        alert.setDosage(dosage);
        alert.setAlertTime(makeAlert);

        return alert;
    }


    private AlertRecord createAlertRecord(Alert alert) {
        return new AlertRecord(
                alert.getAlertId(),
                alert.getMedicationName(),
                alert.getMedicationId(),
                alert.getDosage(),
                alert.getAlertTime());
    }
}
