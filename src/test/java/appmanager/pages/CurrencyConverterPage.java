package appmanager.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;

// --- Страница Конвертера валют ---

public class CurrencyConverterPage {

    // ------------------------------------ ЭЛЕМЕНТЫ ------------------------------------

    private SelenideElement converterPopularTitleEl = $(".converter-popularTitle");
    private ElementsCollection converterPopularListEl = $$(".converter-popularList .converter-popularItem");

    private ElementsCollection converterTabCurrencyNames =$$(".converter-tabCurrencyName");

    private SelenideElement selectedCurrencyName = $(".converter-currenciesItem__selected .converter-currenciesName");

    private SelenideElement fieldFirst = $(".converter-tab .converter-tabItem:nth-child(2) input");
    private SelenideElement fieldSecond = $(".converter-tab .converter-tabItem:nth-child(3) input");

    private SelenideElement buttonClear = $(".converter-tabBtns span");

    // ------------------------------------- МЕТОДЫ -------------------------------------

    @Step("Выбрать валюту {currency} из списка \"популярные валюты\"")
    public CurrencyConverterPage selectCurrencyFromPopularList(String currency) {
        $x(String.format("//ul[@class='converter-popularList']/li[.='%s']", currency))
                .shouldBe(Condition.visible)
                .click();
        return this;
    }

    @Step("Выбрать валюту {currency} из списка \"основные валюты\"")
    public CurrencyConverterPage selectCurrency(String currency) {
        $x(String.format("//li[@class='converter-currenciesItem']/span[.='%s']", currency))
                .shouldBe(Condition.visible)
                .click();
        return this;
    }

    //-----------------------

    @Step("Заполнить поле \"конвертируемая валюта\" значенем {currencyValue}")
    public CurrencyConverterPage fillFieldOfFirstTabCurrency(Double currencyValue) {
        fieldFirst.click();
        fieldFirst.setValue(String.valueOf(currencyValue))
                .parent().shouldHave(Condition.cssClass("converter-tabItem__selected"));
        return this;
    }

    @Step("Перейти в поле \"сковертированная в валюту\"")
    public CurrencyConverterPage clickToFieldOfSecondTabCurrency() {
        fieldSecond.scrollIntoView(true).click();
        fieldSecond.parent().shouldHave(Condition.cssClass("converter-tabItem__selected"));
        return this;
    }

    //-----------------------

    @Step("Ошистить поля конвертера")
    public void clearFields() {
        buttonClear.scrollIntoView(true).shouldHave(Condition.text("Clear"))
                .click();
    }

    //-----------------------

    @Step("Получить значение поля \"конвертируемая валюта\"")
    public Double valueOfFirstTabCurrency() {
        return Double.valueOf(fieldFirst.getValue());
    }

    @Step("Получить значение поля \"сковертированная в валюту\"")
    public Double valueOfSecondTabCurrency() {
        return Double.valueOf(fieldSecond.getValue());
    }

    //-----------------------

    @Step("Получить и проверить текст \"популярные валюты\"")
    public String textOfConverterPopularTitle() {
        return converterPopularTitleEl.shouldBe(Condition.visible, Condition.exist)
                .text();
    }

    @Step("Получить количество валют в списке \"популярные валюты\"")
    public Integer countOfCurrencyFromPopularList() {
        return converterPopularListEl.size();
    }

    @Step("Получить названия валют в списке \"популярные валюты\"")
    public List<String> getNamesOfCurrencyFromPopularList() {
        return  converterPopularListEl.texts();
    }

    //-----------------------

    @Step("Получить название валюты у поля \"ковертируемая валюта\"")
    public String nameOfFirstTabCurrency() {
        return converterTabCurrencyNames.get(0).getText();
    }

    @Step("Получить название валюты у поля \"сковертированная в валюту\"")
    public String nameOfSecondTabCurrency() {
        return converterTabCurrencyNames.get(1).getText();
    }

    //-----------------------

    @Step("Валюта {currencyName} выбрана в списке \"популярные валюты\"")
    public Boolean isSelectedPopularCurrency(String currencyName) {
        $x(String.format("//li[.='%s']", currencyName)).scrollTo()
                .shouldHave(Condition.cssClass("converter-popularItem__selected"));
        return true;
    }

    @Step("Валюта {currencyName} выбрана в \"основном списке\"")
    public Boolean isSelectedCurrency(String currencyName) {
        $x(String.format("//li/span[.='%s']", currencyName)).scrollTo().parent()
                .shouldHave(Condition.cssClass("converter-currenciesItem__selected"));
        return true;
    }

    //-----------------------

    @Step("Получить название выбранной валюты в \"основном списке\"")
    public String getSelectedCurrencyName() {
        return selectedCurrencyName.scrollTo()
                .shouldBe(Condition.visible).getText();
    }
}
