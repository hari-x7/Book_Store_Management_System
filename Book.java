public class Book {
    private String bookId;
    private String title;
    private String author;
    private String category;
    private double price;
    private int stock;

    public Book(String bookId, String title, String author, String category, double price, int stock) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setCategory(String category) { this.category = category; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }

    public boolean isAvailable(int qty) {
        return stock >= qty;
    }

    public void reduceStock(int qty) {
        if (qty > stock) {
            throw new IllegalArgumentException("Not enough stock for " + title);
        }
        stock -= qty;
    }

    // File storage format: id|title|author|category|price|stock
    public String toFileString() {
        return bookId + "|" + title + "|" + author + "|" + category + "|" + price + "|" + stock;
    }

    public static Book fromFileString(String line) {
        String[] p = line.split("\\|");
        return new Book(p[0], p[1], p[2], p[3], Double.parseDouble(p[4]), Integer.parseInt(p[5]));
    }

    @Override
    public String toString() {
        return String.format("%-6s %-28s %-20s %-12s Rs.%-8.2f Stock: %d",
                bookId, title, author, category, price, stock);
    }
}
