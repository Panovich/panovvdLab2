package tech.reliab.course.panovvd.bank.service;

import tech.reliab.course.panovvd.bank.entity.Bank;
import tech.reliab.course.panovvd.bank.entity.PaymentAccount;
import tech.reliab.course.panovvd.bank.entity.User;

public interface PaymentAccountService {
    PaymentAccount create(User owner, Bank inBank);

    PaymentAccount read(int accountID);

    void update(PaymentAccount creditAccount);

    void delete(PaymentAccount creditAccount);
}
