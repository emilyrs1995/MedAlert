package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.AlertRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface AlertRepository extends CrudRepository<AlertRecord, String> {

    // Emily S. 12/21 - used to save/delete Alert objects in the table
}
