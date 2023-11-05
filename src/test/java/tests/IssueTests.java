package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class IssueTests {

    @Test
    public void issueSearchTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        open("https://github.com");

        $(".search-input-container").click();
        $("#query-builder-test").sendKeys("qa-guru/knowledge-base");
        $("#query-builder-test").pressEnter();
        $("[href=\"/qa-guru/knowledge-base\"]").click();

        $("#issues-tab").click();
        $(withText("#3")).should(Condition.exist);
    }

    @Test
    public void lambdaStepIssueSearchTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем github", () -> {
            open("https://github.com");
        });
        step("Находим репозиторий", () -> {
            $(".search-input-container").click();
            $("#query-builder-test").sendKeys("qa-guru/knowledge-base");
            $("#query-builder-test").pressEnter();
        });
        step("Кликаем по репозиторию", () -> {
            $("[href=\"/qa-guru/knowledge-base\"]").click();
        });
        step("Кликаем по полю issues", () -> {
            $("#issues-tab").click();
        });
        step("Проверяем наличие issue с номером 3", () -> {
            $(withText("#3")).should(Condition.exist);
        });
    }

    @Test
    public void annotatedStepIssueSearchTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        SearchSteps steps = new SearchSteps();

        steps.openMainPage();
        steps.searchForRepository("qa-guru/knowledge-base");
        steps.clickOnRepositoryLink("qa-guru/knowledge-base");
        steps.openIssuesTab();
        steps.shouldSeeIssueWithNumber(3);
    }
}
