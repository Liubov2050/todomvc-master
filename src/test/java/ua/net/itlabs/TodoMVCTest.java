package ua.net.itlabs;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import org.junit.Test;
import org.openqa.selenium.Keys;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class TodoMVCTest extends RunTodoMVCandClearAfterTest {

    @Test
    public void smokeTest(){

        createTask("task1");
        startEdit("task1", "1").pressEnter();
        toggle("1");
        assertItemsLeft("0");

        goToActive();
        assertNoTodos();
        createTask("2");
        startEdit("2", "task").sendKeys(Keys.ESCAPE);
        toggle("2");
        assertNoTodos();

        goToCompleted();
        toggle("2");
        assertItemsLeft("1");
        clearCompleted();

        goToActive();
        assertItemsLeft("1");
        toggleAll();
        assertNoTodos();

        goToAll();
        delete("2");
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
    public SelenideElement startEdit(String taskText, String newText) {
        todos.find(exactText(taskText)).$(".view label").doubleClick();
        return ($(".editing .edit").setValue(newText));
    }

    @Step
    public void goToActive(){
        $("[href='#/active']").click();
    }

    @Step
    public void goToCompleted(){
        $("[href='#/completed']").click();
    }

    @Step
    public void goToAll(){
        $("[href='#/']").click();
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
    private void assertItemsLeft(String number) {
        $("#todo-count strong").shouldHave(exactText(number));
    }

    ElementsCollection todos = $$("#todo-list li");
}
