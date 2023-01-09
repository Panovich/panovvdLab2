package tech.reliab.course.panovvd.bank.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.reliab.course.panovvd.bank.service.BankService;

import java.io.Serializable;

@Getter @Setter @Builder @ToString
public class Bank implements Serializable {
    private int Id;
    private String Name;
    int officeCount = 0;
    int atmCount = 0;
    int employeeCount = 0;
    int clientCount = 0;
    int rating;
    long money;
    double creditRate;
}