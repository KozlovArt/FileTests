package tests;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SearchSteps {
    @Step("Открываем github")
    public void openMainPage() {
        open("https://github.com");
    }

    @Step("Находим репозиторий")
    public void searchForRepository(String repo) {
        $(".search-input-container").click();
        $("#query-builder-test").sendKeys(repo);
        $("#query-builder-test").pressEnter();
    }

    @Step("Кликаем по репозиторию")
    public void clickOnRepositoryLink(String repo) {
        $("[href=\"/" + repo + "\"]").click();
    }

    @Step("Кликаем по полю issues")
    public void openIssuesTab() {
        $("#issues-tab").click();
    }

    @Step("Проверяем наличие issue с номером 3")
    public void shouldSeeIssueWithNumber(int issue) {
        $(withText("#" + issue)).should(Condition.exist);
    }
}
