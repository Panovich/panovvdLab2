package tech.reliab.course.panovvd.bank.entity;

import lombok.*;
import tech.reliab.course.panovvd.bank.entity.base.Man;

import java.util.Date;

@Getter @Setter @ToString(exclude = {"workplace", "workOffice"})
public class Employee extends Man {
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

}
