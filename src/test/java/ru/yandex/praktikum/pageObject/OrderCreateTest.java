package ru.yandex.praktikum.pageObject;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertTrue;
import static ru.yandex.praktikum.pageObject.constants.CreateOrderButton.DOWN_BUTTON;
import static ru.yandex.praktikum.pageObject.constants.CreateOrderButton.UP_BUTTON;
import static ru.yandex.praktikum.pageObject.constants.RentDurationConstants.*;
import static ru.yandex.praktikum.pageObject.constants.ScooterColours.BLACK;
import static ru.yandex.praktikum.pageObject.constants.ScooterColours.GREY;

@RunWith(Parameterized.class)
public class OrderCreateTest {

    // 2.  Заказ самоката.

    private WebDriver driver;
    private final String site = "https://qa-scooter.praktikum-services.ru/";
    private final String name;
    private final String surname;
    private final String address;
    private final int stateMetroNumber;
    private final String telephoneNumber;
    private final String date;
    private final String duration;
    private final Enum colour;
    private final String comment;
    private final String expectedHeader = "Заказ оформлен";
    private final Enum button;

    public OrderCreateTest(Enum button,
                           String name,
                           String surname,
                           String address,
                           int stateMetroNumber,
                           String telephoneNumber,
                           String date,
                           String duration,
                           Enum colour,
                           String comment) {
        this.button = button;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.stateMetroNumber = stateMetroNumber;
        this.telephoneNumber = telephoneNumber;
        this.date = date;
        this.duration = duration;
        this.colour = colour;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {UP_BUTTON, "Петя", "Паразитов", "Адрес Малая Арнаутская", 123, "79298311711", "14.07.2023", SIX_DAYS, GREY, "comments one"},
                {UP_BUTTON, "Нестор", "Махно", "Адрес Чебоксары", 7, "79992345252", "22.02.2022", FIVE_DAYS, BLACK, "comments two"},
                {UP_BUTTON, "Барабай", "Гаргай", "Бульвар Дачия 33", 10, "79293936303", "28.05.2023", ONE_DAY, BLACK, "comments three"},
                {DOWN_BUTTON, "Яни", "Гоголако", "Старая почта 7", 123, "79291137811", "17.05.2018", SIX_DAYS, GREY, "comments one"},
                {DOWN_BUTTON, "Митко", "Бабалар", "Малая Малина 2", 7, "79292058292", "12.09.2023", FIVE_DAYS, BLACK, "comments two"},
                {DOWN_BUTTON, "Гыргэ", "Питич", "Соломенной сторожки 12", 10, "79293345733", "21.01.2021", ONE_DAY, BLACK, "comments three"},
        };
    }

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
    public void testCreateOrderWithUpButton() {
        new HomePage(driver)
                .waitForLoadHomePage()
                .clickCookiesButton()
                .clickCreateOrderButton(button);

        new AboutRenter(driver)
                .waitForLoadOrderPage()
                .inputName(name)
                .inputSurname(surname)
                .inputAddress(address)
                .changeStateMetro(stateMetroNumber)
                .inputTelephone(telephoneNumber)
                .clickNextButton();

        new AboutScooter(driver)
                .waitAboutRentHeader()
                .inputDate(date)
                .inputDuration(duration)
                .changeColour(colour)
                .inputComment(comment)
                .clickButtonCreateOrder();

        PopUpWindow popUpWindow = new PopUpWindow(driver);
                popUpWindow.clickButtonYes();

        assertTrue(popUpWindow.getHeaderAfterCreateOrder().contains(expectedHeader));
    }
}
