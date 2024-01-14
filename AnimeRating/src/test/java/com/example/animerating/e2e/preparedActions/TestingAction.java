package com.example.animerating.e2e.preparedActions;

import com.microsoft.playwright.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Component
public class TestingAction {

    public void RegisterAndLogin(Page page) {
        //Register action
        page.navigate("http://localhost:8080/register");

        page.type("input[name=username]", "testNewUser");
        page.type("input[name=password]", "testPassword");
        page.click("button[type=submit]");

        if("http://localhost:8080/register?userExists=true".equals(page.url())){
            page.navigate("http://localhost:8080/login");
            page.type("input[name=username]", "testNewUser");
            page.type("input[name=password]", "testPassword");
            page.click("button[type=submit]");
        }

        //Login action
        page.type("input[name=username]", "testNewUser");
        page.type("input[name=password]", "testPassword");
        page.click("button[type=submit]");
    }

    public void RegisterTwoUsers(Page page) {
        //Register firstUser
        page.navigate("http://localhost:8080/register");

        page.type("input[name=username]", "firstUser");
        page.type("input[name=password]", "firstUserPassword");
        page.click("button[type=submit]");

        //Register secondUser
        page.navigate("http://localhost:8080/register");

        page.type("input[name=username]", "secondUser");
        page.type("input[name=password]", "secondUserPassword");
        page.click("button[type=submit]");
    }

    public void LoginFirstUser(Page page) {
        //Login action
        page.navigate("http://localhost:8080/login");

        page.type("input[name=username]", "firstUser");
        page.type("input[name=password]", "firstUserPassword");
        page.click("button[type=submit]");
    }

    public void LoginSecondUser(Page page) {
        //Login action
        page.navigate("http://localhost:8080/login");

        page.type("input[name=username]", "secondUser");
        page.type("input[name=password]", "secondUserPassword");
        page.click("button[type=submit]");
    }

    public void rateSixAnime(Page page) {
        int initialRating = 1;

        //rate six anime
        for (int i = 0; i <= 6; i++) {
            page.navigate("http://localhost:8080/");
            page.click("#star_icon_toggle");
            page.click("label[for=artStyleStar" + initialRating + "]");
            page.click("label[for=animationStar" + initialRating + "]");
            page.click("label[for=storyStar" + initialRating + "]");
            page.click("label[for=charactersStar" + initialRating + "]");
            page.click("#add_to_watched_button");
            page.navigate("http://localhost:8080/user_list");

            initialRating ++;
        }
        page.navigate("http://localhost:8080/top_rated_anime");
    }
}
