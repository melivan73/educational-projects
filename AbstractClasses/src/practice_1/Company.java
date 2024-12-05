import java.util.*;

public class Company {

    private final ArrayList<Employee> employeeList;
    private double income;

    public Company() {
        employeeList = new ArrayList<>();
    }

    public boolean hire(Employee employee) {
        if (employee != null) {
            return employeeList.add(employee);
        }
        return false;
    }

    public boolean hireAll(Collection<Employee> employees) {
        if (employees != null) {
            Employee[] employeesArray = new Employee[employees.size()];
            employees.toArray(employeesArray);
            return Collections.addAll(employeeList, employeesArray);
        }
        return false;
    }

    // как уволить сотрудника по Employee, если снаружи нет доступа
    // к списку сотрудников ??
    /*    public boolean fire(Employee employee) {
        if (employee != null) {
            return employeeList.remove(employee);
        }
        return false;
    }*/

    public boolean fireRandomEmployees(int count, Class<? extends Employee> classImpl) {
        if (count > 0) {
            int employeeLastCount = employeeList.size();
            Collections.shuffle(employeeList);
            Iterator<Employee> iterator = employeeList.iterator();
            Employee employee;
            int removedCount = 0;

            while (iterator.hasNext()) {
                employee = iterator.next();
                if (employee.getClass().equals(classImpl)) {
                    iterator.remove();
                    removedCount++;
                    if (removedCount == count) {
                        break;
                    }
                }
            }
            return (employeeLastCount - employeeList.size() == count);
        }
        return false;
    }

    @SuppressWarnings("SameParameterValue")
    List<Employee> getTopSalaryStaff(int count) {
        Collections.sort(employeeList);
        return new ArrayList<>(employeeList.subList(0, count));
    }

    @SuppressWarnings("SameParameterValue")
    List<Employee> getLowestSalaryStaff(int count) {
        Collections.sort(employeeList);
        return new ArrayList<>(employeeList.subList(employeeList.size() - count - 1, employeeList.size() - 1).reversed());
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}
