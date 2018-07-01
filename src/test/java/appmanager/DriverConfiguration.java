package appmanager;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;

import static appmanager.ApplicationManager.getProperties;
import static com.codeborne.selenide.Selenide.open;

// --- Установка всех настроек драйвера ---

public class DriverConfiguration {

    private ApplicationManager app;

    DriverConfiguration(ApplicationManager app) {
        this.app = app;
        setAllDriverPath();
        setDriver();
        setDriverProperties();
    }

    public WebDriver getDriver() {
        return WebDriverRunner.getWebDriver();
    }

    public void stopDriver() {
        WebDriverRunner.closeWebDriver();
    }

    private String getBrowser() {
        return getProperties("web.browser");
    }

    private void setAllDriverPath(){
        String osName = System.getProperty("os.name").toLowerCase();
        String browser = getBrowser();
        if (osName.equals("windows")) {
            if (browser.equals("chrome")) {
                System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\drivers\\chromedriver.exe");
            }
        }
        if (osName.equals("mac")){
            if (browser.equals("chrome")) {
                System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
            }
        }
    }

    private void setDriver() {
        switch (getBrowser()) {
            case BrowserType.CHROME:
                Configuration.browser = "chrome";
                break;
            default:
                throw new ExceptionInInitializerError("Браузер " + getBrowser() + "в текущей конфигурации отсутствует");
        }
    }

    private void setDriverProperties() {
        Configuration.fastSetValue = true;
        Configuration.reopenBrowserOnFail = false;
        Configuration.savePageSource = false;
        Configuration.screenshots = true;
        Configuration.startMaximized = true;
    }

    @Step("Open Converter page")
    public void goToUrl() {
        open(getProperties("web.url"));
    }
}
