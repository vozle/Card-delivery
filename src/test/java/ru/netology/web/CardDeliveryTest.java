package ru.netology.web;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @Test
    public void shouldTestCardDeliveryForm() {
        $("[placeholder=\"Город\"]").setValue("Санкт-Петербург");
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[placeholder=\"Дата встречи\"]").setValue(String.valueOf(date));
        $("[name=\"name\"]").setValue("Колчак Александр");
        $("[name=\"phone\"]").setValue("+79118741920");
        $("[data-test-id=\"agreement\"]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=\"notification\"]").shouldBe(visible, Duration.ofSeconds(15));
    }
}
