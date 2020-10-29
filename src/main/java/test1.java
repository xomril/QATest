
import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.BeforeClass;

import static junit.framework.TestCase.assertEquals;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;



public class test1 {
  private static WebDriver driver;

  private static TestPageObject testPage;
  private static ShufersalPageObject sho;
  JavascriptExecutor js = (JavascriptExecutor)driver;


  @BeforeClass
  public static void beforeTest() {
    System.setProperty("webdriver.chrome.driver", "chromedriver");
    System.setProperty("webdriver.chrome.whitelistedIps", "");
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments("--headless");
    chromeOptions.addArguments("--no-sandbox");
    driver = new ChromeDriver();
    sho = new ShufersalPageObject(driver);

  }
  @AfterClass
  public static void afterTest() {
    driver.quit();
  }

  @Test
  public void test_one() throws InterruptedException {
    assertEquals(sho.isLogoLoaded(),true);
    sho.login("", "");
    sho.addFirstProductToCart("חלב");
    Thread.sleep(5000);
  }

  @Test
  public void test_two(){
    if(!testPage.getResultsDiv().contentEquals("Hey World!")){
      js.executeScript("document.querySelector('#finish').style='border:solid 1px #ff0000'");
      TakesScreenshot scrShot =((TakesScreenshot)driver);
      File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
      File DestFile=new File("tmp.png");
      try {
        FileUtils.copyFile(SrcFile, DestFile);
      }
      catch(Exception e){
        System.out.println("Error trying save file: " + e.toString());
      }
    };



  }

}
