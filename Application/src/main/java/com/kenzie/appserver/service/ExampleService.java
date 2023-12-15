package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.model.ExampleRecord;
import com.kenzie.appserver.repositories.ExampleRepository;
import com.kenzie.appserver.service.model.Medication;

import org.springframework.stereotype.Service;

@Service
public class ExampleService {
    private ExampleRepository exampleRepository;

    public ExampleService(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    // Emily S (12/15/23) - commented this out so that it wouldn't throw compiler errors
    /*public Medication findById(String id) {
        Medication medicationFromBackend = exampleRepository
                .findById(id)
                .map(example -> new Medication(example.getId(), example.getName()))
                .orElse(null);

        return medicationFromBackend;
    }*/

    public Medication addNewExample(Medication medication) {
        ExampleRecord exampleRecord = new ExampleRecord();
        exampleRecord.setId(medication.getId());
        exampleRecord.setName(medication.getName());
        exampleRepository.save(exampleRecord);
        return medication;
    }
}
