package tech.reliab.course.panovvd.bank.database;

import tech.reliab.course.panovvd.bank.entity.Bank;
import tech.reliab.course.panovvd.bank.entity.Employee;

public class EmployeeRepository {
    private Employee employeeContainer; //dummy
    public void writeNew(Employee savingEntity) {
        employeeContainer = savingEntity;
    }
    public void update(Employee editEntity) {employeeContainer = editEntity;
    }
    public Employee read(int bankID) {
        if (employeeContainer.getId() == bankID) {
            return employeeContainer;
        } else {
            return null;
        }
    }
    public void delete(Employee deletingEntity) {
        employeeContainer = null; //симулируем бурную деятельность
    }
}
