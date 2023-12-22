package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDateTime;


public class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {

    // Emily S. 12/21 - Used by the DynamoDb table "Alerts" to convert/unconvert string and LocalDateTime object
    @Override
    public String convert(LocalDateTime time) {
        return time.toString();
    }

    @Override
    public LocalDateTime unconvert(String time) {
        return LocalDateTime.parse(time);
    }
}
