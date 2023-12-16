package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.ExampleCreateRequest;
import com.kenzie.appserver.controller.model.ExampleResponse;
import com.kenzie.appserver.service.ExampleService;

import com.kenzie.appserver.service.model.Medication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/example")
public class ExampleController {

    private ExampleService exampleService;

    ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ExampleResponse> get(@PathVariable("id") String id) {
//
//        Medication medication = exampleService.findById(id);
//        if (medication == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        ExampleResponse exampleResponse = new ExampleResponse();
//        exampleResponse.setId(medication.getId());
//        exampleResponse.setName(medication.getName());
//        return ResponseEntity.ok(exampleResponse);
//    }

//    @PostMapping
//    public ResponseEntity<ExampleResponse> addNewConcert(@RequestBody ExampleCreateRequest exampleCreateRequest) {
//        Medication medication = new Medication(randomUUID().toString(),
//                exampleCreateRequest.getName());
//        exampleService.addNewExample(medication);
//
//        ExampleResponse exampleResponse = new ExampleResponse();
//        exampleResponse.setId(medication.getId());
//        exampleResponse.setName(medication.getName());
//
//        return ResponseEntity.created(URI.create("/example/" + exampleResponse.getId())).body(exampleResponse);
//    }
}
