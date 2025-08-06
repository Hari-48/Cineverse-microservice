package com.hari.tamil_movies.batchProcessing.Model;

import lombok.Data;

import java.util.HashMap;

public @Data
class DataRecord {
    private Integer lineNumber;
    private HashMap<String, String> dataMap;

}
