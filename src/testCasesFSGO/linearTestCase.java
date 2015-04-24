package testCasesFSGO;

import java.util.ArrayList;
import java.util.Properties;
import org.junit.Before;
import org.junit.Test;
import driver.IOSLaunch;
import fileReaders.PropertiesReader;
import testCasesFSGO.AbstractActions;
import objects.Accounts;


public class linearTestCase extends IOSLaunch {
	
	Properties properties = PropertiesReader.getPropertiesFile("xpath");
	AbstractActions am_test = new AbstractActions();
	boolean loginFlag =true;
	
	@Before
	public void locationSettings() {
		
	}
	
	@Test
	public void TestScenario() throws InterruptedException
	{
		int mvpd_counter =0;
		while(mvpd_counter<MVPD_MAX) {
			
			System.out.println("NAME :" + mvpd.get(mvpd_counter).getName());
			//This function iterates throughout the entire accounts present in the mvpd.
			accountLooper(mvpd.get(mvpd_counter).getAccount(),mvpd.get(mvpd_counter).getName());
			mvpd_counter++;
		}	
	}
	private void accountLooper(ArrayList<Accounts> accounts,String mvpdName) throws InterruptedException {
		
		int account_counter =0;
		while(account_counter< ACCOUNT_MAX) {
			
			/*
			 * at first the login value is set to true for the first time because the login process
			 * would require the following actions:
			 * to click on the settings, then click on the Sign-In option, then more providers option
			 * followed by the MVPD name
			 */
			if(loginFlag)
				am_test.loginAccountSettings(mvpdName);
			/*
			 * this function sign-in into the account using the username and password
			 * on successful login the function returns true or it returns false on failure.
			 */
			loginFlag = am_test.login(accounts.get(account_counter),mvpdName);
			System.out.println(accounts.get(account_counter).getUsername() + " Login: " + loginFlag);
			//if the login is true the videos are played.
			if(loginFlag){
				am_test.playVideos();
				am_test.logout();
				
			}
			account_counter++;
		}
		/*
		 * if the login was failed for the last account, then move to the default starting page
		 */
		if(!loginFlag) {
			//tap on the cancel button
			tap(waitForElementXpath(properties.getProperty("cancel"),60));
			//tap on the back-arrow
			tap(waitForElementXpath(properties.getProperty("back_arrow"),60));
			//tap on cancel to get to the default start page.
			tap(waitForElementName("Cancel",60));
			//set to the default value.
			loginFlag= true;
		}
	}

}
