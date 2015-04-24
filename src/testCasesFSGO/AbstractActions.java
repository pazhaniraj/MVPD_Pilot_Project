package testCasesFSGO;
import java.util.List;
import java.util.Properties;

import objects.Accounts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import driver.IOSLaunch;
import fileReaders.PropertiesReader;




public class AbstractActions extends IOSLaunch {
	
	Properties properties = PropertiesReader.getPropertiesFile("xpath");
	
	/* 
	 * The loginAccountSettings() is used to navigate to the sign-in page of the mvpd 
	 * Parameter : mvpdName is the name of the MVPD example AT&T U-verse.
	 */
	public void loginAccountSettings (String mvpdName)  {
		
		//Tap on the settings button.
		tap(waitForElementXpath(properties.getProperty("settings"),60));
		
		//Tap on the sign_in 
		tap (waitForElementXpath(properties.getProperty("sign_in_out"),60));
		
		//swipe
		driver.swipe(225,500,225,250,3000);
		
		//tap on the more-providers button
		tap(waitForElementXpath(properties.getProperty("more_providers"),60));
		
		//tap on the mvpd name
		tap(waitForElementName(mvpdName,60));
		
	}
	
	/*
	 * The login() function is used to enter the username and password and click on the sign-in button
	 * Parameter account is the type of Account object which contains the username and password
	 * mvpdName is name of the MVPD associated with the account.
	 * 
	 * This function returns a boolean value 
	 * If the sign-in is successful and the function returns true.
	 * else return false
	 */
	public boolean login(Accounts account,String mvpdName) {
		
		//type in the username 
		WebElement element = waitForElementXpath(properties.getProperty("username"),60);
		click(element);
		element.sendKeys(account.getUsername());
		
		//type in the password
		//if the mvpd is AT&T U-verse the xpath for the password is different from all the other mvpds
		if(mvpdName.equals("AT&T U-verse")) 
			element = waitForElementXpath(properties.getProperty("password_att"),60);
		else
			element = waitForElementXpath(properties.getProperty("password"),60);
		click(element);
		element.sendKeys(account.getPassword());
		
		//tap on the done button present on the keypad   
		tap(waitForElementXpath(properties.getProperty("done"),60));
		
		//tap on the Sign-In button
		tap(waitForElementXpath(properties.getProperty("login"),60));
		
		//This try catch block is used to check whether the sign-in is 
		//This is done by checking if the username field is present or not
		//If its is present then return false  else return turn(timeout exception)
		try{
			  
			  Thread.sleep(1000);
			  driver.swipe(225,500,225,250,3000);
			  waitForElementXpath(properties.getProperty("username"),3).isEnabled();
		} catch (Exception e) {
			try {
				waitForElementName("Cancel",5).isEnabled();
			}catch(Exception ep) {
				
				return true;
			}
			tap(waitForElementName("Cancel",15));
			tap(waitForElementName(mvpdName,60));
			return true;
		}
		
		return false;
	
	}
	
	/*
	 * The function playVideos()  
	 */
	public void playVideos() throws InterruptedException {
		
		//The  variable xpath for the video.
		String xpath="";
		
		//The sleep is used to wait for the entire videos to be loaded/ to wait for the entire page to be loaded
		Thread.sleep(5000);
		
		//Counter for the number of videos
		int i=1;
		
		//To get the total number of videos present.
		List<WebElement> labels= driver.findElementsByXPath(properties.getProperty("video_collection"));
		System.out.println("Size:" + labels.size());
		
		//The while loop is used to iterate if the counter is less than the total number of
		//videos and less than or equal to the VIDEO_MAX
		while(i>0 && i<= labels.size()  && i<= VIDEO_MAX) {
			
			Thread.sleep(5000);
			System.out.println("Iteration:" + i);
			xpath = properties.getProperty("video_collection")+"["+i+"]"+"/UIAStaticText[1]";
			System.out.println("XPATH: " +xpath);
			
			//The xpath variable now contains the xpath for the videos.
			//finding the element using that xpath.
			WebElement ele = driver.findElementByXPath(xpath);
			String name = ele.getText();
			
			//checks if "ON NOW" is present on the particular video
			//If  its not present break from the while loop
			//else play the video
			
			if(!name.equalsIgnoreCase("ON NOW")) {
				break;
			
			}
			//tap on the video.
			tap(ele);
			
			//takes the screenshot of the video being played.
			takescreenshots(xpath);
			
			//thread.sleep is used to play the video for the amount of the time slept.
			Thread.sleep(9000);
	
			//click on the back button.
			pressButton("iPhone video backarrow");
			
			//wait for the page to reload after navigating to video page.
			Thread.sleep(30000);	
			
			//swipe for the next video.
			System.out.println("SWIPE");
			int j=0;
			while(j<i){
				driver.swipe(225,440,225,250,1000);
				j++;
			}
			i++;
		}
		
		
	}
	
	public void logout(){
		//tap on the settings icon.
		tap(waitForElementXpath(properties.getProperty("settings"),60));
		//tap on Sign-out.
		tap(waitForElementXpath(properties.getProperty("sign_in_out"),60));
		//tap on the OK pop-up
		tap(waitForElementName("OK",60));
	}

	

}
