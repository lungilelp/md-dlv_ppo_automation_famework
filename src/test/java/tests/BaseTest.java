package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import pages.LoginPage;
import utils.logs.Log;

public class BaseTest {
    public WebDriver driver;
    public LoginPage loginPage;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeSuite
    public void setupProxy() {

    }

    @BeforeClass
    public void classLevelSetup() {
        Log.info("Tests is starting!");
//
//        ChromeOptions options = new ChromeOptions();
//
//        Proxy proxy = new Proxy();
//        proxy.setAutodetect(false);
//        proxy.setNoProxy("no_proxy-var");
//
//        options.setCapability("proxy", proxy);
        ChromeOptions options = new ChromeOptions(); //Create chrome options
        options.setPlatformName("Windows 10");
        options.setBrowserVersion("latest");
        options.setCapability("Site key", "6Lem7ZYUAAAAAMc3Y--CC6s7WYl5pOZ_AzkG7zlC");
        options.setCapability("Secret key","6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe");

        //Map<String, Object> cloudOptions = new HashMap<>();
        //cloudOptions.put("build", myTestBuild);
        //cloudOptions.put("name", myTestName);
        //options.setCapability("cloud:options", cloudOptions);
        //WebDriver driver = new RemoteWebDriver(new URL(cloudUrl),options);

        //was here

        //DesiredCapabilities capabilities = new DesiredCapabilities();////Create chrome capabilities
        //capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        //options.merge(capabilities); //Merge chrome options and capabilities

//        LocalDriverContext.setRemoteWebDriverThreadLocal(new ChromeDriver());
//        webDriver = LocalDriverContext.getRemoteWebDriver();
//        webDriver.get(Settings.AUT);
//        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
//        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
//
//        //Maximize Window
//        webDriver.manage().window().maximize();
//
//        //Wait for element to be actionable for a maximum of 120s
//        wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

        driver = new ChromeDriver();
    }

    @BeforeMethod
    public void methodLevelSetup() {
        loginPage = new LoginPage(driver);
    }

    @AfterClass
    public void teardown() {
        Log.info("Tests are ending!");
        driver.quit();
    }
}