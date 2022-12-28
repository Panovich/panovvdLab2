package tech.reliab.course.panovvd.bank.database;

import tech.reliab.course.panovvd.bank.entity.Bank;
import tech.reliab.course.panovvd.bank.entity.BankOffice;
import tech.reliab.course.panovvd.bank.entity.CreditAccount;
import tech.reliab.course.panovvd.bank.entity.PaymentAccount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class CreditAccountRepository {
    private static CreditAccountRepository instance;
    private final Map<Integer, CreditAccount> accountEntities; //dummy

    private CreditAccountRepository() {
        accountEntities = new HashMap<>();
    }

    public static CreditAccountRepository getInstance() {
        if (instance == null) {
            instance = new CreditAccountRepository();
        }
        return instance;
    }

    static int baseID = 0;
    public void writeNew(CreditAccount savingEntity) {
        accountEntities.put(baseID, savingEntity);
        baseID++;
    }

    public void update(CreditAccount editEntity) {
        boolean isInDB = accountEntities.containsKey(editEntity.getId());
        if (!isInDB) {
            throw new NoSuchElementException(String.format("UPDATE: CreditAccount c ID %d не существует в бд!", editEntity.getId()));
        } else {
            accountEntities.put(editEntity.getId(), editEntity);
        }
    }
    public CreditAccount read(int accountID) {
        if (accountEntities.containsKey(accountID)) {
            return accountEntities.get(accountID);
        } else {
            throw new NoSuchElementException(String.format("READ: CreditAccount c ID %d не существует в бд!", accountID));
        }
    }

    public void delete(CreditAccount deletingEntity) {
        accountEntities.remove(deletingEntity.getId());
    }

    public List<CreditAccount> requestAccountsByOwner(int ownerID) {
        return accountEntities.values().stream().filter(x -> x.getOwner().getId() == ownerID).toList();
    }
}
