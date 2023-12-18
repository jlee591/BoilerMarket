/**
 * Project5 -- Product
 *
 * Initializes Product object.
 *
 * @author Chaewon Lee, Peter Kang, Marco Zhang, Iddo Mayblum, Joseph Lee, lab sec LC5
 *
 * @version December 10, 2022
 *
 */
public class Product {
    private String name;
    private String store;
    private String description;
    private int quantity;
    private double price;
    private String seller;

    public Product(String name, String store, String description, int quantity, double price, String seller) {
        this.name = name;
        this.store = store;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.seller = seller;
    }

    /**
     * Returns information of each Product.
     *
     * @return String information of each Product
     */
    @Override
    public String toString() {
        return String.format("Product: %s\nStore: %s\nDescription: %s\nQuantity: %d\nPrice: %.2f\nSeller: %s\n",
                name, store, description, quantity, price, seller);
    }

    /**
     * Returns String value of each Product in file format.
     *
     * @return String information of each Product
     */
    public String printToFile() {
        return String.format("%s,%s,%s,%d,%.2f,%s", name, store, description, quantity, price, seller);
    }

    /**
     * Returns String value of each Product in file format for Sellers.
     *
     * @return String information of each Product without Seller username
     */
    public String exportToFile() {
        return String.format("%s,%s,%s,%d,%.2f", name, store, description, quantity, price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
