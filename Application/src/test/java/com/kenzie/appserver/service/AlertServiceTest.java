package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.AlertRepository;
import com.kenzie.appserver.repositories.model.AlertRecord;
import com.kenzie.appserver.service.model.Alert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class AlertServiceTest {

    @InjectMocks
    private AlertService alertService;

    @Mock
    private AlertRepository alertRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /** ------------------------------------------------------------------------
     *  alertService.addAlert
     *  ------------------------------------------------------------------------ **/
    @Test
    void addAlert_withValidInput_addsAlert() {
        // GIVEN
        String name = "Aspirin";
        String dosage = "1 pill";
        String alertTime = "8:00";
        List<String> alertDays = new ArrayList<>();
        alertDays.add("Mon");
        alertDays.add("Wed");
        alertDays.add("Fri");

        Alert alert = new Alert(name, dosage, alertTime, alertDays);

        ArgumentCaptor<AlertRecord> alertRecordCaptor = ArgumentCaptor.forClass(AlertRecord.class);

        // WHEN
        alertService.addAlert(alert);

        // THEN
        verify(alertRepository).save(alertRecordCaptor.capture());
        AlertRecord record = alertRecordCaptor.getValue();

        Assertions.assertNotNull(record);
        Assertions.assertNotNull(alert.getAlertId());
        Assertions.assertEquals("MONDAY", alert.getAlertDays().get(0).toString());
        Assertions.assertEquals("WEDNESDAY", alert.getAlertDays().get(1).toString());
        Assertions.assertEquals("FRIDAY", alert.getAlertDays().get(2).toString());
    }

    /** ------------------------------------------------------------------------
     *  alertService.updateAlert
     *  ------------------------------------------------------------------------ **/
    @Test
    void updateAlert_withValidAlert_updatesAlert() {
        // GIVEN
        String name = "Aspirin";
        String dosage = "1 pill";
        String alertTime = "8:00";
        List<String> alertDays = new ArrayList<>();
        alertDays.add("Tues");
        alertDays.add("Thurs");
        alertDays.add("Sat");

        Alert alert = new Alert(name, dosage, alertTime, alertDays);
        when(alertRepository.existsById(name)).thenReturn(true);

        ArgumentCaptor<AlertRecord> alertRecordCaptor = ArgumentCaptor.forClass(AlertRecord.class);

        // WHEN
        alertService.updateAlert(alert);

        // THEN
        verify(alertRepository).save(alertRecordCaptor.capture());
        AlertRecord record = alertRecordCaptor.getValue();

        Assertions.assertNotNull(record);
        Assertions.assertNotNull(alert.getAlertId());
        Assertions.assertEquals("TUESDAY", alert.getAlertDays().get(0).toString());
        Assertions.assertEquals("THURSDAY", alert.getAlertDays().get(1).toString());
        Assertions.assertEquals("SATURDAY", alert.getAlertDays().get(2).toString());
    }

    @Test
    void updateAlert_withInvalidAlert_updatesAlert() {
        // GIVEN
        Alert alert = new Alert(null, null, null, null);
        when(alertRepository.existsById(alert.getMedicationName())).thenReturn(false);

        // WHEN
        alertService.updateAlert(alert);

        // THEN
        verify(alertRepository, never()).save(new AlertRecord());
    }
}
