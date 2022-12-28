package tech.reliab.course.panovvd.bank.database;

import tech.reliab.course.panovvd.bank.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class EmployeeRepository {
    private static EmployeeRepository instance;
    private final Map<Integer, Employee> employeeEntities; //dummy
    private EmployeeRepository() {
        employeeEntities = new HashMap<>();
    }

    public static EmployeeRepository getInstance() {
        if (instance == null) {
            instance = new EmployeeRepository();
        }
        return instance;
    }
    static int baseID = 0;
    public void writeNew(Employee savingEntity) {
        employeeEntities.put(baseID, savingEntity);
        savingEntity.setId(baseID);
        baseID++;
    }

    public void update(Employee editEntity) {
        boolean isInDB = employeeEntities.containsKey(editEntity.getId());
        if (!isInDB) {
            throw new NoSuchElementException(String.format("UPDATE: Employee c ID %d не существует в бд!", editEntity.getId()));
        } else {
            employeeEntities.put(editEntity.getId(), editEntity);
        }
    }
    public Employee read(int emplID) {
        if (employeeEntities.containsKey(emplID)) {
            return employeeEntities.get(emplID);
        } else {
            throw new NoSuchElementException(String.format("READ: Employee c ID %d не существует в бд!", emplID));
        }
    }
    public void delete(Employee deletingEntity) {
        employeeEntities.remove(deletingEntity.getId());
    }

    public List<Employee> requestEmployeesByOffice(int officeID) {
        return employeeEntities.values().stream().filter(x -> x.getWorkOffice().getId() == officeID).toList();
    }
}
