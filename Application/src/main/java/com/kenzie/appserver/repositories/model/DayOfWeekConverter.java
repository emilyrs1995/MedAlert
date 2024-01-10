package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.List;

public class DayOfWeekConverter implements DynamoDBTypeConverter<String, List<DayOfWeek>> {

    /*Citing this question on StackOverflow for an example of how to use the ObjectMapper - AWS DynamoDB: Could not unconvert attribute error
    * URL - https://stackoverflow.com/questions/47926783/aws-dynamodb-could-not-unconvert-attribute-error */

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convert(List<DayOfWeek> days) {
        try {
            return objectMapper.writeValueAsString(days);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Unable to parse JSON");
    }

    @Override
    public List<DayOfWeek> unconvert(String dayString) {
        try {
            return objectMapper.readValue(dayString, new TypeReference<List<DayOfWeek>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
