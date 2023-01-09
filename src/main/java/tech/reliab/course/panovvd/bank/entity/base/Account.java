package tech.reliab.course.panovvd.bank.entity.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tech.reliab.course.panovvd.bank.entity.User;

import java.io.Serializable;

@Getter @Setter @AllArgsConstructor
public class Account implements Serializable {
    private int id;
    private User owner;
}
