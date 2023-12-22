package com.kenzie.appserver.service;

import com.kenzie.appserver.service.model.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;



// Emily S. 12/21 - saving in case we decide to go this route down the line

@Service
public class AsynchronousService {

//    @Value("${time.to.repeat.medication.alert}")
//    private Integer timeToRepeatAlert;
//
//    @Autowired
//    private TaskExecutor executorService;
//
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    public void executeAsynchronously() {
//        AlertService alertService = applicationContext.getBean(AlertService.class);
//        List<Alert> alerts = applicationContext.getBean(List.class);
//        executorService.execute(new CreateAlertTask(timeToRepeatAlert, alerts, alertService));
//    }
}
