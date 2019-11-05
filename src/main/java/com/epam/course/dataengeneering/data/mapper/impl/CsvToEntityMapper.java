package com.epam.course.dataengeneering.data.mapper.impl;

import com.epam.course.dataengeneering.data.mapper.ToEntityMapper;
import com.epam.course.dataengeneering.model.Stuff;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class CsvToEntityMapper implements ToEntityMapper {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-dd-MM");
    private static final int COLUMNS_SIZE = 18;

    @Override
    @SneakyThrows
    public Stuff map(List<String> entityData) {
        if (entityData.size() != COLUMNS_SIZE){
            throw new IllegalArgumentException("Source columns size doesn't match with entity columns: " + entityData);
        }

        return Stuff.builder()
                .dateReceived(DATE_FORMAT.parse(entityData.get(0)))
                .productName(entityData.get(1))
                .subProduct(entityData.get(2))
                .issue(entityData.get(3))
                .subIssue(entityData.get(4))
                .consumerComplaintNarrative(entityData.get(5))
                .companyPublicResponse(entityData.get(6))
                .company(entityData.get(7))
                .stateName(entityData.get(8))
                .zipCode(entityData.get(9))
                .tags(entityData.get(10))
                .consumerConsentProvided(entityData.get(11))
                .submittedVia(entityData.get(12))
                .dateSentToCompany(DATE_FORMAT.parse(entityData.get(13)))
                .companyResponseToConsumer(entityData.get(14))
                .timelyResponse(entityData.get(15).equalsIgnoreCase("Yes"))
                .consumerDisputed(entityData.get(16).equalsIgnoreCase("Yes"))
                .complaintID(Long.parseLong(entityData.get(17)))
                .build();
    }
}
