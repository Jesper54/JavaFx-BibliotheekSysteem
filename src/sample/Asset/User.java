package sample.Asset;

public class User {


    private static String name;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        User.role = role;
    }

    private static String role;

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    private static String email;

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        User.id = id;
    }

    private static String id;

}
