package ua.net.itlabs;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import org.junit.After;

import org.junit.Test;
import org.openqa.selenium.Keys;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class TodoMVCTest {

    @After
    public void tearDown() throws IOException {
        screenshot();
    }

    @Attachment(type = "image/png")
    public byte[] screenshot() throws IOException {
        File screenshot = Screenshots.getScreenShotAsFile();
        return Files.toByteArray(screenshot);
    }

    @Test
    public void smokeTest(){

        open("http://todomvc.com/examples/troopjs_require/");

        createTask("task1");
        edit("task1", "1", Keys.ENTER);
        toggle("1");
        assertCompleted("1");

        goToActiveTodos();
        assertNoTodos();
        createTask("2");
        edit("2", "task", Keys.ESCAPE);
        toggle("2");

        goToCompletedTodos();
        delete("1");
        toggle("2");

        goToActiveTodos();
        toggleAll();
        assertNoTodos();
        clearCompleted();
        assertNoTodos();
    }

    @Step
    public void createTask(String taskName){
        $("#new-todo").setValue(taskName).pressEnter();
    }

    @Step
    public void delete(String taskName){
        todos.find(exactText(taskName)).hover().$(".destroy").click();
    }

    @Step
    public void edit(String taskText, String newText, Keys key) {
        todos.find(exactText(taskText)).$(".view label").doubleClick();
        $(".editing .edit").setValue(newText).sendKeys(key);
    }

    @Step
    public void goToActiveTodos(){
        $("[href='#/active']").click();
    }

    @Step
    public void goToCompletedTodos(){
        $("[href='#/completed']").click();
    }

    @Step
    private void toggle(String taskText){
        todos.find(exactText(taskText)).$(".toggle").click();
    }

    @Step
    private void toggleAll(){
        $("#toggle-all").click();
    }

    @Step
    private void clearCompleted(){
        $("#clear-completed").click();
    }

    @Step
    private void assertNoTodos(){
        todos.filter(visible).shouldBe(empty);
    }

    @Step
    private void assertCompleted(String taskText) {
        todos.findBy(exactText(taskText)).shouldHave(attribute("checked=''"));
    }

    ElementsCollection todos = $$("#todo-list li");
}
