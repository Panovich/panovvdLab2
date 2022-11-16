package tech.reliab.course.panovvd.bank.database;

import tech.reliab.course.panovvd.bank.entity.Bank;
import tech.reliab.course.panovvd.bank.entity.CreditAccount;

public class CreditAccountRepository {
    private CreditAccount accountEntity; //dummy
    public void writeNew(CreditAccount savingEntity) {
        accountEntity = savingEntity;
    }
    public void update(CreditAccount editEntity) {
        accountEntity = editEntity;
    }
    public CreditAccount read(int accountID) {
        if (accountEntity.getId() == accountID) {
            return accountEntity;
        } else {
            return null;
        }
    }
    public void delete(CreditAccount deletingEntity) {
        accountEntity = null; //симулируем бурную деятельность
    }
}
