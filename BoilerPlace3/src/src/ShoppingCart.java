import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Project5 -- ShoppingCart
 *
 * This class handles all interactions with the shoppingcart.txt file.
 *
 * @author Marco Zhang, Peter Kang, Chaewon Lee, Iddo Mayblum, Joseph Lee, lab sec LC5
 *
 * @version December 10, 2022
 *
 */

public class ShoppingCart {

    JFrame cartFrame;
    Container content;
    JPanel productListPanel;
    JPanel operationPanel;
    ArrayList<JPanel> productPanels;
    ArrayList<JLabel> productLabelPanels;
    ArrayList<JButton> removeButtons;
    JButton checkoutButton;
    JButton backButton;
    JPanel topPanel;
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) size.getWidth();
    int height = (int) size.getHeight();

    /**
     * displays the JFrame and JPanels for shopping cart with products
     *
     * @param products
     */
    public void displayCart(ArrayList<Product> products) {
        cartFrame = new JFrame("Shopping Cart");
        content = cartFrame.getContentPane();
        content.setLayout(new BorderLayout());

        productListPanel = new JPanel();
        productListPanel.setSize(width - 100, height - 100);
        productListPanel.setLayout(new BoxLayout(productListPanel, BoxLayout.Y_AXIS));
        operationPanel = new JPanel();

        productPanels = new ArrayList<>();
        productLabelPanels = new ArrayList<>();
        removeButtons = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            String label = String.format("Product: %s, Store: %s, Quantity in Cart: %d, Price for each: %.2f",
                    products.get(i).getName(), products.get(i).getStore(), products.get(i).getQuantity(),
                    products.get(i).getPrice());
            JLabel productLabel = new JLabel(label);
            JButton remove = new JButton("Remove");
            JPanel product = new JPanel();
            product.setLayout(new BorderLayout());
            Dimension preferredSize = new Dimension(width - 100, 50);
            product.setMinimumSize(preferredSize);
            product.setPreferredSize(preferredSize);
            product.setMaximumSize(preferredSize);
            product.add(productLabel, BorderLayout.WEST);
            product.add(remove, BorderLayout.EAST);

            productListPanel.add(product);

            productPanels.add(product);
            productLabelPanels.add(productLabel);
            removeButtons.add(remove);
        }

        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setSize(width, 100);
        backButton = new JButton("<--- Back");
        topPanel.add(backButton, BorderLayout.WEST);


        checkoutButton = new JButton("Purchase Items");
        operationPanel.add(checkoutButton);
        Dimension operationSize = new Dimension(width - 100, 100);
        Dimension checkoutSize = new Dimension(150, 100);
        operationPanel.setPreferredSize(operationSize);
        operationPanel.setMinimumSize(operationSize);
        operationPanel.setMaximumSize(operationSize);
        checkoutButton.setPreferredSize(checkoutSize);
        checkoutButton.setMinimumSize(checkoutSize);
        checkoutButton.setMaximumSize(checkoutSize);

        cartFrame.setSize(width, height);
        cartFrame.setLocationRelativeTo(null);
        cartFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        cartFrame.setVisible(true);

        JScrollPane scrollBar = new JScrollPane(productListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBar.setPreferredSize(new Dimension(width - 100, height - 200));
        scrollBar.setMinimumSize(new Dimension(width - 100, height - 200));
        scrollBar.setMaximumSize(new Dimension(width - 100, height - 200));
        cartFrame.add(BorderLayout.CENTER, scrollBar);
        cartFrame.add(operationPanel, BorderLayout.SOUTH);
        cartFrame.add(topPanel, BorderLayout.NORTH);
    }
}
