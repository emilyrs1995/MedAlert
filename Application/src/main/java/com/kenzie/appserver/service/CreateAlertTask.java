package com.kenzie.appserver.service;

import com.kenzie.appserver.service.model.Alert;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class CreateAlertTask implements Runnable {

    private final Integer timeToRepeatAlert;
    private final List<Alert> alerts;
    // private final AlertService alertService;

    public CreateAlertTask(Integer timeToRepeatAlert, List<Alert> alerts, AlertService alertService) {
        this.timeToRepeatAlert = timeToRepeatAlert;
        this.alerts = alerts;
        // this.alertService = alertService;
    }

    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();

//        Alert alert = new Alert();
//        alert.setAlertTime(LocalDateTime.now());
//
//        if(alert.getAlertTime().getYear() == now.getYear()
//                && alert.getAlertTime().getMonthValue() == now.getMonthValue()
//                && alert.getAlertTime().getDayOfMonth() == now.getDayOfMonth()
//                && alert.getAlertTime().getHour() == now.getHour()
//                && alert.getAlertTime().getMinute() == now.getMinute()) {


//            Frame frame = new Frame("Alert!");
//            JOptionPane.showMessageDialog(frame,
//                    String.format("It is time for you to take %s of your medication [%s]", alert.getDosage(), alert.getMedicationName()));

//        }
    }
}


//        for(Alert alert : alerts) {
//
//            if(alert.getAlertTime().getYear() == now.getYear()
//                    && alert.getAlertTime().getMonthValue() == now.getMonthValue()
//                    && alert.getAlertTime().getDayOfMonth() == now.getDayOfMonth()
//                    && alert.getAlertTime().getHour() == now.getHour()
//                    && alert.getAlertTime().getMinute() == now.getMinute()) {
//
//
//                Frame frame = new Frame("Alert!");
//                JOptionPane.showMessageDialog(frame,
//                        String.format("It is time for you to take %s of your medication [%s]", alert.getDosage(), alert.getMedicationName()));
//
//                System.out.println("I am running. The time is " + now + " and the alert now is " + alert.getMedicationName() + " the alert time is " + alert.getAlertTime());
//            }
//        }
// "2023-12-21T17:13:21.820Z"