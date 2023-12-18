import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Project5 -- Client
 *
 * Client side of the project which deals with all user input and GUIs.
 *
 * @author Chaewon Lee, Peter Kang, Marco Zhang, Iddo Mayblum, Joseph Lee, lab sec LC5
 *
 * @version December 11, 2022
 *
 */

public class Client extends JComponent implements Runnable {
    JButton createAccount;
    JButton login;
    ArrayList<String> productList;
    ArrayList<String> searchedProductList;
    ArrayList<String[]> productInfoList;
    ArrayList<String[]> searchedProductInfoList;
    ArrayList<JButton> buttonList;
    ArrayList<JLabel> labelList;
    ArrayList<JPanel> panelList;
    ArrayList<JPanel> searchedPanelList;
    JFrame logInFrame;
    JFrame customerFrame;
    JPanel productPanel;
    JPanel searchSortPanel;
    JPanel dashboardButtonPanel;
    JPanel dashboardContent;

    JTextField searchText;
    JButton searchButton;
    JButton sortPriceAscendingButton;
    JButton sortPriceDescendingButton;
    JButton sortQuantityAscendingButton;
    JButton sortQuantityDescendingButton;
    JButton refreshButton;
    JButton viewStoresBackButton;
    JButton sellerDashboardBackButton;
    JButton sortStoreSalesAscending;
    JButton sortStoreSalesDescending;
    JButton storesPurchasedFrom;
    JPanel customerOperationPanel;
    JButton viewCartButton;
    JButton viewStoresButton;
    JFrame viewStoresFrame;
    JPanel viewStoresButtonPanel;
    JPanel viewStoresContentPanel;
    JButton editCustomerButton;
    JButton viewHistoryButton;
    JButton exportHistoryButton;
    JButton leaveReviewButton;
    JButton createButton;
    JButton editButton;
    JButton deleteButton;
    JButton importButton;
    JButton exportButton;
    JButton viewDashboard;
    JButton viewSales;
    JButton viewReviews;
    JButton editUser;
    JButton refreshSellerButton;
    JButton logoutCustomer;
    JButton logoutSeller;

    JButton totalPurchasesAscending;
    JButton totalPurchasesDescending;
    JButton customerPurchasesAscending;
    JButton customerPurchasesDescending;
    JPanel buttonPanel;
    JPanel sellerPanel;
    ArrayList<JPanel> sellerProductPanels;
    ArrayList<JLabel> sellerProductLabels;

    JFrame sellerFrame;
    JFrame dashboardFrame;
    Container content;
    Seller seller;
    Customer customer;
    boolean isCustomer;
    boolean creatingProduct = false;
    boolean addingToCart = false;
    boolean viewingReviews = false;
    boolean viewingCart = false;
    int selectedProductIndex;
    JTextField usernameText;
    JTextField passwordText;
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) size.getWidth();
    int height = (int) size.getHeight();
    private String username;
    private String password;
    ShoppingCart cart;
    ArrayList<Product> cartProducts;

    Socket socket;
    BufferedReader reader;
    PrintWriter writer;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == login) {
                username = usernameText.getText();
                password = passwordText.getText();
                if (!username.equals("") && !password.equals("")) {
                    writer.println("sign in");
                    writer.println(username);
                    writer.println(password);
                    writer.flush();

                    try {
                        String loginSuccess = reader.readLine();
                        if (loginSuccess.equals("customer")) {
                            logInFrame.dispose();
                            customerFrame();
                            isCustomer = true;
                        } else if (loginSuccess.equals("seller")) {
                            logInFrame.dispose();
                            sellerFrame();
                            isCustomer = false;
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid Username or Password",
                                    "Login Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid Username or Password",
                                "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (e.getSource() == createAccount) {
                username = usernameText.getText();
                password = passwordText.getText();
                if (!username.equals("") && !password.equals("")) {
                    if (username.contains("@")) {
                        String[] buttons = {"Customer", "Seller"};
                        int option = JOptionPane.showOptionDialog(null, "Are you a customer " +
                                        "or seller?", "User Type", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[1]);
                        option++;
                        if (option != 0) {
                            String optionSend = String.format("%d", option);
                            writer.println("new account");
                            writer.println(username);
                            writer.println(password);
                            writer.println(optionSend);
                            writer.flush();
                            try {
                                String loginSuccess = reader.readLine();
                                if (loginSuccess.equals("true")) {
                                    if (option == 1) {
                                        logInFrame.dispose();
                                        customerFrame();
                                        isCustomer = true;
                                    } else if (option == 2) {
                                        logInFrame.dispose();
                                        sellerFrame();
                                        isCustomer = false;
                                    }
                                } else if (loginSuccess.equals("false")) {
                                    JOptionPane.showMessageDialog(null, "Failed to create account",
                                            "Account Creation Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, "Failed to create account",
                                        "Account Creation Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Please select an account type",
                                    "Account Creation Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username!",
                                "Account Creation Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            if (isCustomer) {
                if (e.getSource() == searchButton) {
                    searchedPanelList = panelList;
                    searchedProductList = productList;
                    searchedProductInfoList = productInfoList;
                    String searchField = searchText.getText();
                    if (searchField.equals("")) {
                        productPanel.removeAll();
                        for (int i = 0; i < panelList.size(); i++) {
                            productPanel.add(panelList.get(i));
                        }
                        productPanel.revalidate();
                        productPanel.repaint();
                    } else {
                        searchedPanelList = new ArrayList<>();
                        searchedProductList = new ArrayList<>();
                        searchedProductInfoList = new ArrayList<>();
                        ArrayList<Integer> searchResult = new ArrayList<>();
                        for (int i = 0; i < productInfoList.size(); i++) {
                            if (productInfoList.get(i)[0].toLowerCase().contains(searchField.toLowerCase()) ||
                                    productInfoList.get(i)[1].toLowerCase().contains(searchField.toLowerCase()) ||
                                    productInfoList.get(i)[2].toLowerCase().contains(searchField.toLowerCase())) {
                                searchResult.add(i);
                            }
                        }
                        productPanel.removeAll();
                        for (int i = 0; i < searchResult.size(); i++) {
                            searchedPanelList.add(panelList.get(searchResult.get(i)));
                            searchedProductList.add(productList.get(searchResult.get(i)));
                            searchedProductInfoList.add(productInfoList.get(searchResult.get(i)));
                        }
                        for (int i = 0; i < searchedPanelList.size(); i++) {
                            productPanel.add(searchedPanelList.get(i));
                        }
                        productPanel.revalidate();
                        productPanel.repaint();
                    }
                } else if (e.getSource() == refreshButton) {
                    customerFrame.dispose();
                    try {
                        customerFrame();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Failed to reload page",
                                "Account Creation Error", JOptionPane.ERROR_MESSAGE);
                        login();
                    }
                } else if (e.getSource() == viewStoresButton) {
                    customerFrame.dispose();
                    viewStores();

                } else if (e.getSource() == sortStoreSalesAscending) {
                    writer.println("sort store sales ascending");
                    writer.flush();

                    try {
                        ArrayList<String> ascendingSort = (ArrayList<String>) ois.readObject();
                        storeSalesAscending(ascendingSort);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (e.getSource() == sortStoreSalesDescending) {
                    writer.println("sort store sales descending");
                    writer.flush();

                    try {
                        ArrayList<String> descendingSort = (ArrayList<String>) ois.readObject();
                        storeSalesDescending(descendingSort);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }

                } else if (e.getSource() == storesPurchasedFrom) {
                    writer.println("stores purchased from");
                    writer.flush();
                    try {
                        ArrayList<String> storesPurchasedFromList = (ArrayList<String>) ois.readObject();
                        storesPurchasedFrom(storesPurchasedFromList);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }


                } else if (e.getSource() == viewStoresBackButton) {
                    viewStoresFrame.dispose();
                    try {
                        customerFrame();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (e.getSource() == sortPriceAscendingButton) {
                    ArrayList<Product> products = parseProductList(searchedProductInfoList);
                    Customer dummy = new Customer("dummy");
                    products = dummy.sortPriceAscending(products);
                    ArrayList<Integer> sortResult = new ArrayList<>();
                    for (int i = 0; i < products.size(); i++) {
                        for (int j = 0; j < searchedProductInfoList.size(); j++) {
                            if (products.get(i).getName().equals(searchedProductInfoList.get(j)[0]) &&
                                    products.get(i).getStore().equals(searchedProductInfoList.get(j)[1])) {
                                sortResult.add(j);
                            }
                        }
                    }
                    productPanel.removeAll();
                    ArrayList<JPanel> tempPanels = searchedPanelList;
                    ArrayList<String[]> tempProductsInfo = searchedProductInfoList;
                    ArrayList<String> tempProducts = searchedProductList;
                    searchedPanelList = new ArrayList<>();
                    searchedProductInfoList = new ArrayList<>();
                    searchedProductList = new ArrayList<>();
                    for (int i = 0; i < sortResult.size(); i++) {
                        searchedPanelList.add(tempPanels.get(sortResult.get(i)));
                        searchedProductInfoList.add(tempProductsInfo.get(sortResult.get(i)));
                        searchedProductList.add(tempProducts.get(sortResult.get(i)));
                    }
                    for (int i = 0; i < searchedPanelList.size(); i++) {
                        productPanel.add(searchedPanelList.get(i));
                    }
                    productPanel.revalidate();
                    productPanel.repaint();
                } else if (e.getSource() == sortPriceDescendingButton) {
                    ArrayList<Product> products = parseProductList(searchedProductInfoList);
                    Customer dummy = new Customer("dummy");
                    products = dummy.sortPriceDescending(products);
                    ArrayList<Integer> sortResult = new ArrayList<>();
                    for (int i = 0; i < products.size(); i++) {
                        for (int j = 0; j < searchedProductInfoList.size(); j++) {
                            if (products.get(i).getName().equals(searchedProductInfoList.get(j)[0]) &&
                                    products.get(i).getStore().equals(searchedProductInfoList.get(j)[1])) {
                                sortResult.add(j);
                            }
                        }
                    }
                    productPanel.removeAll();
                    ArrayList<JPanel> tempPanels = searchedPanelList;
                    ArrayList<String[]> tempProductsInfo = searchedProductInfoList;
                    ArrayList<String> tempProducts = searchedProductList;
                    searchedPanelList = new ArrayList<>();
                    searchedProductInfoList = new ArrayList<>();
                    searchedProductList = new ArrayList<>();
                    for (int i = 0; i < sortResult.size(); i++) {
                        searchedPanelList.add(tempPanels.get(sortResult.get(i)));
                        searchedProductInfoList.add(tempProductsInfo.get(sortResult.get(i)));
                        searchedProductList.add(tempProducts.get(sortResult.get(i)));
                    }
                    for (int i = 0; i < searchedPanelList.size(); i++) {
                        productPanel.add(searchedPanelList.get(i));
                    }
                    productPanel.revalidate();
                    productPanel.repaint();
                } else if (e.getSource() == sortQuantityAscendingButton) {
                    ArrayList<Product> products = parseProductList(searchedProductInfoList);
                    Customer dummy = new Customer("dummy");
                    products = dummy.sortQuantityAscending(products);
                    ArrayList<Integer> sortResult = new ArrayList<>();
                    for (int i = 0; i < products.size(); i++) {
                        for (int j = 0; j < searchedProductInfoList.size(); j++) {
                            if (products.get(i).getName().equals(searchedProductInfoList.get(j)[0]) &&
                                    products.get(i).getStore().equals(searchedProductInfoList.get(j)[1])) {
                                sortResult.add(j);
                            }
                        }
                    }
                    productPanel.removeAll();
                    ArrayList<JPanel> tempPanels = searchedPanelList;
                    ArrayList<String[]> tempProductsInfo = searchedProductInfoList;
                    ArrayList<String> tempProducts = searchedProductList;
                    searchedPanelList = new ArrayList<>();
                    searchedProductInfoList = new ArrayList<>();
                    searchedProductList = new ArrayList<>();
                    for (int i = 0; i < sortResult.size(); i++) {
                        searchedPanelList.add(tempPanels.get(sortResult.get(i)));
                        searchedProductInfoList.add(tempProductsInfo.get(sortResult.get(i)));
                        searchedProductList.add(tempProducts.get(sortResult.get(i)));
                    }
                    for (int i = 0; i < searchedPanelList.size(); i++) {
                        productPanel.add(searchedPanelList.get(i));
                    }
                    productPanel.revalidate();
                    productPanel.repaint();
                } else if (e.getSource() == sortQuantityDescendingButton) {
                    ArrayList<Product> products = parseProductList(searchedProductInfoList);
                    Customer dummy = new Customer("dummy");
                    products = dummy.sortQuantityDescending(products);
                    ArrayList<Integer> sortResult = new ArrayList<>();
                    for (int i = 0; i < products.size(); i++) {
                        for (int j = 0; j < searchedProductInfoList.size(); j++) {
                            if (products.get(i).getName().equals(searchedProductInfoList.get(j)[0]) &&
                                    products.get(i).getStore().equals(searchedProductInfoList.get(j)[1])) {
                                sortResult.add(j);
                            }
                        }
                    }
                    productPanel.removeAll();
                    ArrayList<JPanel> tempPanels = searchedPanelList;
                    ArrayList<String[]> tempProductsInfo = searchedProductInfoList;
                    ArrayList<String> tempProducts = searchedProductList;
                    searchedPanelList = new ArrayList<>();
                    searchedProductInfoList = new ArrayList<>();
                    searchedProductList = new ArrayList<>();
                    for (int i = 0; i < sortResult.size(); i++) {
                        searchedPanelList.add(tempPanels.get(sortResult.get(i)));
                        searchedProductInfoList.add(tempProductsInfo.get(sortResult.get(i)));
                        searchedProductList.add(tempProducts.get(sortResult.get(i)));
                    }
                    for (int i = 0; i < searchedPanelList.size(); i++) {
                        productPanel.add(searchedPanelList.get(i));
                    }
                    productPanel.revalidate();
                    productPanel.repaint();
                } else if (e.getSource() == editCustomerButton) {
                    String[] options = {"Edit Account Details", "Delete Account"};
                    String option = (String) JOptionPane.showInputDialog(null,
                            "Edit or delete account?", "Select Option",
                            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (option != null) {
                        if (option.equals(options[0])) {
                            String newUsername = JOptionPane.showInputDialog(null,
                                    "Enter your new username", "New Username",
                                    JOptionPane.QUESTION_MESSAGE);
                            if (newUsername == null) {
                                // clicked 'x' or cancel
                            } else if (newUsername.equals("") || !newUsername.contains("@")) {
                                JOptionPane.showMessageDialog(null, "Invalid username!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                String newPassword = JOptionPane.showInputDialog(null,
                                        "Enter your new password", "New Password",
                                        JOptionPane.QUESTION_MESSAGE);
                                if (newPassword == null) {
                                    // clicked 'x' or cancel
                                } else if (newPassword.equals("")) {
                                    JOptionPane.showMessageDialog(null, "Invalid password!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    writer.println("edit customer");
                                    writer.println(username);
                                    writer.println(password);
                                    writer.println(option);
                                    writer.flush();
                                    writer.println(newUsername);
                                    writer.println(newPassword);
                                    writer.flush();
                                    try {
                                        String accountEdited = reader.readLine();
                                        if (accountEdited.equals("true")) {
                                            JOptionPane.showMessageDialog(null, "Account edited!",
                                                    "Account Edited", JOptionPane.INFORMATION_MESSAGE);
                                            username = newUsername;
                                            password = newPassword;
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Error editing account!",
                                                    "Error", JOptionPane.ERROR_MESSAGE);
                                        }
                                    } catch (IOException ex) {
                                        JOptionPane.showMessageDialog(null, "Error editing account!",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                        } else if (option.equals(options[1])) {
                            writer.println("edit customer");
                            writer.println(username);
                            writer.println(password);
                            writer.println(option);
                            writer.flush();
                            try {
                                String accountDeleted = reader.readLine();
                                if (accountDeleted.equals("true")) {
                                    JOptionPane.showMessageDialog(null, "Account deleted!",
                                            "Account Deleted", JOptionPane.INFORMATION_MESSAGE);
                                    customerFrame.dispose();
                                    login();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Error deleting account!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, "Error deleting account!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                } else if (e.getSource() == viewCartButton) {
                    cartProducts = new ArrayList<>();
                    writer.println("return shoppingcart");
                    writer.println(username);
                    writer.flush();

                    try {
                        String line = reader.readLine();
                        while (!line.equals("end")) {
                            String[] lineSplit = line.split(",");
                            cartProducts.add(new Product(lineSplit[1], lineSplit[2], lineSplit[3],
                                    Integer.parseInt(lineSplit[4]), Double.parseDouble(lineSplit[5]), lineSplit[6]));
                            line = reader.readLine();
                        }

                        customerFrame.dispose();
                        cart = new ShoppingCart();
                        cart.displayCart(cartProducts);
                        viewingCart = true;
                        for (int i = 0; i < cart.removeButtons.size(); i++) {
                            cart.removeButtons.get(i).addActionListener(actionListener);
                        }
                        cart.checkoutButton.addActionListener(actionListener);
                        cart.backButton.addActionListener(actionListener);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error reading cart!",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (e.getSource() == viewHistoryButton) {
                    writer.println("view history");
                    writer.println(username);
                    writer.flush();
                    try {
                        String reviewsExist = reader.readLine();
                        if (reviewsExist.equals("false")) {
                            JOptionPane.showMessageDialog(null, "No previous transactions!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            ArrayList<String> transactions = new ArrayList<>();
                            String transaction = reader.readLine();
                            while (!transaction.equals("end")) {
                                transactions.add(transaction);
                                transaction = reader.readLine();
                            }
                            String[] history = new String[transactions.size()];
                            for (int i = 0; i < transactions.size(); i++) {
                                history[i] = transactions.get(i);
                            }
                            String selectedHistory = (String) JOptionPane.showInputDialog(null,
                                    "Displaying previous transactions:", "View Previous Transactions",
                                    JOptionPane.QUESTION_MESSAGE, null, history, history[0]);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error displaying transactions",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (e.getSource() == exportHistoryButton) {
                    String fileName = JOptionPane.showInputDialog(null,
                            "Enter a file name to export history to", "Export History",
                            JOptionPane.QUESTION_MESSAGE);
                    if (fileName == null) {
                        // clicked 'x' or cancel
                    } else if (fileName.equals("") || fileName.equals("Products.txt") || fileName.equals("History.txt")
                            || fileName.equals("shoppingcart.txt") || fileName.equals("userlogin.txt")
                            || fileName.equals("Reviews.txt")) {
                        JOptionPane.showMessageDialog(null, "Invalid file name",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        writer.println("export customer history");
                        writer.println(username);
                        writer.println(fileName);
                        writer.flush();

                        try {
                            String success = reader.readLine();
                            if (success.equals("true")) {
                                JOptionPane.showMessageDialog(null, "History exported!",
                                        "History Exported", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Failed to export history",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (e.getSource() == leaveReviewButton) {
                    writer.println("leave review");
                    writer.println(username);
                    writer.flush();
                    try {
                        String reviewsExist = reader.readLine();
                        if (reviewsExist.equals("false")) {
                            JOptionPane.showMessageDialog(null, "No products to leave reviews on!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            ArrayList<String> transactions = new ArrayList<>();
                            String transaction = reader.readLine();
                            while (!transaction.equals("end")) {
                                transactions.add(transaction);
                                transaction = reader.readLine();
                            }
                            String[] history = new String[transactions.size()];
                            for (int i = 0; i < transactions.size(); i++) {
                                history[i] = transactions.get(i);
                            }
                            String selectedHistory = (String) JOptionPane.showInputDialog(null,
                                    "Choose a product to leave a review on:", "Choose Product",
                                    JOptionPane.QUESTION_MESSAGE, null, history, history[0]);
                            if (selectedHistory == null) {
                                // clicked 'x' or cancel
                                writer.println("no");
                                writer.flush();
                            } else {
                                boolean answer = true;
                                String input = JOptionPane.showInputDialog(null,
                                        "Enter review", "Enter Review", JOptionPane.PLAIN_MESSAGE);
                                while (answer) {
                                    if (input == null) {
                                        answer = false;
                                    } else if (input.length() > 40) {
                                        JOptionPane.showMessageDialog(null,
                                                "Keep the review under 40 characters!", "Over Character Limit",
                                                JOptionPane.ERROR_MESSAGE);
                                        input = JOptionPane.showInputDialog(null,
                                                "Enter review", "Enter Review", JOptionPane.PLAIN_MESSAGE);
                                        answer = true;
                                    } else if (input.length() == 0) {
                                        JOptionPane.showMessageDialog(null,
                                                "Please enter a review!", "Enter Review", JOptionPane.ERROR_MESSAGE);
                                        input = JOptionPane.showInputDialog(null,
                                                "Enter review", "Enter Review", JOptionPane.PLAIN_MESSAGE);
                                        answer = true;
                                    } else if (input.contains(";")) {
                                        JOptionPane.showMessageDialog(null,
                                                "Review cannot contain semicolons!", "Over Character Limit",
                                                JOptionPane.ERROR_MESSAGE);
                                        input = JOptionPane.showInputDialog(null,
                                                "Enter review", "Enter Review", JOptionPane.PLAIN_MESSAGE);
                                        answer = true;
                                    } else {
                                        answer = false;
                                    }
                                }
                                if (input != null) {
                                    writer.println("review");
                                    writer.println(selectedHistory);
                                    writer.println(input);
                                    writer.flush();

                                    String success = reader.readLine();
                                    if (success.equals("success")) {
                                        JOptionPane.showMessageDialog(null, "Review left!",
                                                "Review", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                } else {
                                    writer.println("no");
                                    writer.flush();
                                }
                            }
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error leaving review",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (e.getSource() == logoutCustomer) {
                    JOptionPane.showMessageDialog(null, "Thank you for using BoilerMarket!",
                            "Goodbye", JOptionPane.INFORMATION_MESSAGE);
                    customerFrame.dispose();
                    login();
                }
                if (addingToCart) {
                    if (e.getSource() == customer.addToShoppingCart) {
                        boolean amountValid;
                        String amount;
                        do {
                            amountValid = true;
                            amount = JOptionPane.showInputDialog(null,
                                    "How many would you like to purchase?", "Amount",
                                    JOptionPane.QUESTION_MESSAGE);

                            try {
                                writer.println("return products");
                                writer.flush();
                                String line = reader.readLine();
                                productList = new ArrayList<>();
                                productInfoList = new ArrayList<>();
                                while (!line.equals("end")) {
                                    productList.add(line);
                                    String[] lineInfo = line.split(",");
                                    productInfoList.add(lineInfo);
                                    line = reader.readLine();
                                }
                                if (Integer.parseInt(amount) >
                                        Integer.parseInt(productInfoList.get(selectedProductIndex)[3])) {
                                    amountValid = false;
                                    JOptionPane.showMessageDialog(null,
                                            "Requested amount exceeds quantity available!", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                } else if (Integer.parseInt(amount) < 1) {
                                    amountValid = false;
                                    JOptionPane.showMessageDialog(null,
                                            "Enter a valid amount!", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (NumberFormatException ex) {
                                if (amount != null) {
                                    JOptionPane.showMessageDialog(null, "Enter a valid amount!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    amountValid = false;
                                }
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, "Enter a valid amount!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                amountValid = false;
                            }
                        } while (!amountValid);


                        if (amount != null && !amount.equals("")) {
                            writer.println("add to cart");
                            writer.println(username);
                            writer.println(productInfoList.get(selectedProductIndex)[0]);
                            writer.println(productInfoList.get(selectedProductIndex)[1]);
                            writer.println(productInfoList.get(selectedProductIndex)[2]);
                            writer.println(amount);
                            writer.println(productInfoList.get(selectedProductIndex)[4]);
                            writer.println(productInfoList.get(selectedProductIndex)[5]);
                            writer.flush();

                            try {
                                String addSuccess = reader.readLine();
                                if (addSuccess.equals("true")) {
                                    JOptionPane.showMessageDialog(null, "Product added to cart!",
                                            "Success!", JOptionPane.INFORMATION_MESSAGE);
                                    writer.println("return products");
                                    writer.flush();
                                    String line = reader.readLine();
                                    productList = new ArrayList<>();
                                    productInfoList = new ArrayList<>();
                                    while (!line.equals("end")) {
                                        productList.add(line);
                                        String[] lineInfo = line.split(",");
                                        productInfoList.add(lineInfo);
                                        line = reader.readLine();
                                    }
                                }
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, "Failed adding to cart!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }


                            customer.productFrame.dispose();
                            addingToCart = false;
                            viewingReviews = false;
                        }
                    }
                }
                if (viewingReviews) {
                    if (e.getSource() == customer.viewReviews) {
                        writer.println("return reviews");
                        writer.println(productInfoList.get(selectedProductIndex)[0]);
                        writer.println(productInfoList.get(selectedProductIndex)[1]);
                        writer.flush();

                        try {
                            String line = reader.readLine();
                            ArrayList<String[]> reviewsList = new ArrayList<>();
                            while (!line.equals("end")) {
                                reviewsList.add(line.split(";"));
                                line = reader.readLine();
                            }
                            ArrayList<Reviews> reviews = new ArrayList<>();
                            for (int i = 0; i < reviewsList.size(); i++) {
                                reviews.add(parseReview(reviewsList.get(i)));
                            }
                            customer.displayReviews(reviews);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Failed to view reviews!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                if (viewingCart) {
                    if (e.getSource() == cart.backButton) {
                        viewingCart = false;
                        cart.cartFrame.dispose();
                        try {
                            customerFrame();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Failed to open home page!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else if (e.getSource() == cart.checkoutButton) {
                        int confirmCheckout = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to checkout?", "Checkout",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (confirmCheckout == 0) {
                            writer.println("checkout");
                            writer.println(username);
                            writer.flush();

                            JOptionPane.showMessageDialog(null, "Purchase successful!",
                                    "Transaction Complete", JOptionPane.QUESTION_MESSAGE);
                            viewingCart = false;
                            cart.cartFrame.dispose();
                            try {
                                customerFrame();
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, "Failed to open home page!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    for (int i = 0; i < cart.removeButtons.size(); i++) {
                        if (e.getSource() == cart.removeButtons.get(i)) {
                            writer.println("remove from cart");
                            writer.println(username);
                            writer.println(cartProducts.get(i).getName());
                            writer.println(cartProducts.get(i).getStore());
                            writer.flush();

                            cartProducts = new ArrayList<>();
                            writer.println("return shoppingcart");
                            writer.println(username);
                            writer.flush();

                            try {
                                String line = reader.readLine();
                                while (!line.equals("end")) {
                                    String[] lineSplit = line.split(",");
                                    cartProducts.add(new Product(lineSplit[1], lineSplit[2], lineSplit[3],
                                            Integer.parseInt(lineSplit[4]), Double.parseDouble(lineSplit[5]),
                                            lineSplit[6]));
                                    line = reader.readLine();
                                }

                                cart.cartFrame.dispose();
                                cart.displayCart(cartProducts);
                                for (int j = 0; j < cart.removeButtons.size(); j++) {
                                    cart.removeButtons.get(j).addActionListener(actionListener);
                                }
                                cart.backButton.addActionListener(actionListener);
                                cart.checkoutButton.addActionListener(actionListener);
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, "Error reading cart!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
                for (int i = 0; i < buttonList.size(); i++) {
                    if (e.getSource() == buttonList.get(i)) {
                        Product selectedProduct = parseProduct(productInfoList.get(i));
                        customer = new Customer(username);
                        customer.displayProduct(selectedProduct);
                        customer.addToShoppingCart.addActionListener(actionListener);
                        customer.viewReviews.addActionListener(actionListener);
                        addingToCart = true;
                        viewingReviews = true;
                        selectedProductIndex = i;
                    }
                }
            }
            if (!isCustomer) {
                if (e.getSource() == createButton) {
                    seller = new Seller(username);
                    seller.create();
                    seller.createButton.addActionListener(actionListener);
                    creatingProduct = true;
                } else if (e.getSource() == editButton) {
                    ArrayList<String[]> sellerProductList = new ArrayList<>();
                    for (int i = 0; i < productInfoList.size(); i++) {
                        if (productInfoList.get(i)[5].equals(username)) {
                            sellerProductList.add(productInfoList.get(i));
                        }
                    }
                    if (sellerProductList.size() != 0) {
                        String[] sellerProducts = new String[sellerProductList.size()];
                        for (int i = 0; i < sellerProductList.size(); i++) {
                            sellerProducts[i] = sellerProductList.get(i)[0];
                        }
                        String editedProduct = (String) JOptionPane.showInputDialog(null,
                                "Which product would you like to edit?", "Select Product",
                                JOptionPane.QUESTION_MESSAGE, null, sellerProducts, sellerProducts[0]);
                        if (editedProduct != null) {
                            int editedProductIndex = -1;
                            for (int i = 0; i < sellerProductList.size(); i++) {
                                if (sellerProductList.get(i)[0].equals(editedProduct)) {
                                    editedProductIndex = i;
                                }
                            }
                            String[] options = new String[5];
                            options[0] = "Edit product name";
                            options[1] = "Edit store name";
                            options[2] = "Edit product description";
                            options[3] = "Edit quantity available";
                            options[4] = "Edit price";
                            String option = (String) JOptionPane.showInputDialog(null,
                                    "Select edit option", "Select Option", JOptionPane.PLAIN_MESSAGE, null,
                                    options, null);
                            boolean badEdit;
                            String editedOption;
                            do {
                                badEdit = false;
                                editedOption = JOptionPane.showInputDialog(null,
                                        "Enter edited field:", "Edit Product", JOptionPane.QUESTION_MESSAGE);
                                if (editedOption != null) {
                                    try {
                                        if (option.equals("Edit quantity available")) {
                                            Integer.parseInt(editedOption);
                                        } else if (option.equals("Edit price")) {
                                            Double.parseDouble(editedOption);
                                        } else if (editedOption.equals("")) {
                                            throw new NumberFormatException();
                                        }
                                    } catch (NumberFormatException ex) {
                                        JOptionPane.showMessageDialog(null, "Invalid edited value!",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                        badEdit = true;
                                    }
                                }
                            } while (badEdit);

                            if (editedOption != null) {
                                writer.println("edit product");
                                writer.println(sellerProductList.get(editedProductIndex)[0]);
                                writer.println(sellerProductList.get(editedProductIndex)[1]);
                                writer.println(option);
                                writer.println(editedOption);
                                writer.flush();

                                try {
                                    String editSuccess = reader.readLine();
                                    if (editSuccess.equals("false")) {
                                        JOptionPane.showMessageDialog(null,
                                                "Failed to edit product", "Error", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(null,
                                                "Product edited!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                        writer.println("return products");
                                        writer.flush();
                                        String line = reader.readLine();
                                        productList = new ArrayList<>();
                                        productInfoList = new ArrayList<>();
                                        while (!line.equals("end")) {
                                            productList.add(line);
                                            String[] lineInfo = line.split(",");
                                            productInfoList.add(lineInfo);
                                            line = reader.readLine();
                                        }
                                    }
                                } catch (IOException ex) {
                                    JOptionPane.showMessageDialog(null,
                                            "Failed to edit product", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
                } else if (e.getSource() == importButton) {
                    String fileName = JOptionPane.showInputDialog(null,
                            "Enter a file name to import from", "Import Products",
                            JOptionPane.QUESTION_MESSAGE);
                    if (fileName == null) {
                        // clicked 'x' or cancel
                    } else if (fileName.equals("")) {
                        JOptionPane.showMessageDialog(null, "Invalid file name!",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        File f = new File(fileName);
                        if (f.exists()) {
                            writer.println("import products");
                            writer.println(username);
                            writer.flush();
                            try {
                                BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
                                String line = br.readLine();
                                while (line != null) {
                                    writer.println(line);
                                    line = br.readLine();
                                }
                                br.close();
                                writer.println("end");
                                writer.flush();
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, "Failed to import file",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            try {
                                String importSuccess = reader.readLine();
                                if (importSuccess.equals("false")) {
                                    JOptionPane.showMessageDialog(null, "Failed to import file",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, "File imported!",
                                            "File Imported", JOptionPane.INFORMATION_MESSAGE);
                                    writer.println("return products");
                                    writer.flush();
                                    String line = reader.readLine();
                                    productList = new ArrayList<>();
                                    productInfoList = new ArrayList<>();
                                    while (!line.equals("end")) {
                                        productList.add(line);
                                        String[] lineInfo = line.split(",");
                                        productInfoList.add(lineInfo);
                                        line = reader.readLine();
                                    }
                                }
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, "Failed to import file",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Enter a valid file name",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (e.getSource() == exportButton) {
                    String fileName = JOptionPane.showInputDialog(null,
                            "Enter a file name to export products to", "Export Products",
                            JOptionPane.QUESTION_MESSAGE);
                    if (fileName == null) {
                        // clicked 'x' or cancel
                    } else if (fileName.equals("")) {
                        JOptionPane.showMessageDialog(null, "Invalid file name!",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        ArrayList<String> list = new ArrayList<>();
                        for (int i = 0; i < productInfoList.size(); i++) {
                            if (productInfoList.get(i)[5].equals(username)) {
                                list.add(String.format("%s,%s,%s,%s,%s", productInfoList.get(i)[0],
                                        productInfoList.get(i)[1], productInfoList.get(i)[2], productInfoList.get(i)[3],
                                        productInfoList.get(i)[4]));
                            }
                        }
                        try {
                            if (fileName.equals("Products.txt") || fileName.equals("History.txt") ||
                                    fileName.equals("shoppingcart.txt") || fileName.equals("userlogin.txt") ||
                                    fileName.equals("Reviews.txt")) {
                                throw new FileOverwrittenException();
                            }
                            File f = new File(fileName);
                            f.createNewFile();
                            FileOutputStream fos = new FileOutputStream(f.getAbsolutePath(), false);
                            PrintWriter pw = new PrintWriter(fos);

                            for (int i = 0; i < list.size(); i++) {
                                pw.println(list.get(i));
                            }
                            pw.close();
                            JOptionPane.showMessageDialog(null, "File exported!",
                                    "File Exported", JOptionPane.INFORMATION_MESSAGE);

                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Failed to export file",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (FileOverwrittenException ex) {
                            JOptionPane.showMessageDialog(null, "Failed to export file",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (e.getSource() == deleteButton) {
                    ArrayList<String[]> sellerProductList = new ArrayList<>();
                    for (int i = 0; i < productInfoList.size(); i++) {
                        if (productInfoList.get(i)[5].equals(username)) {
                            sellerProductList.add(productInfoList.get(i));
                        }
                    }
                    if (sellerProductList.size() != 0) {
                        String[] sellerProducts = new String[sellerProductList.size()];
                        for (int i = 0; i < sellerProductList.size(); i++) {
                            sellerProducts[i] = sellerProductList.get(i)[0];
                        }
                        String selectedProduct = (String) JOptionPane.showInputDialog(null,
                                "Which product would you like to delete?", "Delete Product",
                                JOptionPane.QUESTION_MESSAGE, null, sellerProducts, sellerProducts[0]);
                        if (selectedProduct != null) {
                            String selectedStore = "";
                            for (int i = 0; i < sellerProducts.length; i++) {
                                if (selectedProduct.equals(sellerProducts[i])) {
                                    selectedStore = sellerProductList.get(i)[1];
                                }
                            }
                            int confirm = JOptionPane.showConfirmDialog(null,
                                    "Are you sure you want to delete the product?", "Confirm Delete",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (confirm == 0) {
                                writer.println("delete product");
                                writer.println(selectedProduct);
                                writer.println(selectedStore);
                                writer.flush();

                                try {
                                    String deleteSuccess = reader.readLine();
                                    if (deleteSuccess.equals("false")) {
                                        JOptionPane.showMessageDialog(null, "Failed to delete product",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Product deleted!",
                                                "Deleted", JOptionPane.INFORMATION_MESSAGE);
                                        writer.println("return products");
                                        writer.flush();
                                        String line = reader.readLine();
                                        productList = new ArrayList<>();
                                        productInfoList = new ArrayList<>();
                                        while (!line.equals("end")) {
                                            productList.add(line);
                                            String[] lineInfo = line.split(",");
                                            productInfoList.add(lineInfo);
                                            line = reader.readLine();
                                        }
                                    }
                                } catch (IOException ex) {
                                    JOptionPane.showMessageDialog(null, "Failed to delete product",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
                } else if (e.getSource() == viewReviews) {
                    writer.println("view seller reviews");
                    writer.println(username);
                    writer.flush();
                    try {
                        String reviewsExist = reader.readLine();
                        if (reviewsExist.equals("false")) {
                            JOptionPane.showMessageDialog(null, "No reviews!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            ArrayList<String> reviews = new ArrayList<>();
                            String review = reader.readLine();
                            while (!review.equals("end")) {
                                reviews.add(review);
                                review = reader.readLine();
                            }
                            String[] sellerReviews = new String[reviews.size()];
                            for (int i = 0; i < reviews.size(); i++) {
                                sellerReviews[i] = reviews.get(i);
                            }
                            String selectedReview = (String) JOptionPane.showInputDialog(null,
                                    "View reviews", "View Reviews",
                                    JOptionPane.QUESTION_MESSAGE, null, sellerReviews, sellerReviews[0]);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error viewing reviews",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (e.getSource() == viewSales) {
                    String[] options = {"Store Sales", "Current Customer Carts"};
                    String option = (String) JOptionPane.showInputDialog(null,
                            "View by store or shopping cart?", "Select Option",
                            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (option == null) {
                        // clicked 'x' or cancel
                    } else if (option.equals("")) {
                        JOptionPane.showMessageDialog(null, "Invalid store name!",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (option.equals(options[0])) {
                            String store = JOptionPane.showInputDialog(null,
                                    "Enter store name:", "Store Sales", JOptionPane.QUESTION_MESSAGE);
                            if (store == null) {
                                // clicked 'x' or cancel
                            } else if (store.equals("")) {
                                JOptionPane.showMessageDialog(null, "Invalid store name!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                writer.println("view sales");
                                writer.println(username);
                                writer.println(option);
                                writer.println(store);
                                writer.flush();
                                ArrayList<String> sales = new ArrayList<>();
                                try {
                                    String salesExist = reader.readLine();
                                    if (salesExist.equals("false")) {
                                        JOptionPane.showMessageDialog(null, "No sales for this store!",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        String sale = reader.readLine();
                                        while (!sale.equals("end")) {
                                            sales.add(sale);
                                            sale = reader.readLine();
                                        }
                                        String[] salesInfo = new String[sales.size()];
                                        for (int i = 0; i < sales.size(); i++) {
                                            salesInfo[i] = sales.get(i);
                                        }
                                        String selectedSales = (String) JOptionPane.showInputDialog(null,
                                                "View sales", "View Sales",
                                                JOptionPane.QUESTION_MESSAGE, null, salesInfo, salesInfo[0]);
                                    }
                                } catch (IOException ex) {
                                    JOptionPane.showMessageDialog(null, "Error viewing sales",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } else if (option.equals(options[1])) {
                            writer.println("view sales");
                            writer.println(username);
                            writer.println(option);
                            writer.flush();
                            ArrayList<String> contents = new ArrayList<>();
                            try {
                                String contentsExist = reader.readLine();
                                if (contentsExist.equals("false")) {
                                    JOptionPane.showMessageDialog(null, "No products in shopping cart!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    String info = reader.readLine();
                                    while (!info.equals("end")) {
                                        contents.add(info);
                                        info = reader.readLine();
                                    }
                                    String[] contentsInfo = new String[contents.size()];
                                    for (int i = 0; i < contents.size(); i++) {
                                        contentsInfo[i] = contents.get(i);
                                    }
                                    String selectedContent = (String) JOptionPane.showInputDialog(null,
                                            "View products in shopping cart", "View Shopping Cart",
                                            JOptionPane.QUESTION_MESSAGE, null, contentsInfo, contentsInfo[0]);
                                }
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, "Error viewing shopping cart",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                } else if (e.getSource() == editUser) {
                    String[] options = {"Edit Account Details", "Delete Account"};
                    String option = (String) JOptionPane.showInputDialog(null,
                            "Edit or delete account?", "Select Option",
                            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (option.equals(options[0])) {
                        String newUsername = JOptionPane.showInputDialog(null,
                                "Enter your new username", "New Username",
                                JOptionPane.QUESTION_MESSAGE);
                        if (newUsername == null) {
                            // clicked 'x' or cancel
                        } else if (newUsername.equals("") || !newUsername.contains("@")) {
                            JOptionPane.showMessageDialog(null, "Invalid username!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            String newPassword = JOptionPane.showInputDialog(null,
                                    "Enter your new password", "New Password",
                                    JOptionPane.QUESTION_MESSAGE);
                            if (newPassword == null) {
                                // clicked 'x' or cancel
                            } else if (newPassword.equals("")) {
                                JOptionPane.showMessageDialog(null, "Invalid password!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                writer.println("edit user");
                                writer.println(username);
                                writer.println(password);
                                writer.println(option);
                                writer.flush();
                                writer.println(newUsername);
                                writer.println(newPassword);
                                writer.flush();
                                try {
                                    String accountEdited = reader.readLine();
                                    if (accountEdited.equals("true")) {
                                        JOptionPane.showMessageDialog(null, "Account edited!",
                                                "Account Edited", JOptionPane.INFORMATION_MESSAGE);
                                        username = newUsername;
                                        password = newPassword;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Error editing account!",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                } catch (IOException ex) {
                                    JOptionPane.showMessageDialog(null, "Error editing account!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    } else if (option.equals(options[1])) {
                        writer.println("edit user");
                        writer.println(username);
                        writer.println(password);
                        writer.println(option);
                        writer.flush();
                        try {
                            String accountDeleted = reader.readLine();
                            if (accountDeleted.equals("true")) {
                                JOptionPane.showMessageDialog(null, "Account deleted!",
                                        "Account Deleted", JOptionPane.INFORMATION_MESSAGE);
                                sellerFrame.dispose();
                                login();
                            } else {
                                JOptionPane.showMessageDialog(null, "Error deleting account!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Error deleting account!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (e.getSource() == logoutSeller) {
                    JOptionPane.showMessageDialog(null, "Thank you for using BoilerMarket!",
                            "Goodbye", JOptionPane.INFORMATION_MESSAGE);
                    sellerFrame.dispose();
                    login();
                } else if (e.getSource() == viewDashboard) {
                    sellerFrame.dispose();
                    viewDashboardFrame();
                } else if (e.getSource() == sellerDashboardBackButton) {
                    try {
                        dashboardFrame.dispose();
                        sellerFrame();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (e.getSource() == totalPurchasesAscending) {
                    writer.println("purchases ascending");
                    writer.flush();
                    try {
                        String ascendingSort = reader.readLine();
                        if (ascendingSort.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No products", "Error",
                                    JOptionPane.WARNING_MESSAGE);
                        } else {
                            purchaseContentAscending(ascendingSort);
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (e.getSource() == totalPurchasesDescending) {
                    writer.println("purchase descending");
                    writer.flush();
                    try {
                        String descendingSort = reader.readLine();
                        if (descendingSort.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No products", "Error",
                                    JOptionPane.WARNING_MESSAGE);
                        } else {
                            purchaseContentDescending(descendingSort);
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (e.getSource() == customerPurchasesAscending) {
                    writer.println("customer ascending");
                    writer.flush();
                    try {
                        LinkedHashMap<String, Integer> ascendingSortCustomer =
                                (LinkedHashMap<String, Integer>) ois.readObject();
                        if (ascendingSortCustomer.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No purchases", "Error",
                                    JOptionPane.WARNING_MESSAGE);
                        } else {
                            customerPurchasesAscending(ascendingSortCustomer);
                        }

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (e.getSource() == customerPurchasesDescending) {
                    writer.println("customer descending");
                    writer.flush();
                    try {
                        LinkedHashMap<String, Integer> descendingSortCustomer =
                                (LinkedHashMap<String, Integer>) ois.readObject();
                        if (descendingSortCustomer.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No purchases", "Error",
                                    JOptionPane.WARNING_MESSAGE);
                        } else {
                            customerPurchasesDescending(descendingSortCustomer);
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (e.getSource() == refreshSellerButton) {
                    sellerFrame.dispose();
                    try {
                        sellerFrame();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Failed to refresh page!",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        login();
                    }
                }
                if (creatingProduct) {
                    if (e.getSource() == seller.createButton) {
                        String product = seller.product.getText();
                        String store = seller.store.getText();
                        String description = seller.description.getText();
                        String quantity = seller.quantity.getText();
                        String price = seller.price.getText();
                        if (!product.equals("") && !store.equals("") && !description.equals("")
                                && !quantity.equals("") && !price.equals("")) {
                            if (!product.contains(",") && !store.contains(",") && !description.contains(",")) {
                                try {
                                    Integer.parseInt(quantity);
                                    Double.parseDouble(price);
                                    writer.println("create product");
                                    writer.println(product);
                                    writer.println(store);
                                    writer.println(description);
                                    writer.println(quantity);
                                    writer.println(price);
                                    writer.println(seller.getUsername());
                                    writer.flush();
                                    String createSuccess = reader.readLine();
                                    if (createSuccess.equals("false")) {
                                        JOptionPane.showMessageDialog(null, "Failed to create product",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        seller.productFrame.dispose();
                                        creatingProduct = false;
                                        writer.println("return products");
                                        writer.flush();
                                        String line = reader.readLine();
                                        productList = new ArrayList<>();
                                        productInfoList = new ArrayList<>();
                                        while (!line.equals("end")) {
                                            productList.add(line);
                                            String[] lineInfo = line.split(",");
                                            productInfoList.add(lineInfo);
                                            line = reader.readLine();
                                        }
                                    }
                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(null, "Invalid quantity or price",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                } catch (IOException ex) {
                                    JOptionPane.showMessageDialog(null, "Failed to create product",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
                }
            }

        }
    };

    /**
     * connect to server
     */
    public void client() {
        try {
            socket = new Socket("localhost", 4243);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * creates login frame
     */
    public void login() {
        logInFrame = new JFrame("Welcome");
        content = logInFrame.getContentPane();

        content.setLayout((new BorderLayout()));
        login = new JButton("Sign In");
        createAccount = new JButton("Create Account");
        usernameText = new JTextField("", 15);
        passwordText = new JTextField("", 15);
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        login.addActionListener(actionListener);
        createAccount.addActionListener(actionListener);

        logInFrame.setSize(300, 150);
        logInFrame.setLocationRelativeTo(null);
        logInFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        logInFrame.setVisible(true);

        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new FlowLayout());

        userInfoPanel.add(usernameLabel);
        userInfoPanel.add(usernameText);
        userInfoPanel.add(passwordLabel);
        userInfoPanel.add(passwordText);
        userInfoPanel.add(login);
        userInfoPanel.add(createAccount);
        content.add(userInfoPanel, BorderLayout.CENTER);
    }

    /**
     * creates customer frame
     */
    public void customerFrame() throws IOException {
        customerFrame = new JFrame("Marketplace");
        content = customerFrame.getContentPane();
        productPanel = new JPanel();
        productPanel.setSize(width - 200, height - 200);
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));

        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new BorderLayout());

        writer.println("return products");
        writer.flush();
        String line = reader.readLine();
        productList = new ArrayList<>();
        productInfoList = new ArrayList<>();
        buttonList = new ArrayList<>();
        labelList = new ArrayList<>();
        panelList = new ArrayList<>();
        JButton temp;
        JLabel tempLabel;
        JPanel tempPanel;
        while (!line.equals("end")) {
            productList.add(line);
            String[] lineInfo = line.split(",");
            productInfoList.add(lineInfo);
            tempPanel = new JPanel(new BorderLayout());
            tempPanel.setPreferredSize(new Dimension(width - 400, 50));
            tempPanel.setMaximumSize(new Dimension(width - 400, 50));
            tempPanel.setMinimumSize(new Dimension(width - 400, 50));
            tempLabel = new JLabel("Product: " + lineInfo[0] + ", Store: " + lineInfo[1] +
                    ", Price: $" + String.format("%.2f", Double.parseDouble(lineInfo[4])));
            temp = new JButton("Select " + lineInfo[0]);
            temp.setPreferredSize(new Dimension(200, 50));
            temp.addActionListener(actionListener);
            buttonList.add(temp);
            labelList.add(tempLabel);
            panelList.add(tempPanel);
            line = reader.readLine();
        }
        searchedPanelList = panelList;
        searchedProductList = productList;
        searchedProductInfoList = productInfoList;
        for (int i = 0; i < panelList.size(); i++) {
            panelList.get(i).add(buttonList.get(i), BorderLayout.EAST);
            panelList.get(i).add(labelList.get(i), BorderLayout.WEST);
            productPanel.add(panelList.get(i));
        }
        searchSortPanel = new JPanel();
        searchSortPanel.setPreferredSize(new Dimension(width - 200, 50));
        searchText = new JTextField("", 20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(actionListener);
        sortPriceAscendingButton = new JButton("Price Ascending");
        sortPriceAscendingButton.addActionListener(actionListener);
        sortPriceDescendingButton = new JButton("Price Descending");
        sortPriceDescendingButton.addActionListener(actionListener);
        sortQuantityAscendingButton = new JButton("Quantity Ascending");
        sortQuantityAscendingButton.addActionListener(actionListener);
        sortQuantityDescendingButton = new JButton("Quantity Descending");
        sortQuantityDescendingButton.addActionListener(actionListener);
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(actionListener);
        searchSortPanel.add(searchText);
        searchSortPanel.add(searchButton);
        searchSortPanel.add(new JLabel("Sort by:"));
        searchSortPanel.add(sortPriceAscendingButton);
        searchSortPanel.add(sortPriceDescendingButton);
        searchSortPanel.add(sortQuantityAscendingButton);
        searchSortPanel.add(sortQuantityDescendingButton);
        searchSortPanel.add(refreshButton);

        viewCartButton = new JButton("View Shopping Cart");
        viewCartButton.addActionListener(actionListener);


        viewStoresButton = new JButton("Customer Dashboard");
        viewStoresButton.addActionListener(actionListener);

        editCustomerButton = new JButton("Edit/Delete User");
        editCustomerButton.addActionListener(actionListener);

        viewHistoryButton = new JButton("Previous Transactions");
        viewHistoryButton.addActionListener(actionListener);

        exportHistoryButton = new JButton("Export Transactions");
        exportHistoryButton.addActionListener(actionListener);

        leaveReviewButton = new JButton("Leave Reviews");
        leaveReviewButton.addActionListener(actionListener);

        logoutCustomer = new JButton("Logout");
        logoutCustomer.addActionListener(actionListener);

        customerOperationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridheight = 1;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;

        customerOperationPanel.setPreferredSize(new Dimension(200, height));
        customerOperationPanel.setMaximumSize(new Dimension(200, height));
        customerOperationPanel.setMinimumSize(new Dimension(200, height));
        gbc.gridy = 0;
        customerOperationPanel.add(viewCartButton, gbc);
        gbc.gridy++;
        customerOperationPanel.add(viewStoresButton, gbc);
        gbc.gridy++;
        customerOperationPanel.add(editCustomerButton, gbc);
        gbc.gridy++;
        customerOperationPanel.add(viewHistoryButton, gbc);
        gbc.gridy++;
        customerOperationPanel.add(exportHistoryButton, gbc);
        gbc.gridy++;
        customerOperationPanel.add(leaveReviewButton, gbc);
        gbc.gridy++;
        customerOperationPanel.add(logoutCustomer, gbc);


        JScrollPane scrollBar = new JScrollPane(productPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel leftEmpty = new JPanel();
        leftEmpty.setPreferredSize(new Dimension(100, height - 50));
        customerPanel.add(leftEmpty, BorderLayout.WEST);

        customerPanel.add(searchSortPanel, BorderLayout.NORTH);
        customerPanel.add(scrollBar, BorderLayout.CENTER);
        customerPanel.add(customerOperationPanel, BorderLayout.EAST);

        customerFrame.setSize(width, height);
        customerFrame.setLocationRelativeTo(null);
        customerFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        customerFrame.setVisible(true);
        customerFrame.add(customerPanel);
    }

    /**
     * creates seller frame
     */
    public void sellerFrame() throws IOException {
        sellerFrame = new JFrame("Marketplace");
        sellerFrame.setLayout(new BorderLayout());
        content = sellerFrame.getContentPane();
        buttonPanel = new JPanel();
        sellerPanel = new JPanel();
        GridBagLayout sellerLayout = new GridBagLayout();
        buttonPanel.setLayout(sellerLayout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridheight = 1;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        sellerPanel.setSize(width - 200, height);
        sellerPanel.setLayout(new BoxLayout(sellerPanel, BoxLayout.Y_AXIS));


        buttonPanel.setPreferredSize(new Dimension(200, height));
        buttonPanel.setMinimumSize(new Dimension(200, height));
        buttonPanel.setMaximumSize(new Dimension(200, height));
        sellerFrame.setSize(width, height);
        sellerFrame.setLocationRelativeTo(null);
        sellerFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        sellerFrame.setVisible(true);

        ArrayList<JButton> rightPanel = new ArrayList<>();
        createButton = new JButton("Create Product");
        createButton.addActionListener(actionListener);
        rightPanel.add(createButton);
        editButton = new JButton("Edit Product");
        editButton.addActionListener(actionListener);
        rightPanel.add(editButton);
        deleteButton = new JButton("Delete Product");
        deleteButton.addActionListener(actionListener);
        rightPanel.add(deleteButton);
        importButton = new JButton("Import Product(s)");
        importButton.addActionListener(actionListener);
        rightPanel.add(importButton);
        exportButton = new JButton("Export Product(s)");
        exportButton.addActionListener(actionListener);
        rightPanel.add(exportButton);
        viewDashboard = new JButton("View Dashboard");
        viewDashboard.addActionListener(actionListener);
        rightPanel.add(viewDashboard);
        viewSales = new JButton("View Sales");
        viewSales.addActionListener(actionListener);
        rightPanel.add(viewSales);
        viewReviews = new JButton("View Reviews");
        viewReviews.addActionListener(actionListener);
        rightPanel.add(viewReviews);
        editUser = new JButton("Edit User");
        editUser.addActionListener(actionListener);
        rightPanel.add(editUser);
        logoutSeller = new JButton("Logout");
        logoutSeller.addActionListener(actionListener);
        rightPanel.add(logoutSeller);
        refreshSellerButton = new JButton("Refresh");
        refreshSellerButton.addActionListener(actionListener);
        rightPanel.add(refreshSellerButton);
        gbc.gridx = 0;
        gbc.gridy = 0;
        for (int i = 0; i < rightPanel.size(); i++) {
            buttonPanel.add(rightPanel.get(i), gbc);
            gbc.gridy = i + 1;
        }


        writer.println("return products");
        writer.flush();
        String line = reader.readLine();
        productList = new ArrayList<>();
        productInfoList = new ArrayList<>();
        while (!line.equals("end")) {
            productList.add(line);
            String[] lineInfo = line.split(",");
            productInfoList.add(lineInfo);
            line = reader.readLine();
        }

        sellerProductPanels = new ArrayList<>();
        sellerProductLabels = new ArrayList<>();

        JPanel topMessage = new JPanel();
        topMessage.setPreferredSize(new Dimension(width - 200, 50));
        topMessage.setMinimumSize(new Dimension(width - 200, 50));
        topMessage.setMaximumSize(new Dimension(width - 200, 50));
        JLabel currentProducts = new JLabel("Current Listings: ");
        currentProducts.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        JPanel empty = new JPanel();
        empty.setPreferredSize(new Dimension(50, 50));
        topMessage.setLayout(new BorderLayout());
        topMessage.add(empty, BorderLayout.WEST);
        topMessage.add(currentProducts, BorderLayout.CENTER);
        sellerProductPanels.add(topMessage);

        for (int i = 0; i < productInfoList.size(); i++) {
            if (productInfoList.get(i)[5].equals(username)) {
                String[] temp = productInfoList.get(i);
                JLabel tempLabel = new JLabel(String.format("Product: %s, Store: %s, Description: %s, " +
                        "Quantity: %s, Price: %s", temp[0], temp[1], temp[2], temp[3], temp[4]));
                sellerProductLabels.add(tempLabel);
                JPanel tempPanel = new JPanel();
                tempPanel.setPreferredSize(new Dimension(width - 200, 50));
                tempPanel.setMinimumSize(new Dimension(width - 200, 50));
                tempPanel.setMaximumSize(new Dimension(width - 200, 50));
                tempPanel.setLayout(new BorderLayout());
                sellerProductPanels.add(tempPanel);
                JPanel emptyLeft = new JPanel();
                emptyLeft.setPreferredSize(new Dimension(50, 50));
                tempPanel.add(emptyLeft, BorderLayout.WEST);
                tempPanel.add(tempLabel, BorderLayout.CENTER);
            }
        }

        for (int i = 0; i < sellerProductPanels.size(); i++) {
            sellerPanel.add(sellerProductPanels.get(i));
        }

        JScrollPane scrollBar = new JScrollPane(sellerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sellerFrame.add(BorderLayout.CENTER, scrollBar);
        sellerFrame.add(buttonPanel, BorderLayout.EAST);
    }

    /**
     *
     */
    public void run() {
        login();
        client();
    }

    /**
     *
     */
    public Client() {
    }

    /**
     * creates dashboard frame for seller
     */
    public void viewDashboardFrame() {
        dashboardFrame = new JFrame("Dashboard");
        dashboardFrame.setLayout(new BorderLayout());

        dashboardButtonPanel = new JPanel();
        totalPurchasesAscending = new JButton("Sort by Total Purchases Ascending");
        totalPurchasesAscending.addActionListener(actionListener);

        totalPurchasesDescending = new JButton("Sort by Total Purchases Descending");
        totalPurchasesDescending.addActionListener(actionListener);

        customerPurchasesAscending = new JButton("Sort by Customer Purchase Ascending");
        customerPurchasesAscending.addActionListener(actionListener);

        customerPurchasesDescending = new JButton("Sort by Customer Purchase Descending");
        customerPurchasesDescending.addActionListener(actionListener);

        sellerDashboardBackButton = new JButton("Back");
        sellerDashboardBackButton.addActionListener(actionListener);

        dashboardButtonPanel.add(sellerDashboardBackButton);
        dashboardButtonPanel.add(totalPurchasesAscending);
        dashboardButtonPanel.add(totalPurchasesDescending);
        dashboardButtonPanel.add(customerPurchasesAscending);
        dashboardButtonPanel.add(customerPurchasesDescending);

        dashboardContent = new JPanel(new GridBagLayout());


        dashboardFrame.add(dashboardButtonPanel, BorderLayout.NORTH);
        dashboardFrame.add(dashboardContent, BorderLayout.CENTER);

        dashboardFrame.setSize(width, height);
        dashboardFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dashboardFrame.setLocationRelativeTo(null);
        dashboardFrame.setVisible(true);
    }

    /**
     * creates store info frame for customer
     */
    public void viewStores() {
        viewStoresFrame = new JFrame("Store Info");
        viewStoresFrame.setLayout(new BorderLayout());

        viewStoresButtonPanel = new JPanel();

        sortStoreSalesAscending = new JButton("Sort stores by sale ascending");
        sortStoreSalesAscending.addActionListener(actionListener);

        sortStoreSalesDescending = new JButton("Sort stores by sale descending");
        sortStoreSalesDescending.addActionListener(actionListener);

        storesPurchasedFrom = new JButton("Stores purchased from");
        storesPurchasedFrom.addActionListener(actionListener);

        viewStoresBackButton = new JButton("Back");
        viewStoresBackButton.addActionListener(actionListener);

        viewStoresButtonPanel.add(viewStoresBackButton);
        viewStoresButtonPanel.add(sortStoreSalesAscending);
        viewStoresButtonPanel.add(sortStoreSalesDescending);
        viewStoresButtonPanel.add(storesPurchasedFrom);

        viewStoresContentPanel = new JPanel(new GridBagLayout());

        viewStoresFrame.add(viewStoresButtonPanel, BorderLayout.NORTH);
        viewStoresFrame.add(viewStoresContentPanel, BorderLayout.CENTER);

        viewStoresFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        viewStoresFrame.setSize(width, height);
        viewStoresFrame.setLocationRelativeTo(null);
        viewStoresFrame.setVisible(true);

    }

    /**
     * updates dashboard to sort by content purchased (ascending)
     *
     * @param ascendingSort String of sorted information
     */
    public void purchaseContentAscending(String ascendingSort) {

        int counter = 0;
        dashboardContent.removeAll();

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;


        for (String part : ascendingSort.split(":")) {

            if (!part.isEmpty()) {
                if (counter % 2 == 0) {
                    JLabel storeName = new JLabel("Store name: " + part);
                    dashboardContent.add(storeName, gridBagConstraints);
                    gridBagConstraints.gridy++;
                    counter++;

                } else if (counter % 2 == 1) {
                    for (String product : part.split(";")) {
                        ArrayList<String> line = new ArrayList<>();
                        for (String parts : product.split(",")) {
                            line.add(parts);
                        }
                        JLabel productName = new JLabel("Product name: " + line.get(0));
                        dashboardContent.add(productName, gridBagConstraints);

                        gridBagConstraints.gridx = 200;
                        JLabel productQuantity = new JLabel("       Product quantity: " + line.get(1));
                        dashboardContent.add(productQuantity, gridBagConstraints);

                        gridBagConstraints.gridy++;
                        gridBagConstraints.gridx = 0;
                    }
                    counter++;
                }

            }
            dashboardFrame.repaint();
            dashboardFrame.setVisible(true);
        }
    }

    /**
     * updates dashboard to sort by content purchased (descending)
     *
     * @param descendingSort String of sorted information
     */
    public void purchaseContentDescending(String descendingSort) {
        int counter = 0;
        dashboardContent.removeAll();

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        for (String part : descendingSort.split(":")) {
            if (part.isEmpty()) {
                // empty
            } else {
                if (counter % 2 == 0) {
                    JLabel storeName = new JLabel("Store name: " + part);
                    dashboardContent.add(storeName, gridBagConstraints);
                    gridBagConstraints.gridy++;
                    counter++;
                } else if (counter % 2 == 1) {
                    for (String product : part.split(";")) {
                        ArrayList<String> line = new ArrayList<>();
                        for (String parts : product.split(",")) {
                            line.add(parts);
                        }
                        JLabel productName = new JLabel("Product name: " + line.get(0));
                        dashboardContent.add(productName, gridBagConstraints);

                        gridBagConstraints.gridx = 200;
                        JLabel productQuantity = new JLabel("       Product quantity: " + line.get(1));
                        dashboardContent.add(productQuantity, gridBagConstraints);

                        gridBagConstraints.gridy++;
                        gridBagConstraints.gridx = 0;

                    }
                    counter++;
                }
                dashboardFrame.repaint();
                dashboardFrame.setVisible(true);
            }
        }
    }

    /**
     * updates dashboard to sort by customer purchases (ascending)
     *
     * @param ascendingSortCustomer LinkedHashMap<String, Integer> of sorted information
     */
    public void customerPurchasesAscending(LinkedHashMap<String, Integer> ascendingSortCustomer) {
        ArrayList<JLabel> labels = new ArrayList<>();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        for (Map.Entry<String, Integer> mapElement : ascendingSortCustomer.entrySet()) {
            JLabel customerName = new JLabel("Customer name: " + mapElement.getKey());
            labels.add(customerName);

            JLabel numOfPurchases = new JLabel("Number of Purchases: " + (mapElement.getValue()));
            labels.add(numOfPurchases);
        }

        dashboardContent.removeAll();

        for (int i = 0; i < labels.size(); i++) {
            dashboardContent.add(labels.get(i), gridBagConstraints);
            gridBagConstraints.gridy++;
        }


        dashboardFrame.repaint();
        dashboardFrame.setVisible(true);
    }

    /**
     * updates dashboard to sort by customer purchases (descending)
     *
     * @param descendingSortCustomer LinkedHashMap<String, Integer> of sorted information
     */
    public void customerPurchasesDescending(LinkedHashMap<String, Integer> descendingSortCustomer) {
        ArrayList<JLabel> labels = new ArrayList<>();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        for (Map.Entry<String, Integer> mapElement : descendingSortCustomer.entrySet()) {

            JLabel customerName = new JLabel("Customer name: " + mapElement.getKey());
            labels.add(customerName);

            JLabel numOfPurchases = new JLabel("Number of Purchases: " + (mapElement.getValue()));
            labels.add(numOfPurchases);

        }

        dashboardContent.removeAll();

        for (int i = 0; i < labels.size(); i++) {
            dashboardContent.add(labels.get(i), gridBagConstraints);
            gridBagConstraints.gridy++;
        }


        dashboardFrame.repaint();
        dashboardFrame.setVisible(true);
    }

    /**
     * updates dashboard to sort by store sales (ascending)
     *
     * @param ascendingSort ArrayList<String> of sorted information
     */
    public void storeSalesAscending(ArrayList<String> ascendingSort) {
        viewStoresContentPanel.removeAll();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        for (int i = 0; i < ascendingSort.size(); i++) {
            JLabel info = new JLabel(ascendingSort.get(i));
            viewStoresContentPanel.add(info, gridBagConstraints);
            gridBagConstraints.gridy++;

        }

        viewStoresContentPanel.repaint();
        viewStoresContentPanel.revalidate();
        viewStoresContentPanel.setVisible(true);
    }

    /**
     * updates dashboard to sort by store sales (descending)
     *
     * @param descendingSort ArrayList<String> of sorted information
     */
    public void storeSalesDescending(ArrayList<String> descendingSort) {
        viewStoresContentPanel.removeAll();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        for (int i = 0; i < descendingSort.size(); i++) {
            JLabel info = new JLabel(descendingSort.get(i));

            viewStoresContentPanel.add(info, gridBagConstraints);
            gridBagConstraints.gridy++;
        }

        viewStoresContentPanel.repaint();
        viewStoresContentPanel.revalidate();
        viewStoresContentPanel.setVisible(true);
    }

    /**
     * creates a list of stores that the customer has purchased from and displays
     * the contents on the viewStoresContentPanel.
     *
     * @param storesPurchasedFromList ArrayList<String> of strings of stores that are purchased from.
     */
    public void storesPurchasedFrom(ArrayList<String> storesPurchasedFromList) {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        if (storesPurchasedFromList.size() == 0) {
            JOptionPane.showMessageDialog(null, "No stores purchased from", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            viewStoresContentPanel.removeAll();
            for (int i = 0; i < storesPurchasedFromList.size(); i++) {

                JLabel info = new JLabel(storesPurchasedFromList.get(i));
                viewStoresContentPanel.add(info, gridBagConstraints);
                gridBagConstraints.gridy++;
            }

            viewStoresContentPanel.repaint();
            viewStoresContentPanel.revalidate();
            viewStoresContentPanel.setVisible(true);
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Client());
    }

    /**
     * takes in an ArrayList of ArrayList of Strings and returns an ArrayList of Product Objects
     *
     * @param products ArrayList<String[]> ArrayList of an Array of Strings
     * @return ArrayList<Product> of Product objects
     */
    public static ArrayList<Product> parseProductList(ArrayList<String[]> products) {
        ArrayList<Product> productList = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            productList.add(new Product(products.get(i)[0], products.get(i)[1], products.get(i)[2],
                    Integer.parseInt(products.get(i)[3]), Double.parseDouble(products.get(i)[4]), products.get(i)[5]));
        }
        return productList;
    }

    /**
     * takes in a String array and creates a Product object
     *
     * @param product String[] name of array
     * @return an object of Product
     */
    public static Product parseProduct(String[] product) {
        return new Product(product[0], product[1], product[2], Integer.parseInt(product[3]),
                Double.parseDouble(product[4]), product[5]);
    }

    /**
     * takes in a String array and creates a Review object
     *
     * @param review String[] name of array
     * @return an object of Reviews
     */
    public static Reviews parseReview(String[] review) {
        return new Reviews(review[0], review[2], review[1], review[3], review[4]);
    }
}
