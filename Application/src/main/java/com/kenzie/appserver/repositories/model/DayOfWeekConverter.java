package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class DayOfWeekConverter implements DynamoDBTypeConverter<String, List<DayOfWeek>> {

    // Emily S. 12/21 - Used by the DynamoDb table "Alerts" to convert/unconvert string and DayOfWeek object
    @Override
    public String convert(List<DayOfWeek> days) {
        return days.toString();
    }

    @Override
    public List<DayOfWeek> unconvert(String dayString) {
        String strippedString = dayString.replace("[", "").replace("]", "");
        String[] dayArray = strippedString.split(",");

        List<DayOfWeek> convertedDays = new ArrayList<>();

        for (String day : dayArray) {
            convertedDays.add(DayOfWeek.valueOf(day.trim()));
        }

        return convertedDays;
    }

}
