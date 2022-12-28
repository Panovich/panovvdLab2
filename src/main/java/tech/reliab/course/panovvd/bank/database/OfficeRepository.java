package tech.reliab.course.panovvd.bank.database;

import tech.reliab.course.panovvd.bank.entity.BankOffice;
import tech.reliab.course.panovvd.bank.entity.CreditAccount;
import tech.reliab.course.panovvd.bank.entity.PaymentAccount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class OfficeRepository {
    private static OfficeRepository instance;
    private final Map<Integer, BankOffice> officeEntities; //dummy
    private OfficeRepository() {
        officeEntities = new HashMap<>();
    }

    public static OfficeRepository getInstance() {
        if (instance == null) {
            instance = new OfficeRepository();
        }
        return instance;
    }
    static int baseID = 0;
    public void writeNew(BankOffice savingEntity) {
        officeEntities.put(baseID, savingEntity);
        savingEntity.setId(baseID);
        baseID++;
    }

    public void update(BankOffice editEntity) {
        boolean isInDB = officeEntities.containsKey(editEntity.getId());
        if (!isInDB) {
            throw new NoSuchElementException(String.format("UPDATE: BankOffice c ID %d не существует в бд!", editEntity.getId()));
        } else {
            officeEntities.put(editEntity.getId(), editEntity);
        }
    }

    public BankOffice read(int officeID) {
        if (officeEntities.containsKey(officeID)) {
            return officeEntities.get(officeID);
        } else {
            throw new NoSuchElementException(String.format("READ: BankOffice c ID %d не существует в бд!", officeID));
        }
    }
    public void delete(BankOffice deletingEntity) {
        officeEntities.remove(deletingEntity.getId());
    }
    public List<BankOffice> requestOfficesByBank(int ownerID) {
        return officeEntities.values().stream().filter(x -> x.getOwner().getId() == ownerID).toList();
    }
}
