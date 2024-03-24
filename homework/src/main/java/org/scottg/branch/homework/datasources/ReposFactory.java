package org.scottg.branch.homework.datasources;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.scottg.branch.github.model.Repo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReposFactory {

    public List<Repo> buildUserReposFromJSON(final String json) {
        final List<Repo> repoList = new ArrayList<>();
        if(StringUtils.isNotEmpty(json)) {
            final Object object = JSONValue.parse(json);
            if(object instanceof JSONArray){
                final JSONArray jsonArray = (JSONArray) object;
                if(null != jsonArray) {
                    jsonArray.forEach(jsonObj -> {
                        final Repo repo = buildRepo((JSONObject) jsonObj);
                        if (null != repo) repoList.add(repo);
                    });
                }
            }
        }
        return repoList;
    }

    private Repo buildRepo(final JSONObject jsonObject) {
        Repo repo = null;
        if(null != jsonObject) {
            repo = new Repo();
            repo.setName((String) jsonObject.get("name"));
            repo.setUrl(JsonTypesMapper.buildURI(jsonObject, "html_url"));
        }
        return repo;
    }

}
