package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    public String date(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @BeforeEach
    void setUp() {
//        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @Test
    public void shouldTestCardDeliveryForm() {
        String meeting = date(3);
        $("[placeholder=\"Город\"]").setValue("Санкт-Петербург");
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[placeholder=\"Дата встречи\"]").setValue(meeting);
        $("[name=\"name\"]").setValue("Колчак Александр");
        $("[name=\"phone\"]").setValue("+79118741920");
        $("[data-test-id=\"agreement\"]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=\"notification\"]").shouldHave(Condition.text("Встреча успешно забронирована на " + meeting), Duration.ofSeconds(15));
    }

    @Test
    public void shouldTestInvalidCity() {
        String meeting = date(3);
        $("[placeholder=\"Город\"]").setValue("Химки");
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[placeholder=\"Дата встречи\"]").setValue(meeting);
        $("[name=\"name\"]").setValue("Колчак Александр");
        $("[name=\"phone\"]").setValue("+79118741920");
        $("[data-test-id=\"agreement\"]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=\"notification\"]").should(Condition.not(visible));
        $(withText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }

    @Test
    public void shouldTestNotFilledCity() {
        String meeting = date(3);
        $("[placeholder=\"Город\"]");
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[placeholder=\"Дата встречи\"]").setValue(meeting);
        $("[name=\"name\"]").setValue("Колчак Александр");
        $("[name=\"phone\"]").setValue("+791187419201");
        $("[data-test-id=\"agreement\"]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=\"notification\"]").should(Condition.not(visible));
        $(withText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    public void shouldTestInvalidDate() {
        String meeting = date(2);
        $("[placeholder=\"Город\"]").setValue("Санкт-Петербург");
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[placeholder=\"Дата встречи\"]").setValue(meeting);
        $("[name=\"name\"]").setValue("Колчак Александр");
        $("[name=\"phone\"]").setValue("+79118741920");
        $("[data-test-id=\"agreement\"]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=\"notification\"]").should(Condition.not(visible));
        $(withText("Заказ на выбранную дату невозможен")).shouldBe(visible);
    }

    @Test
    public void shouldTestInvalidDateV2() {
        $("[placeholder=\"Город\"]").setValue("Санкт-Петербург");
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[placeholder=\"Дата встречи\"]").setValue(String.valueOf(LocalDate.now()));
        $("[name=\"name\"]").setValue("Колчак Александр");
        $("[name=\"phone\"]").setValue("+79118741920");
        $("[data-test-id=\"agreement\"]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=\"notification\"]").should(Condition.not(visible));
        $(withText("Неверно введена дата")).shouldBe(visible);
    }

    @Test
    public void shouldTestInvalidName() {
        String meeting = date(3);
        $("[placeholder=\"Город\"]").setValue("Санкт-Петербург");
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[placeholder=\"Дата встречи\"]").setValue(meeting);
        $("[name=\"name\"]").setValue("Kolchak Alexander");
        $("[name=\"phone\"]").setValue("+79118741920");
        $("[data-test-id=\"agreement\"]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=\"notification\"]").should(Condition.not(visible));
        $(withText("Допустимы только русские буквы")).shouldBe(visible);
    }

    @Test
    public void shouldTestNotFilledName() {
        String meeting = date(3);
        $("[placeholder=\"Город\"]").setValue("Санкт-Петербург");
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[placeholder=\"Дата встречи\"]").setValue(meeting);
        $("[name=\"name\"]");
        $("[name=\"phone\"]").setValue("+79118741920");
        $("[data-test-id=\"agreement\"]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=\"notification\"]").should(Condition.not(visible));
        $(withText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    public void shouldTestInvalidPhone() {
        String meeting = date(3);
        $("[placeholder=\"Город\"]").setValue("Санкт-Петербург");
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[placeholder=\"Дата встречи\"]").setValue(meeting);
        $("[name=\"name\"]").setValue("Колчак Александр");
        $("[name=\"phone\"]").setValue("+791187419201");
        $("[data-test-id=\"agreement\"]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=\"notification\"]").should(Condition.not(visible));
        $(withText("Телефон указан неверно")).shouldBe(visible);
    }

    @Test
    public void shouldTestNotFilledPhone() {
        String meeting = date(3);
        $("[placeholder=\"Город\"]").setValue("Санкт-Петербург");
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[placeholder=\"Дата встречи\"]").setValue(meeting);
        $("[name=\"name\"]").setValue("Колчак Александр");
        $("[name=\"phone\"]");
        $("[data-test-id=\"agreement\"]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=\"notification\"]").should(Condition.not(visible));
        $(withText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    public void shouldTestNotMarkedCheckBox() {
        String meeting = date(3);
        $("[placeholder=\"Город\"]").setValue("Санкт-Петербург");
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[placeholder=\"Дата встречи\"]").setValue(meeting);
        $("[name=\"name\"]").setValue("Колчак Александр");
        $("[name=\"phone\"]").setValue("+79118741920");
        $("[data-test-id=\"agreement\"]");
        $(byText("Забронировать")).click();
        $("[data-test-id=\"notification\"]").should(Condition.not(visible));
        $(byCssSelector("[data-test-id=\"agreement\"].input_invalid .checkbox__text"));
    }

    @Test
    public void shouldTestNotFilledAllFields() {
        String meeting = date(3);
        $("[placeholder=\"Город\"]");
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[name=\"name\"]");
        $("[name=\"phone\"]");
        $("[data-test-id=\"agreement\"]");
        $(byText("Забронировать")).click();
        $("[data-test-id=\"notification\"]").should(Condition.not(visible));
        $(byCssSelector("[data-test-id=\"agreement\"].input_invalid .checkbox__text"));
        $(withText("Поле обязательно для заполнения")).shouldBe(visible);
    }
}