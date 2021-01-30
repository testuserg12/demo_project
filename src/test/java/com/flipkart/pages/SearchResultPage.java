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
	
	@FindBy(xpath = "//div[contains(text(),'"+SORT_BY_PRICE_LOW_TO_HIGH+"')]")
	WebElement lnkSortPriceLowToHigh;
	
	@FindBy(css = ".s1Q9rs")
	List<WebElement> txtItem;
	
	@FindBy(css = "._2_R_DZ")
	List<WebElement> productRating;
	
	@FindBy(css = "._30jeq3")
	WebElement txtprice;
	
	@FindBy(css = "._25b18c")
	List<WebElement> pr;
	
	
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
  		//lnkSortPriceLowToHigh.click();
  	}
  	
  	public String getProductRating(WebElement element){
		String rating;
		try {
			rating = element.findElement(By.xpath("..")).findElement(By.cssSelector("span[id*='productRating']+span"))
					.getText().replaceAll("-", "");		
		} catch (Exception e) {
			rating = "";
		}
		return rating;
  	}
  	
  	public String getPrize(WebElement element){
		String price;
		try {
			FlipKartUtils.waitForElement(driver, txtprice);
			price = element.findElement(By.xpath("..")).findElement(By.cssSelector("._30jeq3"))
					.getText();			
		} catch (NoSuchElementException e) {
			price = "";
		}
		return price;
  	}
  	
  	public TreeMap<String,String> getItemDetails() {
  		String prize;
  		String storage = "";
  		String ratings = "";
  		TreeMap<String,String> map = new TreeMap<String,String>();  
  		FlipKartUtils.waitForElement(driver, txtItem.get(0));
  		for(int i=0; i<txtItem.size(); i++){
  			storage = txtItem.get(i).getText();
  			ratings = getProductRating(txtItem.get(i));
  			prize = getPrize(txtItem.get(i));
  			map.put(prize, storage+";"+ratings); 
  		}

		return map;
  	}
}
