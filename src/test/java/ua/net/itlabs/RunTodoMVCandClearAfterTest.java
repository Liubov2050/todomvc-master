package ua.net.itlabs;

import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import org.junit.After;
import org.junit.Before;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;

public class RunTodoMVCandClearAfterTest extends TestBase{

    @Before
    public void openPage(){
        open("http://todomvc.com/examples/troopjs_require/");
    }

    @After
    public void clearData() throws IOException {
        executeJavaScript("localStorage.clear();");
    }
}
