import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static java.lang.System.currentTimeMillis;

public class ShufersalPageObject {
    private static String url = "https://www.shufersal.co.il/";

    private final WebDriver driver;
    private static WebDriverWait wait;

    By logoElement = By.className("banner__component");
    By userNameField = By.cssSelector("[id='j_username']");
    By passNameField = By.cssSelector("[id='j_password']");
    By loginForm = By.cssSelector("[id='loginForm']");
    By searchBox = By.cssSelector("[id='js-site-search-input']");
    By searchForm = By.cssSelector("[id='formSearchSubmit']");
    By addToCartBtn = By.cssSelector("[.js-add-to-cart]");
    String searchString = "//a[contains(@title,'%s')]";
    By productsList;
    public ShufersalPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 60);
        driver.navigate().to(url);
    }

    public List<WebElement> getProductsList(String productName){
        this.productsList = By.xpath(String.format(searchString,productName));
        return driver.findElements(productsList);
    }

    public void addFirstProductToCart(String productName){
        WebElement elm = searchForProduct(productName);
        elm.click();
        waitForElement(addToCartBtn).click();
    }

    public WebElement searchForProduct(String productNameStr){
        waitForElement(searchBox).sendKeys(productNameStr);
        waitForElement(searchForm).submit();
        return getFirstElement(productNameStr);
    }

    public WebElement getFirstElement(String productName){
        List<WebElement> elms = getProductsList(productName);
        return elms.get(0);
    }
    public void login(String user, String pass){
        driver.navigate().to(url + "online/he/login");
        waitForElement(userNameField).sendKeys(user);
        waitForElement(passNameField).sendKeys(pass);
        waitForElement(loginForm).submit();
        waitForElement(searchBox);

    }
    public boolean isLogoLoaded() {
        WebElement imgElement =  waitForElement(logoElement).findElement(By.tagName("img"));
        Object result = ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].complete && "+
                        "typeof arguments[0].naturalWidth != \"undefined\" && "+
                        "arguments[0].naturalWidth > 0", imgElement);

        boolean loaded = false;
        if (result instanceof Boolean) {
            loaded = (Boolean) result;
            System.out.println(loaded);
        }
        return loaded;
    }

    public WebElement waitForElement(By elm){
        return waitForElement(elm, 60);
    }

    public WebElement waitForElement(By elm, int timeOutInSeconds){
        wait = new WebDriverWait(driver, timeOutInSeconds);
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(elm));
        return element;
    }

    public static Boolean timerUntilTrue(Function<Object, Boolean> lambdaInTimer, Integer... optionalTimeout) {
        Integer timeout = (optionalTimeout.length > 0) ? optionalTimeout[0] : 5000;
        long startTime = currentTimeMillis(); //get starting time
        while (((currentTimeMillis() - startTime) / 1000) < timeout) {
            try {
                if (lambdaInTimer.apply(new Object()).booleanValue()) {
                    return true;
                }
            } catch (Exception e) {
            }
        }
        return false;
    }
}
