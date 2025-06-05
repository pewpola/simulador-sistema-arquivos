import java.util.*;

public class FileSystemSimulator {
    private static Directory root;
    private static Directory current;
    private static Journal journal;

    public static void main(String[] args) {
        journal = new Journal("journal.log");
        root = journal.loadState();
        if (root == null) {
            root = new Directory("root");
        }
        current = root;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> journal.saveState(root)));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(current.getPath() + "> ");
            String input = scanner.nextLine().trim();
            String[] parts = input.split("\\s+");
            if (parts.length == 0 || parts[0].isEmpty()) continue;

            String cmd = parts[0];
            try {
                if (cmd.equals("exit")) {
                    journal.saveState(root);
                    break;
                } else if (cmd.equals("mkdir") && parts.length > 1) {
                    Directory newDir = new Directory(parts[1], current);
                    current.addDirectory(newDir);
                    journal.log(input);
                } else if (cmd.equals("rmdir") && parts.length > 1) {
                    current.removeDirectory(parts[1]);
                    journal.log(input);
                } else if (cmd.equals("touch") && parts.length > 1) {
                    current.addFile(new MyFile(parts[1]));
                    journal.log(input);
                } else if (cmd.equals("rm") && parts.length > 1) {
                    current.removeFile(parts[1]);
                    journal.log(input);
                } else if (cmd.equals("rename") && parts.length > 2) {
                    current.renameEntry(parts[1], parts[2]);
                    journal.log(input);
                } else if (cmd.equals("cp") && parts.length >= 3) {
                    String source = parts[1];
                    String target = parts[2];

                    if (target.contains("/")) {
                        String[] targetParts = target.split("/");
                        String dirName = targetParts[0];
                        String newName = targetParts.length > 1 ? targetParts[1] : source;

                        Directory targetDir = current.getSubDirectory(dirName);
                        if (targetDir == null) {
                            System.out.println("Destination directory not found.");
                        } else {
                            current.copyFile(source, newName, targetDir);
                            journal.log("cp " + source + " " + dirName + "/" + newName);
                        }
                    } else if (current.getDirectories().containsKey(target)) {
                        Directory targetDir = current.getSubDirectory(target);
                        current.copyFile(source, source, targetDir);
                        journal.log("cp " + source + " " + target + "/" + source);
                    } else {
                        current.copyFile(source, target, current);
                        journal.log("cp " + source + " " + target);
                    }
                } else if (cmd.equals("cd") && parts.length > 1) {
                    if (parts[1].equals("..")) {
                        if (current.getParent() != null)
                            current = current.getParent();
                    } else {
                        Directory next = current.getSubDirectory(parts[1]);
                        if (next != null) current = next;
                        else System.out.println("Directory not found.");
                    }
                } else if (cmd.equals("ls")) {
                    current.listEntries();
                } else {
                    System.out.println("Comando inválido");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
