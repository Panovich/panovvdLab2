package tech.reliab.course.panovvd.bank.service;

import tech.reliab.course.panovvd.bank.entity.Bank;
import tech.reliab.course.panovvd.bank.entity.PaymentAccount;
import tech.reliab.course.panovvd.bank.entity.User;

import java.util.List;

public interface PaymentAccountService {
    PaymentAccount create(User owner, Bank inBank);
    PaymentAccount read(int accountID);
    void update(PaymentAccount creditAccount);
    void delete(PaymentAccount creditAccount);

    List<PaymentAccount> requestUserAccounts(User user);
}
