package org.scottg.branch.homework.datasources;


import org.scottg.branch.github.model.Repo;
import org.scottg.branch.github.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;


@Component
public class UserDataSourceImpl implements UserDataSource {

    public UserDataSourceImpl(@Autowired final ThreadPoolTaskExecutor taskExecutor,
                              @Autowired UserFactory userFactory, @Autowired ReposFactory reposFactory) {
        this.taskExecutor = taskExecutor;
        this.userFactory = userFactory;
        this.reposFactory = reposFactory;
    }

    private final String usersURL
            = "https://api.github.com/users/";


    private final ThreadPoolTaskExecutor taskExecutor;

    private final UserFactory userFactory;
    private final ReposFactory reposFactory;


    @Override
    public User retrieveUserByUserName(final String userName) {
        final RestTemplate restTemplate = new RestTemplate();
        User user = null;
        try {
            CompletableFuture<User> userFuture = CompletableFuture
                    .supplyAsync(() -> restTemplate.getForEntity(usersURL + userName, String.class), taskExecutor)
                    .thenApply(responseEntity -> userFactory.buildUser(responseEntity.getBody()));

            CompletableFuture<List<Repo>> reposFuture = CompletableFuture
                    .supplyAsync(() -> restTemplate.getForEntity(usersURL + userName + "/repos", String.class), taskExecutor)
                    .thenApply(responseEntity -> reposFactory.buildUserReposFromJSON(responseEntity.getBody()));
            user = userFuture.join();
            user.setRepos(reposFuture.join());
        } catch (CompletionException completionException) {
            Throwable cause = completionException.getCause();
            if (cause instanceof HttpClientErrorException) {
                throw (HttpClientErrorException) cause;
            } else {
                throw new HttpServerErrorException(HttpStatusCode.valueOf(500));
            }
        }

        return user;
    }

}
