package tech.reliab.course.panovvd.bank.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.reliab.course.panovvd.bank.entity.base.Account;
//@ToString(exclude = {"issuedBy"})
@Getter @Setter
public class PaymentAccount extends Account {
    private Bank issuedBy;
    private int money = 0;

    @Builder
    public PaymentAccount(int id, User owner, Bank issuedBy, int money) {
        super(id, owner);
        this.issuedBy = issuedBy;
        this.money = money;
    }

    public String toString() {
        String result = this.getClass().getSimpleName();
        result = String.format("%s(issuedBy=%s, money=%d)",
                result, this.issuedBy.getName(), this.money);
        return result;
    }
}
