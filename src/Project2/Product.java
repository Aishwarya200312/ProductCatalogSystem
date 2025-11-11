package Program2;
public class Product {
    private int id;
    private String name;
    private String category;
    private double price;
    private int stock;
    public Product(int id, String name, String category, double price, int stock) {
        this.id = id;
        this.name = name.trim();
        this.category = category.trim();
        this.price = price;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return String.format("Product[id=%d, name=%s, category=%s, price=%.2f, stock=%d]",
                id, name, category, price, stock);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        return this.id == ((Product) o).id;
    }
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}