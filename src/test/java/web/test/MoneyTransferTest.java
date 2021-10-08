package web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.data.DataHelper;
import web.page.DashboardReplenishCards;
import web.page.DashboardYourCards;
import web.page.LoginPageV1;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    @BeforeEach
    public void setUpAll() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void transferMaxMoneyBetweenOwnCards() {
        var cardsInfo = DataHelper.getCardsInfo();
        var yourCards = new DashboardYourCards();
        int firstBalanceBefore = yourCards.getFirstCardBalance();
        int secondBalanceBefore = yourCards.getSecondCardBalance();
        int difference = 100000;
        yourCards.replenishFirst();
        var replenishThis = new DashboardReplenishCards();
        replenishThis.replenish(Integer.toString(difference), cardsInfo, 1);
        assertEquals(firstBalanceBefore + difference, yourCards.getFirstCardBalance());
        assertEquals(secondBalanceBefore - difference, yourCards.getSecondCardBalance());
    }

    @Test
    void transferMoreMaxMoneyBetweenOwnCards() {
        var cardsInfo = DataHelper.getCardsInfo();
        var yourCards = new DashboardYourCards();
        int firstBalanceBefore = yourCards.getFirstCardBalance();
        int secondBalanceBefore = yourCards.getSecondCardBalance();
        int difference = 100000;
        yourCards.replenishSecond();
        var replenishThis = new DashboardReplenishCards();
        replenishThis.replenish(Integer.toString(difference), cardsInfo, 2);
        assertEquals(firstBalanceBefore, yourCards.getFirstCardBalance());
        assertEquals(secondBalanceBefore, yourCards.getSecondCardBalance());
    }
}

