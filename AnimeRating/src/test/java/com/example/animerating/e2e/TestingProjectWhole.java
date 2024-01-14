package com.example.animerating.e2e;

import com.example.animerating.e2e.preparedActions.TestingAction;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.example.animerating.e2e.preparedActions")
public class TestingProjectWhole {

    @Autowired
    private TestingAction actions;
    private static Browser browser;
    private static Page page;

    @BeforeAll
    public static void setUp() {
        try {
            browser = Playwright.create().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            page = context.newPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void tearDown() {
        if (browser != null) {
            browser.close();
        }
    }

    @Test
    @Order(1)
    public void MainPageFunctionalityTest(){
        actions.RegisterAndLogin(page);
        //Rate random on main page
        page.click("#star_icon_toggle");
        page.click("label[for=artStyleStar1]");
        page.click("label[for=animationStar1]");
        page.click("label[for=storyStar1]");
        page.click("label[for=charactersStar1]");
        page.click("#add_to_watched_button");
        page.navigate("http://localhost:8080/user_list");
        page.waitForTimeout(1500);
        //Saving to watchLater list
        page.navigate("http://localhost:8080/");
        page.click("#save_anime_icon");
        page.click("#save_anime_icon");
        page.navigate("http://localhost:8080/user_list");
        page.waitForTimeout(1500);
        //Refresh random anime
        page.navigate("http://localhost:8080/");
        page.click("#refresh_anime_icon");
        page.waitForTimeout(1000);
    }

    @Test
    @Order(2)
    public void UserListFunctionalityTest(){
        //At saved anime to currentlyWatching list
        page.navigate("http://localhost:8080/user_list");
        page.click("#add_to_watching_anime");
        page.waitForTimeout(1000);
        page.click("#delete_anime");
        page.waitForTimeout(1000);
        //Rate anime in currentlyWatching list
        page.click("#rate_watching_anime");
        page.click("label[for=artStyleStar5]");
        page.click("label[for=animationStar9]");
        page.click("label[for=storyStar1]");
        page.click("label[for=charactersStar2]");
        page.click("#submit_anime_edit");
        page.waitForTimeout(1000);
        //Edit anime rating in alreadyWatched List
        page.click("#edit_anime_rating");
        page.click("label[for=artStyleStar10]");
        page.click("label[for=animationStar10]");
        page.click("label[for=storyStar10]");
        page.click("label[for=charactersStar10]");
        page.click("#submit_anime_edit");
        page.waitForTimeout(1500);
    }

    @Test
    @Order(3)
    public void CategorySearchingFunctionalityTest(){
        //Search Anime by category
        page.navigate("http://localhost:8080/anime_categories");
        page.click("#category_adventure");
        page.click("#category_fantasy");
        page.click("#category_comedy");
        page.click("#search-input");
        page.waitForTimeout(1000);
        //Rate searched anime
        page.evaluate("document.querySelector('#anime_poster').click();");
        page.waitForTimeout(1000);
        page.click("#star_icon_toggle");
        page.click("label[for=artStyleStar10]");
        page.click("label[for=animationStar10]");
        page.click("label[for=storyStar10]");
        page.click("label[for=charactersStar10]");
        page.click("#add_to_watched_button");
        page.navigate("http://localhost:8080/user_list");
        page.waitForTimeout(1000);
        //Save searched anime
        page.navigate("http://localhost:8080/anime_categories");
        page.click("#category_adventure");
        page.click("#search-input");
        page.evaluate("document.querySelector('#anime_poster').click();");
        page.waitForTimeout(1000);
        page.click("#save_anime_icon");
        page.navigate("http://localhost:8080/user_list");
        page.waitForTimeout(1500);
    }

    @Test
    @Order(4)
    public void ShowTopSixAnimeAcrossTwoUsersTest(){
        //Register two users
        actions.RegisterTwoUsers(page);
        //Rate six anime as firstUser
        actions.LoginFirstUser(page);
        actions.rateSixAnime(page);
        //Rate six anime as secondUser
        actions.LoginSecondUser(page);
        actions.rateSixAnime(page);
        //Show the TopRated page
        page.navigate("http://localhost:8080/top_rated_anime");
        page.waitForTimeout(1500);
    }
}
