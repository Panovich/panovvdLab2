package tech.reliab.course.panovvd.bank.service.impl;

import tech.reliab.course.panovvd.bank.database.UserRepository;
import tech.reliab.course.panovvd.bank.entity.Employee;
import tech.reliab.course.panovvd.bank.entity.User;
import tech.reliab.course.panovvd.bank.service.BankService;
import tech.reliab.course.panovvd.bank.service.UserService;

import java.util.Date;
import java.util.Random;

public class DefaultUserService implements UserService {
    private UserRepository userRepo;

    public DefaultUserService() {
        userRepo = new UserRepository();
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
}
