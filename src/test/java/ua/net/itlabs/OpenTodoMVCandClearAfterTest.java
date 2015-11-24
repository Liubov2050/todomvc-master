package ua.net.itlabs;

import org.junit.After;
import org.junit.Before;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class OpenTodoMVCandClearAfterTest extends TestBase {

    @Before
    public void openPage(){
        open("https://todomvc4tasj.herokuapp.com/");
    }

    @After
    public void clearData() throws IOException {
        executeJavaScript("localStorage.clear()");
    }
}
