package tech.reliab.course.panovvd.bank.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.reliab.course.panovvd.bank.entity.base.Account;

import java.time.LocalDate;
import java.util.Date;
//@ToString(exclude = {"issuer", "assignedEmployee", "payementAccount"})
@Getter @Setter
public class CreditAccount extends Account {
    private Bank issuer;
    private LocalDate issued;
    private LocalDate expiration;
    private int months;
    private int money;
    private int monthlyPayment;
    private double rate;
    private Employee assignedEmployee;
    private PaymentAccount payementAccount;

    @Builder
    public CreditAccount(int ID, User owner, Bank issuer, LocalDate issued, LocalDate expiration,
                         int months, int money, int monthlyPayment, double rate, Employee assignedEmployee, PaymentAccount payementAccount) {
        super(0, owner);
        this.issuer = issuer;
        this.issued = issued;
        this.expiration = expiration;
        this.months = months;
        this.money = money;
        this.monthlyPayment = monthlyPayment;
        this.rate = rate;
        this.assignedEmployee = assignedEmployee;
        this.payementAccount = payementAccount;
    }

    public String toString() {
        String result = this.getClass().getSimpleName();
        result = String.format("%s(issuer=%s, issued=%s, expiration=%s, months=%d, money=%d, mpayment=%d, rate=%f, assignedEmpl=%s, payAccID=%s)",
        result, this.issuer.getName(), this.issued.toString(), this.expiration.toString(), this.months, this.money, this.monthlyPayment, this.rate,
        this.assignedEmployee.getName(), this.payementAccount.getId());
        return result;
    }
}
