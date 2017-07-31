package com.qait.TATOC;

import javax.swing.text.Document;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;

public class TATOC_JS_BASIC {

	static FileReader f = new FileReader();
	static WebDriver driver;

	public static void main(String a[]) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "/home/navendushukla/Downloads/chromedriver");
		driver = new ChromeDriver();

		driver.get("http://10.0.1.86/tatoc");
		basicourse();
		grid_gate();
		dungeon();
		drag_around();
		pop_up_window();
		cookie_handling();

		Thread.sleep(5000);
		driver.close();

	}

	public static WebElement getelement(String s) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = (WebElement) js.executeScript("return document.evaluate( \"" + f.getELement(s)
				+ "\" ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;");
		return element;

	}

	public static void basicourse() {

		getelement("basic").click();
	}

	public static void grid_gate() {
		getelement("greenbox").click();
	}

	public static void dungeon() throws InterruptedException {
		driver.switchTo().frame("main");
		WebElement e = getelement("required_colour");
		String main = e.getAttribute("class").toString();
		getelement("paintbox_2").click();
		driver.switchTo().frame("child");
		WebElement e1 = getelement("required_colour");
		String child = e1.getAttribute("class").toString();

		driver.switchTo().parentFrame();
		while (!child.equals(main)) {

			getelement("paintbox_2").click();
			driver.switchTo().frame("child");
			WebElement e2 = getelement("required_colour");
			String child1 = e2.getAttribute("class").toString();
			child = child1;
			driver.switchTo().parentFrame();
			Thread.sleep(1000);
		}

		getelement("proceed_dungeon").click();

	}

	public static void drag_around() throws InterruptedException {
		WebElement target = getelement("drag_box");
		WebElement destination = getelement("drop_box");
		(new Actions(driver)).dragAndDrop(target, destination).perform();

		getelement("proceed_drag").click();
		Thread.sleep(1000);

	}

	public static void pop_up_window() throws InterruptedException {

		String handle = driver.getWindowHandle();

		getelement("launch_pop_up").click();

		for (String handle1 : driver.getWindowHandles()) {

			driver.switchTo().window(handle1);

		}

		// driver.switchTo().window("http://10.0.1.86/tatoc/basic/windows/popup");
		Thread.sleep(3000);
		getelement("input_name").click();
		getelement("input_name").sendKeys("Navendu Shukla");
		getelement("submit_name").click();
		driver.switchTo().window(handle);
		Thread.sleep(2000);
		getelement("proceed_pop_up").click();

	}
	public static void cookie_handling() {
		getelement("generate_token").click();
		String tokenn = getelement("token_string").getText();
		String token = tokenn.split(": ")[1];
		// System.out.println(token);
		Cookie name = new Cookie("Token", token);
		driver.manage().addCookie(name);

		getelement("proceed_cookie").click();

		String result = getelement("confirmation").getText();
		System.out.println(result);
		System.out.println("Task Completed");
	}
}
