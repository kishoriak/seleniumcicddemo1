package rahulshettyacademy.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;

public class StandAloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        System.out.println("change to check cicd")
		String productName = "ZARA COAT 3";
		//chrome setup using WebDriverManager, chrome browser
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		
		//wait for browser to open, implicite wait is global wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//maximize the window so that all elements will be visible
		driver.manage().window().maximize();
		
		//login to the site
		driver.get("https://rahulshettyacademy.com/client");
		//LandingPage landingPage = new LandingPage(driver);
		driver.findElement(By.id("userEmail")).sendKeys("anshika@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Iamking@000");
		driver.findElement(By.id("login")).click();
		
		//wait till list of product page opens
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		//find list of all products in the page
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		
		//find element for product  which has "ZARA COAT 3" , and select the element
		WebElement prod =	products.stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		//checking the message product added is visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		//ng-animating
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		//click on cart icon
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		//check on the cart page selected element exists or not
		List <WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));		
	Boolean match = 	cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productName));
	Assert.assertTrue(match);
	driver.findElement(By.cssSelector(".totalRow button")).click();
	
	//select the value from the dropdown
	Actions a = new Actions(driver);
	a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
	
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
	
	//click the button place order
	driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
	driver.findElement(By.cssSelector(".action__submit")).click();
	
	//to check the following message appears on the screen
	String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
	Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	driver.close();
	

	}

}
