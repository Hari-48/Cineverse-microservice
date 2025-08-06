package com.hari.tamil_movies.batchProcessing.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class CustomLineMapper extends DefaultLineMapper<DataRecord> {
    private static final Logger log = LoggerFactory.getLogger(CustomLineMapper.class);

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final DateTimeFormatter dateFormatterSub = DateTimeFormatter.ofPattern("d/M/yyyy");
    final ArrayList<String> fields;
    final String delimiter;


    public CustomLineMapper(ArrayList<String> fields, String delimiter) {
        this.fields = fields;
        this.delimiter = delimiter;
    }


//
//    | Step                 | Action                                                   |
//            | -------------------- | -------------------------------------------------------- |
//            | 🔄 `mapLine(...)`    | Called for each line in file                             |
//            | 📦 `lineToMap(...)`  | Converts line into a map using provided field names      |
//            | 🧾 `DataRecord`      | Holds line number and map of column-value                |
//            | 📤 Sent to processor | You can then convert it to an entity like `MatchDetails` |



    // called each line from the file

    @Override
    public DataRecord mapLine(String line, int lineNumber) throws Exception {

//        log.info("Line Number in csvFile : {}",lineNumber-1);

//        log.info("Data present in line number {} is {}",lineNumber-1,line);

        DataRecord record = new DataRecord();
        record.setLineNumber(lineNumber);
        record.setDataMap(lineToMap(fields, line, delimiter));

        log.info("Record :{}",record);



        return record;
    }


    // convert the lone to map using provided field names
    public static HashMap<String, String> lineToMap(ArrayList<String> fields, String line, String delimiter) {

//
//        log.info("FIELDS :{}",fields);
//        log.info("LINE :{}",line);
//

        HashMap<String, String> dataMap = new HashMap<>();
        String[] lineArr = line.split(Pattern.quote(delimiter) + "(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
//
//        log.info("line array length :{}",lineArr.length);
//
//        log.info("line arr :{}",lineArr);

        for (int i = 0; i < fields.size(); i++) {
            String field = fields.get(i);

//            log.info("field :{}", field);

            if (i < lineArr.length) {
                if (lineArr[i].isEmpty() || lineArr[i] == null)
                    dataMap.put(field, null);
                else {
//                    log.info("Value:{}",lineArr[i]);
                    dataMap.put(field, lineArr[i]);
                }
            } else {
                dataMap.put(field, null);
            }
        }
        log.info("dataMap {}", dataMap);
        return dataMap;
    }

}
