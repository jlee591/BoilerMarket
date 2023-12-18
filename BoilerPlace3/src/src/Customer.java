import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JScrollPane;

/**
 * Project5 -- Customer
 *
 * This class implements the functionality of the Customer in the terminal.
 * The main method calls each method in this class and provides the functionality
 * of each option main menu of the Customer.
 *
 * @author Chaewon Lee, Peter Kang, Marco Zhang, Iddo Mayblum, Joseph Lee, lab sec LC5
 *
 * @version December 10, 2022
 *
 */

public class Customer {

    private String username;

    JFrame productFrame;
    JPanel productPanel;
    JButton addToShoppingCart;
    JButton viewReviews;
    JLabel productNameLabel;
    JLabel storeNameLabel;
    JLabel descriptionLabel;
    JLabel quantityLabel;
    JLabel priceLabel;
    JLabel sellerLabel;
    JLabel reviewLabel;

    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) size.getWidth();
    int height = (int) size.getHeight();

    /**
     * displays product information when the product is selected
     *
     * @param product Product takes in a Product object.
     */
    public void displayProduct(Product product) {
        productFrame = new JFrame("Product Information");
        productPanel = new JPanel(new GridBagLayout());

        viewReviews = new JButton("View Reviews");
        addToShoppingCart = new JButton("Add to Cart");
        productNameLabel = new JLabel("Product Name: " + product.getName());
        storeNameLabel = new JLabel("Store Name: " + product.getStore());
        descriptionLabel = new JLabel("Description: " + product.getDescription());
        quantityLabel = new JLabel("Quantity: " + product.getQuantity());
        priceLabel = new JLabel("Price: " + String.format("$%.2f", product.getPrice()));
        sellerLabel = new JLabel("Seller Name: " + product.getSeller());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridx = 0;


        productPanel.add(productNameLabel, gridBagConstraints);
        gridBagConstraints.gridy = 1;
        productPanel.add(storeNameLabel, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;

        productPanel.add(descriptionLabel, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;

        productPanel.add(quantityLabel, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;

        productPanel.add(priceLabel, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;

        productPanel.add(sellerLabel, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;

        productFrame.add(productPanel);
        productPanel.add(addToShoppingCart, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;

        productPanel.add(viewReviews, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;

        productFrame.setSize(350, 225);
        productFrame.setLocationRelativeTo(null);
        productFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        productFrame.setVisible(true);
    }
    
    /**
     * displays reviews of a review on a certain product
     *
     * @param reviews ArrayList<Reivews> of reviews of products
     */
    public void displayReviews(ArrayList<Reviews> reviews) {
        productFrame = new JFrame("Display Reviews");
        productPanel = new JPanel();
        productPanel.setSize(width - 100, height);
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));

        if (reviews.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No reviews on this product", "No Reviews", JOptionPane.ERROR_MESSAGE);
        } else {
            for (int i = 0; i < reviews.size(); i++) {
                String label = String.format("Customer Name: %s, Store Name: %s, Product Name: %s, Seller Name: %s, " +
                        "Review: %s", reviews.get(i).getProductCustomerName(), reviews.get(i).getProductStore(),
                        reviews.get(i).getProductName(), reviews.get(i).getProductSeller(),
                        reviews.get(i).getProductReview());
                reviewLabel = new JLabel(label);
                productPanel.add(reviewLabel);
            }
            JScrollPane scrollBar = new JScrollPane(productPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            productFrame.add(scrollBar, BorderLayout.CENTER);
            productFrame.setSize(1000, 600);
            productFrame.setLocationRelativeTo(null);
            productFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            productFrame.setVisible(true);
        }


    }

    public Customer(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * sorts the Price of products in ascending order.
     *
     * @param products ArrayList<Product> of products to write to Products.txt file
     * @return An ArrayList<Product> of the sorted products
     */
    public ArrayList<Product> sortPriceAscending(ArrayList<Product> products) {
        Product[] sortedProducts = new Product[products.size()];
        int temp = products.size();
        for (int i = 0; i < temp; i++) {
            double smallestValue = Double.MAX_VALUE;
            int smallestIndex = Integer.MAX_VALUE;
            for (int j = 0; j < products.size(); j++) {
                if (smallestValue > products.get(j).getPrice()) {
                    smallestValue = products.get(j).getPrice();
                    sortedProducts[i] = products.get(j);
                    smallestIndex = j;
                }
            }
            products.remove(smallestIndex);
        }
        ArrayList<Product> sortedProductsList = new ArrayList<>();
        for (int i = 0; i < sortedProducts.length; i++) {
            sortedProductsList.add(sortedProducts[i]);
        }
        return sortedProductsList;
    }

    /**
     * sorts the products by prices in descending order.
     *
     * @param products ArrayList<Product> of products to write to Products.txt file
     * @return An ArrayList<Product> of the sorted products
     */
    public ArrayList<Product> sortPriceDescending(ArrayList<Product> products) {
        ArrayList<Product> tempProducts = sortPriceAscending(products);
        ArrayList<Product> sortedProducts = new ArrayList<>();
        for (int i = tempProducts.size() - 1; i >= 0; i--) {
            sortedProducts.add(tempProducts.get(i));
        }
        return sortedProducts;
    }

    /**
     * sorts the products by quantity in ascending order.
     *
     * @param products ArrayList<Product> of products to write to Products.txt file
     * @return An ArrayList<Product> of the sorted products
     */
    public ArrayList<Product> sortQuantityAscending(ArrayList<Product> products) {
        Product[] sortedProducts = new Product[products.size()];
        int temp = products.size();
        for (int i = 0; i < temp; i++) {
            double smallestValue = Integer.MAX_VALUE;
            int smallestIndex = Integer.MAX_VALUE;
            for (int j = 0; j < products.size(); j++) {
                if (smallestValue > products.get(j).getQuantity()) {
                    smallestValue = products.get(j).getQuantity();
                    sortedProducts[i] = products.get(j);
                    smallestIndex = j;
                }
            }
            products.remove(smallestIndex);
        }
        ArrayList<Product> sortedProductsList = new ArrayList<>();
        for (int i = 0; i < sortedProducts.length; i++) {
            sortedProductsList.add(sortedProducts[i]);
        }
        return sortedProductsList;

    }

    /**
     * sorts the products by quantity in descending order.
     *
     * @param products ArrayList<Product> of products to write to Products.txt file
     * @return An ArrayList<Product> of the sorted products
     */
    public ArrayList<Product> sortQuantityDescending(ArrayList<Product> products) {
        ArrayList<Product> tempProducts = sortQuantityAscending(products);
        ArrayList<Product> sortedProducts = new ArrayList<>();
        for (int i = tempProducts.size() - 1; i >= 0; i--) {
            sortedProducts.add(tempProducts.get(i));
        }
        return sortedProducts;
    }

}
