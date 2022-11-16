package tech.reliab.course.panovvd.bank.service.impl;

import lombok.AllArgsConstructor;
import tech.reliab.course.panovvd.bank.database.CreditAccountRepository;
import tech.reliab.course.panovvd.bank.entity.*;
import tech.reliab.course.panovvd.bank.service.BankService;
import tech.reliab.course.panovvd.bank.service.CreditAccountService;

import java.time.LocalDate;
import java.time.Period;

@AllArgsConstructor
public class DefaultCreditAccountService implements CreditAccountService {
    private CreditAccountRepository creditAccountRepo;
    private BankService bankService;

    public DefaultCreditAccountService(BankService baseBankService) {
        creditAccountRepo = new CreditAccountRepository();
        bankService = baseBankService;
    }


    /**
     * позволяет завести новый кредитный счет для клиента
     *
     * @param owner            владелец счета
     * @param inBank           банк, в котором открывается счет
     * @param creditTime       срок, на который дается кредит в днях. Начиная с сегодняшнего дня.
     * @param creditMoney      количество денег на кредитном счете
     * @param monthPayment     минимальная оплата по кредиту в месяц
     * @param assignedEmployee сотрудник банка, который выдавал кредит от имени банка
     * @param paymentAccount   аккаунт в банке, с которого будет производиться оплата кредитного счета
     */
    @Override
    public CreditAccount create(User owner, Bank inBank,
                                int creditTime,
                                int creditMoney, int monthPayment,
                                Employee assignedEmployee, PaymentAccount paymentAccount) {

        LocalDate issuedDate = LocalDate.now();
        LocalDate expirationDate = issuedDate.plusDays(creditTime);
        var newCreditAccount = CreditAccount
                .builder()
                .owner(owner)
                .issuer(inBank)
                .issued(issuedDate)
                .expiration(expirationDate)
                .months(Period.between(issuedDate, expirationDate).getMonths())
                .money(creditMoney)
                .monthlyPayment(monthPayment)
                .rate(inBank.getCreditRate())
                .assignedEmployee(assignedEmployee)
                .payementAccount(paymentAccount)
                .build();

        inBank.setMoney(inBank.getMoney() - creditMoney);
        bankService.update(inBank);
        creditAccountRepo.writeNew(newCreditAccount);
        return newCreditAccount;
    }

    @Override
    public CreditAccount read(int accountID) {
        return creditAccountRepo.read(accountID);
    }

    @Override
    public void update(CreditAccount creditAccount) {
        creditAccountRepo.update(creditAccount);
    }

    @Override
    public void delete(CreditAccount creditAccount) {
        creditAccountRepo.delete(creditAccount);
    }
}
