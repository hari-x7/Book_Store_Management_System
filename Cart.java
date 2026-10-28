import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private Map<Book, Integer> items = new LinkedHashMap<>();

    public void addBook(Book book, int qty) {
        if (qty <= 0) {
            System.out.println("Quantity must be at least 1.");
            return;
        }
        int newQty = items.getOrDefault(book, 0) + qty;
        if (!book.isAvailable(newQty)) {
            System.out.println("Only " + book.getStock() + " copies of '" + book.getTitle() + "' in stock.");
            return;
        }
        items.put(book, newQty);
        System.out.println("Added to cart: " + book.getTitle() + " x" + qty);
    }

    public void removeBook(Book book) {
        if (items.remove(book) != null) {
            System.out.println("Removed from cart: " + book.getTitle());
        } else {
            System.out.println("That book is not in your cart.");
        }
    }

    public void updateQuantity(Book book, int qty) {
        if (!items.containsKey(book)) {
            System.out.println("That book is not in your cart.");
            return;
        }
        if (qty <= 0) {
            removeBook(book);
        } else if (!book.isAvailable(qty)) {
            System.out.println("Only " + book.getStock() + " copies in stock.");
        } else {
            items.put(book, qty);
            System.out.println("Quantity updated.");
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (Map.Entry<Book, Integer> e : items.entrySet()) {
            total += e.getKey().getPrice() * e.getValue();
        }
        return total;
    }

    public Map<Book, Integer> getItems() { return items; }

    public boolean isEmpty() { return items.isEmpty(); }

    public void clear() { items.clear(); }

    public void display() {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        System.out.println("--- Shopping Cart ---");
        for (Map.Entry<Book, Integer> e : items.entrySet()) {
            Book b = e.getKey();
            int q = e.getValue();
            System.out.printf("%-6s %-28s Rs.%-8.2f x %d = Rs.%.2f%n",
                    b.getBookId(), b.getTitle(), b.getPrice(), q, b.getPrice() * q);
        }
        System.out.printf("Total: Rs.%.2f%n", calculateTotal());
    }
}
