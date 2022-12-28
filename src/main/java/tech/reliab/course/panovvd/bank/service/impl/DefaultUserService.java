package tech.reliab.course.panovvd.bank.service.impl;

import tech.reliab.course.panovvd.bank.database.CreditAccountRepository;
import tech.reliab.course.panovvd.bank.database.PayementAccountRepository;
import tech.reliab.course.panovvd.bank.database.UserRepository;
import tech.reliab.course.panovvd.bank.entity.*;
import tech.reliab.course.panovvd.bank.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DefaultUserService implements UserService {
    private UserRepository userRepo;
    private PayementAccountRepository payementAccountRepo;
    private CreditAccountRepository creditAccountRepo;

    public DefaultUserService() {
        userRepo = UserRepository.getInstance();
        payementAccountRepo = PayementAccountRepository.getInstance();
        creditAccountRepo = CreditAccountRepository.getInstance();
    }

    // CRUD операции ****************************

    /**
     * создание нового клиентского аккаунта
     * @param username имя создаваемого сотрудника
     * @param workplace банк, в котором появится новый сотрудник
     */
    @Override
    public User create(String username, String workplace, Date birthday) {
        Random random = new Random();
        User newOne = User
                .builder()
                .workplace(workplace)
                .build();
        newOne.setName(username);
        newOne.setBirthday(birthday);
        newOne.setSalary(random.nextInt(10000));
        newOne.setCreditRating((newOne.getSalary() < 1000) ? 100 : (int)Math.ceil(newOne.getSalary()/10.0));

        userRepo.writeNew(newOne);
        return newOne;
    }

    @Override
    public Employee read(int userID) {
        return null; //TODO implement
    }

    @Override
    public Employee read(String username) {
        return null; //TODO implement
    }

    @Override
    public void delete(User empl) {
        userRepo.delete(empl);
    }

    @Override
    public void update(User empl) {
        userRepo.update(empl);
    }

    @Override
    public List<User> requestAllUsers() {
        return userRepo.requestAllUsers();
    }
    @Override
    public List<PaymentAccount> requestPaymentAccounts(User user) {
        return payementAccountRepo.requestAccountsByOwner(user.getId());
    }

    @Override
    public List<CreditAccount> requestCreditAccounts(User user) {
        return creditAccountRepo.requestAccountsByOwner(user.getId());
    }
    @Override
    public List<Bank> requestBankUses(User user) {
        //кредитного счета без основного быть не может (наверное), поэтому короче пока по платежным находим
        var Acclist = requestPaymentAccounts(user);
        List<Bank> banksUsing = new ArrayList<>();
        for (var acc: Acclist) {
            if (!banksUsing.contains(acc.getIssuedBy())) {
                banksUsing.add(acc.getIssuedBy());
            }
        }
        return banksUsing;
    }

    @Override
    public List<User> requestUsersByBank(Bank inBank) {
        var userList = requestAllUsers();
        userList = userList.stream().filter(x -> requestBankUses(x).contains(inBank)).toList();
        return userList;
    }
}
