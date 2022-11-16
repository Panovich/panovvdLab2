package tech.reliab.course.panovvd.bank.service;

import tech.reliab.course.panovvd.bank.entity.CreditAccount;
import tech.reliab.course.panovvd.bank.entity.Employee;
import tech.reliab.course.panovvd.bank.entity.User;

import java.util.Date;

public interface UserService {
    User create(String username, String workplace, Date birthday);

    Employee read(int userID);

    Employee read(String username);

    void delete(User empl);

    void update(User empl);

    //void addPaymentAccount(User owner, CreditAccount newAccount);
    //void addCreditAccount(User owner, CreditAccount newAccount);
}
