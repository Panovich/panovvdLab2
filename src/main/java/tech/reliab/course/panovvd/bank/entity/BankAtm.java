package tech.reliab.course.panovvd.bank.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString(exclude = {"owner", "location", "serviceMan"}) @Builder
public class BankAtm {
    public enum AtmStatus {ONLINE, OFFLINE, NOMONEY}

    private int id;
    private String name;
    private String address;
    private AtmStatus status;
    private Bank owner;
    private BankOffice location;
    private Employee serviceMan;
    private boolean withdrawAvail;
    private boolean depositAvail;
    private int money;
    private int maintenanceCost;
}
