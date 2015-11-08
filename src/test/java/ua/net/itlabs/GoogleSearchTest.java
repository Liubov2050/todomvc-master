package ua.net.itlabs;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class GoogleSearchTest {

    @Test
    public void GoogleSearchTest() throws InterruptedException {

        open("http://google.com/ncr");

        search("selenium");
        assertResultsFound(10);
        assertFirstHaveText("Selenium automates browsers");
        clickFirstLink();

        switchToTab(0);
        assertTitleText("Selenium - Web Browser Automation");
    }

    public void search(String text){
        $("#lst-ib").sendKeys(text);
    }

    public void assertResultsFound(int amount){
        $$("#rso .srg .g").shouldHaveSize(amount);
    }

    public void assertFirstHaveText(String text){
        $(".g:nth-of-type(1) .rc .st").shouldHave(text(text));
    }

    public void clickFirstLink(){
        $(".g:nth-of-type(1) .rc .r>a").click();
    }

    public void switchToTab(int tabPosition){
        ArrayList<String> tabs = new ArrayList<String>(getWebDriver().getWindowHandles());
        getWebDriver().switchTo().window(tabs.get(tabPosition));
    }

    public void assertTitleText(String text){
        assertEquals(text, getWebDriver().getTitle());
    }
}
