package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.MedicationCreateRequest;
import com.kenzie.appserver.controller.model.MedicationResponse;
import com.kenzie.appserver.service.MedicationService;
import com.kenzie.appserver.service.model.Medication;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
        Medication medication = new Medication(createRequest.getName(), randomUUID().toString(),
                createRequest.getTimeOfDay(), createRequest.getDosage(), createRequest.getAlertTime(),
                createRequest.getAlertDays());
        medicationService.addNewMedication(medication);

        MedicationResponse response = createMedicationResponse(medication);

        return ResponseEntity.created(URI.create("/medication/" + response.getId())).body(response);
    }


    private MedicationResponse createMedicationResponse(Medication medication){
        MedicationResponse response = new MedicationResponse();
        response.setName(medication.getName());
        response.setId(medication.getId());
        response.setTimeOfDay(medication.getTimeOfDay());
        response.setDosage(medication.getDosage());
        response.setAlertTime(medication.getAlertTime());
        response.setAlertDays(medication.getAlertDays());
        return response;
    }
}
