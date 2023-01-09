package tech.reliab.course.panovvd.bank.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.reliab.course.panovvd.bank.entity.CreditAccount;
import tech.reliab.course.panovvd.bank.entity.PaymentAccount;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@ToString
public class UserAccountsModel implements Serializable {
    private int bankID;
    private List<CreditAccount> creditAccount;
    private List<PaymentAccount> paymentAccounts;
}
