package com.example.animerating.e2e.preparedActions;

import com.microsoft.playwright.*;
import org.springframework.stereotype.Component;

@Component
public class TestingAction {

    public void RegisterAndLogin(Page page, String webUrl) {
        //Register action
        page.navigate(webUrl + "/register");

        page.type("input[name=username]", "testNewUser");
        page.type("input[name=password]", "testPassword");
        page.click("button[type=submit]");

        if ((webUrl + "/register?userExists=true").equals(page.url())) {
            page.navigate(webUrl + "/login");
            page.type("input[name=username]", "testNewUser");
            page.type("input[name=password]", "testPassword");
            page.click("button[type=submit]");
        }

        //Login action
        page.type("input[name=username]", "testNewUser");
        page.type("input[name=password]", "testPassword");
        page.click("button[type=submit]");
    }

    public void RegisterTwoUsers(Page page, String webUrl) {
        //Register firstUser
        page.navigate(webUrl + "/register");

        page.type("input[name=username]", "firstUser");
        page.type("input[name=password]", "firstUserPassword");
        page.click("button[type=submit]");

        //Register secondUser
        page.navigate(webUrl + "/register");

        page.type("input[name=username]", "secondUser");
        page.type("input[name=password]", "secondUserPassword");
        page.click("button[type=submit]");
    }

    public void LoginFirstUser(Page page, String webUrl) {
        //Login action
        page.navigate(webUrl + "/login");

        page.type("input[name=username]", "firstUser");
        page.type("input[name=password]", "firstUserPassword");
        page.click("button[type=submit]");
    }

    public void LoginSecondUser(Page page, String webUrl) {
        //Login action
        page.navigate(webUrl + "/login");

        page.type("input[name=username]", "secondUser");
        page.type("input[name=password]", "secondUserPassword");
        page.click("button[type=submit]");
    }

    public void rateSixAnime(Page page, String webUrl) {
        int initialRating = 1;

        //rate six anime
        for (int i = 0; i <= 6; i++) {
            page.navigate(webUrl + "/");
            page.click("#star_icon_toggle");
            page.click("label[for=artStyleStar" + initialRating + "]");
            page.click("label[for=animationStar" + initialRating + "]");
            page.click("label[for=storyStar" + initialRating + "]");
            page.click("label[for=charactersStar" + initialRating + "]");
            page.click("#add_to_watched_button");
            page.navigate(webUrl + "/user_list");

            initialRating++;
        }
        page.navigate(webUrl + "/top_rated_anime");
    }
}
