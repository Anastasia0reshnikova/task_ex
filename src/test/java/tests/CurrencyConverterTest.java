package tests;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
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
        assertThat("Популярные валюты").isEqualToIgnoringCase(app.converterPage().textOfConverterPopularTitle());
        assertThat(6).isEqualTo(app.converterPage().countOfCurrencyFromPopularList());
        assertThat(asList("USD", "EUR", "CHF", "JPY", "AUD", "CAD")).isEqualTo(app.converterPage().getNamesOfCurrencyFromPopularList());
    }

    @DataProvider
    public static Object[][] popularCurrencies() {
        return new Object[][] {
                {"USD", "Американский доллар"},
                {"EUR", "Евро"},
                {"CAD", "Канадский доллар"},
        };
    }

    @DisplayName("Проверка выбора валют в списке “популярные валюты”, общем списке по регионам")
    @Test
    @UseDataProvider("popularCurrencies")
    public void selectedPopularCurrencyTest(String currency, String currencyName) {
        app.converterPage().selectCurrencyFromPopularList(currency);
        assertThat(true).isEqualTo(app.converterPage().isSelectedPopularCurrency(currency));
        assertThat(true).isEqualTo(app.converterPage().isSelectedCurrency(currency));
        assertThat(currencyName).isEqualToIgnoringCase(app.converterPage().getSelectedCurrencyName());
    }

    @DisplayName("Проверка выбора валют в общем списке по регионам")
    @Test
    public void selectedCurrencyTest() {}

    //2. возможности выбора валют посредством поиска

    //3. выбора и корректной подстановки валют в валюту “из” и “в”

    //4. работоспособность функциональности по очистке формы.
}
