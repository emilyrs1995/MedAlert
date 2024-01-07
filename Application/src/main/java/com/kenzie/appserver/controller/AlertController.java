package com.kenzie.appserver.controller;

import com.kenzie.appserver.service.AlertService;
import com.kenzie.appserver.service.model.Alert;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alert")
public class AlertController {
    private AlertService alertService;

    AlertController(AlertService alertService){
        this.alertService = alertService;
    }
    @GetMapping("/alertStatus")
    public ResponseEntity<List<String>> checkAlertStatus(){
        List<String> alerts = alertService.checkForAlert();

        if(alerts == null || alerts.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(alerts);
    }
}
