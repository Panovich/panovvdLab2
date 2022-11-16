package tech.reliab.course.panovvd.bank.database;

import tech.reliab.course.panovvd.bank.entity.Bank;

public class BankRepository {
    private Bank bankEntity; //dummy
    public void writeNew(Bank savingEntity) {
        bankEntity = savingEntity;
    }
    public void update(Bank editEntity) {
        bankEntity = editEntity;
    }
    public Bank read(int bankID) {
        if (bankEntity.getId() == bankID) {
            return bankEntity;
        } else {
            return null;
        }
    }
    public void delete(Bank deletingEntity) {
        bankEntity = null; //симулируем бурную деятельность
    }
}
