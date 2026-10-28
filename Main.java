import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final BookStore store = new BookStore();
    private static final Admin admin = new Admin("A1", "Store Admin", "admin@bookstore.com");

    public static void main(String[] args) {
        System.out.println("=== Online Book Store Management System ===");
        while (true) {
            System.out.println("\n1. Customer");
            System.out.println("2. Admin");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    customerMenu();
                    break;
                case "2":
                    adminMenu();
                    break;
                case "0":
                    System.out.println("Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void customerMenu() {
        System.out.print("Enter your name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter contact: ");
        String contact = sc.nextLine().trim();
        Customer customer = store.registerCustomer(name, contact);
        System.out.println("Welcome, " + customer);

        while (true) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. View all books");
            System.out.println("2. Search by title / author / category");
            System.out.println("3. Search by price range");
            System.out.println("4. Add book to cart");
            System.out.println("5. Update cart quantity");
            System.out.println("6. Remove book from cart");
            System.out.println("7. View cart");
            System.out.println("8. Place order");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    store.displayBooks();
                    break;
                case "2":
                    System.out.print("Keyword: ");
                    store.displayBooks(store.search(sc.nextLine().trim()));
                    break;
                case "3":
                    try {
                        System.out.print("Min price: ");
                        double min = Double.parseDouble(sc.nextLine().trim());
                        System.out.print("Max price: ");
                        double max = Double.parseDouble(sc.nextLine().trim());
                        List<Book> found = store.search(min, max);
                        store.displayBooks(found);
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter valid numbers.");
                    }
                    break;
                case "4":
                    addToCart(customer);
                    break;
                case "5":
                    updateCart(customer);
                    break;
                case "6":
                    System.out.print("Book ID: ");
                    Book rb = store.findById(sc.nextLine().trim());
                    if (rb == null) System.out.println("Book not found.");
                    else customer.getCart().removeBook(rb);
                    break;
                case "7":
                    customer.getCart().display();
                    break;
                case "8":
                    Order o = store.placeOrder(customer);
                    if (o != null) {
                        System.out.println("Order placed successfully!");
                        System.out.println(o);
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addToCart(Customer customer) {
        try {
            System.out.print("Book ID: ");
            Book b = store.findById(sc.nextLine().trim());
            if (b == null) {
                System.out.println("Book not found.");
                return;
            }
            System.out.print("Quantity: ");
            int qty = Integer.parseInt(sc.nextLine().trim());
            customer.getCart().addBook(b, qty);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private static void updateCart(Customer customer) {
        try {
            System.out.print("Book ID: ");
            Book b = store.findById(sc.nextLine().trim());
            if (b == null) {
                System.out.println("Book not found.");
                return;
            }
            System.out.print("New quantity: ");
            int qty = Integer.parseInt(sc.nextLine().trim());
            customer.getCart().updateQuantity(b, qty);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu (" + admin.getName() + ") ---");
            System.out.println("1. Manage books");
            System.out.println("2. View orders");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    admin.manageBooks(store, sc);
                    break;
                case "2":
                    admin.manageOrders(store);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
