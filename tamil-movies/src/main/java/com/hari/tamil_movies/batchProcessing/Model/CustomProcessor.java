package com.hari.tamil_movies.batchProcessing.Model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Slf4j
public class CustomProcessor implements ItemProcessor<DataRecord, DataRecord> {


    private String filename;

    public CustomProcessor() {}
    public CustomProcessor(String filename) {
        this.filename = filename;
    }

    @Override
    public DataRecord process(final DataRecord dataRecord) throws Exception {
        StringBuilder errors = new StringBuilder();
        validateData(dataRecord, errors);
        return dataRecord;
    }

    private void validateData(DataRecord dataRecord, StringBuilder errors) {
        dataRecord.getDataMap().forEach((key, value) -> {
            if (key.equalsIgnoreCase("gender")) {
                String updatedValue = value.toUpperCase(Locale.ROOT);
                dataRecord.getDataMap().put(key, updatedValue);
            }
        });
    }
}



