package ua.net.itlabs;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import org.openqa.selenium.Keys;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class TodoMVCTest extends OpenTodoMVCandClearAfterTest {

    @Test
    public void testCoreActionsWithTasks(){

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
        assertVisible(1);
        clearCompleted();
        assertNoTodos();

        goToActive();
        assertVisible(1);
        toggleAll();
        assertNoTodos();

        goToAll();
        delete("2");
        assertNoTodos();
    }

    @Test
    public void testCompleteEditByClickOutsideOnAll(){
        createTask("1");

        startEdit("1", "task1");
        clickOutside();
        assertName("task1");
    }

    @Test
    public void testCompleteAndReopenAlOnAll() {

        createTask("1");
        createTask("2");

        toggleAll();
        assertItemsLeft("0");
        toggleAll();
        assertItemsLeft("2");
        toggleAll();
        assertItemsLeft("0");
    }

    @Test
    public void testDeleteByEditingOnActive() {

        createTask("1");

        goToActive();
        startEdit("1", "").pressEnter();
        assertNoTodos();
    }

    @Test
    public void testEditOnCompleted() {

        createTask("1");
        toggle("1");
        assertItemsLeft("0");

        goToCompleted();
        assertVisible(1);

        startEdit("1", "task1").pressEnter();
        assertName("task1");
        assertVisible(1);
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

    private void assertVisible(int number){
        todos.filter(visible).shouldHaveSize(number);
    }

    @Step
    private void assertItemsLeft(String number) {
        $("#todo-count strong").shouldHave(exactText(number));
    }

    @Step
    private void clickOutside(){
        $("#new-todo").click();
    }

    @Step
    private void assertName(String taskName){
        todos.find(exactText(taskName)).shouldBe(visible);
    }

    ElementsCollection todos = $$("#todo-list li");
}
