package tech.reliab.course.panovvd.bank.exceptions;

public class OutOfMoneyException extends BankException {
    public OutOfMoneyException(String message) {
        super(message);
    }
}
