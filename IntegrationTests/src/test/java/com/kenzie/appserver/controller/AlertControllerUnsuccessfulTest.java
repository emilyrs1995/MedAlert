package com.kenzie.appserver.controller;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class AlertControllerUnsuccessfulTest {

    /* This test does not ~always~ pass when running the Jacoco command. In the AlertControllerSuccessfulTest I create
    * a new alert with the current time and test that the alert controller correctly receives a response from the
    * alertService. However, this test (AlertControllerUnsuccessfulTest) is testing with an alert that is for 15 minutes
    * from now and confirms that the controller does not receive a response. The problem is that when I run the Jacoco
    * command all the tests run within a few milliseconds of each other so on some occasions the alert that I created in
    * the AlertControllerSuccessfulTest is still valid and returned even if I delete it within that test. With that said,
    * running all the tests individually always passes */

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AlertService alertService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void checkAlertStatus_withUnreadyAlert_returnsNoContent() throws Exception {
        // GIVEN
        String name = "Tylenol";
        String Id = UUID.randomUUID().toString();
        String dosage = "2 capsules";
        String alertTime = LocalTime.now().plusMinutes(15).toString().substring(0, 5);
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
                .andExpect(status().isNoContent())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertTrue(response.isEmpty());
    }

}
