package ua.net.itlabs;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class TodoMVCTest {

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

    public void createTask(String taskName){
        newTask.setValue(taskName).pressEnter();
    }

    public void delete(String taskName){
        todos.find(exactText(taskName)).hover().$(".destroy").click();
    }

    public void edit(String taskText, String newText) {
        todos.find(exactText(taskText)).$(".view label").doubleClick();
        tasksEditField.setValue(newText).pressEnter();
    }

    public void cancelEditingByESC(String taskText, String newText) {
        todos.find(exactText(taskText)).$(".view label").doubleClick();
        tasksEditField.setValue(newText).sendKeys(Keys.ESCAPE);
    }
    public void toActiveTodos(){
        $("[href='#/active']").click();
    }

    public void toCompletedTodos(){
        $("[href='#/completed']").click();
    }
    private void toggle(String taskText){
        todos.find(exactText(taskText)).$(".toggle").click();
    }

    SelenideElement newTask = $("#new-todo");
    SelenideElement tasksEditField = $(".active.editing .edit");
    SelenideElement clearCompleted = $("#clear-completed");
    SelenideElement completeAll = $("#toggle-all");
    ElementsCollection todos = $$("#todo-list li");
}
