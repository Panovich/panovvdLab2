package tech.reliab.course.panovvd.bank.entity;

import lombok.*;
import tech.reliab.course.panovvd.bank.entity.base.Man;

import java.io.Serializable;
import java.util.Date;
//@ToString(exclude = {"workplace", "workOffice"})
@Getter @Setter
public class Employee extends Man implements Serializable {
    private String post;
    private Bank workplace;
    private boolean isRemote;
    private BankOffice workOffice;
    private boolean canCredit;
    private int salary;

    @Builder
    public Employee(String name, Date birthday, String post, Bank workplace, boolean isRemote, BankOffice workOffice, boolean canCredit, int salary) {
        super(0, name, birthday);
        this.post = post;
        this.workplace = workplace;
        this.isRemote = isRemote;
        this.workOffice = workOffice;
        this.canCredit = canCredit;
        this.salary = salary;
    }

    public String toString() {
        String result = this.getClass().getSimpleName();
        result = String.format("%s(name=%s, post=%s, workplace=%s, isRemote=%b, workOffice=%s, canCredit=%b, salary=%d)",
                result, this.getName(), this.post, this.workplace.getName(), this.isRemote, this.workOffice.getName(), this.canCredit, this.salary);
        return result;
    }

}
