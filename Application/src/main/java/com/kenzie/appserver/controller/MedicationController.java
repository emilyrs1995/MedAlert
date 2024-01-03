package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.MedicationCreateRequest;
import com.kenzie.appserver.controller.model.MedicationResponse;
import com.kenzie.appserver.controller.model.MedicationUpdateRequest;
import com.kenzie.appserver.service.MedicationService;
import com.kenzie.appserver.service.model.Medication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/medication")
public class MedicationController {
    private MedicationService medicationService;

    public MedicationController(MedicationService medicationService){
        this.medicationService = medicationService;
    }
    @PostMapping
    public ResponseEntity<MedicationResponse> createMedication(@RequestBody MedicationCreateRequest createRequest){
        boolean dosage = verifyInput(createRequest.getDosage());
        boolean name = verifyInput(createRequest.getName());
        if (!dosage || !name) {
            return ResponseEntity.badRequest().build();
        }

        Medication medication = new Medication(createRequest.getName(), randomUUID().toString(),
                createRequest.getTimeOfDay(), createRequest.getDosage(), createRequest.getAlertTime(), createRequest.getAlertDays());
        medicationService.addNewMedication(medication);

        MedicationResponse response = createMedicationResponse(medication);

        return ResponseEntity.created(URI.create("/medication/" + response.getId())).body(response);
    }

    @GetMapping("/{medication}")
    public ResponseEntity<MedicationResponse> getMedication(@PathVariable("medication") String medicationName) {
        Medication medication = medicationService.findById(medicationName.toLowerCase());
        if (medication == null) {
            return ResponseEntity.noContent().build();
        }
        MedicationResponse response = this.createMedicationResponse(medication);

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<MedicationResponse> updateMedication(@RequestBody MedicationUpdateRequest updateRequest) {
        Medication medication = new Medication(updateRequest.getName().toLowerCase(),
                updateRequest.getId(),
                updateRequest.getTimeOfDay(),
                updateRequest.getDosage(),
                updateRequest.getAlertTime(),
                updateRequest.getAlertDays());
        medicationService.updateMedication(medication);

        MedicationResponse response = createMedicationResponse(medication);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MedicationResponse>> getMedicationList() {
        List<Medication> medications = medicationService.getAllMedications();
        if (medications == null || medications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<MedicationResponse> response = new ArrayList<>();
        for (Medication medication : medications) {
            response.add(this.createMedicationResponse(medication));
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{medication}")
    public ResponseEntity deleteMedication(@PathVariable("medication") String medication) {
        medicationService.deleteMedication(medication.toLowerCase());
        return ResponseEntity.noContent().build();
    }

    private MedicationResponse createMedicationResponse(Medication medication){
        MedicationResponse response = new MedicationResponse();
        response.setName(medication.getName().toUpperCase(Locale.ROOT));
        response.setId(medication.getId());
        response.setTimeOfDay(medication.getTimeOfDay());
        response.setDosage(medication.getDosage());
        response.setAlertTime(medication.getAlertTime());
        response.setAlertDays(medication.getAlertDays());
        return response;
    }

    private boolean verifyInput(String string) {
        String allowedStrings = "1234567890abcdefghijklmnopqrstupvwxyz";
        StringBuilder validatedString = new StringBuilder();
        validatedString.append(string.toLowerCase());

        if (string.length() > 20) {
            return false;
        }

        for (int i = 0; i < validatedString.length(); i++) {
            if(!allowedStrings.contains(validatedString.substring(i, i + 1))) {
                validatedString.deleteCharAt(i);
                i--;
            }
        }
        return validatedString.length() >= 1;
    }
}
