import java.util.Scanner;

public class Admin extends Person {

    public Admin(String adminId, String name, String contact) {
        super(adminId, name, contact);
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    public void manageBooks(BookStore store, Scanner sc) {
        while (true) {
            System.out.println("\n--- Manage Books ---");
            System.out.println("1. View all books");
            System.out.println("2. Add a book");
            System.out.println("3. Update price / stock");
            System.out.println("4. Remove a book");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    store.displayBooks();
                    break;
                case "2":
                    try {
                        System.out.print("Book ID: ");
                        String id = sc.nextLine().trim();
                        if (store.findById(id) != null) {
                            System.out.println("A book with that ID already exists.");
                            break;
                        }
                        System.out.print("Title: ");
                        String title = sc.nextLine().trim();
                        System.out.print("Author: ");
                        String author = sc.nextLine().trim();
                        System.out.print("Category: ");
                        String cat = sc.nextLine().trim();
                        System.out.print("Price: ");
                        double price = Double.parseDouble(sc.nextLine().trim());
                        System.out.print("Stock: ");
                        int stock = Integer.parseInt(sc.nextLine().trim());
                        store.addBook(new Book(id, title, author, cat, price, stock));
                        System.out.println("Book added.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number entered. Book not added.");
                    }
                    break;
                case "3":
                    System.out.print("Book ID: ");
                    Book b = store.findById(sc.nextLine().trim());
                    if (b == null) {
                        System.out.println("Book not found.");
                        break;
                    }
                    try {
                        System.out.print("New price (current " + b.getPrice() + "): ");
                        b.setPrice(Double.parseDouble(sc.nextLine().trim()));
                        System.out.print("New stock (current " + b.getStock() + "): ");
                        b.setStock(Integer.parseInt(sc.nextLine().trim()));
                        store.saveBooks();
                        System.out.println("Book updated.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number entered.");
                    }
                    break;
                case "4":
                    System.out.print("Book ID to remove: ");
                    if (store.removeBook(sc.nextLine().trim())) {
                        System.out.println("Book removed.");
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public void manageOrders(BookStore store) {
        System.out.println("\n--- All Orders ---");
        if (store.getOrders().isEmpty()) {
            System.out.println("No orders placed yet.");
            return;
        }
        for (Order o : store.getOrders()) {
            System.out.println(o);
        }
    }
}
