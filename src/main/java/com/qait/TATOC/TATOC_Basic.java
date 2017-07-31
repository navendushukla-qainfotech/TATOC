package com.qait.TATOC;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

/** @author Navendu Shukla */

public class TATOC_Basic {
	static WebDriver driver;

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "/home/navendushukla/Downloads/chromedriver");
		driver = new ChromeDriver();

		driver.get("http://10.0.1.86/tatoc");
		driver.findElement(By.xpath("//a[text()='Basic Course']")).click();

		// TODO Auto-generated method stub

		grid_gate();
		dungeon();
		drag_around();
		pop_up_window();
		cookie_handling();
	}

	public static void grid_gate() {
		driver.findElement(By.xpath("//div[@class='greenbox']")).click();
	}

	public static void dungeon() throws InterruptedException {
		driver.switchTo().frame("main");
		WebElement e = driver.findElement(By.xpath(".//*[@id='answer']"));
		String main = e.getAttribute("class").toString();
		driver.findElement(By.xpath("//a[text()='Repaint Box 2']")).click();
		driver.switchTo().frame("child");
		WebElement e1 = driver.findElement(By.xpath(".//*[@id='answer']"));
		String child = e1.getAttribute("class").toString();

		driver.switchTo().parentFrame();
		while (!child.equals(main)) {

			driver.findElement(By.xpath("//a[text()='Repaint Box 2']")).click();
			driver.switchTo().frame("child");
			WebElement e2 = driver.findElement(By.xpath(".//*[@id='answer']"));
			String child1 = e2.getAttribute("class").toString();
			child = child1;
			driver.switchTo().parentFrame();
			Thread.sleep(1000);
		}

		driver.findElement(By.xpath("//a[text()='Proceed']")).click();

	}

	public static void drag_around() throws InterruptedException {
		WebElement target = driver.findElement(By.xpath(".//div[@id='dragbox']"));
		WebElement destination = driver.findElement(By.xpath(".//div[@id='dropbox']"));
		(new Actions(driver)).dragAndDrop(target, destination).perform();

		driver.findElement(By.xpath("//a[text()='Proceed']")).click();
		Thread.sleep(1000);

	}

	public static void pop_up_window() throws InterruptedException {
		driver.findElement(By.xpath("//a[text()='Launch Popup Window']")).click();

		String handle = driver.getWindowHandle();

		// System.out.println(handle);
		driver.findElement(By.xpath("//a[text()='Launch Popup Window']")).click();
		// Set handles = driver.getWindowHandles();

		// System.out.println(handles);
		for (String handle1 : driver.getWindowHandles()) {

			// System.out.println(handle1);

			driver.switchTo().window(handle1);

		}

		// driver.switchTo().window("http://10.0.1.86/tatoc/basic/windows/popup");
		Thread.sleep(3000);
		driver.findElement(By.xpath(".//input[@id='name']")).click();
		driver.findElement(By.xpath(".//input[@id='name']")).sendKeys("Navendu Shukla");
		driver.findElement(By.xpath(".//input[@id='submit']")).click();
		driver.switchTo().window(handle);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[text()='Proceed']")).click();

	}

	public static void cookie_handling() {
		driver.findElement(By.xpath("//a[text()='Generate Token']")).click();
		String tokenn = driver.findElement(By.xpath(".//span[@id='token']")).getText();
		String token = tokenn.split(": ")[1];
		// System.out.println(token);
		Cookie name = new Cookie("Token", token);
		driver.manage().addCookie(name);

		driver.findElement(By.xpath("//a[text()='Proceed']")).click();

		String result = driver.findElement(By.xpath("//span[@class='finish']")).getText();
		System.out.println(result);
		System.out.println("Task Completed");
	}
}
