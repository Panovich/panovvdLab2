package tech.reliab.course.panovvd.bank.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
//@ToString(exclude = {"owner", "location", "serviceMan"})
@Getter @Setter @Builder
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

    public String toString() {
        String result = this.getClass().getSimpleName();
        result = String.format("%s(id=%d, name=%s, addr=%s, status=%s, owner=%s, loc=%s, svcMan=%s, canWithdraw=%b, canDeposit=%b, money=%d, maintCost=%d)",
                result, this.id, this.name, this.address, this.status.toString(), this.owner.getName(), this.location.getName(), this.serviceMan.getName(),
                this.withdrawAvail, this.depositAvail, this.money, this.maintenanceCost);
        return result;
    }
}
