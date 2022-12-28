package tech.reliab.course.panovvd.bank.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.reliab.course.panovvd.bank.entity.base.Man;

import java.util.Date;
import java.util.List;

@Getter @Setter
public class User extends Man {
    private String workplace;
    private int salary;
    private List<Bank> banksUsing; //TODO: к удалению, выдаёт БД
    private List<CreditAccount> creditAccounts; //TODO: к удалению, выдаёт БД
    private List<PaymentAccount> paymentAccounts; //TODO: к удалению, выдаёт БД
    private int creditRating;

    @Builder
    public User(int id, String name, Date birthday, String workplace, int salary, int creditRating) {
        super(id, name, birthday);
        this.workplace = workplace;
        this.salary = salary;
        this.creditRating = creditRating;
    }

    public String toString() {
        String result = this.getClass().getSimpleName();
        result = String.format("%s(id=%d, name=%s, birthday=%s, workplace=%s, salary=%d, creditRating=%d)",
                result, this.getId(), this.getName(), this.getBirthday().toString(), this.getWorkplace(),
                    this.getSalary(), this.getCreditRating());
        return result;
    }

}