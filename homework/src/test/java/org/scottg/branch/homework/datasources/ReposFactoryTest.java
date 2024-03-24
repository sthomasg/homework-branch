package org.scottg.branch.homework.datasources;

import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;
import org.scottg.branch.github.model.Repo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ReposFactoryTest {


    @Test
    public void testBuildReposFromJSON() throws Exception{
        final ReposFactory testSubject = new ReposFactory();
        List<String> expectedNames = List.of(
                "boysenberry-repo-1",
                "git-consortium",
                "hello-worId",
                "Hello-World",
                "linguist",
                "octocat.github.io",
                "Spoon-Knife",
                "test-repo1"
        );

        assertDoesNotThrow(() -> {
            final List<Repo> repoList = testSubject.buildUserReposFromJSON(readJsonString());
            assertNotNull(repoList);
            assertEquals(8, repoList.size());
            Map<String, Repo> repoMap = repoList.stream().collect(Collectors.toMap(Repo::getName, Function.identity()));
            expectedNames.stream().forEach(name -> assertTrue(repoMap.containsKey(name)));
        });
    }

    @Test
    public void testEmtpyJSON(){
        final ReposFactory testSubject = new ReposFactory();
        assertDoesNotThrow(() -> {
            final List<Repo> repoList = testSubject.buildUserReposFromJSON("{}");
            assertNotNull(repoList);
            assertTrue(repoList.isEmpty());
        });
    }


    public String readJsonString() throws Exception{
        final StringBuffer buffer = new StringBuffer();
        final URL fileURI = this.getClass().getClassLoader().getResource("json/repos.json");
        final File jsonFile = Path.of(fileURI.toURI()).toFile();
        final JSONParser jsonParser = new JSONParser();
        try(final BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFile))){
            for(String line; (line = bufferedReader.readLine()) != null;){
                buffer.append(line);
            }
        }
        return buffer.toString();
    }

}