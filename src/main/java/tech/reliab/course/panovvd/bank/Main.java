package tech.reliab.course.panovvd.bank;

import tech.reliab.course.panovvd.bank.entity.*;
import tech.reliab.course.panovvd.bank.exceptions.AuthException;
import tech.reliab.course.panovvd.bank.exceptions.BankException;
import tech.reliab.course.panovvd.bank.exceptions.UserCancelException;
import tech.reliab.course.panovvd.bank.model.UserAccountsModel;
import tech.reliab.course.panovvd.bank.service.PaymentAccountService;
import tech.reliab.course.panovvd.bank.service.impl.*;

import java.io.IOException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


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
                    office.setWithdrawAvail(true);
                    office.setDepositAvail(true);
                    office.setOnline(true);
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

    public static void createRandomData() {
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
            newUser.setLogin(String.valueOf(i));
            newUser.setPassword(String.valueOf(i));
            //Добавляется 2 счета в разных банках
            for (int j = 0; j < 2; j++) {
                Bank selectedBank;
                do {
                    selectedBank = allBanks.get(random.nextInt(allBanks.size()));
                } while (userService.requestBankUses(newUser).contains(selectedBank));

                //обычный счет
                var paymentAccount = paymentAccountService.create(newUser, selectedBank);
                paymentAccount.setMoney(random.nextInt(20001));
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
    }

    public static void printActions() {
        System.out.println("1) Получение кредита");
        System.out.println("2) Создание платежного счета");
        System.out.println("3) Получение данных о счетах");
        System.out.println("4) Экспорт счетов из банка в txt");
        System.out.println("5) Перенос счетов из txt в другой банк");
        System.out.println("6) Выход");
    }

    public static int userSelectNumber(int max) {
        boolean selected = false;
        System.out.print("Ваш выбор: ");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            var index = scanner.nextInt();
            if (index > max || index < 1) {
                System.out.println("Вы ввели неверный индекс");
            } else {
                return index - 1;
            }
        }
    }

    public static Bank selectBank() {
        var banks = bankService.requestAllBanks();
        int i = 1;
        for (var bank: banks) {
            var usrs = userService.requestUsersByBank(bank);
            bank.setClientCount(usrs.size());
            System.out.printf("%d) %s Кредитный рейтинг: %f Всего денег: %d Кол-во клиентов: %d Рейтинг по оценкам пользователей: %d Офисы: %d\n", i,
                    bank.getName(), bank.getCreditRate(),
                    bank.getMoney(), bank.getClientCount(), bank.getRating(), bank.getOfficeCount());
            i++;
        }
        int bankIndex = userSelectNumber(banks.size());
        return banks.get(bankIndex);
    }

    public static void printUserAccountsByBank(Bank bank, User user) {
        var payementAccounts = paymentAccountService.requestUserAccountsByBank(user, bank);
        if (payementAccounts.isEmpty()) System.out.println("У вас нет здесь аккаунтов");
        else {
            for (var acc: payementAccounts) {
                System.out.print('\t');
                System.out.println(acc);
            }
        }
    }

    private static PaymentAccount selectPaymentAccount(Bank bank, User user) {
        System.out.println("Ваши существующие платежные счета в этом банке:");
        var payementAccounts = paymentAccountService.requestUserAccountsByBank(user, bank);
        if (payementAccounts.isEmpty()) System.out.println("У вас нет здесь аккаунтов");
        else {
            for (var acc: payementAccounts) {
                System.out.print('\t');
                System.out.println(acc);
            }
        }
        int accIndex = userSelectNumber(payementAccounts.size());
        return payementAccounts.get(accIndex);
    }

    public static String toRussian(boolean var) {
        if (var) return "Да";
        else return "Нет";
    }

    public static BankOffice selectOffice(Bank bank) {
        var offices = officeService.requestBankOffices(bank);
        int i = 1;
        for (var office: offices) {
            System.out.printf("%d) %s Работает %s Банкоматов: %d Всего денег: %d Выдача кредитов: %s Выдача наличных: %s \n", i, office.getAddress(),
                    toRussian(office.isOnline()), office.getAtmCount(),
                    office.getMoney(), toRussian(office.isLoanAvail()), toRussian(office.isWithdrawAvail()));
            i++;
        }
        int officeIndex = userSelectNumber(offices.size());
        return offices.get(officeIndex);
    }

    public static Employee selectEmployee(BankOffice office) {
        var employees = emplService.requestEmployeesByOffice(office);
        int i = 1;
        for (var employee: employees) {
            System.out.printf("%d) Имя: %s Выдаёт кредиты: %s Работает удалённо: %s \n", i, employee.getName(), toRussian(employee.isCanCredit()),
                    toRussian(employee.isRemote()));
            i++;
        }
        int emplIndex = userSelectNumber(employees.size());
        return employees.get(emplIndex);
    }

    public static boolean userProcessAnswer() {
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        answer = answer.toLowerCase();
        return answer.equals("да") || answer.equals("y");
    }

    public static void processCreateAccount(User user) {
        try {
            System.out.println("Выберите банк для открытия счета: ");
            Bank bank = selectBank();
            if (!paymentAccountService.requestUserAccountsByBank(user, bank).isEmpty()) {
                System.out.println("Ваши существующие платежные счета в этом банке:");
                printUserAccountsByBank(bank, user);
            }
            System.out.println("Хотите открыть новый счет?");
            boolean agree = userProcessAnswer();
            if (agree) {
                paymentAccountService.create(user, bank);
                System.out.printf("Поздравляем, %s! Вы создали аккаунт\n", user.getName());
            }
        }
        catch (InputMismatchException err) {
            System.out.println("Операция прервана пользователем");
        }
    }

    public static BankAtm selectAvailableWithdrawATM(BankOffice office, int withdrawMoney) {
        var atms = atmService.requestATMs(office);
        for (var atm: atms) {
            if (atm.getMoney() >= withdrawMoney) return atm;
        }
        return null;
    }

    private static void processCredit(User user) {
        try {
            //выбор банка
            System.out.println("Выберите банк для кредитования: ");
            Bank bank = selectBank();
            //валидация кредитоспособности
            bankService.validateСreditworthiness(bank, user);
            //сколько хочет взять
            System.out.print("Введите сумму кредита: ");
            Scanner scanner = new Scanner(System.in);
            int loanMoney = scanner.nextInt();
            bankService.validateWithdrawMoneyAvailable(bank, loanMoney);
            //выбор платежного счета
            PaymentAccount account = null;
            if (!paymentAccountService.requestUserAccountsByBank(user, bank).isEmpty()) {
                account = selectPaymentAccount(bank, user);
            } else {
                System.out.println("У вас нет счета в этом банке. Хотите открыть новый счет?");
                boolean agree = userProcessAnswer();
                if (agree) {
                    account = paymentAccountService.create(user, bank);
                    System.out.printf("Поздравляем, %s! Вы создали счет в банке %s\n", user.getName(), bank.getName());

                } else throw new UserCancelException("Операция прервана пользователем");
            }
            //выбор офиса в котором получаем кредит и сотрудника его выдающего
            BankOffice office = null;
            Employee employee = null;
            while (true) {
                try {
                    office = selectOffice(bank);
                    officeService.validateCanLoan(office);
                    if (office.getMoney() < loanMoney) {
                        System.out.print("В выбранном офисе денег меньше чем вы хотите взять, но оставшаяся сумма может быть выдана в другом офисе. Продолжить? ");
                        if (!userProcessAnswer()) {
                            throw new BankException("Прервано пользователем.");
                        }
                    }
                    System.out.println("Выберите сотрудника для кредитования:");
                    employee = selectEmployee(office);
                    emplService.validateCanLoan(employee);
                    break;
                }
                catch (BankException err) {
                    System.out.print("Офис недоступен для выдачи кредита: ");
                    System.out.println(err.getMessage());
                }
            }
            //тут типо можно побеседовать с сотрудником и тд и тп...
            //создаём кредитный счет
            System.out.print("Время, на которое берется кредит: ");
            int creditTime = scanner.nextInt();
            creditAccountService.create(user, bank, creditTime, loanMoney, loanMoney/creditTime, employee, account);
            System.out.printf("Вы взяли кредит на сумму %dр, месячный платеж составит %dp. В выбранном банке создан кредитный счет\n", loanMoney, loanMoney/creditTime);
            //дальше берем бабки с банкомата или обращаемся к сотруднику
            BankAtm atm = selectAvailableWithdrawATM(office, loanMoney);
            if (atm != null) {
                System.out.printf("Для получения денег подойдите к банкомату %s", atm.getName());
                atmService.withdrawMoney(atm, loanMoney);
            } else {
                System.out.printf("Для получения денег обратитесь к сотруднику %s ", employee.getName());
                int remains = officeService.withdrawMoney(office, loanMoney, true);
                if (remains != 0) {
                    System.out.printf("Оставшуюся сумму (%dр) вы можете получить в одном из офисов:\n", remains);
                    var offices = officeService.requestBankOffices(bank);
                    for (var off: offices) {
                        if (off != office) {
                            System.out.printf("%s Работает %s Банкоматов: %d Всего денег: %d Выдача кредитов: %s Выдача наличных: %s \n", off.getAddress(),
                                    toRussian(off.isOnline()), off.getAtmCount(),
                                    off.getMoney(), toRussian(off.isLoanAvail()), toRussian(off.isWithdrawAvail()));
                        }
                    }
                }
            }
        }
        catch (InputMismatchException err) {
            throw new UserCancelException("Операция прервана пользователем");
        }
    }

    public static void main(String[] args) {
        createRandomData();
        //закинем пару пользователей
        User user1 = userService.create("Клим Саныч", "Завод", new Date());
        user1.setLogin("klimsanich");
        user1.setPassword("123");
        User user2 = userService.create("Джамшут Игоревич", "Завод", new Date());
        user2.setLogin("123jamshut123");
        user2.setPassword("qwerty");

        //вывод всех банков (отладка)
        for (var bank:  bankService.requestAllBanks()) {
            System.out.println(bank);
            printDetails(bank);
        }
        //пользователи (отладка)
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


        Scanner scanner = new Scanner(System.in);
        System.out.println("Вас приветствует система 'БанкМастер' Пожалуйста, войдите, чтобы продолжить");
        //Шаг 1 - вход в систему
        User userAccount = null;
        boolean stepSuccess = false;
        while (!stepSuccess) {
            String login = null;
            String password = null;
            try {
                System.out.print("Ваш логин: ");
                login = scanner.nextLine();
                System.out.print("Пароль: ");
                password = scanner.nextLine();
                //пытаемся войти в систему с введенными данными
                userAccount = userService.authentificateUser(login, password);
                stepSuccess = true;
                System.out.printf("Добро пожаловать, %s\n", userAccount.getName());
                System.out.printf("Согласно нашим данным, ваша зарплата составляет %dр, кредитный рейтинг %d\n", userAccount.getSalary(), userAccount.getCreditRating());
            }
            catch (AuthException err) {
                System.out.print("Попытка входа в систему неудачна: ");
                System.out.println(err.getMessage());
            }
            catch (BankException err) {
                System.out.println("Системная ошибка :(");
            }
        }
        //шаг 2 - выбор слуги
        System.out.println("Для продолжения, выберите услугу:");
        boolean logOut = false;
        while (!logOut) {
            printActions();
            try {
                int selectedAction = scanner.nextInt();
                switch (selectedAction) {
                    case 1 -> {
                        processCredit(userAccount);
                    }
                    case 2 -> {
                        processCreateAccount(userAccount);
                    }
                    case 3 -> {
                        Bank selectedBank = selectBank();
                        printUserAccountsByBank(selectedBank, userAccount);
                    }
                    case 4 -> {
                        Bank selectedBank = selectBank();
                        System.out.print("Имя файла для экспорта аккаунтов: ");
                        String filename = scanner.nextLine();
                        filename = scanner.nextLine();
                        var payAccs = paymentAccountService.requestUserAccountsByBank(userAccount, selectedBank);
                        var credAccs = creditAccountService.requestUserAccountsByBank(userAccount, selectedBank);
                        userService.exportUserAccounts(filename, selectedBank, payAccs, credAccs);
                        System.out.println("Успешно");
                    }
                    case 5 -> {
                        Bank selectedBank = selectBank();
                        System.out.print("Имя файла для переноса аккаунтов: ");
                        scanner.nextLine();
                        String filename = scanner.nextLine();
                        var model = userService.importUserAccounts(filename);
                        for (var acc: model.getPaymentAccounts()) {
                            acc.setIssuedBy(selectedBank);
                            acc.setOwner(userAccount);
                            paymentAccountService.update(acc);
                        }
                        for (var acc: model.getCreditAccount()) {
                            acc.setIssuer(selectedBank);
                            acc.setOwner(userAccount);
                            creditAccountService.update(acc);
                        }
                        System.out.println("Успешно");
                    }

                    case 6 -> {
                        System.out.println("Всего доброго!");
                        logOut = true;
                    }
                    default -> System.out.println("Указанного действия не существует.");
                }
            }
            catch (UserCancelException err) {
                System.out.println(err.getMessage());
            }
            catch (BankException err) {
                System.out.println(err.getMessage());
            }
            catch (InputMismatchException err) {
                System.out.println("Ошибка ввода");
            }
            catch (IOException e) {
                System.out.println("Ошибка создания файла: ");
                System.out.println(e.toString());
            }
            catch (ClassNotFoundException e) {
                System.out.println("Формат файла неверен: ");
                System.out.println(e.toString());
            }
        }
    }
}
