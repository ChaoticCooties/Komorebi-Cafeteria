package cafeteria.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static cafeteria.util.util.USER_DATA_LOC;

public class RecordMethods extends RecordParser {

    public static Boolean login(String username, String password) {
        Boolean loginSuccess = false;
        try {
            FileReader fr = new FileReader(USER_DATA_LOC);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(username) && values[2].equals(password)) {
                    loginSuccess = true;
                    break;
                } else {
                    loginSuccess = false;
                }
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return loginSuccess;
    }

    public static Boolean isDuplicate(String searchTerm, int index, String filepath) {
        Boolean isDuplicate = false;
        try {
            FileReader fr = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] rows = line.split(",");
                if (rows[index].equals(searchTerm)) {
                    isDuplicate = true;
                    break;
                } else {
                    isDuplicate = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }

        return isDuplicate;
    }

    public static String search(String searchTerm, int index, int searchIndex, String filepath) {
        String result = "";

        try {
            FileReader fr = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[index].equals(searchTerm)) {
                    result = values[searchIndex];
                    break;
                }
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return result;
    }

    public static String search(String searchTerm, int index, String filepath) {
        String resultRow = "";

        try {
            FileReader fr = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[index].equals(searchTerm)) {
                    resultRow = line;
                    break;
                }
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return resultRow;
    }
}
