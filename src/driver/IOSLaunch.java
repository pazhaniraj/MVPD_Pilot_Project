package driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import objects.MVPD;
import fileReaders.JsonRead;

public abstract class IOSLaunch {
	public static AppiumDriver driver;
	public static WebDriverWait wait;
	private static String filepath ="/Users/ramyar/Documents/workspace/New/MVPD_Pilot_Project/resources/read.json";
	public static ArrayList<MVPD>mvpd = new ArrayList<MVPD>();
	private static JsonRead jsonReader; 
	public int MVPD_MAX =2;
	public int VIDEO_MAX = 3;
	public int ACCOUNT_MAX =2;
	
	private WebElement ele_ = driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]"
			+ "/UIAStaticText[1]"));
	
	public static void launchDriverAppthwack() throws Throwable {
		
	}
	@BeforeClass
	public static void launchDriver() throws Throwable {
		
		JsonRead.readJsonFromFile(filepath); 
		DesiredCapabilities capabilities = new DesiredCapabilities();
		//iphone5
		capabilities.setCapability("deviceName", "Raj Bangaru Samy’s iPhone");
		capabilities.setCapability("platformName", "ios"); 
		capabilities.setCapability("platformVersion", "7.1.1");
		//iphone4s
		//capabilities.setCapability("deviceName", "iPhone 4");
		//capabilities.setCapability("platformVersion", "8.3");
		//capabilities.setCapability("udid", "aa18c45a49bcea5055be2e894748ccb25665e1c6");
		capabilities.setCapability("udid", "ce7d4a568c96f9886ed561d6aad36007e13fe0ff");
		capabilities.setCapability("bundleId", "com.uie.foxsports.foxsportsgo");
		driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);	
		
	}
	
	
	
	public void tap(WebElement element) {
		driver.tap(1, element, 1);	
	}
	
	public void click(WebElement element) {
		element.click();
	}
	
	public WebElement waitForElementXpath(String xpath,int waitTime) {
		wait = new WebDriverWait(driver, waitTime);
		WebElement element = wait
				.until(ExpectedConditions.elementToBeClickable(By
						.xpath(xpath)));
		return element;	
	}
	
	public WebElement waitForElementName(String name,int waitTime) {
		
		wait = new WebDriverWait(driver, waitTime);
		WebElement element = wait
				.until(ExpectedConditions.elementToBeClickable(By
						.name(name)));
		return element;
	}
	
	public void pressButton(String name){
		while (!(ele_.isDisplayed())) {
			driver.tap(1, 290, 210, 1);
			tap( waitForElementName(name,15));	
			break;
		}
	}
	
	protected void  takescreenshots(String xpath) throws InterruptedException {
		
		//tap(waitForElementXpath(xpath,60));
		driver = (IOSDriver) new Augmenter().augment(driver);
		Thread.sleep(9000);
		// Get the screenshot
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		System.out.println("Screenshot completed");
		try{
			File calsspathRoot = new File(System.getProperty("user.dir")); 
			//workspace space is set in application folder
			File testScreenShot = new File(calsspathRoot + "screenShots", "preRollAds");
			// Copy the file to screenshot folder
			FileUtils.copyFile(scrFile, testScreenShot);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		driver.quit();
	}

}
