package tech.reliab.course.panovvd.bank.service.impl;

import lombok.AllArgsConstructor;
import tech.reliab.course.panovvd.bank.database.OfficeRepository;
import tech.reliab.course.panovvd.bank.entity.*;
import tech.reliab.course.panovvd.bank.exceptions.BankException;
import tech.reliab.course.panovvd.bank.exceptions.CreditValidatingException;
import tech.reliab.course.panovvd.bank.exceptions.OutOfMoneyException;
import tech.reliab.course.panovvd.bank.service.BankOfficeService;
import tech.reliab.course.panovvd.bank.service.BankService;

import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class DefaultBankOfficeService implements BankOfficeService {
    OfficeRepository officeRepo;
    BankService bankService;

    static Random random = new Random(System.currentTimeMillis());

    public DefaultBankOfficeService(BankService baseBankService) {
        bankService = baseBankService;
        officeRepo = OfficeRepository.getInstance();
    }

    // CRUD операции ****************************

    /**
     * создаёт новый банковский офис в банке с указанным именем. Количество денег случайно. После создания офис требуется сконфигурировать
     * @param owner банк, который владеет офисом
     * @param officeName название нового офиса
     */
    @Override
    public BankOffice create(Bank owner, String officeName, String address) {
        BankOffice newOffice = BankOffice.builder().money(0).owner(owner).online(false).loanAvail(false).name(officeName).address(address).build();
        newOffice.setMoney(random.nextInt(300000));
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

    @Override
    public List<BankOffice> requestBankOffices(Bank bank) {
        return officeRepo.requestOfficesByBank(bank.getId());
    }

    public void validateCanLoan(BankOffice office) {
        if (!office.isOnline()) throw new CreditValidatingException("Выбранный офис не работает");
        if (!office.isLoanAvail()) throw new CreditValidatingException("Выбранный офис не выдает кредиты");
    }

    public int withdrawMoney(BankOffice office, int loanMoney, boolean allowTransfer) {
        if (office.getMoney() < loanMoney && !allowTransfer) throw new OutOfMoneyException("В офисе нет столько денег");
        if (office.getMoney() < loanMoney) {
            int remain = loanMoney - office.getMoney();
            office.setMoney(0);
            return remain;
        } else {
            int remain = office.getMoney() - loanMoney;
            office.setMoney(remain);
            return 0;
        }
    }
}
