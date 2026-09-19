public class Patron {

    private String id;
    private String name;

    public Patron(String id, String name) {
        if (id == null || id.trim().equals("")) {
            throw new IllegalArgumentException("Patron ID cannot be empty.");
        }
        if (name == null || name.trim().equals("")) {
            throw new IllegalArgumentException("Patron name cannot be empty.");
        }
        this.id = id.trim();
        this.name = name.trim();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name + " (ID: " + id + ")";
    }
}
