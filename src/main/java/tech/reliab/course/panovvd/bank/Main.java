package tech.reliab.course.panovvd.bank;

import tech.reliab.course.panovvd.bank.entity.*;
import tech.reliab.course.panovvd.bank.service.impl.*;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        //создание сервисов для работы
        DefaultBankService bankService = new DefaultBankService();
        DefaultBankOfficeService officeService = new DefaultBankOfficeService(bankService);
        DefaultAtmService atmService = new DefaultAtmService(bankService, officeService);
        DefaultEmployeeService emplService = new DefaultEmployeeService(bankService);
        DefaultUserService userService = new DefaultUserService();
        DefaultCreditAccountService creditAccountService = new DefaultCreditAccountService(bankService);
        DefaultPaymentAccountService paymentAccountService = new DefaultPaymentAccountService(bankService);

        //создание сущностей каждого вида

        Bank superBank = bankService.create("Sberbank");
        BankOffice office = officeService.create(superBank, "Kostykuova", "Kostykuova 14");
        office.setAtmSlot(true);
        BankAtm atm = atmService.create("QIWI_Terminal", superBank, office);
        Employee employee = emplService.create("Kolyan", superBank, office);
        atm.setServiceMan(employee);
        User user = userService.create("Klim Sanich", "OAO KUZMASH", Date.valueOf("1974-10-11"));
        PaymentAccount paymentAccount = paymentAccountService.create(user, superBank);
        CreditAccount creditAccount = creditAccountService.create(user, superBank, 120, 1990, 340, employee,paymentAccount);

        //вывод сущностей

        System.out.println(creditAccount.toString()
                + "; BankName: " + creditAccount.getIssuer().getName()
                + "; EmplName: " + creditAccount.getAssignedEmployee().getName()
                +  "; PayementAccID: " + creditAccount.getPayementAccount().getId());

        System.out.println(paymentAccount.toString() + " BankName: " + paymentAccount.getIssuedBy().getName());

        System.out.println(user.toString());

        System.out.println(employee.toString() + "; Workplace: " + employee.getWorkplace().getName() + "; Workoffice: " + employee.getWorkOffice().getName());

        System.out.println(atm.toString() + "; Owner: " + atm.getOwner().getName()
                + "; Office: " + atm.getLocation().getName()
                + "; ServiceMan: " + atm.getServiceMan().getName());

        System.out.println(office.toString() + "; Owner: " + office.getOwner().getName());

        System.out.print(superBank.toString());

    }
}
