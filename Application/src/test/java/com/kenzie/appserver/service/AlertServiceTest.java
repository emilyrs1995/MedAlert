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

import java.time.DayOfWeek;
import java.util.*;

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
        String Id = UUID.randomUUID().toString();
        String dosage = "1 pill";
        String alertTime = "8:00";
        List<DayOfWeek> alertDays = new ArrayList<>();
        alertDays.add(DayOfWeek.MONDAY);
        alertDays.add(DayOfWeek.WEDNESDAY);
        alertDays.add(DayOfWeek.FRIDAY);
        alertDays.add(DayOfWeek.SUNDAY);

        Alert alert = new Alert(name, Id, dosage, alertTime, alertDays);

        ArgumentCaptor<AlertRecord> alertRecordCaptor = ArgumentCaptor.forClass(AlertRecord.class);

        // WHEN
        alertService.addAlert(alert);

        // THEN
        verify(alertRepository).save(alertRecordCaptor.capture());
        AlertRecord record = alertRecordCaptor.getValue();

        Assertions.assertNotNull(record);
        Assertions.assertEquals(4, record.getAlertDays().size());
    }

    /** ------------------------------------------------------------------------
     *  alertService.updateAlert
     *  ------------------------------------------------------------------------ **/
    @Test
    void updateAlert_withValidAlert_updatesAlert() {
        // GIVEN
        String name = "Aspirin";
        String Id = UUID.randomUUID().toString();
        String dosage = "1 pill";
        String alertTime = "8:00";
        List<DayOfWeek> alertDays = new ArrayList<>();
        alertDays.add(DayOfWeek.TUESDAY);
        alertDays.add(DayOfWeek.THURSDAY);
        alertDays.add(DayOfWeek.SATURDAY);

        Alert alert = new Alert(name, Id, dosage, alertTime, alertDays);
        when(alertRepository.existsById(alert.getAlertId())).thenReturn(true);

        ArgumentCaptor<AlertRecord> alertRecordCaptor = ArgumentCaptor.forClass(AlertRecord.class);

        // WHEN
        alertService.updateAlert(alert);

        // THEN
        verify(alertRepository).save(alertRecordCaptor.capture());
        AlertRecord record = alertRecordCaptor.getValue();

        Assertions.assertNotNull(record);
        Assertions.assertEquals(3, record.getAlertDays().size());
    }

    @Test
    void updateAlert_withInvalidAlert_updatesAlert() {
        // GIVEN
        Alert alert = new Alert(null, null, null, null, null);
        when(alertRepository.existsById(alert.getMedicationName())).thenReturn(false);

        // WHEN
        alertService.updateAlert(alert);

        // THEN
        verify(alertRepository, never()).save(new AlertRecord());
    }

    /** ------------------------------------------------------------------------
     *  alertService.deleteAlert
     *  ------------------------------------------------------------------------ **/
    @Test
    void deleteAlert_withValidInput_deletesAlert() {
        // GIVEN
        String name = "Aspirin";
        String Id = UUID.randomUUID().toString();
        String dosage = "1 pill";
        String alertTime = "8:00";
        List<DayOfWeek> alertDays = new ArrayList<>();
        alertDays.add(DayOfWeek.TUESDAY);
        alertDays.add(DayOfWeek.THURSDAY);
        alertDays.add(DayOfWeek.SATURDAY);
        Alert alert = new Alert(name, Id, dosage, alertTime, alertDays);

        AlertRecord alertRecord = new AlertRecord();
        alertRecord.setMedicationName(alert.getMedicationName());
        alertRecord.setAlertId(alert.getAlertId());
        alertRecord.setDosage(alert.getDosage());
        alertRecord.setAlertTime(alert.getAlertTime());
        alertRecord.setAlertDays(alert.getAlertDays());

        when(alertRepository.findById(Id)).thenReturn(Optional.of(alertRecord));

        // WHEN
        alertService.deleteAlert(Id);

        // THEN
        verify(alertRepository).delete(alertRecord);
    }

    @Test
    void deleteAlert_withMoreValidInput_deletesAlert() {
        // GIVEN
        String name = "Aspirin";
        String Id = UUID.randomUUID().toString();
        String dosage = "1 pill";
        String alertTime = "8:00";
        List<DayOfWeek> alertDays = new ArrayList<>();
        alertDays.add(DayOfWeek.MONDAY);
        alertDays.add(DayOfWeek.WEDNESDAY);
        alertDays.add(DayOfWeek.FRIDAY);
        alertDays.add(DayOfWeek.SUNDAY);
        Alert alert = new Alert(name, Id, dosage, alertTime, alertDays);

        AlertRecord alertRecord = new AlertRecord();
        alertRecord.setMedicationName(alert.getMedicationName());
        alertRecord.setAlertId(alert.getAlertId());
        alertRecord.setDosage(alert.getDosage());
        alertRecord.setAlertTime(alert.getAlertTime());
        alertRecord.setAlertDays(alert.getAlertDays());

        when(alertRepository.findById(Id)).thenReturn(Optional.of(alertRecord));

        // WHEN
        alertService.deleteAlert(Id);

        // THEN
        verify(alertRepository).delete(alertRecord);
    }

    @Test
    void deleteAlert_withInvalidInput_doesNotDeleteAlert() {
        // GIVEN
        String id = UUID.randomUUID().toString();
        when(alertRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        alertService.deleteAlert(id);

        // THEN
        verify(alertRepository, never()).delete(new AlertRecord());
    }
}
