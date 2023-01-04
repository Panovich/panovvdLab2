package tech.reliab.course.panovvd.bank.service.impl;

import tech.reliab.course.panovvd.bank.database.EmployeeRepository;
import tech.reliab.course.panovvd.bank.entity.*;
import tech.reliab.course.panovvd.bank.exceptions.CreditValidatingException;
import tech.reliab.course.panovvd.bank.service.BankService;
import tech.reliab.course.panovvd.bank.service.EmployeeService;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DefaultEmployeeService implements EmployeeService {
    private EmployeeRepository emplRepo;
    private BankService bankService;

    public DefaultEmployeeService(BankService baseBankService) {
        bankService = baseBankService;
        emplRepo = EmployeeRepository.getInstance();
    }

    // CRUD операции ****************************

    /**
     * создание нового банковского сотрудника. После создания требуется конфигурация
     * @param emplName имя создаваемого сотрудника
     * @param workplace банк, в котором появится новый сотрудник
     */

    static Random random = new Random();
    @Override
    public Employee create(String emplName, Bank workplace, BankOffice workOffice) {
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
        newOne.setSalary(random.nextInt(20001));
        newOne.setCanCredit(random.nextInt(2) == 1);
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

    @Override
    public List<Employee> requestEmployeesByOffice(BankOffice office) {
        return emplRepo.requestEmployeesByOffice(office.getId());
    }

    public void validateCanLoan(Employee employee) {
        if (employee.isRemote()) throw new CreditValidatingException("Сотрудника нет в офисе он работает удалённо");
        if (!employee.isCanCredit()) throw new CreditValidatingException("Сотрудник не наделен полномочиями давать кредиты");
    }
}
