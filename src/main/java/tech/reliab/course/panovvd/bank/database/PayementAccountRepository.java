package tech.reliab.course.panovvd.bank.database;

import tech.reliab.course.panovvd.bank.entity.PaymentAccount;

public class PayementAccountRepository {
    private PaymentAccount accountEntity; //dummy
    public void writeNew(PaymentAccount savingEntity) {
        accountEntity = savingEntity;
    }
    public void update(PaymentAccount editEntity) {
        accountEntity = editEntity;
    }
    public PaymentAccount read(int accountID) {
        if (accountEntity.getId() == accountID) {
            return accountEntity;
        } else {
            return null;
        }
    }
    public void delete(PaymentAccount deletingEntity) {
        accountEntity = null; //симулируем бурную деятельность
    }
}
