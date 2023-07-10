package ru.apteka.test;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CatalogPage {

    SelenideElement header = $("h1");
    ElementsCollection tabs = $$("#section_item_inner active ul li");
    public ElementsCollection getSubtabs(SelenideElement tab) {
        return tab.$$("ul li");
    }

}
