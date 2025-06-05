import java.io.*;

class Journal {
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

    public void saveState(Directory root) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("filesystem.dat"))) {
            oos.writeObject(root);
        } catch (IOException e) {
            System.out.println("Failed to save filesystem state: " + e.getMessage());
        }
    }

    public Directory loadState() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("filesystem.dat"))) {
            return (Directory) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load filesystem state, starting fresh.");
            return new Directory("root");
        }
    }
}