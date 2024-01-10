package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.MedicationRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface MedicationRepository extends CrudRepository<MedicationRecord, String> {
}
