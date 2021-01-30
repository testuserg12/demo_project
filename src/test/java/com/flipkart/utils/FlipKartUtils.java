package com.flipkart.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlipKartUtils {

	public static int flipKartMaxElementWait = 3;
	
	/**
     * To wait for the specific element on the page
     * 
     * @param driver -
     * @param element - webelement to wait for to appear
     * @return boolean - return true if element is present else return false
     */
    public static boolean waitForElement(WebDriver driver, WebElement element) {
        boolean statusOfElementToBeReturned = false;
        WebDriverWait wait = new WebDriverWait(driver, flipKartMaxElementWait);
        try {
            WebElement waitElement = wait.until(ExpectedConditions.visibilityOf(element));
            if (waitElement.isDisplayed() && waitElement.isEnabled()) {
                statusOfElementToBeReturned = true;
            }
        } catch (Exception ex) {
        	System.out.println("Unable to find a element" + ex.getMessage());
    }
		return statusOfElementToBeReturned;
    }
	
	
}
