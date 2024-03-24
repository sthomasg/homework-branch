package org.scottg.branch.homework.datasources;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class JsonTypesMapperTest {

    @Test
    public void testEmail_happy(){
        String expected = "scott@testme.com";
        String json = "{\"email\":\"" + expected + "\"}";
        JSONObject jsonObjectdecode =parseToObject(json);
        assertEquals(expected, JsonTypesMapper.buildEmail(jsonObjectdecode));
    }

    @Test
    public void testEmail_happyOnNull(){
        assertDoesNotThrow(() -> {
            String expected = null;
            String json = "{\"email\":null}";
            JSONObject jsonObjectdecode = parseToObject(json);
            assertEquals(expected, JsonTypesMapper.buildEmail(jsonObjectdecode));
        });
    }

    @Test
    public void testURI_happy(){
        String expected = "https://api.github.com/users/octocat";
        String json = "{\"url\":\"" + expected + "\"}";
        JSONObject jsonObjectdecode =parseToObject(json);
        assertEquals(expected, JsonTypesMapper.buildURI(jsonObjectdecode, "url").toString());
    }

    @Test
    public void testURI_invalid(){
        assertDoesNotThrow(() -> {
            String expected = null;
            String json = "{\"url\":\"bad:\\ur-el\"}";
            JSONObject jsonObjectdecode = parseToObject(json);
            assertNull(JsonTypesMapper.buildURI(jsonObjectdecode, "url"));
        });
    }

    //"created_at": "2011-01-25T18:44:36Z",
    @Test
    public void testDateTime_happy(){
        OffsetDateTime expected = OffsetDateTime.of(2011, 01, 25, 18, 44, 36, 0, ZoneOffset.UTC);
        String json = "{\"created_at\":\"2011-01-25T18:44:36Z\"}";
        JSONObject jsonObjectdecode =parseToObject(json);
        assertEquals(expected.toEpochSecond(), JsonTypesMapper.buildDate(jsonObjectdecode, "created_at").toEpochSecond());
    }

    private JSONObject parseToObject(final String rawJSON){
        return (JSONObject) JSONValue.parse(rawJSON);
    }
}