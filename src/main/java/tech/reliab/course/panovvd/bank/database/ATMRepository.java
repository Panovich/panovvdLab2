package tech.reliab.course.panovvd.bank.database;

import tech.reliab.course.panovvd.bank.entity.Bank;
import tech.reliab.course.panovvd.bank.entity.BankAtm;
import tech.reliab.course.panovvd.bank.entity.BankOffice;
import tech.reliab.course.panovvd.bank.entity.CreditAccount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class ATMRepository {
    private static ATMRepository instance;
    private final Map<Integer, BankAtm> atmEntities; //dummy

    private ATMRepository() {
        atmEntities = new HashMap<>();
    }

    public static ATMRepository getInstance() {
        if (instance == null) {
            instance = new ATMRepository();
        }
        return instance;
    }

    static int baseID = 0;
    public void writeNew(BankAtm savingEntity) {
        atmEntities.put(baseID, savingEntity);
        savingEntity.setId(baseID);
        baseID++;
    }

    public void update(BankAtm editEntity) {
        boolean isInDB = atmEntities.containsKey(editEntity.getId());
        if (!isInDB) {
            throw new NoSuchElementException(String.format("UPDATE: BankAtm c ID %d не существует в бд!", editEntity.getId()));
        } else {
            atmEntities.put(editEntity.getId(), editEntity);
        }
    }
    public BankAtm read(int atmID) {
        if (atmEntities.containsKey(atmID)) {
            return atmEntities.get(atmID);
        } else {
            throw new NoSuchElementException(String.format("READ: BankAtm c ID %d не существует в бд!", atmID));
        }
    }
    public void delete(BankAtm deletingEntity) {
        atmEntities.remove(deletingEntity.getId());
    }

    public List<BankAtm> requestATMsByOffice(int officeID) {
        return atmEntities.values().stream().filter(x -> x.getLocation().getId() == officeID).toList();
    }
}
