package ru.yandex.praktikum.pageObject;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.pageObject.AboutRenter;
import ru.yandex.praktikum.pageObject.AboutScooter;
import ru.yandex.praktikum.pageObject.HomePage;
import ru.yandex.praktikum.pageObject.OrderStatus;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class ScooterButtonTest {
    WebDriver driver;
    private final String site = "https://qa-scooter.praktikum-services.ru/";

    @Before
    public void startUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get(site);
    }

    @After
    public void teardown() {
        driver.quit();
    }

    @Test
    public void clickScooterFromAboutRenterPage() {
        HomePage homePage = new HomePage(driver);
        AboutRenter aboutRenter = new AboutRenter(driver);

        homePage.waitForLoadHomePage()
                .clickUpOrderButton();

        aboutRenter.waitForLoadOrderPage()
                .clickScooter();

        new WebDriverWait(driver, Duration.ofSeconds(5));

        assertEquals("https://qa-scooter.praktikum-services.ru/", driver.getCurrentUrl());
    }

    // - Если нажать на логотип «Самоката», попадёшь на главную страницу «Самоката».

    @Test
    public void clickScooterFromAboutScooterPage() {
        HomePage homePage = new HomePage(driver);
        AboutRenter aboutRenter = new AboutRenter(driver);
        AboutScooter aboutScooter = new AboutScooter(driver);

        homePage.waitForLoadHomePage()
                .clickCookiesButton()
                .clickUpOrderButton();

        aboutRenter.waitForLoadOrderPage()
                .inputName("Имя")
                .inputSurname("Фамилия")
                .inputAddress("Адрес")
                .changeStateMetro(77)
                .inputTelephone("+79999999999")
                .clickNextButton();

        aboutScooter.waitAboutRentHeader()
                .clickScooter();

        new WebDriverWait(driver, Duration.ofSeconds(5));

        assertEquals("https://qa-scooter.praktikum-services.ru/", driver.getCurrentUrl());
    }

    @Test
    public void clickScooterFromOrderStatusPage() {
        HomePage homePage = new HomePage(driver);
        OrderStatus orderStatus = new OrderStatus(driver);

        homePage.waitForLoadHomePage()
                .clickCookiesButton()
                .clickOrderState()
                .inputOrderNumber("12345")
                .clickGo();

        orderStatus.waitLoadOrderStatusPade()
                .clickScooter();

        new WebDriverWait(driver, Duration.ofSeconds(5));

        assertEquals("https://qa-scooter.praktikum-services.ru/", driver.getCurrentUrl());
    }

}
