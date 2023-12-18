import javax.swing.*;
import java.awt.*;

/**
 * Project5 -- Seller
 *
 * Initializes Seller object and has methods for each Seller action.
 *
 * @author Chaewon Lee, Peter Kang, Marco Zhang, Iddo Mayblum, Joseph Lee, lab sec LC5
 *
 * @version December 10, 2022
 *
 */

public class Seller {
    private String username;

    JFrame productFrame;
    JPanel productPanel;
    JButton createButton;
    JLabel productLabel;
    JLabel storeLabel;
    JLabel descriptionLabel;
    JLabel quantityLabel;
    JLabel priceLabel;
    JTextField product;
    JTextField store;
    JTextField description;
    JTextField quantity;
    JTextField price;

    /**
     * Creates a "create product" frame to be used in Client
     */
    public void create() {
        productFrame = new JFrame("Create Product");
        productPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridx = 0;

        createButton = new JButton("Create Product");
        productLabel = new JLabel("Enter product name:");
        product = new JTextField(10);
        storeLabel = new JLabel("Enter store name:");
        store = new JTextField(10);
        descriptionLabel = new JLabel("Enter product description:");
        description = new JTextField(10);
        quantityLabel = new JLabel("Enter quantity available:");
        quantity = new JTextField(10);
        priceLabel = new JLabel("Enter price of product:");
        price = new JTextField(10);

        productPanel.add(productLabel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        productPanel.add(product, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        productPanel.add(storeLabel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        productPanel.add(store, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        productPanel.add(descriptionLabel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        productPanel.add(description, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        productPanel.add(quantityLabel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        productPanel.add(quantity, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        productPanel.add(priceLabel, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        productPanel.add(price, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        productPanel.add(createButton, gridBagConstraints);

        productFrame.add(productPanel);
        productFrame.setSize(350, 225);
        productFrame.setLocationRelativeTo(null);
        productFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        productFrame.setVisible(true);
    }

    public Seller(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
