package com.kenzie.appserver;


import com.kenzie.appserver.service.MedicationService;
import com.kenzie.appserver.service.model.Medication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;


@Component
public class ApplicationStartUpListener {

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Perform any application start-up tasks
    }
}
