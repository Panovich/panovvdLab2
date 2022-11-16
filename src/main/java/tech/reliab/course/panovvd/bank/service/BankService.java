package tech.reliab.course.panovvd.bank.service;

import tech.reliab.course.panovvd.bank.entity.*;

public interface BankService {

    //круды
    Bank create(String bankName);
    Bank read(int bankID);
    Bank read(String bankName);
    void delete(Bank bank);
    void update(Bank bank);

    //логика
    void addOffice(Bank target, BankOffice newOffice);
    void addClient(Bank target, User newClient);
    void addATM(Bank target, BankOffice office, BankAtm newATM);
    void addEmployee(Bank target, BankOffice office, Employee newEmployee);

    void detachOffice(Bank target, BankOffice newOffice);
    void removeClient(Bank target, User newClient);

    void removeATM(Bank target, BankAtm ATM);

    void removeEmployee(Bank target, Employee newEmployee);
 }
