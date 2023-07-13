package ru.apteka.test;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static io.qameta.allure.Allure.step;

public class AptekaTest extends WebTest {

    MainPage mainPage = new MainPage();
    CityPopup cityPopup = new CityPopup();
    CatalogPage catalogPage = new CatalogPage();

    @BeforeEach
    public void setSelenide() {
        open("https://klassika-apteka.ru");
        Selenide.webdriver().driver().getWebDriver().manage().addCookie(new Cookie("BITRIX_SM_current_city", "114375"));
        refresh();
        cityPopup.modal.shouldNotBe(visible);
    }

    @Test
    @DisplayName("Переход по подкатегориям в каталоге товаров")
    @Feature("Каталог товаров")
    @Story("Подкатегории")
    public void shouldOpenCatalogTab() {
        $(".big_logo").shouldBe(visible);
        mainPage.tabs.shouldHave(sizeGreaterThan(0));
        SelenideElement tab = mainPage.tabs.filter(text("Аптечки")).get(0);

        step("Навести курсор на вкладку", () -> {
            tab.hover();
        });

        step("Кликнуть на появившуюся подкатегорию", () -> {
            ElementsCollection subtabs = mainPage.getSubtabs(tab);
            subtabs.filter(text("Для подготовки к медицинским обследованиям")).get(0).click();
        });

        step("Проверить, что произошел переход на страницу товаров категории", () -> {
            catalogPage.header.shouldHave(text("Для подготовки к медицинским обследованиям"));
        });
    }
}
