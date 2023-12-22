package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocalDateTimeListConverter implements DynamoDBTypeConverter<String, List<LocalDateTime>> {


    // Emily S. 12/21 - saving in case we revert back to using the LocalDateTime with the converter class
    @Override
    public String convert(List<LocalDateTime> times) {
        return times.toString();
    }

    @Override
    public List<LocalDateTime> unconvert(String timeString) {
        String strippedString = timeString.replace("[", "").replace("]", "");
        String[] timeArray = strippedString.split(",");

        List<LocalDateTime> convertedTimes = new ArrayList<>();

        for (String s : timeArray) {
            convertedTimes.add(LocalDateTime.parse(s.trim()));
        }

        return convertedTimes;
    }
}
