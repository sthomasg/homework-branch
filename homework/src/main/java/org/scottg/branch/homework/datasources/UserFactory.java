package org.scottg.branch.homework.datasources;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.scottg.branch.github.model.User;
import org.springframework.stereotype.Component;


@Component
public class UserFactory {

    public final User buildUser(final String json){
        final User user = new User();
        if(StringUtils.isNotEmpty(json)) {
            final JSONObject jsonObject = (JSONObject) JSONValue.parse(json);
            if(null != jsonObject) {
                user.setUserName((String) jsonObject.get("login"));
                user.setDisplayName((String) jsonObject.get("name"));
                user.setAvatar(JsonTypesMapper.buildURI(jsonObject, "avatar_url"));
                user.setGeoLocation((String) jsonObject.get("location"));
                user.email(JsonTypesMapper.buildEmail(jsonObject));
                user.setUrl(JsonTypesMapper.buildURI(jsonObject, "url"));
                user.setCreatedAt(JsonTypesMapper.buildDate(jsonObject, "created_at"));
            }
        }
        return user;
    }
}
