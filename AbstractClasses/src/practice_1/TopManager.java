public class TopManager implements Employee {
    public final static double TOP_MANAGER_SALARY_PERCENTAGE = 1.5;
    public final static int PERCENTAGE_ADD_INCOME_THRESHOLD = 10_000_000;
    public final static int MANAGER_PERCENTAGE_UPPER_LIMIT =  600_000;
    public final static int MANAGER_PERCENTAGE_LOWER_LIMIT = 400_000;


    private final String personName;
    private final Company company;
    private final double monthSalary;

    public TopManager(String name, Company company) {
        this.personName = name;
        this.company = company;
        monthSalary = calculateMonthSalary();
    }

    @Override
    public double getMonthSalary() {
        return monthSalary;
    }

    public double calculateMonthSalary() {
        double topManagerFixed = (MANAGER_PERCENTAGE_UPPER_LIMIT * Math.random());
        topManagerFixed = (topManagerFixed >= MANAGER_PERCENTAGE_LOWER_LIMIT)
                ? topManagerFixed
                : topManagerFixed + (MANAGER_PERCENTAGE_LOWER_LIMIT - topManagerFixed);

        double topManagerPercentage = (company.getIncome() > PERCENTAGE_ADD_INCOME_THRESHOLD)
                ? TOP_MANAGER_SALARY_PERCENTAGE * topManagerFixed
                : 0.0;
        return Math.round(topManagerFixed + topManagerPercentage);
    }

    @Override
    public String getName() {
        return personName;
    }
}
