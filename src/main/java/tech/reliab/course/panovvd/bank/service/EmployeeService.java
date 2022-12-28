package tech.reliab.course.panovvd.bank.service;

import tech.reliab.course.panovvd.bank.entity.Bank;
import tech.reliab.course.panovvd.bank.entity.BankOffice;
import tech.reliab.course.panovvd.bank.entity.Employee;

import java.util.List;

public interface EmployeeService {

    Employee create(String emplName, Bank workplace, BankOffice workOffice);

    Employee read(int emplID);

    Employee read(String emplName);

    void delete(Employee empl);

    void update(Employee empl);

    List<Employee> requestEmployeesByOffice(BankOffice office);
}
