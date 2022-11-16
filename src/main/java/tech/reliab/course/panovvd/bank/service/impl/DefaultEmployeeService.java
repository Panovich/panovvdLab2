package tech.reliab.course.panovvd.bank.service.impl;

import tech.reliab.course.panovvd.bank.database.EmployeeRepository;
import tech.reliab.course.panovvd.bank.entity.Bank;
import tech.reliab.course.panovvd.bank.entity.BankOffice;
import tech.reliab.course.panovvd.bank.entity.Employee;
import tech.reliab.course.panovvd.bank.service.BankService;
import tech.reliab.course.panovvd.bank.service.EmployeeService;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

public class DefaultEmployeeService implements EmployeeService {
    private EmployeeRepository emplRepo;
    private BankService bankService;

    public DefaultEmployeeService(BankService baseBankService) {
        bankService = baseBankService;
        emplRepo = new EmployeeRepository();
    }

    // CRUD операции ****************************

    /**
     * создание нового банковского сотрудника. После создания требуется конфигурация
     * @param emplName имя создаваемого сотрудника
     * @param workplace банк, в котором появится новый сотрудник
     */
    @Override
    public Employee create(String emplName, Bank workplace, BankOffice workOffice) {
        Random random = new Random();
        Employee newOne = Employee
                .builder()
                .post("NOT ASSIGNED.")
                .workplace(workplace)
                .isRemote(false)
                .workOffice(null)
                .canCredit(false)
                .salary(0)
                .build();
        newOne.setName(emplName);
        newOne.setBirthday(Date.from(Instant.now()));
        emplRepo.writeNew(newOne); //это должно задать ID

        bankService.addEmployee(workplace, workOffice, newOne);

        return newOne;
    }

    @Override
    public Employee read(int emplID) {
        return null; //TODO implement
    }

    @Override
    public Employee read(String emplName) {
        return null; //TODO implement
    }

    @Override
    public void delete(Employee empl) {
        emplRepo.delete(empl);
    }

    @Override
    public void update(Employee empl) {
        emplRepo.update(empl);
    }
}
