package tech.reliab.course.panovvd.bank.database;

import tech.reliab.course.panovvd.bank.entity.PaymentAccount;
import tech.reliab.course.panovvd.bank.entity.User;

import java.util.*;

public class UserRepository {
    private static UserRepository instance;
    private final Map<Integer, User> userEntities; //dummy

    private UserRepository() {
        userEntities = new HashMap<>();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }
    static int baseID = 0;
    public void writeNew(User savingEntity) {
        userEntities.put(baseID, savingEntity);
        savingEntity.setId(baseID);
        baseID++;
    }

    public void update(User editEntity) {
        boolean isInDB = userEntities.containsKey(editEntity.getId());
        if (!isInDB) {
            throw new NoSuchElementException(String.format("UPDATE: User c ID %d не существует в бд!", editEntity.getId()));
        } else {
            userEntities.put(editEntity.getId(), editEntity);
        }
    }
    public User read(int userID) {
        if (userEntities.containsKey(userID)) {
            return userEntities.get(userID);
        } else {
            throw new NoSuchElementException(String.format("READ: User c ID %d не существует в бд!", userID));
        }
    }

    public void delete(User deletingEntity) {
        userEntities.remove(deletingEntity.getId());
    }

    public List<User> requestUsersByBank(int bankID) {
        return new ArrayList<>();
    }

    public List<User> requestAllUsers() {
        return new ArrayList<>(userEntities.values());
    }
}
