package com.example.animerating.e2e;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomAnimeRatingTest {

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

    @BeforeEach
    public void register() {
        page.navigate("http://localhost:8080/register");

        page.type("input[name=username]", "testUser");
        page.type("input[name=password]", "testPassword");
        page.click("button[type=submit]");

        page.waitForLoadState(LoadState.NETWORKIDLE);

        page.type("input[name=username]", "testUser");
        page.type("input[name=password]", "testPassword");
        page.click("button[type=submit]");

        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    @AfterAll
    public static void tearDown() {
        if (browser != null) {
            browser.close();
        }
    }

    @Test
    @Order(1)
    public void ratingRandomAnimeTest() {
        page.click("#star_icon_toggle");

        page.click("#add_to_watched_button");
        page.waitForLoadState(LoadState.NETWORKIDLE);

        assertEquals("http://localhost:8080/", page.url());
    }
}
