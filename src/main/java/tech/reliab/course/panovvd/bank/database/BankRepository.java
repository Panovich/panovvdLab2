package tech.reliab.course.panovvd.bank.database;

import tech.reliab.course.panovvd.bank.entity.Bank;
import tech.reliab.course.panovvd.bank.entity.CreditAccount;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class BankRepository {
    private static BankRepository instance;
    private final Map<Integer, Bank> bankEntities; //dummy

    private BankRepository() {
        bankEntities = new HashMap<>();
    }

    public static BankRepository getInstance() {
        if (instance == null) {
            instance = new BankRepository();
        }
        return instance;
    }

    static int baseID = 0;
    public void writeNew(Bank savingEntity) {
        bankEntities.put(baseID, savingEntity);
        savingEntity.setId(baseID);
        baseID++;
    }

    public void update(Bank editEntity) {
        boolean isInDB = bankEntities.containsKey(editEntity.getId());
        if (!isInDB) {
            throw new NoSuchElementException(String.format("UPDATE: Bank c ID %d не существует в бд!", editEntity.getId()));
        } else {
            bankEntities.put(editEntity.getId(), editEntity);
        }
    }
    public Bank read(int bankID) {
        if (bankEntities.containsKey(bankID)) {
            return bankEntities.get(bankID);
        } else {
            throw new NoSuchElementException(String.format("READ: Bank c ID %d не существует в бд!", bankID));
        }
    }
    public void delete(Bank deletingEntity) {
        bankEntities.remove(deletingEntity.getId());
    }

    public List<Bank> readAll() {
        return new ArrayList<>(bankEntities.values());
    }
}
