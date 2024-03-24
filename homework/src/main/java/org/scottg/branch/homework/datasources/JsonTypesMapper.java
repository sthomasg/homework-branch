package org.scottg.branch.homework.datasources;

import org.json.simple.JSONObject;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class JsonTypesMapper {
    private final static java.time.format.DateTimeFormatter formatter =  DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    static OffsetDateTime buildDate(final JSONObject jsonObject, final String key){
        final String val =  (String) jsonObject.get(key);
        OffsetDateTime dateTime = null;
        if(null != val){
            try {
                dateTime =OffsetDateTime.parse(val, formatter);
            }catch (DateTimeParseException ex){
                // swallow in our simple example and allow the null to return.
            }
        }
        return dateTime;
    }

    static URI buildURI(final JSONObject jsonObject, final String key){
        final String val =  (String) jsonObject.get(key);
        URI uri = null;
        if(null != val) {
            try {
                uri = URI.create(val);
            } catch (IllegalArgumentException e) {
                // swallow and return null.
            }
        }
        return uri;
    }
    static String buildEmail(final JSONObject jsonObject){
        return (String) jsonObject.get("email");
    }
}
