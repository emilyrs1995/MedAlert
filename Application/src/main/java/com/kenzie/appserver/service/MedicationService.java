package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.MedicationRepository;
import com.kenzie.appserver.repositories.model.MedicationRecord;
import com.kenzie.appserver.service.model.Alert;
import com.kenzie.appserver.service.model.Medication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicationService {

    private MedicationRepository medicationRepository;
    private AlertService alertService;

    @Autowired
    public MedicationService(MedicationRepository medicationRepository, AlertService alertService){
        this.medicationRepository = medicationRepository;
        this.alertService = alertService;
    }

    public List<Medication> findByName(String medicationName) {
        List<Medication> medications = new ArrayList<>();

        List<Medication> allMedication = this.getAllMedications();
        for (Medication medication : allMedication) {
            if (medication.getName().equals(medicationName)) {
                medications.add(medication);
            }
        }
        return medications;
    }

    public Medication findById(String id) {

        return medicationRepository
                .findById(id)
                .map(medication -> new Medication(medication.getName(), medication.getId(), medication.getTimeOfDay(),
                        medication.getDosage(), medication.getAlertTime(), medication.getAlertDays()))
                .orElse(null);
    }

    public Medication addNewMedication(Medication medication) {
        MedicationRecord medicationRecord = makeMedicationRecord(medication);
        medicationRepository.save(medicationRecord);
        alertService.addAlert(medication.getAlert());
        return medication;
    }

    public void updateMedication(Medication medication){
        if(medicationRepository.existsById(medication.getName())){
            MedicationRecord medicationRecord = makeMedicationRecord(medication);
            medicationRepository.save(medicationRecord);
            alertService.updateAlert(medication.getAlert());
        }
    }

    public List<Medication> getAllMedications(){
        List<Medication> medications = new ArrayList<>();

        Iterable<MedicationRecord> iterator = medicationRepository.findAll();
        for(MedicationRecord record: iterator){
            medications.add(new Medication(record.getName(), record.getId(), record.getTimeOfDay(),
                    record.getDosage(), record.getAlertTime(), record.getAlertDays()));
        }

        return medications;
    }

    public void deleteMedication(String medication){
        Optional<MedicationRecord> medicationRecord = medicationRepository.findById(medication);
        if(medicationRecord.isPresent()){
            MedicationRecord deleteRecord = medicationRecord.get();
            medicationRepository.delete(deleteRecord);
        }
    }
    private MedicationRecord makeMedicationRecord(Medication medication){
        MedicationRecord medicationRecord = new MedicationRecord();
        medicationRecord.setId(medication.getId());
        medicationRecord.setName(medication.getName());
        medicationRecord.setTimeOfDay(medication.getTimeOfDay());
        medicationRecord.setDosage(medication.getDosage());
        medicationRecord.setAlertTime(medication.getAlertTime());
        medicationRecord.setAlertDays(medication.getAlertDays());
        return medicationRecord;
    }
}
