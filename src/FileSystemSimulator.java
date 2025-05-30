import java.util.*;

public class FileSystemSimulator {
    private Directory root;
    private Journal journal;
    private Directory currentDir;

    public FileSystemSimulator() {
        this.root = new Directory("/");
        this.currentDir = root;
        this.journal = new Journal("journal.log");
    }

    public void startShell() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(currentDir.getPath() + "> ");
            String input = scanner.nextLine().trim();
            if (input.equals("exit")) break;
            handleCommand(input);
        }
        scanner.close();
    }

    private void handleCommand(String input) {
        String[] parts = input.split(" ");
        String command = parts[0];
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);

        try {
            switch (command) {
                case "mkdir": createDirectory(args[0]); break;
                case "rmdir": removeDirectory(args[0]); break;
                case "rename": rename(args[0], args[1]); break;
                case "touch": createFile(args[0]); break;
                case "rm": removeFile(args[0]); break;
                case "cp": copyFile(args[0], args[1]); break;
                case "ls": listFiles(); break;
                case "cd": changeDirectory(args[0]); break;
                default: System.out.println("Unknown command.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void createDirectory(String name) {
        journal.log("mkdir " + name);
        currentDir.addDirectory(new Directory(name, currentDir));
    }

    private void removeDirectory(String name) {
        journal.log("rmdir " + name);
        currentDir.removeDirectory(name);
    }

    private void rename(String oldName, String newName) {
        journal.log("rename " + oldName + " " + newName);
        currentDir.renameEntry(oldName, newName);
    }

    private void createFile(String name) {
        journal.log("touch " + name);
        currentDir.addFile(new MyFile(name));
    }

    private void removeFile(String name) {
        journal.log("rm " + name);
        currentDir.removeFile(name);
    }

    private void copyFile(String source, String target) {
        journal.log("cp " + source + " " + target);
        currentDir.copyFile(source, target);
    }

    private void listFiles() {
        currentDir.listEntries();
    }

    private void changeDirectory(String path) {
        if (path.equals("..")) {
            if (currentDir.getParent() != null)
                currentDir = currentDir.getParent();
        } else {
            Directory next = currentDir.getSubDirectory(path);
            if (next != null)
                currentDir = next;
            else
                System.out.println("Directory not found.");
        }
    }

    public static void main(String[] args) {
        FileSystemSimulator sim = new FileSystemSimulator();
        sim.startShell();
    }
}
