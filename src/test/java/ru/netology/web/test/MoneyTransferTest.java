package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV2;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

  @BeforeEach
  public void openPage() {

    open("http://localhost:9999");
    val loginPage = new LoginPageV2();
    val authInfo = DataHelper.getAuthInfo();
    val verificationPage = loginPage.validLogin(authInfo);
    val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    verificationPage.validVerify(verificationCode);
  }

  @Test
  void shouldTransferMoneyBetweenOwnCards1() {

    val dashboardPage = new DashboardPage();

    int balanceFirstCard = dashboardPage.getFirstCardBalance();
    int balanceSecondCard = dashboardPage.getSecondCardBalance();
    val moneyTransfer = dashboardPage.firstCardButton();
    val cardInfo = DataHelper.getSecondCardNumber();
    String sum = "1500";
    moneyTransfer.transferForm(sum, cardInfo);

    assertEquals(balanceFirstCard + Integer.parseInt(sum), dashboardPage.getFirstCardBalance());
    assertEquals(balanceSecondCard - Integer.parseInt(sum), dashboardPage.getSecondCardBalance());
  }

  @Test
  void shouldTransferMoneyBetweenOwnCards2() {

    val dashboardPage = new DashboardPage();

    int balanceFirstCard = dashboardPage.getFirstCardBalance();
    int balanceSecondCard = dashboardPage.getSecondCardBalance();
    val moneyTransfer = dashboardPage.secondCardButton();
    val cardInfo = DataHelper.getFirstCardNumber();
    String sum = "5000";
    moneyTransfer.transferForm(sum, cardInfo);

    assertEquals(balanceFirstCard - Integer.parseInt(sum), dashboardPage.getFirstCardBalance());
    assertEquals(balanceSecondCard + Integer.parseInt(sum), dashboardPage.getSecondCardBalance());
  }

  @Test
  void shouldCancelMoneyTransfer() {

    val dashboardPage = new DashboardPage();

    int balanceFirstCard = dashboardPage.getFirstCardBalance();
    int balanceSecondCard = dashboardPage.getSecondCardBalance();
    val moneyTransfer = dashboardPage.firstCardButton();
    val cardInfo = DataHelper.getSecondCardNumber();
    String sum = "1000";
    moneyTransfer.transferForm(sum, cardInfo);
    moneyTransfer.cancelButton();

    assertEquals(balanceFirstCard, dashboardPage.getFirstCardBalance());
    assertEquals(balanceSecondCard, dashboardPage.getSecondCardBalance());
  }

  @Test
  void shouldTransferMoneyBetweenOwnCardsError() {

    val dashboardPage = new DashboardPage();

    int balanceFirstCard = dashboardPage.getFirstCardBalance();
    int balanceSecondCard = dashboardPage.getSecondCardBalance();
    val moneyTransfer = dashboardPage.secondCardButton();
    val cardInfo = DataHelper.getFirstCardNumber();
    String sum = "15000";
    moneyTransfer.transferForm(sum, cardInfo);
    moneyTransfer.getError();

    assertEquals(balanceFirstCard, dashboardPage.getFirstCardBalance());
    assertEquals(balanceSecondCard, dashboardPage.getSecondCardBalance());
  }

}

