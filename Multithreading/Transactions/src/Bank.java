import java.util.*;
import java.util.stream.Collectors;

public class Bank {
    private final Random random = new Random();
    private final Map<String, Account> accounts;
    private List<Account> blockedAccountsList;
    private long totalAmount = 0;

    public Bank(Map<String, Account> accounts) {
        this.accounts = accounts;
        this.blockedAccountsList = accounts.values().stream()
                                           .filter(Account::getIsBlocked)
                                           .collect(Collectors.toCollection(ArrayList::new));
    }

    @SuppressWarnings("unused")
    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * Initial @return void has been changed to @return boolean
     * return false if transfer cant be completed (due to illegal AccountNum @params),
     * otherwise true.
     */
     public synchronized boolean transfer(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {
        String[] arr = new String[2];
        arr[0] = fromAccountNum;
        arr[1] = toAccountNum;
        if (!checkAccounts(arr)) {
            return false;
        }

        Account accountFrom;
        Account accountTo;
        List<Account> list;
        list = accounts.values().stream()
                       .filter(acc -> acc.getAccNumber().equals(fromAccountNum))
                       .collect(Collectors.toCollection(ArrayList::new));
        if (list.size() != 1) {
            return false;
        }
        accountFrom = list.getFirst();

        list.removeFirst();
        list = accounts.values().stream()
                       .filter(acc -> acc.getAccNumber().equals(toAccountNum))
                       .toList();
        if (list.size() != 1) {
            return false;
        }
        accountTo = list.getFirst();

        if (amount > 50_000 && isFraud(fromAccountNum, toAccountNum, amount)) {
            System.out.println("Fraud detected : from " + fromAccountNum + " to " +
                    toAccountNum + ". Amount = " + amount + ". Thread = " + Thread.currentThread());
            accountFrom.setIsBlocked(true);
            accountTo.setIsBlocked(true);
            this.updateBlockedList();
            return false;
        } else {
            accountFrom.setMoney(accountFrom.getMoney() - amount);
            accountTo.setMoney(accountTo.getMoney() + amount);
        }
        return true;
    }

    public synchronized long getBalance(String accountNum) {
        String[] arr = new String[1];
        arr[0] = accountNum;
        if (!checkAccounts(arr)) {
            return -1;
        }
        List<Account> list = accounts.values()
                                     .stream()
                                     .filter(acc -> acc.getAccNumber().equals(accountNum))
                                     .toList();
        if (list.size() == 1) {
            return list.getFirst().getMoney();
        }
        return -1;
    }

    public long getSumAllAccounts() {
        long sum = accounts.values().stream()
                           .mapToLong(Account::getMoney)
                           .sum();
        if (totalAmount != 0 && sum != totalAmount) {
            System.out.println("Error in BankTotalAmount");
            return -1;
        }
        totalAmount = sum;
        return sum;
    }

    /**
     * checking @params account numbers format, duplicates in @params, existence in
     * internal Bank HashMap (assuming the Map contains account numbers approved by the bank)
     * and if existed Account objects not in blocked state.
     * returns false if there is at least one of the accounts has any prohibited design
     */
    private boolean checkAccounts(String[] accountsNum) {
        for (String accToCheck : accountsNum) {
            if (accToCheck.isBlank() || accToCheck.isEmpty()) {
                return false;
            }
        }

        if (Arrays.stream(accountsNum)
                  .distinct()
                  .count() < accountsNum.length) {
            return false;
        }

        return accounts.values().stream().filter(acc -> {
            for (String accNum : accountsNum) {
                if (acc.getAccNumber().equals(accNum) && !acc.getIsBlocked()) {
                    return true;
                }
            }
            return false;
        }).count() == accountsNum.length;
    }

    private synchronized void resetAccountsBlock() {
        accounts.values().forEach(acc -> acc.setIsBlocked(false));
        blockedAccountsList.clear();
    }

    private synchronized int updateBlockedList() {
        blockedAccountsList.clear();
        blockedAccountsList.addAll(accounts.values().stream()
                                           .filter(Account::getIsBlocked)
                                           .toList());
        return blockedAccountsList.size();
    }

    public int getAccountsTotal() {
        return accounts.size();
    }

    public int getBlockedTotal() {
        return blockedAccountsList.size();
    }
}