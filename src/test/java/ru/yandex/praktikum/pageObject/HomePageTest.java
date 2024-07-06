package ru.yandex.praktikum.pageObject;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


import static org.junit.Assert.assertEquals;
import static ru.yandex.praktikum.pageObject.constants.HomePageConstants.*;

@RunWith(Parameterized.class)
public class HomePageTest {

    //1. Выпадающий список в разделе «Вопросы о важном». Нужно проверить: когда нажимаешь на стрелочку, открывается соответствующий текст.


    private WebDriver driver;
    private final String site = "https://qa-scooter.praktikum-services.ru/";
    private final By question;
    private final By answer;
    private final By labelResult;
    private final String expected;

    public HomePageTest(By question, By answer, By labelResult, String expected) {
        this.question = question;
        this.answer = answer;
        this.labelResult = labelResult;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {QUESTION_0, ANSWER_0, ITEM_ANSWER_0, TEXT_ANSWER_0},
                {QUESTION_1, ANSWER_1, ITEM_ANSWER_1, TEXT_ANSWER_1},
                {QUESTION_2, ANSWER_2, ITEM_ANSWER_2, TEXT_ANSWER_2},
                {QUESTION_3, ANSWER_3, ITEM_ANSWER_3, TEXT_ANSWER_3},
                {QUESTION_4, ANSWER_4, ITEM_ANSWER_4, TEXT_ANSWER_4},
                {QUESTION_5, ANSWER_5, ITEM_ANSWER_5, TEXT_ANSWER_5},
                {QUESTION_6, ANSWER_6, ITEM_ANSWER_6, TEXT_ANSWER_6},
                {QUESTION_7, ANSWER_7, ITEM_ANSWER_7, TEXT_ANSWER_7},
        };
    }

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(); // - Если нужно заменить new FirefoxDriver() на хром - new ChromeDriver()
        driver.get(site);
    }

    // - В приложении есть баг, который не даёт оформить заказ. Не нажимается кнопка. Он воспроизводится только в Chrome.

    @After
    public void teardown() {

        driver.quit();
    }

    @Test
    public void checkQuestions() {
        new HomePage(driver)
                .waitForLoadHomePage()
                .scrollToQuestions()
                .clickQuestion(question)
                .waitLoadAfterClickQuestion(labelResult);
        String result = driver.findElement(answer).getText();

        assertEquals(expected, result);
    }
}