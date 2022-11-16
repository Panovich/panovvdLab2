package tech.reliab.course.panovvd.bank.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.reliab.course.panovvd.bank.entity.base.Account;

@Getter @Setter @ToString(exclude = {"issuedBy"})
public class PaymentAccount extends Account {
    private Bank issuedBy;
    private int money = 0;

    @Builder
    public PaymentAccount(int id, User owner, Bank issuedBy, int money) {
        super(id, owner);
        this.issuedBy = issuedBy;
        this.money = money;
    }
}
