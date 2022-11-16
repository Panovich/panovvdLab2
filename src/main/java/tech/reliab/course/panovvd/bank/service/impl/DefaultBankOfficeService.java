package tech.reliab.course.panovvd.bank.service.impl;

import lombok.AllArgsConstructor;
import tech.reliab.course.panovvd.bank.database.OfficeRepository;
import tech.reliab.course.panovvd.bank.entity.Bank;
import tech.reliab.course.panovvd.bank.entity.BankAtm;
import tech.reliab.course.panovvd.bank.entity.BankOffice;
import tech.reliab.course.panovvd.bank.entity.Employee;
import tech.reliab.course.panovvd.bank.service.BankOfficeService;
import tech.reliab.course.panovvd.bank.service.BankService;

@AllArgsConstructor
public class DefaultBankOfficeService implements BankOfficeService {
    OfficeRepository officeRepo;
    BankService bankService;

    public DefaultBankOfficeService(BankService baseBankService) {
        bankService = baseBankService;
        officeRepo = new OfficeRepository();
    }

    // CRUD операции ****************************

    /**
     * создаёт новый банковский офис в банке с указанным именем. После создания офис требуется сконфигурировать
     * @param owner банк, который владеет офисом
     * @param officeName название нового офиса
     */
    @Override
    public BankOffice create(Bank owner, String officeName, String address) {
        BankOffice newOffice = BankOffice.builder().money(0).owner(owner).online(false).loanAvail(false).name(officeName).address(address).build();
        bankService.addOffice(owner, newOffice);
        //тут какая то абстрактная запись в бд
        officeRepo.writeNew(newOffice); //автоматически присвоит ид при добавлении в бд
        return newOffice;
    }

    @Override
    public BankOffice read(int officeID) {
        return officeRepo.read(officeID); //TODO implement
    }

    @Override
    public BankOffice read(Bank owner, String officeName) {
        return null; //TODO implement
    }

    @Override
    public void delete(BankOffice office) {
        officeRepo.delete(office);
    }

    @Override
    public void update(BankOffice office) {
        officeRepo.update(office);
    }

    // бизнес-логика *************************

    @Override
    public void addEmployee(BankOffice office, Employee newEmpl) {
        //возможна ленивая загрузка, когда owner не прогружен
        if (office.getOwner() == null) {
            office = officeRepo.read(office.getId());
            //загрузить из бд банк и добавить туда сотрудника
        }
        office.getOwner().setEmployeeCount(office.getOwner().getEmployeeCount() + 1);
        newEmpl.setWorkOffice(office);
        newEmpl.setWorkplace(office.getOwner());
        //записать сотрудника в бд?
        //newEmpl.add()
    }

    @Override
    public void addATM(BankOffice office, BankAtm newATM) {
        if (office.isAtmSlot()) {
            office.getOwner().setAtmCount(office.getOwner().getAtmCount() + 1);
            office.setAtmCount(office.getAtmCount() + 1);
            newATM.setOwner(office.getOwner());
            newATM.setAddress(office.getAddress());
            newATM.setLocation(office);
        } else {
            throw new IllegalStateException("Нет слота для банкомата");
        }
    }

    @Override
    public void detachATM(BankOffice from, BankAtm removingATM) {
        from.setAtmCount(from.getAtmCount() - 1);
        from.setMaintenanceCost(from.getMaintenanceCost() - removingATM.getMaintenanceCost());
        from.setAtmSlot(true);
        from.setMoney(from.getMoney() - removingATM.getMoney());
    }
}
