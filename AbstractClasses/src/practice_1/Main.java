import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static final int OPERATOR_COUNT = 180;
    public static final int MANAGER_COUNT = 80;
    public static final int TOPMANAGER_COUNT = 10;

    public static final String[] firstNamesMen = { "Иван", "Сергей", "Александр", "Алексей",
            "Дмитрий", "Михаил", "Никита", "Мирослав", "Виктор", "Юрий"};
    public static final String[] firstNamesWomen = { "Екатерина", "Елизавета", "Тамара", "Светлана",
            "Наталия", "Татьяна", "Юлия", "Ульяна", "Алена", "Яна" };
    public static final String[] lastNames = {"Андианов", "Потапов", "Гусев", "Кузнецов", "Петров",
            "Смирнов", "Коровин", "Соколов", "Морозов", "Семенов"};

    public static void main(String[] args) {
        Company company = new Company();
        company.setIncome(12_000_000);
        for (int i = 0; i < OPERATOR_COUNT; i++) {
            Employee employee = new Operator(formName(), company);
            company.hire(employee);
        }

        for (int i = 0; i < MANAGER_COUNT; i++) {
            Employee employee = new Manager(formName(), company);
            company.hire(employee);
        }

        for (int i = 0; i < TOPMANAGER_COUNT; i++) {
            Employee employee = new TopManager(formName(), company);
            company.hire(employee);
        }

        System.out.println("10 самых дорогих сотрудников: ");
        outputSalaryList(company.getTopSalaryStaff(10));
        System.out.println("30 альтруистов: ");
        outputSalaryList(company.getLowestSalaryStaff(30));

        int employeeCountToFire = (OPERATOR_COUNT + MANAGER_COUNT + TOPMANAGER_COUNT) / 2;
        int topManagersPercentage = 100 * TOPMANAGER_COUNT / (OPERATOR_COUNT + MANAGER_COUNT + TOPMANAGER_COUNT);
        int topManagersToFire = topManagersPercentage * employeeCountToFire / 100;
        int managerPercentage = 100 * MANAGER_COUNT / (OPERATOR_COUNT + MANAGER_COUNT + TOPMANAGER_COUNT);
        int managersToFire = managerPercentage * employeeCountToFire / 100;
        int operatorsToFire = employeeCountToFire - topManagersToFire - managersToFire;

        if (!company.fireRandomEmployees(topManagersToFire, TopManager.class)) {
            System.out.println("Ошибка при удалении сотрудника. Тип " + TopManager.class);
        }
        if (!company.fireRandomEmployees(managersToFire, Manager.class)) {
            System.out.println("Ошибка при удалении сотрудника. Тип " + Manager.class);
        }
        if (!company.fireRandomEmployees(operatorsToFire, Operator.class)) {
            System.out.println("Ошибка при удалении сотрудника. Тип " + Operator.class);
        }

        System.out.println("10 самых дорогих сотрудников: ");
        outputSalaryList(company.getTopSalaryStaff(10));
        System.out.println("30 альтруистов: ");
        outputSalaryList(company.getLowestSalaryStaff(30));
    }

    public static String formName() {
        String name;
        List<String> firstMen = Arrays.asList(firstNamesMen);
        Collections.shuffle(firstMen);

        List<String> firstWomen = Arrays.asList(firstNamesWomen);
        Collections.shuffle(firstWomen);

        List<String> last = Arrays.asList(lastNames);
        Collections.shuffle(last);
        name = (Math.random() >= 0.5)
                ? firstMen.getFirst().concat(" ").concat(last.getFirst())
                : firstWomen.getFirst().concat(" ").concat(last.getFirst().concat("а"));
        return name;
    }

    public static void outputSalaryList(List<Employee> employeeList) {
        if (employeeList != null) {
            for (Employee employee : employeeList) {
                System.out.println(employee.getName() + " - " + employee.getMonthSalary());
            }
        }
    }
}
