package com.example.animerating.e2e;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Test;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

public class LoginAndRegisterTest {
    private Browser browser;
    private Page page;

    @BeforeEach
    public void setUp() {
        try {
            browser = Playwright.create().chromium().launch();
            BrowserContext context = browser.newContext();
            page = context.newPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        if (browser != null) {
            browser.close();
        }
    }

    @Test
    @Order(1)
    public void testWrongPasswordRegistration() {
        browser = Playwright.create().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext context = browser.newContext();
        page = context.newPage();

        page.navigate("http://localhost:8080/register");

        page.type("input[name=username]", "testUser");
        page.type("input[name=password]", "123");
        page.click("button[type=submit]");

        Assertions.assertEquals("http://localhost:8080/register", page.url());
    }

    @Test
    @Order(2)
    public void testRegistration() {
        browser = Playwright.create().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext context = browser.newContext();
        page = context.newPage();

        page.navigate("http://localhost:8080/register");

        page.type("input[name=username]", "testNewUser");
        page.type("input[name=password]", "testPassword");
        page.click("button[type=submit]");

        Assertions.assertEquals("http://localhost:8080/login", page.url());
    }

    @Test
    @Order(3)
    public void testLogin() {
        browser = Playwright.create().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext context = browser.newContext();
        page = context.newPage();

        page.navigate("http://localhost:8080/login");

        page.type("input[name=username]", "notRegisteredUser");
        page.type("input[name=password]", "testPassword");
        page.click("button[type=submit]");

        Assertions.assertEquals("http://localhost:8080/login?error", page.url());
    }

    @Test
    @Order(4)
    public void testRegisterUsernameExist() {
        browser = Playwright.create().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext context = browser.newContext();
        page = context.newPage();

        page.navigate("http://localhost:8080/register");

        page.type("input[name=username]", "testNewUser");
        page.type("input[name=password]", "testPassword");
        page.click("button[type=submit]");

        Assertions.assertEquals("http://localhost:8080/register?userExist=true", page.url());
    }
}
