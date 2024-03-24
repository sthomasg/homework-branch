package org.scottg.branch.homework.datasources;

import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.scottg.branch.github.model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class UserFactoryTest {


    @Test
    public void testBuildUser() throws Exception{
        String jsonString = readJsonString();
        OffsetDateTime expected =
                OffsetDateTime.of(2011, 01, 25, 18, 44, 36, 0, ZoneOffset.UTC);
        UserFactory testSubject = new UserFactory();

        // method under test:
        User user = testSubject.buildUser(jsonString);

        assertNotNull(user);
        assertEquals("octocat", user.getUserName());
        assertEquals("The Octocat", user.getDisplayName());
        assertEquals("octocat@example.com", user.getEmail());
        assertEquals("San Francisco", user.getGeoLocation());
        assertEquals(URI.create("https://avatars.githubusercontent.com/u/583231?v=4").toURL(), user.getAvatar().toURL());
        assertEquals(expected.toEpochSecond(), user.getCreatedAt().toEpochSecond());
        assertEquals(URI.create("https://api.github.com/users/octocat").toURL(), user.getUrl().toURL());
    }

    @Test
    public void testNull(){
        UserFactory testSubject = new UserFactory();
        assertDoesNotThrow(() -> {
            User user = testSubject.buildUser(null);
            // I elected to have an empty user in this case.
            assertNotNull(user);
        });
    }

    @Test
    public void testEmpty(){
        UserFactory testSubject = new UserFactory();
        assertDoesNotThrow(() -> {
            User user = testSubject.buildUser("   ");
            // I elected to have an empty user in this case.
            assertNotNull(user);
        });
    }

    @Test
    public void testEmptyjson(){
        UserFactory testSubject = new UserFactory();
        assertDoesNotThrow(() -> {
            User user = testSubject.buildUser("{}");
            // I elected to have an empty user in this case.
            assertNotNull(user);
        });
    }

    public String readJsonString() throws Exception{
        StringBuffer buffer = new StringBuffer();
        URL fileURI = this.getClass().getClassLoader().getResource("json/octocat.json");
        File jsonFile = Path.of(fileURI.toURI()).toFile();
        JSONParser jsonParser = new JSONParser();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFile))){
          for(String line; (line = bufferedReader.readLine()) != null;){
              buffer.append(line);
          }
        }
        return buffer.toString();
    }


}