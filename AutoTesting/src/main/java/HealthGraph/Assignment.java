package HealthGraph;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.core.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import BasePackage.projectSpecficBase;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Assignment extends projectSpecficBase {

	@Parameters({ "url", "password", "username" })
	@Test
	private void SetUP(String URL, String UserName, String Password) {

		// Enter Login credentials
		driver.get(URL);
		driver.findElement(By.id("AccountLink")).click();
		driver.findElement(By.id("uid")).sendKeys(UserName);
		driver.findElement(By.id("passw")).sendKeys(Password);
		driver.findElement(By.name("btnSubmit")).click();
		String PageTitle = driver.getTitle();
		if (PageTitle.equals("Altoro Mutual")) {

			System.out.println(" signed in with correct credentials");

		} else {

			System.out.println("Invalid Credentials");
		}

		// Clicking e View Account Summary option
		driver.findElement(By.id("MenuHyperLink1")).click();

		// Select the 800001 Checking account option and click on the Go button
		WebElement accountDetailsDropdown = driver.findElement(By.id("listAccounts"));
		Select s1 = new Select(accountDetailsDropdown);
		s1.selectByValue("800001");
		driver.findElement(By.id("btnGetAccount")).click();

		// Assert the Available Balance
		String actualAmount = driver.findElement(By.xpath("//table/following::td[@align='right']")).getText();
		String expectedAmount = "$114282.44";
		Assert.assertEquals(actualAmount, expectedAmount);

		// Click on the Transfer Funds option
		driver.findElement(By.id("MenuHyperLink3")).click();

		// Enter the following data & click on the Transfer Funds button
		WebElement toAccount = driver.findElement(By.xpath("//select[@id='toAccount']"));
		Select s2 = new Select(toAccount);
		s2.selectByValue("800001");
		driver.findElement(By.xpath("//input[@id='transferAmount']")).sendKeys("9876");
		driver.findElement(By.cssSelector("input#transfer")).click();

		// Validate the Transaction Details message
		String successMessage = driver.findElement(By.id("_ctl0__ctl0_Content_Main_postResp")).getText();
		if (successMessage.contains("9876.0")) {

			System.out.println("Successfully Transferred");

		}

		// Click on the View Recent Transactions

		driver.findElement(By.id("MenuHyperLink2")).click();

		// Click on Contact Us on Top Right and then click on online form
		driver.findElement(By.xpath("//a[text()='Contact Us']")).click();
		driver.findElement(By.xpath("//a[text()='online form']")).click();

		// Enter some details here and click on Submit button
		driver.findElement(By.xpath("//textarea[@name='comments']")).sendKeys("Testing the Application");
		driver.findElement(By.xpath("//input[@name='submit']")).click();

		// a Thank You message is displayed
		String ThankText = driver.findElement(By.xpath("//h1")).getText();
		if (ThankText.equals("Thank You")) {

			System.out.println("Form Successfully Submitted");

		} else {

			System.out.println("Form is not submitted");
		}

		// Click on the Sign Off button
		driver.findElement(By.id("LoginLink")).click();

	}

}
