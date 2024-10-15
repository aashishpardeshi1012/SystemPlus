package StepDefination;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AllSteps {

    WebDriver driver;
    WebDriverWait wait;
    String centerTitle="";
    @Given("Launch browser and open application")
    public void launch_browser_and_open_application() {
        System.setProperty("webdriver.chrome.driver","chromedriver.exe");
        driver=new ChromeDriver();
        driver.get("https://www.brighthorizons.com/");
        driver.manage().window().maximize();
        wait=new WebDriverWait(driver, Duration.ofSeconds(100));
        driver.findElement(By.xpath("//button[@id='onetrust-accept-btn-handler']")).click();
    }

    @Given("I click on search icon")
    public void i_click_on_search_icon() {
        driver.findElement(By.xpath("//a[@role='button' and contains(@href,'search-desktop-top')]//span")).click();
    }

    @Then("I see search field is visible")
    public void i_see_search_field_is_visible() {
        boolean display=getSearchField().isDisplayed();
        Assert.assertTrue(display);
    }

    public WebElement getSearchField(){
        WebElement searchField=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//nav[@id='subnav-search-desktop-top']//input[@id='search-field']")));
        return searchField;
    }

    @And("I search for {string}")
    public void i_search_for(String searchText) {
        getSearchField().sendKeys(searchText+ Keys.ENTER);
    }

    @Then("I see search result {string} at place {string}")
    public void i_See_Search_Result_At_Place(String name, String indx) {
        int col=Integer.parseInt(indx);
        String resultText=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class,'search-result')][1]//h3"))).getText();
        Assert.assertEquals(resultText,name);
    }

    @And("I click on {string} button")
    public void i_Click_On_Button(String bname) {
        driver.findElement(By.xpath("(//nav[@role='navigation']//li[contains(@class,'displayed-desktop')]//a[normalize-space()='"+bname+"'])[2]")).click();
    }

    @Then("I see url contain {string}")
    public void i_See_Url_Contain(String url) {
        String urlGet=driver.getCurrentUrl();
        boolean containsOrNot = urlGet.contains(url);
        Assert.assertTrue(containsOrNot);
    }

    @And("I enter {string} into field and press Enter")
    public void i_Enter_Into_Field_And_Press_Enter(String city) {
        WebElement element= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='addressInput']")));//.sendKeys(city+ Keys.ENTER);
        element.sendKeys(city+ Keys.ENTER);
    }

    @Then("I see result number and list number are same")
    public void i_See_Result_Number_And_List_Number_Are_Same() {
        String number=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='center-results-container']//span[contains(@class,'resultsNumber')]"))).getText();
        List<WebElement> list=driver.findElements(By.xpath("//div[@id='center-results-container']//div[contains(@class,'centerResult infoWindow')]"));
        int num=Integer.parseInt(number);
        Assert.assertEquals(num,list.size());
    }

    @And("I click on center {string}")
    public void i_Click_On_Center(String center) {
        driver.findElement(By.xpath("//div[@id='center-results-container']//div[contains(@class,'centerResult infoWindow')]//h3[text()='"+center+"']")).click();
        centerTitle=center;
    }

    @Then("I see {string} are same on list and popup")
    public void iSeeAreSameOnListAndPopup(String find) {
        if(find.equals("name")){
           String getTitl= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='dialog']//div[@class='mapTooltip']//span[contains(@class,'headline')]"))).getText();
            Assert.assertEquals(centerTitle,getTitl);
        }else if(find.equals("address")){
        String addressList=driver.findElement(By.xpath("//div[@id='center-results-container']//div[contains(@class,'centerResult infoWindow')]//h3[text()='"+centerTitle+"']//following-sibling::span[contains(@class,'address')]")).getText();
        String addressPop=driver.findElement(By.xpath("//div[@role='dialog']//div[@class='mapTooltip']//div[contains(@class,'address')]")).getText();
            System.out.println(addressList+"---"+addressPop);
        Assert.assertEquals(addressList,addressPop.replace("\n", " "));
        }
    }
}
