import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerStorage {
    public final static String REGEX_PHONE = "^(8|\\+7)(\\(?\\d{3})?\\)?\\d{7}$";
    public final static String REGEX_EMAIL = "^[A-Z0-9._%+-]+@[A-Z0-9-]+.+.[A-Z]{2,4}$";
    public final static int ADD_MIN_PARAMS_COUNT = 4;
    public final static int REMOVE_MIN_PARAMS_COUNT = 2;

    private final Map<String, Customer> storage;

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) {
        final int INDEX_NAME = 0;
        final int INDEX_SURNAME = 1;
        final int INDEX_EMAIL = 2;
        final int INDEX_PHONE = 3;

        String[] components = data.split("\\s+");

        if (components.length < ADD_MIN_PARAMS_COUNT) {
            throw new CommandParamsCountException("Too few parameters for add command");
        }
        if (components.length > ADD_MIN_PARAMS_COUNT) {
            throw new CommandParamsCountException("Too many parameters for add command");
        }
        String name = components[INDEX_NAME] + " " + components[INDEX_SURNAME];

        if (!checkEmail(components[INDEX_EMAIL])) {
            throw new WrongEmailFormatException("Non valid email format");
        }

        if (!checkPhone(components[INDEX_PHONE])) {
            throw new WrongPhoneFormatException("Non valid phone format");
        }

        storage.put(name, new Customer(name, components[INDEX_PHONE], components[INDEX_EMAIL]));
    }

    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
        String[] customerName = name.split(" ");
        if (customerName.length < REMOVE_MIN_PARAMS_COUNT) {
            throw new CommandParamsCountException("Too few parameters for remove command");
        }
        storage.remove(name);
    }

    public Customer getCustomer(String name) {
        return storage.get(name);
    }

    public int getCount() {
        return storage.size();
    }

    private boolean checkEmail(String email) {
        Pattern p = Pattern.compile(REGEX_EMAIL, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private boolean checkPhone(String phone) {
        Pattern p = Pattern.compile(REGEX_PHONE);
        Matcher m = p.matcher(phone);
        return m.matches();
    }
}