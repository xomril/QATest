import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TestPageObject {
  private final WebDriver driver;
  private static WebDriverWait wait;
  private static final By divStartButton = By.id("start");
  private static final By divLoading = By.id("loading");
  private static final By divFinish = By.id("finish");

  public void clickStartButton(){
    driver.findElement(divStartButton).findElement(By.tagName("button")).click();

    WebElement loadingElement = wait.until(
        ExpectedConditions.visibilityOfElementLocated(divLoading));
    if (loadingElement.isDisplayed()) {
      wait = new WebDriverWait(driver, 60);
      wait.until(
          ExpectedConditions.visibilityOfElementLocated(divFinish));
    }

  }
  public String getResultsDiv() {
    wait = new WebDriverWait(driver, 60);
    WebElement finishElement = wait.until(
              ExpectedConditions.visibilityOfElementLocated(divFinish));
      return finishElement.getText();
  }


  public TestPageObject(WebDriver driver){
    this.driver = driver;
    this.wait = new WebDriverWait(driver, 60);
  }



}
