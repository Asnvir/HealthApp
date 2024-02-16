package superapp.utils;

public class Validator {

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isBlank() || str.isEmpty();
    }
}
