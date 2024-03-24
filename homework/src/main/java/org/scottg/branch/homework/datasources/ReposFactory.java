package org.scottg.branch.homework.datasources;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.scottg.branch.github.model.Repo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReposFactory {

    public List<Repo> buildUserReposFromResponse(final String json) {
        final JSONArray jsonArray = (JSONArray) JSONValue.parse(json);
        return jsonArray.stream().map(jsonObj -> buildRepo((JSONObject) jsonObj)).toList();
    }

    protected Repo buildRepo(final JSONObject jsonObject) {
        final Repo repo = new Repo();
        repo.setName((String) jsonObject.get("name"));
        repo.setUrl(JsonTypesMapper.buildURI(jsonObject, "html_url"));
        return repo;
    }

}
