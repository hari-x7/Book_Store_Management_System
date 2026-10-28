import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookStore {
    private static final String BOOK_FILE = "books.txt";
    private static final String ORDER_FILE = "orders.txt";

    private List<Book> books = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private Map<String, Customer> customers = new HashMap<>();
    private int orderCounter = 1000;

    public BookStore() {
        loadBooks();
    }

    // ---------- Book management ----------
    public void addBook(Book b) {
        books.add(b);
        saveBooks();
    }

    public boolean removeBook(String id) {
        Book b = findById(id);
        if (b == null) return false;
        books.remove(b);
        saveBooks();
        return true;
    }

    public Book findById(String id) {
        for (Book b : books) {
            if (b.getBookId().equalsIgnoreCase(id)) return b;
        }
        return null;
    }

    // Overloaded search methods (Polymorphism - overloading)
    public List<Book> search(String keyword) {
        List<Book> result = new ArrayList<>();
        String k = keyword.toLowerCase();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(k)
                    || b.getAuthor().toLowerCase().contains(k)
                    || b.getCategory().toLowerCase().contains(k)) {
                result.add(b);
            }
        }
        return result;
    }

    public List<Book> search(double minPrice, double maxPrice) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (b.getPrice() >= minPrice && b.getPrice() <= maxPrice) {
                result.add(b);
            }
        }
        return result;
    }

    public void displayBooks() {
        displayBooks(books);
    }

    public void displayBooks(List<Book> list) {
        if (list.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        System.out.printf("%-6s %-28s %-20s %-12s %-11s %s%n",
                "ID", "Title", "Author", "Category", "Price", "Stock");
        for (Book b : list) {
            System.out.println(b);
        }
    }

    // ---------- Customers ----------
    public Customer registerCustomer(String name, String contact) {
        String id = "C" + (customers.size() + 1);
        Customer c = new Customer(id, name, contact);
        customers.put(id, c);
        return c;
    }

    // ---------- Orders ----------
    public Order placeOrder(Customer customer) {
        Cart cart = customer.getCart();
        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Nothing to order.");
            return null;
        }
        // Final stock check before confirming
        for (Map.Entry<Book, Integer> e : cart.getItems().entrySet()) {
            if (!e.getKey().isAvailable(e.getValue())) {
                System.out.println("Not enough stock for " + e.getKey().getTitle() + ". Update your cart.");
                return null;
            }
        }
        Order order = new Order("O" + (++orderCounter), customer, cart);
        // Update stock after the order is placed
        for (Map.Entry<Book, Integer> e : cart.getItems().entrySet()) {
            e.getKey().reduceStock(e.getValue());
        }
        orders.add(order);
        cart.clear();
        saveBooks();
        saveOrder(order);
        return order;
    }

    public List<Order> getOrders() { return orders; }

    // ---------- File I/O ----------
    public void loadBooks() {
        File f = new File(BOOK_FILE);
        if (!f.exists()) {
            loadSampleBooks();
            saveBooks();
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) books.add(Book.fromFileString(line));
            }
        } catch (IOException | RuntimeException e) {
            System.out.println("Could not read " + BOOK_FILE + ", loading sample data.");
            books.clear();
            loadSampleBooks();
        }
    }

    public void saveBooks() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(BOOK_FILE))) {
            for (Book b : books) pw.println(b.toFileString());
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    private void saveOrder(Order o) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ORDER_FILE, true))) {
            pw.println(o);
        } catch (IOException e) {
            System.out.println("Error saving order: " + e.getMessage());
        }
    }

    private void loadSampleBooks() {
        books.add(new Book("B101", "Java Basics", "Herbert Schildt", "Programming", 450, 10));
        books.add(new Book("B102", "OOP in Java", "Balagurusamy", "Programming", 520, 8));
        books.add(new Book("B103", "Data Structures", "Seymour Lipschutz", "Programming", 380, 6));
        books.add(new Book("B104", "Wings of Fire", "APJ Abdul Kalam", "Biography", 250, 15));
        books.add(new Book("B105", "Electrical Machines", "J.B. Gupta", "Engineering", 600, 5));
    }
}
