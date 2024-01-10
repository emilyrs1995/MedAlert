package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.AlertRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface AlertRepository extends CrudRepository<AlertRecord, String> {

}
