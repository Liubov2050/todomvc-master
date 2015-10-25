package ua.net.itlabs;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.SelenideElement;
import com.google.common.io.Files;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.Keys;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.not;
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
        edit("task1", "1");
        toggle("1");
        clearCompleted.shouldBe(visible);
        createTask("2");
        todos.shouldHave(texts("1", "2"));

        toActiveTodos();
        createTask("3");
        cancelEditingByESC("3", "task");
        toggle("3");
        edit("2","");
        todos.filter(visible).shouldBe(empty);

        toCompletedTodos();
        delete("1");
        toggle("3");
        clearCompleted.shouldBe(not(visible));

        toActiveTodos();
        completeAll.click();
        clearCompleted.click();
        todos.shouldBe(empty);

    }

    @Step
    public void createTask(String taskName){
        newTask.setValue(taskName).pressEnter();
    }

    @Step
    public void delete(String taskName){
        todos.find(exactText(taskName)).hover().$(".destroy").click();
    }

    @Step
    public void edit(String taskText, String newText) {
        todos.find(exactText(taskText)).$(".view label").doubleClick();
        tasksEditField.setValue(newText).pressEnter();
    }

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
    private void toggle(String taskText){
        todos.find(exactText(taskText)).$(".toggle").click();
    }

    SelenideElement newTask = $("#new-todo");
    SelenideElement tasksEditField = $(".active.editing .edit");
    SelenideElement clearCompleted = $("#clear-completed");
    SelenideElement completeAll = $("#toggle-all");
    ElementsCollection todos = $$("#todo-list li");
}
