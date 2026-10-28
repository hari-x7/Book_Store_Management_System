// Abstract base class (Abstraction + Inheritance)
public abstract class Person {
    private String id;
    private String name;
    private String contact;

    public Person(String id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getContact() { return contact; }

    public void setName(String name) { this.name = name; }
    public void setContact(String contact) { this.contact = contact; }

    // Each subclass defines its own role (Polymorphism - overriding)
    public abstract String getRole();

    @Override
    public String toString() {
        return getRole() + " [" + id + "] " + name + " (" + contact + ")";
    }
}
