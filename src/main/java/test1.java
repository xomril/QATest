
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.BeforeClass;

import static junit.framework.TestCase.assertEquals;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;



public class test1 {
  private static WebDriver driver;
  private static String url = "http://the-internet.herokuapp.com/dynamic_loading/2";
  private static TestPageObject testPage;
  JavascriptExecutor js = (JavascriptExecutor)driver;


  @BeforeClass
  public static void beforeTest() {
    System.setProperty("webdriver.chrome.driver", "chromedriver");
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments("--headless");
    chromeOptions.addArguments("--no-sandbox");
    driver = new ChromeDriver();

    driver.navigate().to(url);

    testPage = new TestPageObject(driver);
    testPage.clickStartButton();
  }
  @AfterClass
  public static void afterTest() {
    driver.quit();
  }

  @Test
  public void test_one() {
    assertEquals("div test:", testPage.getResultsDiv(),"Hello World!");
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
