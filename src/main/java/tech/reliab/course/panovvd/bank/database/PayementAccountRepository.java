package tech.reliab.course.panovvd.bank.database;

import tech.reliab.course.panovvd.bank.entity.PaymentAccount;
import tech.reliab.course.panovvd.bank.entity.User;

import java.util.*;

public class PayementAccountRepository {
    private static PayementAccountRepository instance;
    private final Map<Integer, PaymentAccount> accountEntities; //dummy

    private PayementAccountRepository() {
        accountEntities = new HashMap<>();
    }

    public static PayementAccountRepository getInstance() {
        if (instance == null) {
            instance = new PayementAccountRepository();
        }
        return instance;
    }

    static int baseID = 0;
    public void writeNew(PaymentAccount savingEntity) {
        accountEntities.put(baseID, savingEntity);
        savingEntity.setId(baseID);
        baseID++;
    }

    public void update(PaymentAccount editEntity) {
        boolean isInDB = accountEntities.containsKey(editEntity.getId());
        if (!isInDB) {
            throw new NoSuchElementException(String.format("UPDATE: PaymentAccount c ID %d не существует в бд!", editEntity.getId()));
        } else {
            accountEntities.put(editEntity.getId(), editEntity);
        }
    }

    public PaymentAccount read(int accountID) {
        if (accountEntities.containsKey(accountID)) {
            return accountEntities.get(accountID);
        } else {
            throw new NoSuchElementException(String.format("READ: PaymentAccount c ID %d не существует в бд!", accountID));
        }
    }

    public void delete(PaymentAccount deletingEntity) {
        accountEntities.remove(deletingEntity.getId());
    }

    public List<PaymentAccount> requestAccountsByOwner(int ownerID) {
        return accountEntities.values().stream().filter(x -> x.getOwner().getId() == ownerID).toList();
    }
}
