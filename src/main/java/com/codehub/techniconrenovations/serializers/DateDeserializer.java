package com.codehub.techniconrenovations.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.LoggerFactory;

public class DateDeserializer extends JsonDeserializer<Date> {
    
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DateDeserializer.class);
    
    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) {
        try {
            long timestamp = p.getLongValue();
            return new Date(timestamp);
        } catch (IOException ex) {
            logger.error("" + ex);
            return new Date(1, 0, 1);
        }
    }
}
