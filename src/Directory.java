import java.util.*;

public class Directory {
    private String name;
    private Directory parent;
    private Map<String, Directory> directories = new HashMap<>();
    private Map<String, MyFile> files = new HashMap<>();

    public Directory(String name) {
        this.name = name;
    }

    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    public void addDirectory(Directory dir) {
        directories.put(dir.name, dir);
    }

    public void removeDirectory(String name) {
        directories.remove(name);
    }

    public void addFile(MyFile file) {
        files.put(file.getName(), file);
    }

    public void removeFile(String name) {
        files.remove(name);
    }

    public void renameEntry(String oldName, String newName) {
        if (files.containsKey(oldName)) {
            MyFile file = files.remove(oldName);
            file.setName(newName);
            files.put(newName, file);
        } else if (directories.containsKey(oldName)) {
            Directory dir = directories.remove(oldName);
            dir.setName(newName);
            directories.put(newName, dir);
        } else {
            throw new IllegalArgumentException("Entry not found");
        }
    }

    public void copyFile(String source, String target) {
        if (files.containsKey(source)) {
            MyFile file = files.get(source);
            MyFile copy = new MyFile(target, file.getContent());
            files.put(target, copy);
        } else {
            throw new IllegalArgumentException("Source file not found");
        }
    }

    public void listEntries() {
        for (String name : directories.keySet()) {
            System.out.println("[DIR] " + name);
        }
        for (String name : files.keySet()) {
            System.out.println("[FILE] " + name);
        }
    }

    public Directory getSubDirectory(String name) {
        return directories.get(name);
    }

    public Directory getParent() {
        return parent;
    }

    public String getPath() {
        return parent == null ? "/" : parent.getPath() + name + "/";
    }

    public void setName(String newName) {
        this.name = newName;
    }
} 
