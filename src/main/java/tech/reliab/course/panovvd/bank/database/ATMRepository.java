package tech.reliab.course.panovvd.bank.database;

import tech.reliab.course.panovvd.bank.entity.Bank;
import tech.reliab.course.panovvd.bank.entity.BankAtm;

public class ATMRepository {
    private BankAtm atmEntity; //dummy
    public void writeNew(BankAtm savingEntity) {
        atmEntity = savingEntity;
    }
    public void update(BankAtm editEntity) {
        atmEntity = editEntity;
    }
    public BankAtm read(int atmID) {
        if (atmEntity.getId() == atmID) {
            return atmEntity;
        } else {
            return null;
        }
    }
    public void delete(BankAtm deletingEntity) {
        atmEntity = null; //симулируем бурную деятельность
    }
}
