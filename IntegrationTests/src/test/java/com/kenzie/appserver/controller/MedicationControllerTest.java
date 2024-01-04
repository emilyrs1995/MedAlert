package com.kenzie.appserver.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.MedicationCreateRequest;
import com.kenzie.appserver.controller.model.MedicationResponse;
import com.kenzie.appserver.controller.model.MedicationUpdateRequest;
import com.kenzie.appserver.service.MedicationService;
import com.kenzie.appserver.service.model.Medication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class MedicationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    MedicationService medicationService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getMedication_medicationExists() throws Exception {
        // GIVEN
        String name = "Aspirin2222";
        String id = UUID.randomUUID().toString();
        String timeOfDay = "AFTERNOON";
        String dosage = "1 pill";
        String alertTime = LocalDateTime.now().toString();
        List<String> alertDays = new ArrayList<>();
        alertDays.add("Mon");
        alertDays.add("Wed");
        alertDays.add("Fri");

        Medication medication = new Medication(name, id, timeOfDay, dosage, alertTime, alertDays);
        Medication persistedMedication = medicationService.addNewMedication(medication);

        // WHEN
        mvc.perform(get("/medication/{medication}", persistedMedication.getName())
                    .accept(MediaType.APPLICATION_JSON))
        // THEN
                .andExpect(jsonPath("name")
                        .value(is(name.toUpperCase())))
                .andExpect(jsonPath("id")
                        .value(is(id)))
                .andExpect(jsonPath("timeOfDay")
                        .value(is(timeOfDay)))
                .andExpect(jsonPath("dosage")
                        .value(is(dosage)))
                .andExpect(jsonPath("alertTime")
                        .value(is(alertTime)))
                .andExpect(jsonPath("alertDays")
                        .value(is(alertDays)))
                .andExpect(status().isOk());
    }

    @Test
    public void getMedication_medicationDoesNotExist() throws Exception {
        // GIVEN
        String name = "Advil!!!!!";

        // WHEN
        mvc.perform(get("/medication/{medication}", name)
                    .accept(MediaType.APPLICATION_JSON))
        // THEN
                .andExpect(status().isNotFound());
    }

    @Test
    public void createMedication_createSuccessful() throws Exception {
        // GIVEN
        String name = "Tylenol????";
        String timeOfDay = "AFTERNOON";
        String dosage = "1 pill";
        String alertTime = LocalDateTime.now().toString();
        List<String> alertDays = new ArrayList<>();
        alertDays.add("Mon");
        alertDays.add("Wed");
        alertDays.add("Fri");

        MedicationCreateRequest medicationCreateRequest = new MedicationCreateRequest();
        medicationCreateRequest.setName(name);
        medicationCreateRequest.setTimeOfDay(timeOfDay);
        medicationCreateRequest.setDosage(dosage);
        medicationCreateRequest.setAlertTime(alertTime);
        medicationCreateRequest.setAlertDays(alertDays);

        mapper.registerModule(new JavaTimeModule());

        // WHEN
        mvc.perform(post("/medication")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(medicationCreateRequest)))
        // THEN
                .andExpect(jsonPath("name")
                        .value(is(name.toUpperCase())))
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("timeOfDay")
                        .value(is(timeOfDay)))
                .andExpect(jsonPath("dosage")
                        .value(is(dosage)))
                .andExpect(jsonPath("alertTime")
                        .value(is(alertTime)))
                .andExpect(jsonPath("alertDays")
                        .value(is(alertDays)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateMedication_PutSuccessful() throws Exception {
        // GIVEN
        String name = "Tylenol3333";
        String id = UUID.randomUUID().toString();
        String timeOfDay = "AFTERNOON";
        String dosage = "1 pill";
        String alertTime = LocalDateTime.now().toString();
        List<String> alertDays = new ArrayList<>();
        alertDays.add("Mon");
        alertDays.add("Wed");
        alertDays.add("Fri");

        Medication medication = new Medication(name, id, timeOfDay, dosage, alertTime, alertDays);
        Medication persistedMedication = medicationService.addNewMedication(medication);

        String newTimeOfDay = "EVENING";
        String newDosage = "3 capsules";

        MedicationUpdateRequest medicationUpdateRequest = new MedicationUpdateRequest();
        medicationUpdateRequest.setName(name);
        medicationUpdateRequest.setId(id);
        medicationUpdateRequest.setTimeOfDay(newTimeOfDay);
        medicationUpdateRequest.setDosage(newDosage);
        medicationUpdateRequest.setAlertTime(alertTime);
        medicationUpdateRequest.setAlertDays(alertDays);

        mapper.registerModule(new JavaTimeModule());

        // WHEN
        mvc.perform(put("/medication")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(medicationUpdateRequest)))
        // THEN
                .andExpect(jsonPath("name")
                        .value(is(name.toUpperCase())))
                .andExpect(jsonPath("id")
                        .value(is(id)))
                .andExpect(jsonPath("timeOfDay")
                        .value(is(newTimeOfDay)))
                .andExpect(jsonPath("dosage")
                        .value(is(newDosage)))
                .andExpect(jsonPath("alertTime")
                        .value(is(alertTime)))
                .andExpect(jsonPath("alertDays")
                        .value(is(alertDays)))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllMedication_getAllSuccessful() throws Exception {
        // GIVEN
        String name1 = "Aspirin2222";
        String id1 = UUID.randomUUID().toString();
        String timeOfDay1 = "AFTERNOON";
        String dosage1 = "1 pill";
        String alertTime1 = LocalDateTime.now().toString();
        List<String> alertDays1 = new ArrayList<>();
        alertDays1.add("Mon");
        alertDays1.add("Wed");
        alertDays1.add("Fri");

        Medication medication1 = new Medication(name1, id1, timeOfDay1, dosage1, alertTime1, alertDays1);
        Medication persistedMedication1 = medicationService.addNewMedication(medication1);

        String name2 = "Tylenol8888";
        String id2 = UUID.randomUUID().toString();
        String timeOfDay2 = "AFTERNOON";
        String dosage2 = "1 pill";
        String alertTime2 = LocalDateTime.now().toString();
        List<String> alertDays2 = new ArrayList<>();
        alertDays2.add("Mon");
        alertDays2.add("Wed");
        alertDays2.add("Fri");

        Medication medication2 = new Medication(name2, id2, timeOfDay2, dosage2, alertTime2, alertDays2);
        Medication persistedMedication2 = medicationService.addNewMedication(medication2);

        List<Medication> allMedications = medicationService.getAllMedications();

        // WHEN
        String response = mvc.perform(get("/medication/all")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<MedicationResponse> responseList = mapper.readValue(response, new TypeReference<List<MedicationResponse>>() {});

        Assertions.assertEquals(allMedications.size(), responseList.size());
        for (int i = 0; i < allMedications.size(); i++) {
            Assertions.assertEquals(allMedications.get(i).getName().toUpperCase(), responseList.get(i).getName());
            Assertions.assertEquals(allMedications.get(i).getId(), responseList.get(i).getId());
            Assertions.assertEquals(allMedications.get(i).getTimeOfDay(), responseList.get(i).getTimeOfDay());
            Assertions.assertEquals(allMedications.get(i).getDosage(), responseList.get(i).getDosage());
            Assertions.assertEquals(allMedications.get(i).getAlertTime(), responseList.get(i).getAlertTime());
            Assertions.assertEquals(allMedications.get(i).getAlertDays(), responseList.get(i).getAlertDays());
        }
    }

    @Test
    public void deleteMedication_DeleteSuccessful() throws Exception {
        // GIVEN
        String name = "Tylenol4444";
        String id = UUID.randomUUID().toString();
        String timeOfDay = "AFTERNOON";
        String dosage = "1 pill";
        String alertTime = LocalDateTime.now().toString();
        List<String> alertDays = new ArrayList<>();
        alertDays.add("Mon");
        alertDays.add("Wed");
        alertDays.add("Fri");

        Medication medication = new Medication(name, id, timeOfDay, dosage, alertTime, alertDays);
        Medication persistedMedication = medicationService.addNewMedication(medication);

        // WHEN
        mvc.perform(delete("/medication/{medication}", persistedMedication.getName())
                        .accept(MediaType.APPLICATION_JSON))
        // THEN
                .andExpect(status().isNoContent());

        assertThat(medicationService.findById(name)).isNull();
    }
}
