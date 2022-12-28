package tech.reliab.course.panovvd.bank.service.impl;

import lombok.AllArgsConstructor;
import tech.reliab.course.panovvd.bank.database.BankRepository;
import tech.reliab.course.panovvd.bank.entity.*;
import tech.reliab.course.panovvd.bank.service.BankService;

import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class DefaultBankService implements BankService {
    private BankRepository BankRepo;

    public DefaultBankService() {
        BankRepo = BankRepository.getInstance(); //тут типо синглтон с бдшкой будет
    }

    // CRUD операции ****************************

    /**
     * создание нового банка. инициализирует поля money, rating, creditRate случайными значениями, указанными в документации
     * @param bankName имя создаваемого банка
     */
    @Override
    public Bank create(String bankName) {
        Random randomizer = new Random();
        //int money = randomizer.nextInt(1000000);
        int money = 0;
        int rating = randomizer.nextInt(100);
        float baseRate = 4;
        float creditRate = (float)(baseRate + randomizer.nextFloat(20)*(1-rating/100.0));
        Bank newBank = Bank.builder().Name(bankName).money(money).rating(rating).creditRate(creditRate).build();
        //тут какая то абстрактная запись в бд
        BankRepo.writeNew(newBank); //автоматически присвоит ид при добавлении в бд
        return newBank;
    }

    @Override
    public Bank read(int bankID) {
        return null; //TODO implement
    }

    @Override
    public Bank read(String bankName) {
        return null; //TODO implement
    }

    @Override
    public void delete(Bank bank) {
        BankRepo.delete(bank);
    }

    @Override
    public void update(Bank bank) {
        BankRepo.update(bank);
    }

    // бизнес-логика *************************

    private void increaseOfficeCount(Bank target, int diff) {
        target.setOfficeCount(target.getOfficeCount() + diff);
    }

    private void increaseClientCount(Bank target, int diff) {
        target.setClientCount(target.getClientCount() + diff);
    }

    private void increaseATMCount(Bank target, int diff) {
        target.setAtmCount(target.getAtmCount() + diff);
    }

    private void increaseEmployeeCount(Bank target, int diff) {
        target.setEmployeeCount(target.getEmployeeCount() + 1);
    }

    @Override
    public void addOffice(Bank target, BankOffice newOffice) {
        increaseOfficeCount(target, 1);
        target.setMoney(target.getMoney() + newOffice.getMoney());
        newOffice.setOwner(target);
    }

    @Override
    public void addClient(Bank target, User newClient) {
        newClient.getBanksUsing().add(target); //?
        increaseClientCount(target, 1);
    }

    @Override
    public void addATM(Bank target, BankOffice office, BankAtm newATM) {
        newATM.setOwner(target);
        newATM.setAddress(office.getAddress());
        increaseATMCount(target,1);
        target.setMoney(target.getMoney() + newATM.getMoney());
        office.setAtmCount(office.getAtmCount() + 1);
        //office.setMaintenanceCost(office.getMaintenanceCost() + 1);
        //и так далее...
    }

    @Override
    public void addEmployee(Bank target, BankOffice office, Employee newEmployee) {
        increaseEmployeeCount(target, 1);
        newEmployee.setWorkplace(target);
        newEmployee.setWorkOffice(office);
        office.setMaintenanceCost(office.getMaintenanceCost() + newEmployee.getSalary());
    }

    @Override
    public void detachOffice(Bank target, BankOffice newOffice) {
        increaseOfficeCount(target, -1);
        newOffice.setOwner(null);
        //TODO убрать банкоматы?
    }

    @Override
    public void removeClient(Bank target, User Client) {
        increaseClientCount(target, -1);
        Client.getBanksUsing().remove(target);
    }

    @Override
    public void removeATM(Bank target, BankAtm ATM) {
        increaseATMCount(target, -1);
        ATM.setOwner(null);
    }

    @Override
    public void removeEmployee(Bank target, Employee newEmployee) {
        newEmployee.setWorkplace(null);
        newEmployee.setWorkOffice(null);
        newEmployee.setPost("None");
        newEmployee.setSalary(0);
        increaseEmployeeCount(target, -1);
    }
    @Override
    public List<Bank> requestAllBanks() {
        return BankRepo.readAll();
    }
}
