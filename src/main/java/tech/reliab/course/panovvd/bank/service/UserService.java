package tech.reliab.course.panovvd.bank.service;

import tech.reliab.course.panovvd.bank.entity.*;

import java.util.Date;
import java.util.List;

public interface UserService {
    User create(String username, String workplace, Date birthday);

    Employee read(int userID);

    Employee read(String username);

    void delete(User empl);

    void update(User empl);

    List<User> requestAllUsers();

    List<PaymentAccount> requestPaymentAccounts(User user);

    List<CreditAccount> requestCreditAccounts(User user);

    List<Bank> requestBankUses(User user);

    List<User> requestUsersByBank(Bank inBank);

    //void addPaymentAccount(User owner, CreditAccount newAccount);
    //void addCreditAccount(User owner, CreditAccount newAccount);
}
