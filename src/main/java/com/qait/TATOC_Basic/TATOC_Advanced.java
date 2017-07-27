package com.qait.TATOC_Basic;

import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * @author Navendu Shukla
 * 
 * */

public class TATOC_Advanced {
	static WebDriver driver;

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException, ParseException, IOException {

		System.setProperty("webdriver.chrome.driver", "/home/navendushukla/Downloads/chromedriver");
		driver = new ChromeDriver();
		// prop = new Properties();
		// WebDriverWait wait=new WebDriverWait(driver, 20);
		driver.get("http://10.0.1.86/tatoc");
		driver.findElement(By.xpath("//a[text()='Advanced Course']")).click();

		Hover_Menu();
		query_gate();
		ooyala_video_player();
		Restful();
		FileHandle();

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
		//System.out.println(str);
		String credential_name =str.substring(0,1);
		String credential_passkey = "";
		
		ResultSet r1= db.DBhandler();
		while (r1.next()) {
//			String Name = r1.getString(2);
//			String pass = r1.getString(3);
//			System.out.println(Name + "  " + pass);

			if (r1.getString(2).equals(credential_name)) {
				credential_passkey = r1.getString(3);
				
			}
		}
		
		driver.findElement(By.xpath(".//input[@id='name']")).click();
		driver.findElement(By.xpath(".//input[@id='name']")).sendKeys(credential_name);
		driver.findElement(By.xpath(".//input[@id='passkey']")).click();
		driver.findElement(By.xpath(".//input[@id='passkey']")).sendKeys(credential_passkey);
		driver.findElement(By.xpath(".//input[@id='submit']")).click();
		Thread.sleep(2000);
		
		
		r1.close();
		

	}
	/** ooyala video player*/
	
	public static void ooyala_video_player(){
		String S =driver.getTitle();
		String str="Video Player - Advanced Course - T.A.T.O.C";
		if(S.equals(str)){
			driver.get("http://10.0.1.86/tatoc/advanced/rest/#");
		}
	}
	
	
	
	public static void Restful() throws ParseException, InterruptedException{
		String id=driver.findElement(By.xpath(".//span[@id='session_id']")).getText();
		String id1=id.split(": ")[1];
		//System.out.println(id1);
		String s=RestAssured.when().get("http://10.0.1.86/tatoc/advanced/rest/service/token/"+id1).getBody().asString();
		JSONParser parser= new JSONParser();
		JSONObject jo = (JSONObject) parser.parse(s);
		String token =  (String)jo.get("token");
		//System.out.println(token);
		RestAssured.given().parameters("id", id1, "signature", token, "allow_access", "1").when().post("http://10.0.1.86/tatoc/advanced/rest/service/register").then().assertThat().statusCode(200);	
		driver.findElement(By.xpath("//a[text()='Proceed']")).click();		
		//System.out.println(s+" "+token);
		

	}

	public static void FileHandle() throws IOException, InterruptedException{
		
		driver.findElement(By.xpath("//a[text()='Download File']")).click();
		Thread.sleep(6000);
		String filepath= "/home/navendushukla/Downloads/file_handle_test.dat";
		File f =new File(filepath);
		String sig="";
		if(!f.exists()){
			System.out.println("File does not exists");
		}
		else{
			
			BufferedReader br= new BufferedReader(new FileReader(f));
			String line= br.readLine();
			while (line != null){
			String s= line.split(": ")[0];
			if(s.equalsIgnoreCase(line.split(": ")[0])){
				sig=line.split(": ")[1];
				//System.out.println(sig);
			   
			}
			line=br.readLine();
		}
			br.close();
		}
	     driver.findElement(By.xpath("//input[@name='signature']")).click();
	     driver.findElement(By.xpath("//input[@name='signature']")).sendKeys(sig);
	     
	          
	     
	     
	     
	     driver.findElement(By.xpath("//input[@class='submit']")).click();
	     f.delete();
	     
	System.out.println("TATOC Advanced task is completed");
	}
	
	
	
	
	
	// TODO Auto-generated method stub

}
