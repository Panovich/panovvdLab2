package tech.reliab.course.panovvd.bank.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
//@ToString(exclude = {"owner"})
@Getter @Setter @Builder
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

    public String toString() {
        String result = this.getClass().getSimpleName();
        result = String.format("%s(id=%d, name=%s, addr=%s, owner=%s, online=%b, atmSlot=%b, atmCount=%d, canLoan=%b, canWithdraw=%b, canDeposit=%b, money=%d, maintCost = %d)",
                result, this.id, this.name, this.address, this.owner.getName(), this.online, this.atmSlot,
                this.atmCount, this.loanAvail, this.withdrawAvail, this.depositAvail, this.money, this.maintenanceCost);
        return result;
    }
}
