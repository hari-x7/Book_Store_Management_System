public class Customer extends Person {
    private Cart cart = new Cart();

    public Customer(String customerId, String name, String contact) {
        super(customerId, name, contact);
    }

    public Cart getCart() { return cart; }

    @Override
    public String getRole() {
        return "Customer";
    }
}
