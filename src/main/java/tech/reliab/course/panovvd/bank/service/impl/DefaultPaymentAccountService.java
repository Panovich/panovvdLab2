package tech.reliab.course.panovvd.bank.service.impl;

import lombok.AllArgsConstructor;
import tech.reliab.course.panovvd.bank.database.BankRepository;
import tech.reliab.course.panovvd.bank.database.PayementAccountRepository;
import tech.reliab.course.panovvd.bank.entity.*;
import tech.reliab.course.panovvd.bank.service.BankService;
import tech.reliab.course.panovvd.bank.service.PaymentAccountService;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class DefaultPaymentAccountService implements PaymentAccountService {
    private PayementAccountRepository paymentAccountRepo;
    private BankService bankService;

    public DefaultPaymentAccountService(BankService baseBankService) {
        bankService = baseBankService;
        paymentAccountRepo = PayementAccountRepository.getInstance();
    }

    /**
     * позволяет завести новый счет для клиента
     *
     * @param owner            владелец счета
     * @param inBank           банк, в котором открывается счет
     */
    @Override
    public PaymentAccount create(User owner, Bank inBank) {

        var newPaymAccount = PaymentAccount
                .builder()
                .money(0)
                .issuedBy(inBank)
                .build();

        newPaymAccount.setOwner(owner);
        paymentAccountRepo.writeNew(newPaymAccount);
        return newPaymAccount;
    }

    @Override
    public PaymentAccount read(int accountID) {
        return paymentAccountRepo.read(accountID);
    }

    @Override
    public void update(PaymentAccount creditAccount) {
        paymentAccountRepo.update(creditAccount);
    }

    @Override
    public void delete(PaymentAccount creditAccount) {
        paymentAccountRepo.delete(creditAccount);
    }

    @Override
    public List<PaymentAccount> requestUserAccounts(User user) {
        return paymentAccountRepo.requestAccountsByOwner(user.getId());
    }

    public List<PaymentAccount> requestUserAccountsByBank(User user, Bank target) {
        var accounts = requestUserAccounts(user);
        return accounts.stream().filter(x -> x.getIssuedBy().getId() == target.getId()).toList();
    }
}
