public class Operator implements Employee {
    public static int OPERATOR_SALARY_FIXED = 70_000;
    private final String personName;
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final Company company;
    private final double monthSalary;

    public Operator(String name, Company company) {
        this.personName = name;
        this.company = company;
        monthSalary = calculateMonthSalary();
    }

    @Override
    public double getMonthSalary() {
        return monthSalary;
    }

    public double calculateMonthSalary() {
        return OPERATOR_SALARY_FIXED;
    }

    @Override
    public String getName() {
        return personName;
    }
}
