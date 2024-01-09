package com.kenzie.appserver.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.service.AlertService;
import com.kenzie.appserver.service.model.Alert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class AlertControllerSuccessfulTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AlertService alertService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void checkAlertStatus_withReadyAlert_returnsOK() throws Exception {
        // GIVEN
        String name = "Aspirin";
        String Id = UUID.randomUUID().toString();
        String dosage = "2 pill";
        String alertTime = LocalTime.now().toString().substring(0, 5);
        List<DayOfWeek> alertDays = new ArrayList<>();
        alertDays.add(DayOfWeek.MONDAY);
        alertDays.add(DayOfWeek.TUESDAY);
        alertDays.add(DayOfWeek.WEDNESDAY);
        alertDays.add(DayOfWeek.THURSDAY);
        alertDays.add(DayOfWeek.FRIDAY);
        alertDays.add(DayOfWeek.SATURDAY);
        alertDays.add(DayOfWeek.SUNDAY);
        Alert alert = new Alert(name, Id, dosage, alertTime, alertDays);
        // persisted Alert
        alertService.addAlert(alert);

        // WHEN
        String response = mvc.perform(get("/alert/alertStatus")
                    .accept(MediaType.APPLICATION_JSON))
        // THEN
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<String> responseList = mapper.readValue(response, new TypeReference<List<String>>() {});
        Assertions.assertEquals(1, responseList.size());
        Assertions.assertTrue(responseList.get(0).contains(alert.getMedicationName()));
        Assertions.assertTrue(responseList.get(0).contains(alert.getDosage()));
    }
}
