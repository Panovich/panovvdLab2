package tech.reliab.course.panovvd.bank.service.impl;

import lombok.AllArgsConstructor;
import tech.reliab.course.panovvd.bank.database.ATMRepository;
import tech.reliab.course.panovvd.bank.entity.*;
import tech.reliab.course.panovvd.bank.exceptions.OutOfMoneyException;
import tech.reliab.course.panovvd.bank.service.AtmService;
import tech.reliab.course.panovvd.bank.service.BankOfficeService;
import tech.reliab.course.panovvd.bank.service.BankService;

import java.util.List;

@AllArgsConstructor
public class DefaultAtmService implements AtmService {
    private ATMRepository atmRepo;
    private BankService bankService;
    private BankOfficeService officeService;

    public DefaultAtmService(BankService baseBankService, BankOfficeService baseOfficeService) {
        bankService = baseBankService;
        officeService = baseOfficeService;
        atmRepo = ATMRepository.getInstance(); //подгрузка бд
    }

    //CRUD операции **********************************

    /**
     * создаёт новый банкомат в офисе с указанным именем. После создания банкомат требуется сконфигурировать
     * @param name имя (идентификатор) банкомата
     * @param bank владелец банкомата
     * @param location офис, в котором расположен банк
     */
    @Override
    public BankAtm create(String name, Bank bank, BankOffice location) {
        if (!location.isAtmSlot()) {
            throw new IllegalStateException("Офис: " + location.toString() + "не имеет места для добавления банкомата!");
        }

        BankAtm bankAtm = BankAtm
                .builder()
                .name(name)
                .location(location)
                .status(BankAtm.AtmStatus.OFFLINE)
                .owner(bank)
                .withdrawAvail(false)
                .depositAvail(false)
                .money(0)
                .maintenanceCost(0)
                .build();

        atmRepo.writeNew(bankAtm);
        bankService.addATM(bank, location, bankAtm);
        return bankAtm;
    }

    @Override
    public BankAtm read(int atmID) {
        return atmRepo.read(atmID);
    }

    @Override
    public void delete(BankAtm bankAtm) {
        bankService.removeATM(bankAtm.getOwner(), bankAtm);
        officeService.detachATM(bankAtm.getLocation(), bankAtm);
        atmRepo.delete(bankAtm);
    }

    @Override
    public void update(BankAtm bankAtm) {
        atmRepo.update(bankAtm);
    }

    //Немного бизнес-логики *****************************************

    @Override
    public void setServiceMan(BankAtm target, Employee serviceMan) {
        target.setServiceMan(serviceMan);
    }

    @Override
    public void transaction(BankAtm target, int moneyDiff) {
        target.setMoney(target.getMoney() + moneyDiff);
        target.getOwner().setMoney(target.getOwner().getMoney() + moneyDiff);
        target.getLocation().setMoney(target.getLocation().getMoney() + moneyDiff);
    }

    @Override
    public List<BankAtm> requestATMs(BankOffice office) {
        return atmRepo.requestATMsByOffice(office.getId());
    }

    public void withdrawMoney(BankAtm atm, int loanMoney) {
        if (atm.getMoney() < loanMoney) throw new OutOfMoneyException("В банкомате нет столько денег!");
    }
}
