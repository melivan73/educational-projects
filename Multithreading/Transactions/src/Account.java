public class Account {
    private long money;
    private String accNumber;
    private boolean isBlocked;

    public Account(long money, String accNumber, boolean isBlocked) {
        this.money = money;
        this.accNumber = accNumber;
        this.isBlocked = isBlocked;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
