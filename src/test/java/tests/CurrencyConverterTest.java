package tests;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import models.ConvertCurrency;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Калькулятор трейдера/Конвертер валют")
@Feature("Конвертер валют")

@DisplayName("Проверка работы \"Конвертера валют\"")
@RunWith(DataProviderRunner.class)
public class CurrencyConverterTest extends TestBase {

    @DisplayName("Проверка количества и названия валют в списке “популярные валюты”")
    @Test
    public void assertPopularCurrencyList() {
        assertThat("Popular currencies").isEqualToIgnoringCase(app.converterPage().textOfConverterPopularTitle());
        assertThat(6).isEqualTo(app.converterPage().countOfCurrencyFromPopularList());
        assertThat(asList("USD", "EUR", "CHF", "JPY", "AUD", "CAD")).isEqualTo(app.converterPage().getNamesOfCurrencyFromPopularList());
    }

    @DataProvider
    public static Object[][] popularCurrencies() {
        return new Object[][] {
                {new ConvertCurrency().count(100.00).currency("USD").currencyName("United States Dollar")
                        , new ConvertCurrency().count(85.01).currency("EUR").currencyName("Euro")},
                {new ConvertCurrency().count(500.00).currency("CAD").currencyName("Canadian Dollar")
                        , new ConvertCurrency().count(376.01).currency("CHF").currencyName("Swiss Franc")},
        };
    }

    @DisplayName("Проверка выбора валют в списке “популярные валюты” и работы конвертера")
    @Test
    @UseDataProvider("popularCurrencies")
    public void selectedPopularCurrencyAndConvertTest(ConvertCurrency currency, ConvertCurrency convertedCurrency) {
        //Выбрать валюту и заполнить поле "конвертируемая валюта"
        app.converterPage().selectCurrencyFromPopularList(currency.currency()).fillFieldOfFirstTabCurrency(currency.count());
        assertThat(true).isEqualTo(app.converterPage().isSelectedPopularCurrency(currency.currency()));
        assertThat(currency.currency()).isEqualToIgnoringCase(app.converterPage().nameOfFirstTabCurrency());
        assertThat(true).isEqualTo(app.converterPage().isSelectedCurrency(currency.currency()));
        assertThat(currency.currencyName()).isEqualToIgnoringCase(app.converterPage().getSelectedCurrencyName());
        //Выбрать валюту для поля "сконвертированная в валюту"
        app.converterPage().clickToFieldOfSecondTabCurrency().selectCurrencyFromPopularList(convertedCurrency.currency());
        assertThat(true).isEqualTo(app.converterPage().isSelectedPopularCurrency(convertedCurrency.currency()));
        assertThat(convertedCurrency.currency()).isEqualToIgnoringCase(app.converterPage().nameOfSecondTabCurrency());
        assertThat(true).isEqualTo(app.converterPage().isSelectedCurrency(convertedCurrency.currency()));
        assertThat(convertedCurrency.currencyName()).isEqualToIgnoringCase(app.converterPage().getSelectedCurrencyName());
        //Проверить значения после конвертации
        assertThat(currency.count()).isEqualTo(app.converterPage().valueOfFirstTabCurrency());
        assertThat(convertedCurrency.count()).isLessThanOrEqualTo(app.converterPage().valueOfSecondTabCurrency());
    }

    @DataProvider
    public static Object[][] allCurrencies() {
        return new Object[][] {
                {new ConvertCurrency().count(1000.52).currency("DZD").currencyName("Algerian Dinar")
                        , new ConvertCurrency().count(4.53).currency("LVL").currencyName("Latvian Lat")},
                {new ConvertCurrency().count(99.00).currency("AUD").currencyName("Australian Dollar")
                        , new ConvertCurrency().count(2103.37).currency("ARS").currencyName("Argentine Peso")},
        };
    }

    @DisplayName("Проверка выбора валют в общем списке по регионам и работы конвертера")
    @Test
    @UseDataProvider("allCurrencies")
    public void selectedCurrencyAndConvertTest(ConvertCurrency currency, ConvertCurrency convertedCurrency) {
        //Выбрать валюту и заполнить поле "конвертируемая валюта"
        app.converterPage().selectCurrency(currency.currency()).fillFieldOfFirstTabCurrency(currency.count());
        assertThat(currency.currency()).isEqualToIgnoringCase(app.converterPage().nameOfFirstTabCurrency());
        assertThat(true).isEqualTo(app.converterPage().isSelectedCurrency(currency.currency()));
        assertThat(currency.currencyName()).isEqualToIgnoringCase(app.converterPage().getSelectedCurrencyName());
        //Выбрать валюту для поля "сконвертированная в валюту"
        app.converterPage().clickToFieldOfSecondTabCurrency().selectCurrency(convertedCurrency.currency());
        assertThat(convertedCurrency.currency()).isEqualToIgnoringCase(app.converterPage().nameOfSecondTabCurrency());
        assertThat(true).isEqualTo(app.converterPage().isSelectedCurrency(convertedCurrency.currency()));
        assertThat(convertedCurrency.currencyName()).isEqualToIgnoringCase(app.converterPage().getSelectedCurrencyName());
        //Проверить значения после конвертации
        assertThat(currency.count()).isEqualTo(app.converterPage().valueOfFirstTabCurrency());
        assertThat(convertedCurrency.count()).isLessThanOrEqualTo(app.converterPage().valueOfSecondTabCurrency());
    }

    //2. возможности выбора валют посредством поиска

    @DisplayName("Проверка работы кнопки \"Очистить\" на форме Ковертера")
    @Test
    public void clearFieldTest() {
        ConvertCurrency currency = new ConvertCurrency().count(32.12).currency("USD");
        ConvertCurrency convertedCurrency = new ConvertCurrency().count(443.53).currency("ZAR");
        //Выбрать валюты и установить значение для конвертации
        app.converterPage().selectCurrencyFromPopularList("USD").fillFieldOfFirstTabCurrency(currency.count());
        assertThat(currency.count()).isEqualTo(app.converterPage().valueOfFirstTabCurrency());
        app.converterPage().clickToFieldOfSecondTabCurrency().selectCurrency(convertedCurrency.currency());
        assertThat(convertedCurrency.count()).isLessThanOrEqualTo(app.converterPage().valueOfSecondTabCurrency());
        //Очистить и провериить, что значения обнуляются
        app.converterPage().clearFields();
        assertThat(app.converterPage().valueOfFirstTabCurrency()).isZero();
        assertThat(app.converterPage().valueOfSecondTabCurrency()).isZero();
    }
}
