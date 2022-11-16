package tech.reliab.course.panovvd.bank.service;

import tech.reliab.course.panovvd.bank.entity.Bank;
import tech.reliab.course.panovvd.bank.entity.BankAtm;
import tech.reliab.course.panovvd.bank.entity.BankOffice;
import tech.reliab.course.panovvd.bank.entity.Employee;

public interface AtmService {
    BankAtm create(String name, Bank bank, BankOffice location);

    BankAtm read(int atmID);

    void delete(BankAtm bankAtm);

    void update(BankAtm bankAtm);

    void setServiceMan(BankAtm target, Employee serviceMan);

    void transaction(BankAtm target, int moneyDiff);
}
