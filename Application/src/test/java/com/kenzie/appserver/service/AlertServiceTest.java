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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.mockito.Mockito.*;

public class AlertServiceTest {

    @InjectMocks
    private AlertService alertService;

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private AlertMap alertMap;

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
        verify(alertMap).addAlertToMap(alert);
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

        verify(alertMap).addAlertToMap(alert);
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
        verify(alertMap, never()).addAlertToMap(alert);
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
        verify(alertMap).removeAlertFromMap(any());
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
        verify(alertMap).removeAlertFromMap(any());
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
        verify(alertMap, never()).removeAlertFromMap(new Alert());
    }

    /** ------------------------------------------------------------------------
     *  alertService.checkForAlert
     *  ------------------------------------------------------------------------ **/
    @Test
    void checkForAlert_withReadyAlerts_returnsListOfReadyAlerts() {
        // GIVEN
        LocalDate date = LocalDate.now();
        DayOfWeek day = date.getDayOfWeek();

        String name1 = "Aspirin";
        String Id1 = UUID.randomUUID().toString();
        String dosage1 = "1 pill";
        String alertTime1 = LocalTime.now().toString().substring(0, 5);
        List<DayOfWeek> alertDays1 = new ArrayList<>();
        alertDays1.add(DayOfWeek.MONDAY);
        alertDays1.add(DayOfWeek.TUESDAY);
        alertDays1.add(DayOfWeek.WEDNESDAY);
        alertDays1.add(DayOfWeek.THURSDAY);
        alertDays1.add(DayOfWeek.FRIDAY);
        alertDays1.add(DayOfWeek.SATURDAY);
        alertDays1.add(DayOfWeek.SUNDAY);
        Alert alert1 = new Alert(name1, Id1, dosage1, alertTime1, alertDays1);

        String name2 = "Tylenol";
        String Id2 = UUID.randomUUID().toString();
        String dosage2 = "2 capsules";
        String alertTime2 = LocalTime.now().toString().substring(0, 5);
        List<DayOfWeek> alertDays2 = new ArrayList<>();
        alertDays2.add(DayOfWeek.MONDAY);
        alertDays2.add(DayOfWeek.TUESDAY);
        alertDays2.add(DayOfWeek.WEDNESDAY);
        alertDays2.add(DayOfWeek.THURSDAY);
        alertDays2.add(DayOfWeek.FRIDAY);
        alertDays2.add(DayOfWeek.SATURDAY);
        alertDays2.add(DayOfWeek.SUNDAY);
        Alert alert2 = new Alert(name2, Id2, dosage2, alertTime2, alertDays2);

        Map<String, Alert> alerts = new HashMap<>();
        alerts.put(alert1.getAlertId(), alert1);
        alerts.put(alert2.getAlertId(), alert2);

        when(alertMap.getAlertMap().get(day)).thenReturn(alerts);

        // WHEN
        List<String> result = alertService.checkForAlert();

        // THEN
        Assertions.assertEquals(2, result.size());
    }
}
