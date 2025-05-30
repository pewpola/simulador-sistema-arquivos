public class MyFile {
    private String name;
    private String content;

    public MyFile(String name) {
        this.name = name;
        this.content = "";
    }

    public MyFile(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
} 