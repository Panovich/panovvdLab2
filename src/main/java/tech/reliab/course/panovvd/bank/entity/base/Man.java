package tech.reliab.course.panovvd.bank.entity.base;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter @Setter @ToString @AllArgsConstructor
public class Man implements Serializable {
    private int id;
    private String name;
    private Date birthday;
}
