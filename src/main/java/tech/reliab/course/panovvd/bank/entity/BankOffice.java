package tech.reliab.course.panovvd.bank.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @Builder @ToString(exclude = {"owner"})
public class BankOffice {
    private int id;
    private String name;
    private String address;
    private Bank owner;
    private boolean online;
    private boolean atmSlot;
    private int atmCount;
    private boolean loanAvail;
    private boolean withdrawAvail;
    private boolean depositAvail;
    private int money;
    private int maintenanceCost;
}
