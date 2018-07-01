package appmanager.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;

// --- Страница Конвертера валют ---

public class CurrencyConverterPage {

    // --- ЭЛЕМЕНТЫ ---

    private SelenideElement converterPopularTitleEl = $(".converter-popularTitle");
    private ElementsCollection converterPopularListEl = $$(".converter-popularList .converter-popularItem");

    private SelenideElement selectedCurrencyName = $(".converter-currenciesItem__selected .converter-currenciesName");

    private String popularCurrencyEl = "//ul[@class='converter-popularList']/li[.='%s']";

    // --- МЕТОДЫ ---

    @Step("Получить и проверить текст \"популярные валюты\"")
    public String textOfConverterPopularTitle() {
        return converterPopularTitleEl.shouldBe(Condition.visible, Condition.exist)
                .text();
    }

    @Step("Выбрать валюту {currency} из списка \"популярные валюты\"")
    public void selectCurrencyFromPopularList(String currency) {
        $x(String.format(popularCurrencyEl, currency))
                .shouldBe(Condition.visible)
                .click();
    }

    @Step("Получить количество валют в списке \"популярные валюты\"")
    public Integer countOfCurrencyFromPopularList() {
        return converterPopularListEl.size();
    }

    @Step("Получить названия валют в списке \"популярные валюты\"")
    public List<String> getNamesOfCurrencyFromPopularList() {
        return  converterPopularListEl.texts();
    }

    @Step("Валюта выбрана в \"основном списке\"")
    public Boolean isSelectedPopularCurrency(String currencyName) {
        $x(String.format("//li[.='%s']", currencyName)).scrollTo()
                .shouldHave(Condition.cssClass("converter-popularItem__selected"));
        return true;
    }

    @Step("Валюта выбрана в \"основном списке\"")
    public Boolean isSelectedCurrency(String currencyName) {
        $x(String.format("//li/span[.='%s']", currencyName)).scrollTo().parent()
                .shouldHave(Condition.cssClass("converter-currenciesItem__selected"));
        return true;
    }

    @Step("Получить название выбранной валюты в \"основном списке\"")
    public String getSelectedCurrencyName() {
        return selectedCurrencyName.scrollTo()
                .shouldBe(Condition.visible).getText();
    }
}
