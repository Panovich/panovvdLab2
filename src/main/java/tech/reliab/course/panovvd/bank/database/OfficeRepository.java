package tech.reliab.course.panovvd.bank.database;

import tech.reliab.course.panovvd.bank.entity.BankOffice;

public class OfficeRepository {
    private BankOffice officeEntity; //dummy

    public void writeNew(BankOffice savingEntity) {
        officeEntity = savingEntity;
    }
    public void update(BankOffice editEntity) {
        officeEntity = editEntity;
    }
    public BankOffice read(int ID) {
        if (officeEntity.getId() == ID) {
            return officeEntity;
        } else {
            return null;
        }
    }
    public void delete(BankOffice deletingEntity) {
        officeEntity = null; //симулируем бурную деятельность
    }
}
