package ua.net.itlabs;

import com.codeborne.selenide.ElementsCollection;
import org.junit.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class TodoMVCTest {

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

    public void createTask(String taskName){
        $("#new-todo").setValue(taskName).pressEnter();
    }

    public void delete(String taskName){
        todos.find(exactText(taskName)).hover().$(".destroy").click();
    }

    public void edit(String taskText, String newText, Keys key) {
        todos.find(exactText(taskText)).$(".view label").doubleClick();
        $(".editing .edit").setValue(newText).sendKeys(key);
    }

    public void goToActiveTodos(){
        $("[href='#/active']").click();
    }

    public void goToCompletedTodos(){
        $("[href='#/completed']").click();
    }

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
