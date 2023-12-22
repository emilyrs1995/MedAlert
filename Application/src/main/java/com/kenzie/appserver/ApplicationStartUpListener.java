package com.kenzie.appserver;

import com.kenzie.appserver.service.AlertService;
import com.kenzie.appserver.service.model.Alert;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ApplicationStartUpListener {

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Perform any application start-up tasks


        // Emily S. 12/21 - saving in case we decide to use the startUpListener
//        AlertService alertService = event.getApplicationContext()
//                .getBean(AlertService.class);
//        List<Alert> alerts = event.getApplicationContext().getBean(List.class);
//
//        List<Alert> allAlerts = alertService.getAlertList();
//        alerts.addAll(allAlerts);
    }
}
