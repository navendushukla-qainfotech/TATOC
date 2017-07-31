package com.qait.TATOC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FileReader {

static String filepath = "/home/navendushukla/workspace_OLD/TATOC/src/main/resources/path.txt";
static WebDriver driver;		

//public static void main(String a[]){
//	String s=getELement("basic");
//	System.out.println(s);
//	
//}

	public static String getELement(String elementName) {
        
        BufferedReader br = null;
        String matchingLine = "";
        
        try {
        	FileInputStream f = new FileInputStream(filepath);

        	InputStreamReader reader = new InputStreamReader(f);
        	
                br = new BufferedReader(reader);
                String line = br.readLine();
                while (line != null) {
                    if (line.split(": ", 3)[0].equalsIgnoreCase(elementName)) {
                        matchingLine = line;
                        break;
                    }
                    line = br.readLine();
                }
        	
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String S= matchingLine.split(": ", 3)[2];
        
        return S;
        
  
	}

	
}
