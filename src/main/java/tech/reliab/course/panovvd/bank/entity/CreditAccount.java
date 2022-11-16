package tech.reliab.course.panovvd.bank.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.reliab.course.panovvd.bank.entity.base.Account;

import java.time.LocalDate;
import java.util.Date;
@Getter @Setter @ToString(exclude = {"issuer", "assignedEmployee", "payementAccount"})
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
}
