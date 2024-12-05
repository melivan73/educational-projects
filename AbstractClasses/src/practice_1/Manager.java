public class Manager implements Employee {
    public static int MANAGER_PERCENTAGE_UPPER_LIMIT =  140_000;
    public static int MANAGER_PERCENTAGE_LOWER_LIMIT = 115_000;
    public static double MANAGER_PERCENTAGE_VAL = 0.05;
    public static int MANAGER_SALARY_FIXED = 100_000;

    private final String personName;
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final Company company;
    private final double monthSalary;

    public Manager(String name, Company company) {
        this.personName = name;
        this.company = company;
        monthSalary = calculateMonthSalary();
    }

    @Override
    public double getMonthSalary() {
        return monthSalary;
    }

    public double calculateMonthSalary() {
        double randPart  = (int) (Math.random() * MANAGER_PERCENTAGE_UPPER_LIMIT);
        randPart = (randPart >= MANAGER_PERCENTAGE_LOWER_LIMIT)
                ? randPart
                : randPart + (MANAGER_PERCENTAGE_LOWER_LIMIT - randPart);

        return Math.round(MANAGER_SALARY_FIXED + MANAGER_PERCENTAGE_VAL * randPart);
    }

    @Override
    public String getName() {
        return personName;
    }
}
