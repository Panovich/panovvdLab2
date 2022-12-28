package tech.reliab.course.panovvd.bank;

import tech.reliab.course.panovvd.bank.entity.*;
import tech.reliab.course.panovvd.bank.service.impl.*;

import java.util.Date;
import java.util.Random;


public class Main {
    static Random random = new Random(System.currentTimeMillis());
    //создание сервисов для работы
    static DefaultBankService bankService = new DefaultBankService();
    static DefaultBankOfficeService officeService = new DefaultBankOfficeService(bankService);
    static DefaultAtmService atmService = new DefaultAtmService(bankService, officeService);
    static DefaultEmployeeService emplService = new DefaultEmployeeService(bankService);
    static DefaultUserService userService = new DefaultUserService();
    static DefaultCreditAccountService creditAccountService = new DefaultCreditAccountService(bankService);
    static DefaultPaymentAccountService paymentAccountService = new DefaultPaymentAccountService(bankService);
    public static String createRandomName() {
        String[] names = {"Виктор", "Иван", "Анатолий", "Федор", "Ким", "Клим", "Никита", "Петя"};
        String[] surnames = {"Петрович", "Кузьмич", "Сидорович", "Путин", "Стрелков", "Сергеев", "Чен", "Маслов"};
        String name = names[random.nextInt(names.length)];
        String surname = surnames[random.nextInt(surnames.length)];
        return name + " " + surname;
    }

    public static String createRandomAddress() {
        String[] names = {"Центральная", "Молодежная", "Новая", "Садовая", "Гагарина", "Нагорная", "Комсомольская", "Октябрьская"};
        String name = names[random.nextInt(names.length)];
        String number = String.valueOf(random.nextInt(50));
        return name + " " + number;
    }

    public static void addRandomOffices(Bank bank) {
        int iter = random.nextInt(5);
        if (iter < 3) iter = 3;
        for (int _i = 0; _i < iter; _i++) {
            officeService.create(bank, String.format("офис_%d", bank.getOfficeCount()), createRandomAddress());
        }
    }

    public static void addRandomEmployees(BankOffice office) {
        for (int _i = 0; _i < 5; _i++) {
            emplService.create(createRandomName(), office.getOwner(), office);
        }
    }

    public static void addRandomATMs(Bank bank) {
        //здесь мы добавляем банкоматы в некоторые офисы. Банкомат может и не достаться
        for (var office: officeService.requestBankOffices(bank)) {
            int atmCount = random.nextInt(4);
            if (atmCount > 0) {
                office.setAtmSlot(true);
                int unassignedOfficeMoney = office.getMoney();
                // тут сделаем случайного работника который обслуживает терминалы
                Employee technician = emplService.create(createRandomName(), bank, office);
                technician.setPost("Технарь");
                for (int i = 0; i < atmCount; i++) {
                    BankAtm atm = atmService.create(String.format("терминал_%d", bank.getOfficeCount()), bank, office);
                    atm.setMaintenanceCost(technician.getSalary()/atmCount);
                    atmService.setServiceMan(atm, technician);
                    int money = random.nextInt(unassignedOfficeMoney + 1);
                    unassignedOfficeMoney -= money;
                    atm.setMoney(money);
                    atm.setStatus(BankAtm.AtmStatus.ONLINE);
                    atm.setWithdrawAvail(true);
                    atm.setDepositAvail(true);
                }
            } else {
                office.setAtmSlot(false);
            }
        }
    }

    public static void printDetails(Bank bank) {
        System.out.println("Офисы");
        for (var office: officeService.requestBankOffices(bank)) {
            System.out.print('\t');
            System.out.println(office);
            System.out.print('\t');
            System.out.println("Банкоматы");
            for (var atm: atmService.requestATMs(office)) {
                System.out.print('\t');
                System.out.print('\t');
                System.out.println(atm);
            }
            System.out.print('\t');
            System.out.println("Сотрудники");
            for (var empl: emplService.requestEmployeesByOffice(office)) {
                System.out.print('\t');
                System.out.print('\t');
                System.out.println(empl);
            }
        }
        System.out.println("Клиенты");
        for (var user: userService.requestUsersByBank(bank)) {
            System.out.print('\t');
            System.out.println(user);
        }
        System.out.println();
    }

    public static void main(String[] args) {

        //создание сущностей каждого вида

        Bank superBank = bankService.create("Сбернебанк");
        Bank superBank1 = bankService.create("ВитяБ");
        Bank superBank2 = bankService.create("Бета-Банк");
        Bank superBank3 = bankService.create("Закрытие");
        Bank superBank4 = bankService.create("Мегабанк");

        //набиваем банки офисами
        for (var bank: bankService.requestAllBanks()) addRandomOffices(bank);
        //набиваем офисы банков сотрудниками
        for (var bank:  bankService.requestAllBanks()) {
            for (var office: officeService.requestBankOffices(bank)) {
                addRandomEmployees(office);
            }
        }
        //пихаем банкоматы
        for (var bank:  bankService.requestAllBanks()) {
            addRandomATMs(bank);
        }

        var allBanks = bankService.requestAllBanks();
        //создаём 5 клиентов и лепим к ним по 2 счета в рандомных банках
        for (int i = 0; i < 5; i++) {
            User newUser = userService.create(createRandomName(), "Завод", new Date());
            //Добавляется 2 счета в разных банках
            for (int j = 0; j < 2; j++) {
                Bank selectedBank;
                do {
                    selectedBank = allBanks.get(random.nextInt(allBanks.size()));
                } while (userService.requestBankUses(newUser).contains(selectedBank));

                //обычный счет
                var paymentAccount = paymentAccountService.create(newUser, selectedBank);
                //кредитный счет
                int creditMoney = random.nextInt(10000);
                int creditTimeMonths = random.nextInt(13);
                if (creditTimeMonths == 0) creditTimeMonths = 4;
                if (creditMoney == 0) creditMoney = 10000;
                //выбирем случайного сотрудника и назначаем ему полномочия выдавать кредиты
                var offices = officeService.requestBankOffices(selectedBank);
                var selOffice = offices.get(random.nextInt(offices.size()));
                var employees = emplService.requestEmployeesByOffice(selOffice);
                var selEmpl = employees.get(random.nextInt(employees.size()));
                selEmpl.setCanCredit(true);
                creditAccountService.create(newUser, selectedBank, creditTimeMonths, creditMoney/creditTimeMonths, creditMoney,
                        selEmpl, paymentAccount);
            }
        }

        //вывод всех банков
        for (var bank:  bankService.requestAllBanks()) {
            System.out.println(bank);
            printDetails(bank);
        }

        //вывод пользователей со счетами
        System.out.println();
        System.out.println("Пользователи");
        var users = userService.requestAllUsers();
        for (var user: users) {
            System.out.println(user);
            System.out.println("Платежные счета:");
            var payementAccounts = paymentAccountService.requestUserAccounts(user);
            for (var acc: payementAccounts) {
                System.out.print('\t');
                System.out.println(acc);
            }
            System.out.println("Кредитные счета:");
            var creditAccounts = creditAccountService.requestUserAccounts(user);
            for (var acc: creditAccounts) {
                System.out.print('\t');
                System.out.println(acc);
            }
            System.out.println();
        }
    }
}
