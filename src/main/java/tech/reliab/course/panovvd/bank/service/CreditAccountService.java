package tech.reliab.course.panovvd.bank.service;

import tech.reliab.course.panovvd.bank.entity.*;

import java.time.LocalDate;

public interface CreditAccountService {
    CreditAccount create(User owner, Bank inBank,
                         int creditTime,
                         int creditMoney, int monthPayment,
                         Employee assignedEmployee, PaymentAccount paymentAccount);

    CreditAccount read(int accountID);

    void update(CreditAccount creditAccount);

    void delete(CreditAccount creditAccount);
}
