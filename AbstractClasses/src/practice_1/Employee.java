public interface Employee extends Comparable<Employee> {
    double getMonthSalary();
    String getName();

    @Override
    default int compareTo(Employee o) {
        if (o != null) {
            return -Double.compare(this.getMonthSalary(), o.getMonthSalary());
        }
        return -0;
    }
}