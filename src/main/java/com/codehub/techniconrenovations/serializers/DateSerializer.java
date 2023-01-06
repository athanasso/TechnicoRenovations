package com.codehub.techniconrenovations.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Date;
import org.slf4j.LoggerFactory;

public class DateSerializer extends JsonSerializer<Date> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DateSerializer.class);

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider){
        try {
            jsonGenerator.writeString(date.toString());
        } catch (IOException ex) {
            logger.error(" "+ ex);
        }
    }
}

