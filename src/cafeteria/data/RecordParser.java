package cafeteria.data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cafeteria.data.RecordMethods.isDuplicate;
import static cafeteria.util.util.sendAlert;

@SuppressWarnings("Duplicates") //intellij bullshit inspection
public class RecordParser {

    //no duplicate check and not recommended unless duplicates wanted
    public static void insertRecord(String command, String filepath) {
        try {
            //printWriter(bufferedWriter(fileWriter(file)));
            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(command);
            pw.flush();
            pw.close();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    //has duplicate check, safer option
    public static void insertRecord(String[] fields, String filepath) {
        try {
            int i = 0;
            //printWriter(bufferedWriter(fileWriter(file)));
            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            if (isDuplicate(fields[0], 0, filepath)) {
                sendAlert("Error", "Duplicate entry detected, please check your inputs.");
            } else {
                //a,b,c
                for (String field : fields) {
                    //check directly against length without -1
                    if (++i == fields.length) {
                        pw.write(field + "\n");
                    } else {
                        pw.write(field + ",");
                    }
                }
            }

            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    //Java 8 stream API
    public static List<String> listRecord(String searchTerm, int index, String filepath) {

        List<String> list = new ArrayList<>();

        // reading csv file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(filepath))) {

            //filter results -> map to 1 index or more -> store into list
            list = stream.filter(line -> line.contains(searchTerm)).map(line -> {
                //obtain 1 field only
                String[] str = line.split(",");
                return str[index];
            }).collect(Collectors.toList());

        } catch (IOException io) {
            io.printStackTrace();
        }

        //print result list
        return list;
    }

    public static void deleteRecord(String searchTerm, int index, String filepath) {
        String tempfile = "temp.txt";
        File oldFile = new File(filepath);
        File newFile = new File(tempfile);

        try {
            FileWriter fw = new FileWriter(tempfile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            FileReader fr = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (!values[index].equals(searchTerm)) {
                    pw.println(line);
                }
            }

            br.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File(filepath);
            newFile.renameTo(dump);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public static void editRecord(int index, String searchTerm, int editindex, String newTerm, String filepath) {
        String tempfile = "temp.txt";
        String ID;
        String name;
        String resultRow;
        File oldFile = new File(filepath);
        File newFile = new File(tempfile);

        try {
            FileWriter fw = new FileWriter(tempfile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            FileReader fr = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[index].equals(searchTerm)) {
                    values[editindex] = newTerm;
                    StringJoiner sj = new StringJoiner(",");
                    for (String value : values) {
                        sj.add(value);
                    }
                    pw.println(sj);
                } else {
                    pw.println(line);
                }
            }

            br.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File(filepath);
            newFile.renameTo(dump);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public static void editRecord(int index, String searchTerm, String command, String filepath) {
        String tempfile = "temp.txt";
        File oldFile = new File(filepath);
        File newFile = new File(tempfile);

        try {
            FileWriter fw = new FileWriter(tempfile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            FileReader fr = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[index].equals(searchTerm)) {
                    pw.println(command);
                } else {
                    pw.println(line);
                }
            }

            br.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File(filepath);
            newFile.renameTo(dump);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}

