import java.util.*;
import java.util.stream.Collectors;

public class App {
    public static final String[] LETTERS_ACCNUM = "A, C, E, H, K, M, Z".split(", ");
    public static final int MAX_PARALLEL_THREAD_COUNT = Runtime.getRuntime().availableProcessors() * 5;
    public static final float FRAUD_PROBABILITY_THRESHOLD = MAX_PARALLEL_THREAD_COUNT * 0.05f;
    public static final int ACCOUNT_APPROX_COUNT = 1000;
    public static final String THREAD_GROUP_NAME = "__bank";
    public static final Map<String, Account> accountsMap = new HashMap<>();
    public static Bank bank;

    public static void main(String[] args) {
        prepareData();

        final List<BankUserTasks> taskList = new ArrayList<>();
        final List<Thread> threadList = new ArrayList<>();
        Thread mainThread = Thread.currentThread();
        prepareTasksAndThreads(taskList, threadList);

        System.out.println("Bank initial state");
        System.out.println("Accounts total = " + bank.getAccountsTotal() +
                ". Sum amount on all accounts = " + bank.getSumAllAccounts() +
                ". Blocked accounts total = " + bank.getBlockedTotal());
        System.out.println("All threads about to run. Threads total = " + threadList.size());
        threadList.forEach(Thread::start);

        int thCount = 0;
        // for debugging
        thCount = showAllThreadsInfo(threadList);

        while (threadList.stream()
                         .filter(thread -> thread.getThreadGroup() != null)
                         .filter(thread -> thread.getThreadGroup().getName().equals(THREAD_GROUP_NAME))
                         .filter(thread -> thread.isAlive())
                         .count() > 0) {

            List<BankUserTasks> userTasks = taskList.stream()
                                          .filter(bankUserTasks -> bankUserTasks.getUserRole() == null)
                                          .filter(BankUserTasks::isRunning)
                                          .collect(Collectors.toCollection( ArrayList::new));
            if (!userTasks.isEmpty()) {
                userTasks.forEach(BankUserTasks::Terminate);
                System.out.println("Terminated " + userTasks.size() + " runnables");
            }
        }

        System.out.println("Bank final state");
        System.out.println("Accounts total = " + bank.getAccountsTotal() +
                ". Sum amount on all accounts = " + bank.getSumAllAccounts() +
                ". Blocked accounts total = " + bank.getBlockedTotal() +
                ". Thread total about to terminate = " + thCount + ".");
    }

    private synchronized static int showAllThreadsInfo(List<Thread> threads) {
        int thCount = 0;
        for (Thread thread : threads) {
            if (thread.getThreadGroup() != null) {
                if (thread.getThreadGroup().getName().equals(THREAD_GROUP_NAME)) {
                    thCount++;
                    System.out.println(thread + ". State : " + thread.getState());
                    for (StackTraceElement traceElem : thread.getStackTrace()) {
                        if (traceElem.getClassLoaderName() != null) {
                            System.out.println("\t" + " " + traceElem.getClassName() + "." + traceElem.getMethodName());
                        }
                    }
                }
            }
        }
        return thCount;
    }

    private static void prepareTasksAndThreads(List<BankUserTasks> taskList, List<Thread> threadList) {
        ThreadGroup threadGroup = new ThreadGroup(THREAD_GROUP_NAME);
        BankUserTasks newTask;
        Thread newThread;
        Account account;
        Account contactAccount;
        for (int i = 0; i < MAX_PARALLEL_THREAD_COUNT; i++) {
            account = accountsMap.get(Integer.toString(i));
            contactAccount = accountsMap.get(Integer.toString(i + MAX_PARALLEL_THREAD_COUNT));
            if (i % 10 == 0) {
                newTask = new BankUserTasks(bank, BankUserTasks.UserRole.BANK,
                        account.getAccNumber(), 0L, null);
            } else if (i % 5 == 0) {
                if ((i * FRAUD_PROBABILITY_THRESHOLD) % i == 0) {
                    account.setMoney(account.getMoney() + 20000);
                }
                newTask = new BankUserTasks(bank, BankUserTasks.UserRole.VIP,
                        account.getAccNumber(), account.getMoney(),
                        new String[]{contactAccount.getAccNumber()});
            } else {
                newTask = new BankUserTasks(bank, BankUserTasks.UserRole.COMMON,
                        account.getAccNumber(), account.getMoney(),
                        new String[]{contactAccount.getAccNumber()});
            }

            newThread = new Thread(threadGroup, newTask);
            taskList.add(newTask);
            threadList.add(newThread);
        }
    }

    private static void prepareData() {
        Account newAccount;
        Random rand = new Random();
        for (int i = 0; i < ACCOUNT_APPROX_COUNT; i++) {
            long accMoneyAmount = rand.nextInt((50000 - 15000) + 1) + 15000;
            String seqNum = Integer.toString(i);
            String accNum = generateAccName(seqNum);
            newAccount = new Account(accMoneyAmount, accNum, false);
            accountsMap.put(seqNum, newAccount);
        }
        bank = new Bank(accountsMap);
    }

    private static String generateAccName(String seqNum) {
        StringBuilder builder = new StringBuilder();
        builder.append("_");
        int iRnd = Math.round((float) Math.random() * 6f);
        builder.append(LETTERS_ACCNUM[iRnd]);
        iRnd = Math.round((float) Math.random() * 6f);
        builder.append(LETTERS_ACCNUM[iRnd]).append("_");
        builder.append(seqNum);
        return builder.toString();
    }
}
