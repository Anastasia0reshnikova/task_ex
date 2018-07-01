package appmanager;

import appmanager.pages.CurrencyConverterPage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.page;

public class ApplicationManager {

    private static Properties properties;
    private DriverConfiguration driverConfiguration;
    private ApplicationManager app;

    public ApplicationManager() {
        init();
    }

    private void init(){
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties(){
        try {
            properties.load(new FileReader(new File("src/test/resources/local.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperties(String prop) {
        return properties.getProperty(prop);
    }

    public DriverConfiguration driver() {
        if(driverConfiguration == null) {
            driverConfiguration = new DriverConfiguration(app);
        }
        return driverConfiguration;
    }

    public CurrencyConverterPage converterPage() {
        return page(CurrencyConverterPage.class);
    }
}
