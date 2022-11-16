package tech.reliab.course.panovvd.bank.database;

import tech.reliab.course.panovvd.bank.entity.User;

public class UserRepository {
    private User userContainer; //dummy
    public void writeNew(User savingEntity) {
        userContainer = savingEntity;
    }
    public void update(User editEntity) {userContainer = editEntity;
    }
    public User read(int userID) {
        if (userContainer.getId() == userID) {
            return userContainer;
        } else {
            return null;
        }
    }
    public void delete(User deletingEntity) {
        userContainer = null; //симулируем бурную деятельность
    }
}
