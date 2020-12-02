package com.filernm.bl;

import com.filernm.model.ParsedFile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

@Service
public class FileNameGenerator {
    private final Random random = new Random();
    private final String SEPARATOR = "-";
    private final String ZERO = "0";

    public String generate(ParsedFile pf) {
        LocalDateTime dateTime = convertToLocalDateTimeViaInstant(pf.getDateCreated());
        StringBuilder sb = new StringBuilder();
        sb.append(dateTime.getYear()).append(SEPARATOR);
        if(dateTime.getMonthValue() < 10)
            sb.append(ZERO);
        sb.append(dateTime.getMonthValue()).append(SEPARATOR);
        if(dateTime.getDayOfMonth() < 10)
            sb.append(ZERO);
        sb.append(dateTime.getDayOfMonth()).append(SEPARATOR);
        if(dateTime.getHour() < 10)
            sb.append(ZERO);
        sb.append(dateTime.getHour());
        if(dateTime.getMinute() < 10)
            sb.append(ZERO);
        sb.append(dateTime.getMinute());
        if(dateTime.getSecond() < 10)
            sb.append(ZERO);
        sb.append(dateTime.getSecond());

        sb.append(SEPARATOR).append(randomNumber());

        sb.append(".").append(pf.getExtension());

        return sb.toString();
    }

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private int randomNumber(){
        return random.nextInt(99999 - 10000) + 10000;
    }

}
