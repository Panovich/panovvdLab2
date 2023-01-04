package tech.reliab.course.panovvd.bank.exceptions;

public class UserCancelException extends RuntimeException {
    public UserCancelException(String message) {
        super(message);
    }
}
