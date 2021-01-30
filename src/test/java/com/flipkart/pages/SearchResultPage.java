package com.flipkart.pages;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.flipkart.utils.FlipKartUtils;


public class SearchResultPage {
	
	private final WebDriver driver;
	private final String SORT_BY_PRICE_LOW_TO_HIGH = "Price -- Low to High";
	
	private final String textIphoneItemLoacator = ".//a[contains(text(),'iPhone')]";
	private final String textproductRatingLocator = ".//span[contains(@id,'product')]/following-sibling::span";
	private final String textpriceLocator = ".//div[contains(text(),'₹')]";
	
	
	@FindBy(xpath = "//div[contains(text(),'"+SORT_BY_PRICE_LOW_TO_HIGH+"')]")
	WebElement lnkSortPriceLowToHigh;
	
	@FindBy(xpath = "//div[@data-id]")
	List<WebElement> lstDataId;
	
	@FindBy(css = "div[data-id]")
	List<WebElement> lstdataId;
	
	
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
  		JavascriptExecutor executor = (JavascriptExecutor)driver;
  		executor.executeScript("arguments[0].click();", lnkSortPriceLowToHigh);
  	}
  	
  	public String getItem(WebElement element){
		String rating;
		try {
			rating = element.findElement(By.xpath(textIphoneItemLoacator))
					.getText();		
		} catch (Exception e) {
			rating = "";
		}
		return rating;
  	}
  	
  	public String getProductRating(WebElement element){
		String rating;
		try {
			rating = element.findElement(By.xpath(textproductRatingLocator))
					.getText();			
		} catch (Exception e) {
			rating = "";
		}
		return rating;
  	}
  	
  	public String getPrize(WebElement element){
		String price;
		try {
			price = element.findElement(By.xpath(textpriceLocator))
					.getText();			
		} catch (NoSuchElementException e) {
			price = "";
		}
		return price;
  	}
  	
  	public TreeMap<String,String> getItemDetails() {
  		String price;
  		String storage = "";
  		String ratings = "";
  		TreeMap<String,String> map = new TreeMap<String,String>();  
  		FlipKartUtils.waitForElement(driver, lstDataId.get(0));
  		for(int i=0; i<lstDataId.size(); i++){
  			storage = getItem(lstDataId.get(i));
  			storage = storage.substring(0, storage.lastIndexOf(')')+1);
  			ratings = getProductRating(lstDataId.get(i)).replaceAll("^([-+] ?)?[0-9]+(,[0-9]+)?$", "");
  			price = getPrize(lstDataId.get(i));
  			if(Integer.parseInt(price.replaceAll("[^\\d.]", "")) <= 40000  && ratings.length()!=0 && storage.contains("GB"))
  			map.put(price, storage+";"+ratings); 
  			else if(Integer.parseInt(price.replaceAll("[^\\d.]", "")) > 40000)
  			break;  			
  			// toDo click on nextpage and call recursively
  			//else if(Integer.parseInt(price.replaceAll("[^\\d.]", "")) < 40000)
  			//clickNextPage();
  			//getItemDetails();
  		}
		return map;
  	}
}
