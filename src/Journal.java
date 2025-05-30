import java.io.*;

public class Journal {
    private File logFile;

    public Journal(String fileName) {
        this.logFile = new File(fileName);
    }

    public void log(String entry) {
        try (FileWriter fw = new FileWriter(logFile, true)) {
            fw.write(entry + "\n");
        } catch (IOException e) {
            System.out.println("Failed to write journal entry: " + e.getMessage());
        }
    }
}