public class BankUserTasks implements Runnable {
    private final Bank bank;
    private UserRole userRole;
    private final String accountNum;
    private final long accountAmount;
    private final String[] contactsAccountNum;
    private volatile boolean isRunning;

    public BankUserTasks(Bank bank, UserRole userRole, String accountNum, long accountAmount,
                         String[] contactsAccountNum) {
        this.bank = bank;
        this.userRole = userRole;
        this.accountNum = accountNum;
        this.accountAmount = accountAmount;
        this.contactsAccountNum = contactsAccountNum;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            if (userRole != null) {
                switch (userRole) {
                    case COMMON:
                        bank.getBalance(accountNum);
                        try {
                            bank.transfer(accountNum, contactsAccountNum[0], accountAmount);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case VIP:
                        bank.getBalance(accountNum);
                        try {
                            bank.transfer(this.accountNum, contactsAccountNum[0], accountAmount);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case BANK:
                        bank.getSumAllAccounts();
                        break;
                }
            }
            this.userRole = null;
        }
    }

    public void Terminate() {
        isRunning = false;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public enum UserRole {
        COMMON, VIP, BANK
    }
}
