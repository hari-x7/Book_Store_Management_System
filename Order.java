import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Order {
    private String orderId;
    private Customer customer;
    private List<String> items = new ArrayList<>();
    private double total;

    public Order(String orderId, Customer customer, Cart cart) {
        this.orderId = orderId;
        this.customer = customer;
        for (Map.Entry<Book, Integer> e : cart.getItems().entrySet()) {
            items.add(e.getKey().getTitle() + " x" + e.getValue());
        }
        this.total = cart.calculateTotal();
    }

    public String getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public List<String> getItems() { return items; }
    public double getTotal() { return total; }

    @Override
    public String toString() {
        return "Order " + orderId + " | Customer: " + customer.getName()
                + " | Items: " + items + String.format(" | Total: Rs.%.2f", total);
    }
}
