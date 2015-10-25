package ua.net.itlabs;

import com.codeborne.selenide.ElementsCollection;
<<<<<<< HEAD
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.SelenideElement;
import com.google.common.io.Files;
import org.junit.After;
=======
>>>>>>> origin/master
import org.junit.Test;
import org.openqa.selenium.Keys;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
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

        goToActiveTodos();
        listShouldBeEmpty();
        createTask("2");
        edit("2", "task", Keys.ESCAPE);
        toggle("2");

        goToCompletedTodos();
        delete("1");
        toggle("2");

        goToActiveTodos();
        toggleAll();
        listShouldBeEmpty();
        clearCompleted();
        listShouldBeEmpty();
    }

    @Step
    public void createTask(String taskName){
        $("#new-todo").setValue(taskName).pressEnter();
    }

    @Step
    public void delete(String taskName){
        todos.find(exactText(taskName)).hover().$(".destroy").click();
    }

<<<<<<< HEAD
    @Step
    public void edit(String taskText, String newText) {
=======
    public void edit(String taskText, String newText, Keys key) {
>>>>>>> origin/master
        todos.find(exactText(taskText)).$(".view label").doubleClick();
        $(".editing .edit").setValue(newText).sendKeys(key);
    }

<<<<<<< HEAD
    @Step
    public void cancelEditingByESC(String taskText, String newText) {
        todos.find(exactText(taskText)).$(".view label").doubleClick();
        tasksEditField.setValue(newText).sendKeys(Keys.ESCAPE);
    }

    @Step
    public void toActiveTodos(){
        $("[href='#/active']").click();
    }

    @Step
    public void toCompletedTodos(){
        $("[href='#/completed']").click();
    }

    @Step
=======
    public void goToActiveTodos(){
        $("[href='#/active']").click();
    }

    public void goToCompletedTodos(){
        $("[href='#/completed']").click();
    }

>>>>>>> origin/master
    private void toggle(String taskText){
        todos.find(exactText(taskText)).$(".toggle").click();
    }

    private void toggleAll(){
        $("#toggle-all").click();
    }

    private void clearCompleted(){
        $("#clear-completed").click();
    }

    private void listShouldBeEmpty(){
        todos.filter(visible).shouldBe(empty);
    }

    ElementsCollection todos = $$("#todo-list li");
}
