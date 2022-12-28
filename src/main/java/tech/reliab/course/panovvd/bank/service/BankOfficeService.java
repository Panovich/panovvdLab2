package tech.reliab.course.panovvd.bank.service;

import tech.reliab.course.panovvd.bank.entity.Bank;
import tech.reliab.course.panovvd.bank.entity.BankAtm;
import tech.reliab.course.panovvd.bank.entity.BankOffice;
import tech.reliab.course.panovvd.bank.entity.Employee;

import java.util.List;

public interface BankOfficeService {
    BankOffice create(Bank owner, String officeName, String address);

    BankOffice read(int officeID);

    BankOffice read(Bank owner, String officeName);

    void delete(BankOffice office);

    void update(BankOffice office);

    void addEmployee(BankOffice office, Employee newEmpl);
    void addATM(BankOffice office, BankAtm newATM);

    void detachATM(BankOffice from, BankAtm removingATM);

    List<BankOffice> requestBankOffices(Bank bank);
}
