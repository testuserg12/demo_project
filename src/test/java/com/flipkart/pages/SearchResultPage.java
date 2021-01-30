package com.flipkart.pages;

import java.util.List;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.flipkart.utils.FlipKartUtils;

public class SearchResultPage {
	
	private final WebDriver driver;
	private final String SORT_BY_PRICE_LOW_TO_HIGH = "Price -- Low to High";
	
	@FindBy(xpath = "//div[contains(text(),'"+SORT_BY_PRICE_LOW_TO_HIGH+"')]")
	WebElement lnkSortPriceLowToHigh;
	
	@FindBy(css = "a[rel]")
	List<WebElement> txtItem;
	
	@FindBy(css = "span[id*='productRating']+span")
	WebElement productRating;
	
	@FindBy(xpath = "//a[3]")
	WebElement price;
	
	
	 /**
     * 
     * Constructor class for Search page here we initializing the driver for page
     * factory objects.
     * 
     * @param driver
     * @param url
     */
    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        FlipKartUtils.waitForElement(driver, lnkSortPriceLowToHigh);
    }
    
    
    /**
  	 * Click Price -- Low to High link
  	 * 
  	 */
  	public void clickSortPriceLowToHighLink() {
  		FlipKartUtils.waitForElement(driver, lnkSortPriceLowToHigh);
  		lnkSortPriceLowToHigh.click();
  	}
  	
  	public TreeMap<String,String> getItemDetails() {
  		String prize;
  		String storage = "";
  		String ratings = "";
  		String[] result;
  		TreeMap<String,String> map = new TreeMap<String,String>();  
  		for(WebElement element: txtItem){
  			try{
  				result = element.findElement(By.xpath("..")).getText().split("\\r?\\n");
  	  			prize = result[9];
  	  			storage = result[1].substring(0, (result[1].lastIndexOf("GB"))+3);
  	  			ratings = result[2].substring(3, result[2].indexOf('R'));
  	  			if(Integer.parseInt(prize.replaceAll("[^0-9]+	", "")) < 40000)
  	  			map.put(prize, storage+";"+ratings); 
  			} catch(Exception ex) {
  				System.out.println(ex.getMessage());
  			}
  		}
		return map;
  	}
}
