package com.qait.TATOC_Basic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author Navendu Shukla
 * 
 * */

public class TATOC_Advanced {
	static WebDriver driver;

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {

		System.setProperty("webdriver.chrome.driver", "/home/navendushukla/Downloads/chromedriver");
		driver = new ChromeDriver();
		// prop = new Properties();
		// WebDriverWait wait=new WebDriverWait(driver, 20);
		driver.get("http://10.0.1.86/tatoc");
		driver.findElement(By.xpath("//a[text()='Advanced Course']")).click();

		Hover_Menu();
		query_gate();

	}

	/**
	 * Hover Menu
	 * 
	 * @throws InterruptedException
	 */
	public static void Hover_Menu() throws InterruptedException {

		WebElement element = driver.findElement(By.xpath("//span[@class='menutitle']"));
		Actions action = new Actions(driver);

		action.moveToElement(element).build().perform();
		// Thread.sleep(4000);
		driver.findElement(By.xpath("//span[text()='Go Next']")).click();
	}

	/**
	 * query_gate
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 */

	public static void query_gate() throws ClassNotFoundException, SQLException, InterruptedException {

		DBhandler db= new DBhandler();
		Thread.sleep(2000);
		String str = driver.findElement(By.xpath(".//div[@id='symboldisplay']")).getText();
		System.out.println(str);
		String credential_name =str.substring(0,1);
		String credential_passkey = "";
		
		ResultSet r1= db.DBhandler();
		while (r1.next()) {
			String Name = r1.getString(2);
			String pass = r1.getString(3);
			System.out.println(Name + "  " + pass);

			if (r1.getString(2).equals(credential_name)) {
				credential_passkey = r1.getString(3);
				
			}
		}
		
		driver.findElement(By.xpath(".//input[@id='name']")).click();
		driver.findElement(By.xpath(".//input[@id='name']")).sendKeys(credential_name);
		driver.findElement(By.xpath(".//input[@id='passkey']")).click();
		driver.findElement(By.xpath(".//input[@id='passkey']")).sendKeys(credential_passkey);
		driver.findElement(By.xpath(".//input[@id='submit']")).click();
		
		r1.close();
		

	}
	/** ooyala video player*/
	
	public static void ooyala_video_player(){
		
		
	}
	

	
	
	
	
	
	// TODO Auto-generated method stub

}
