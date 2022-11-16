package tech.reliab.course.panovvd.bank.entity.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tech.reliab.course.panovvd.bank.entity.User;

@Getter @Setter @AllArgsConstructor
public class Account {
    private int id;
    private User owner;
}
