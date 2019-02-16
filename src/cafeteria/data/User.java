package cafeteria.data;

import static cafeteria.data.RecordParser.insertRecord;
import static cafeteria.util.util.USER_DATA_LOC;
import static cafeteria.util.util.currentTimeString;

public class User {
    static String username;
    static String name;
    static String password;
    static String role;

    public User(String username, String password) {
        User.username = username;
        User.password = password;
    }

    public User(String username, String name, String password, String role) {
        User.username = username;
        User.name = name;
        User.password = password;
        User.role = role;
    }

    public User(String username, String name, String password) {
        User.username = username;
        User.name = name;
        User.password = password;
    }

    public static String getUsername() {
        return username;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String newName) {
        newName = name;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String newPassword) {
        newPassword = password;
    }

    public static String getRole() {
        return role;
    }

    public void register() {
        String[] insertData = new String[]{username, name, password, role, currentTimeString};
        insertRecord(insertData, USER_DATA_LOC);
    }
}
