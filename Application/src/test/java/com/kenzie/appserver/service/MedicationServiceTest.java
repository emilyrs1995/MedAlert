package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.MedicationRepository;
import com.kenzie.appserver.repositories.model.MedicationRecord;
import com.kenzie.appserver.service.model.Medication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class MedicationServiceTest {

    @InjectMocks
    private MedicationService medicationService;
    @Mock
    private MedicationRepository medicationRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /** ------------------------------------------------------------------------
     *  medicationService.findById
     *  ------------------------------------------------------------------------ **/
    @Test
    void findById_withValidInput_returnsMedication() {
        // GIVEN
        String name = "Aspirin";
        String id = UUID.randomUUID().toString();
        List<String> alertDays = new ArrayList<>();
        alertDays.add("Monday");
        alertDays.add("Wednesday");
        alertDays.add("Friday");

        MedicationRecord record = new MedicationRecord();
        record.setName(name);
        record.setId(id);
        record.setTimeOfDay("MORNING");
        record.setDosage("1 pill");
        record.setAlertTime("8:00");
        record.setAlertDays(alertDays);
        when(medicationRepository.findById(id)).thenReturn(Optional.of(record));

        // WHEN
        Medication medication = medicationService.findById(id);

        // THEN
        Assertions.assertNotNull(medication);
        Assertions.assertEquals(record.getName(), medication.getName(), "Expected medication name to match.");
        Assertions.assertEquals(record.getId(), medication.getId(), "Expected medication id to match.");
        Assertions.assertEquals(record.getTimeOfDay(), medication.getTimeOfDay(), "Expected medication timeOfDay to match.");
        Assertions.assertEquals(record.getDosage(), medication.getDosage(), "Expected medication dosage to match.");
        Assertions.assertEquals(record.getAlertTime(), medication.getAlertTime(), "Expected medication alertTime to match.");
        Assertions.assertEquals(record.getAlertDays(), medication.getAlertDays(), "Expected medication alertDays to match.");
    }

    /** ------------------------------------------------------------------------
     *  medicationService.addNewMedication
     *  ------------------------------------------------------------------------ **/
    @Test
    void addNewMedication_withValidInput_returnsMedication() {
        //GIVEN
        String name = "Aspirin";
        String id = UUID.randomUUID().toString();
        String timeOfDay = "MORNING";
        String dosage = "1 pill";
        String alertTime = "8:00";
        List<String> alertDays = new ArrayList<>();
        alertDays.add("Monday");
        alertDays.add("Wednesday");
        alertDays.add("Friday");
        Medication medication = new Medication(name, id, timeOfDay, dosage, alertTime, alertDays);

        ArgumentCaptor<MedicationRecord> medicationRecordCaptor = ArgumentCaptor.forClass(MedicationRecord.class);

        // WHEN
        Medication returnedMedication = medicationService.addNewMedication(medication);

        // THEN
        Assertions.assertNotNull(returnedMedication);
        verify(medicationRepository).save(medicationRecordCaptor.capture());

        MedicationRecord record = medicationRecordCaptor.getValue();

        Assertions.assertNotNull(record);
        Assertions.assertEquals(record.getName(), medication.getName(), "Expected medication name to match.");
        Assertions.assertEquals(record.getId(), medication.getId(), "Expected medication id to match.");
        Assertions.assertEquals(record.getTimeOfDay(), medication.getTimeOfDay(), "Expected medication timeOfDay to match.");
        Assertions.assertEquals(record.getDosage(), medication.getDosage(), "Expected medication dosage to match.");
        Assertions.assertEquals(record.getAlertTime(), medication.getAlertTime(), "Expected medication alertTime to match.");
        Assertions.assertEquals(record.getAlertDays(), medication.getAlertDays(), "Expected medication alertDays to match.");
    }

    /** ------------------------------------------------------------------------
     *  medicationService.updateMedication
     *  ------------------------------------------------------------------------ **/
    @Test
    void updateMedication_withValidInput_updatesMedication() {
        //GIVEN
        String name = "Aspirin";
        String id = UUID.randomUUID().toString();
        String timeOfDay = "MORNING";
        String dosage = "1 pill";
        String alertTime = "8:00";
        List<String> alertDays = new ArrayList<>();
        alertDays.add("Monday");
        alertDays.add("Wednesday");
        alertDays.add("Friday");
        Medication medication = new Medication(name, id, timeOfDay, dosage, alertTime, alertDays);

        ArgumentCaptor<MedicationRecord> medicationRecordCaptor = ArgumentCaptor.forClass(MedicationRecord.class);
        when(medicationRepository.existsById(name)).thenReturn(true);

        // WHEN
        medicationService.updateMedication(medication);

        // THEN
        verify(medicationRepository).save(medicationRecordCaptor.capture());

        MedicationRecord record = medicationRecordCaptor.getValue();

        Assertions.assertNotNull(record);
        Assertions.assertEquals(record.getName(), medication.getName(), "Expected medication name to match.");
        Assertions.assertEquals(record.getId(), medication.getId(), "Expected medication id to match.");
        Assertions.assertEquals(record.getTimeOfDay(), medication.getTimeOfDay(), "Expected medication timeOfDay to match.");
        Assertions.assertEquals(record.getDosage(), medication.getDosage(), "Expected medication dosage to match.");
        Assertions.assertEquals(record.getAlertTime(), medication.getAlertTime(), "Expected medication alertTime to match.");
        Assertions.assertEquals(record.getAlertDays(), medication.getAlertDays(), "Expected medication alertDays to match.");
    }

    @Test
    void updateMedication_withInvalidInput_doesNotUpdateMedication() {
        // GIVEN
        Medication medication = new Medication(null, null, null, null, null, null);
        when(medicationRepository.existsById(medication.getId())).thenReturn(false);

        // WHEN
        medicationService.updateMedication(medication);

        // THEN
        verify(medicationRepository, never()).save(new MedicationRecord());
    }

    /** ------------------------------------------------------------------------
     *  medicationService.getAllMedications
     *  ------------------------------------------------------------------------ **/
    @Test
    void getAllMedications_returnsListOfMedication() {
        // GIVEN
        List<String> alertDays1 = new ArrayList<>();
        alertDays1.add("Monday");
        alertDays1.add("Wednesday");
        alertDays1.add("Friday");

        MedicationRecord record1 = new MedicationRecord();
        record1.setName("Aspirin");
        record1.setId(randomUUID().toString());
        record1.setTimeOfDay("MORNING");
        record1.setDosage("1 pill");
        record1.setAlertTime("8:00");
        record1.setAlertDays(alertDays1);

        List<String> alertDays2 = new ArrayList<>();
        alertDays2.add("Sunday");
        alertDays2.add("Thursday");

        MedicationRecord record2 = new MedicationRecord();
        record2.setName("Tylenol");
        record2.setId(randomUUID().toString());
        record2.setTimeOfDay("AFTERNOON");
        record2.setDosage("2 pillS");
        record2.setAlertTime("12:00");
        record2.setAlertDays(alertDays2);

        List<MedicationRecord> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);
        when(medicationRepository.findAll()).thenReturn(recordList);

        // WHEN
        List<Medication> medications = medicationService.getAllMedications();

        // THEN
        Assertions.assertNotNull(medications);
        Assertions.assertEquals(2, medications.size(), "Expected two medication records to be returned");

        for (Medication medication : medications) {
            if (medication.getName().equals(record1.getName())) {
                Assertions.assertEquals(record1.getName(), medication.getName(), "Expected medication name to match.");
                Assertions.assertEquals(record1.getId(), medication.getId(), "Expected medication id to match.");
                Assertions.assertEquals(record1.getTimeOfDay(), medication.getTimeOfDay(), "Expected medication timeOfDay to match.");
                Assertions.assertEquals(record1.getDosage(), medication.getDosage(), "Expected medication dosage to match.");
                Assertions.assertEquals(record1.getAlertTime(), medication.getAlertTime(), "Expected medication alertTime to match.");
                Assertions.assertEquals(record1.getAlertDays(), medication.getAlertDays(), "Expected medication alertDays to match.");
            } else if (medication.getName().equals(record2.getName())) {
                Assertions.assertEquals(record2.getName(), medication.getName(), "Expected medication name to match.");
                Assertions.assertEquals(record2.getId(), medication.getId(), "Expected medication id to match.");
                Assertions.assertEquals(record2.getTimeOfDay(), medication.getTimeOfDay(), "Expected medication timeOfDay to match.");
                Assertions.assertEquals(record2.getDosage(), medication.getDosage(), "Expected medication dosage to match.");
                Assertions.assertEquals(record2.getAlertTime(), medication.getAlertTime(), "Expected medication alertTime to match.");
                Assertions.assertEquals(record2.getAlertDays(), medication.getAlertDays(), "Expected medication alertDays to match.");
            } else {
                Assertions.fail("A medication was returned that was not in the records.");
            }
        }
    }

    /** ------------------------------------------------------------------------
     *  medicationService.deleteMedication
     *  ------------------------------------------------------------------------ **/
    @Test
    void deleteMedication_withValidId_deletedMedication() {
        // GIVEN
        String name = "Aspirin";
        String id = UUID.randomUUID().toString();
        List<String> alertDays = new ArrayList<>();
        alertDays.add("Monday");
        alertDays.add("Wednesday");
        alertDays.add("Friday");

        MedicationRecord record = new MedicationRecord();
        record.setName(name);
        record.setId(id);
        record.setTimeOfDay("MORNING");
        record.setDosage("1 pill");
        record.setAlertTime("8:00");
        record.setAlertDays(alertDays);
        medicationRepository.save(record);

        when(medicationRepository.findById(id)).thenReturn(Optional.of(record));
        Medication addedMedication = medicationService.findById(id);

        // WHEN
        medicationService.deleteMedication(id);

        // THEN
        verify(medicationRepository).delete(record);
        assertThat(addedMedication).isNotNull();
    }

    @Test
    void deleteMedication_withInvalidInput_doesNotDeleteMedication() {
        String id = UUID.randomUUID().toString();
        when(medicationRepository.findById(id)).thenReturn(Optional.empty());

        medicationService.deleteMedication(id);

        verify(medicationRepository, never()).delete(new MedicationRecord());
    }
}
