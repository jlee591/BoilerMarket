import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Project5 -- Server
 *
 * Server side of the project which handles all processing.
 *
 * @author Chaewon Lee, Peter Kang, Marco Zhang, Iddo Mayblum, Joseph Lee, lab sec LC5
 *
 * @version December 11, 2022
 *
 */

public class Server implements Runnable {
    Socket socket;
    private final static Object GATE_KEEPER = new Object();

    public Server(Socket socket) {
        this.socket = socket;
    }


    public static void main(String[] args) {
        try {
            File f = new File("Products.txt");
            if (!f.exists()) {
                f.createNewFile();
                PrintWriter pw = new PrintWriter(f.getAbsolutePath());
                pw.println("engineering,textbooks,ENG 133,2,50.00,seller1@gmail.com");
                pw.println("psychology,textbooks,PSY 120,1,25.00,seller1@gmail.com");
                pw.println("sociology,textbooks,SOC 100,2,10.00,seller1@gmail.com");
                pw.println("calculator,tools,TI-84,1,100.00,seller1@gmail.com");
                pw.println("pencil,tools,#2 pencils,10,1.00,seller1@gmail.com");
                pw.println("highlighter,tools,pastel colors,5,3.00,seller1@gmail.com");
                pw.close();
            }

            f = new File("History.txt");
            if (!f.exists()) {
                f.createNewFile();
                PrintWriter pw = new PrintWriter(f.getAbsolutePath());
                pw.println("rando@gmail.com,highlighter,tools,pastel colors,3,3.00,seller1@gmail.com");
                pw.close();
            }

            f = new File("shoppingcart.txt");
            if (!f.exists()) {
                f.createNewFile();
                PrintWriter pw = new PrintWriter(f.getAbsolutePath());
                pw.println("customer2@gmail.com,mini fridge,appliances,new fridge,2,200.00,seller1@gmail.com");
                pw.println("customer5@gmail.com,cups,appliances,plastic cups,5,5.00,seller1@gmail.com");
                pw.close();
            }

            f = new File("Reviews.txt");
            if (!f.exists()) {
                f.createNewFile();
                PrintWriter pw = new PrintWriter(f.getAbsolutePath());
                pw.println("customer3@gmail.com;tools;pencil;seller1@gmail.com;nice #2 pencils");
                pw.println("customer4@gmail.com;tools;highlighter;seller1@gmail.com;" +
                        "pretty pastel colored highlighters");
                pw.close();
            }

            f = new File("userlogin.txt");
            if (!f.exists()) {
                f.createNewFile();
                PrintWriter pw = new PrintWriter(f.getAbsolutePath());
                pw.println("seller1@gmail.com,password,seller");
                pw.println("customer1@gmail.com,pass,customer");
                pw.println("test,test,customer");
                pw.println("test2,test2,seller");
                pw.close();
            }

            ServerSocket ss = new ServerSocket(4243);
            ArrayList<Server> serverList = new ArrayList<>();

            while (true) {
                Socket s = ss.accept();
                Server server = new Server(s);
                serverList.add(server);
                new Thread(server).start();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            String usernameMain = "";

            while (true) {
                String process = br.readLine();
                if (process == null) {

                } else if (process.equals("return products")) {
                    ArrayList<String> productList = new ArrayList<>();
                    synchronized (GATE_KEEPER) {
                        File f = new File("Products.txt");
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(f.getAbsolutePath()));
                        String line = bufferedReader.readLine();
                        while (line != null) {
                            productList.add(line);
                            line = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                    }

                    for (String product : productList) {
                        pw.println(product);
                    }
                    pw.flush();
                    pw.println("end");
                    pw.flush();
                } else if (process.equals("return history")) {
                    ArrayList<String> historyList = new ArrayList<>();
                    synchronized (GATE_KEEPER) {
                        File f = new File("History.txt");
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(f.getAbsolutePath()));
                        String line = bufferedReader.readLine();
                        while (line != null) {
                            historyList.add(line);
                            line = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                    }
                    for (String product : historyList) {
                        pw.println(product);
                    }
                    pw.flush();
                } else if (process.equals("return shoppingcart")) {
                    String username = br.readLine();
                    ArrayList<String> shoppingList = new ArrayList<>();
                    synchronized (GATE_KEEPER) {
                        File f = new File("shoppingcart.txt");
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(f.getAbsolutePath()));

                        String line = bufferedReader.readLine();
                        while (line != null) {
                            if (line.split(",")[0].equals(username)) {
                                shoppingList.add(line);
                            }
                            line = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                    }

                    for (String product : shoppingList) {
                        pw.println(product);
                    }
                    pw.flush();
                    pw.println("end");
                    pw.flush();
                } else if (process.equals("sign in")) {
                    File f = new File("userlogin.txt");
                    ArrayList<String> userLoginList = new ArrayList<>();
                    synchronized (GATE_KEEPER) {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(f.getAbsolutePath()));
                        String line = bufferedReader.readLine();
                        while (line != null) {
                            userLoginList.add(line);
                            line = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                    }
                    usernameMain = br.readLine();
                    String password = br.readLine();
                    ArrayList<String> loginReturn = userLogin(usernameMain, password, userLoginList);
                    for (String login : loginReturn) {
                        pw.println(login);
                    }
                    pw.flush();
                } else if (process.equals("return reviews")) {
                    String productName = br.readLine();
                    String storeName = br.readLine();
                    ArrayList<String> reviewsList = new ArrayList<>();
                    synchronized (GATE_KEEPER) {
                        File f = new File("Reviews.txt");
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(f.getAbsolutePath()));
                        String line = bufferedReader.readLine();
                        while (line != null) {
                            if (line.split(";")[2].equals(productName) &&
                                    line.split(";")[1].equals(storeName)) {
                                reviewsList.add(line);
                            }
                            line = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                    }

                    for (int i = 0; i < reviewsList.size(); i++) {
                        pw.println(reviewsList.get(i));
                    }
                    pw.flush();
                    pw.println("end");
                    pw.flush();
                } else if (process.equals("new account")) {
                    ArrayList<String> userLoginList = new ArrayList<>();
                    synchronized (GATE_KEEPER) {
                        File f = new File("userlogin.txt");
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(f.getAbsolutePath()));
                        String line = bufferedReader.readLine();
                        while (line != null) {
                            userLoginList.add(line);
                            line = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                    }
                    String username = br.readLine();
                    String password = br.readLine();
                    String userTypeString = br.readLine();
                    int userType;
                    try {
                        userType = Integer.parseInt(userTypeString);
                        createAccount(username, password, userType, userLoginList);
                        pw.println("true");
                        pw.flush();
                    } catch (Exception e) {
                        pw.println("false");
                        pw.flush();
                    }
                } else if (process.equals("create product")) {
                    ArrayList<String[]> productList = new ArrayList<>();
                    synchronized (GATE_KEEPER) {
                        File f = new File("Products.txt");
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(f.getAbsolutePath()));

                        String line = bufferedReader.readLine();
                        while (line != null) {
                            productList.add(line.split(","));
                            line = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                    }
                    String product = br.readLine();
                    String store = br.readLine();
                    String description = br.readLine();
                    String quantity = br.readLine();
                    String price = br.readLine();
                    String seller = br.readLine();
                    boolean exists = false;
                    for (int i = 0; i < productList.size(); i++) {
                        if (productList.get(i)[0].equals(product) && productList.get(i)[1].equals(store)) {
                            exists = true;
                            pw.println("false");
                            pw.flush();
                        }
                    }
                    if (!exists) {
                        int quantityNum = Integer.parseInt(quantity);
                        double priceNum = Double.parseDouble(price);
                        String printString = String.format("%s,%s,%s,%d,%.2f,%s", product, store, description,
                                quantityNum, priceNum, seller);
                        synchronized (GATE_KEEPER) {
                            File f = new File("Products.txt");
                            BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsolutePath(), true));
                            bw.write(printString);
                            bw.close();
                        }
                        pw.println("true");
                        pw.flush();
                    }
                } else if (process.equals("edit product")) {
                    String productName = br.readLine();
                    String storeName = br.readLine();
                    String option = br.readLine();
                    String editedOption = br.readLine();
                    ArrayList<String[]> productList = new ArrayList<>();
                    if (!editedOption.contains(",")) {
                        synchronized (GATE_KEEPER) {
                            File f = new File("Products.txt");
                            BufferedReader bufferedReader = new BufferedReader(new FileReader(f.getAbsolutePath()));
                            String line = bufferedReader.readLine();
                            while (line != null) {
                                productList.add(line.split(","));
                                line = bufferedReader.readLine();
                            }
                            bufferedReader.close();
                        }

                        String[] editedProduct = new String[6];
                        int editedProductIndex = -1;
                        for (int i = 0; i < productList.size(); i++) {
                            if (productList.get(i)[0].equals(productName) && productList.get(i)[1].equals(storeName)) {
                                editedProduct = productList.get(i);
                                editedProductIndex = i;
                            }
                        }
                        boolean returnFalse = false;
                        if (option.equals("Edit product name")) {
                            for (int i = 0; i < productList.size(); i++) {
                                if (productList.get(i)[0].equals(editedOption) &&
                                        productList.get(i)[1].equals(editedProduct[1])) {
                                    pw.println("false");
                                    pw.flush();
                                    returnFalse = true;
                                }
                            }
                            if (!returnFalse) {
                                editedProduct[0] = editedOption;
                            }
                        } else if (option.equals("Edit store name")) {
                            for (int i = 0; i < productList.size(); i++) {
                                if (productList.get(i)[1].equals(editedOption) &&
                                        productList.get(i)[0].equals(editedProduct[0])) {
                                    pw.println("false");
                                    pw.flush();
                                    returnFalse = true;
                                }
                            }
                            if (!returnFalse) {
                                editedProduct[1] = editedOption;
                            }
                        } else if (option.equals("Edit product description")) {
                            editedProduct[2] = editedOption;
                        } else if (option.equals("Edit quantity available")) {
                            String quantityNum = String.format("%d", Integer.parseInt(editedOption));
                            editedProduct[3] = quantityNum;
                        } else if (option.equals("Edit price")) {
                            String priceNum = String.format("%.2f", Double.parseDouble(editedOption));
                            editedProduct[4] = priceNum;
                        }
                        try {
                            if (editedProductIndex != -1) {
                                productList.set(editedProductIndex, editedProduct);
                            }
                            synchronized (GATE_KEEPER) {
                                File f = new File("Products.txt");
                                BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsolutePath(), false));
                                for (int i = 0; i < productList.size(); i++) {
                                    String write = String.format("%s,%s,%s,%s,%s,%s", productList.get(i)[0],
                                            productList.get(i)[1], productList.get(i)[2], productList.get(i)[3],
                                            productList.get(i)[4], productList.get(i)[5]);
                                    bw.write(write);
                                    bw.newLine();
                                }
                                bw.close();
                            }
                            pw.println("true");
                            pw.flush();
                        } catch (NumberFormatException e) {
                            pw.println("false");
                            pw.flush();
                        }
                    } else {
                        pw.println("false");
                        pw.flush();
                    }
                } else if (process.equals("import products")) {
                    String username = br.readLine();
                    String line = br.readLine();

                    ArrayList<String> newProducts = new ArrayList<>();
                    boolean badImport = false;
                    while (!line.equals("end")) {
                        if (line.split(",").length != 5) {
                            badImport = true;
                            break;
                        }
                        newProducts.add(line);
                        line = br.readLine();
                    }

                    ArrayList<String> products = new ArrayList<>();
                    synchronized (GATE_KEEPER) {
                        File f = new File("Products.txt");
                        BufferedReader bfr = new BufferedReader(new FileReader(f.getAbsolutePath()));

                        line = bfr.readLine();
                        while (line != null) {
                            products.add(line);
                            line = bfr.readLine();
                        }
                        bfr.close();
                    }


                    for (String product : products) {
                        if (!badImport) {
                            String[] temp = product.split(",");
                            for (String check : newProducts) {
                                String[] temp2 = check.split(",");
                                if (temp[0].equals(temp2[0]) && temp[1].equals(temp2[1])) {
                                    badImport = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (badImport) {
                        pw.println("false");
                        pw.flush();
                    } else {
                        synchronized (GATE_KEEPER) {
                            File f = new File("Products.txt");
                            BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsolutePath(), true));
                            for (int i = 0; i < newProducts.size(); i++) {
                                bw.write(newProducts.get(i) + "," + username);
                                bw.newLine();
                            }
                            bw.close();
                        }

                        pw.println("true");
                        pw.flush();
                    }
                } else if (process.equals("delete product")) {
                    String productName = br.readLine();
                    String storeName = br.readLine();

                    ArrayList<String[]> productList = new ArrayList<>();
                    synchronized (GATE_KEEPER) {
                        File f = new File("Products.txt");
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(f.getAbsolutePath()));
                        String line = bufferedReader.readLine();
                        while (line != null) {
                            productList.add(line.split(","));
                            line = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                    }

                    for (int i = 0; i < productList.size(); i++) {
                        if (productList.get(i)[0].equals(productName) && productList.get(i)[1].equals(storeName)) {
                            productList.remove(i);
                            break;
                        }
                    }

                    synchronized (GATE_KEEPER) {
                        File f = new File("Products.txt");
                        BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsolutePath(), false));
                        for (int i = 0; i < productList.size(); i++) {
                            String write = String.format("%s,%s,%s,%s,%s,%s", productList.get(i)[0],
                                    productList.get(i)[1], productList.get(i)[2], productList.get(i)[3],
                                    productList.get(i)[4], productList.get(i)[5]);
                            bw.write(write);
                            bw.newLine();
                        }
                        bw.close();
                    }

                    pw.println("true");
                    pw.flush();
                } else if (process.equals("view seller reviews")) {
                    String username = br.readLine();
                    ArrayList<String> list = new ArrayList<>();

                    synchronized (GATE_KEEPER) {
                        File f = new File("Reviews.txt");
                        BufferedReader bfr = new BufferedReader(new FileReader(f.getAbsolutePath()));
                        String line = bfr.readLine();
                        while (line != null) {
                            String[] temp = line.split(";");
                            if (temp[3].equals(username)) {
                                list.add(line);
                            }
                            line = bfr.readLine();
                        }
                        bfr.close();
                    }

                    if (list.size() == 0) {
                        pw.println("false");
                        pw.flush();
                    } else {
                        pw.println("true");
                        for (String st : list) {
                            String[] listReview = st.split(";");
                            pw.println(listReview[2] + ": " + listReview[4]);
                        }
                        pw.println("end");
                        pw.flush();
                    }
                } else if (process.equals("add to cart")) {
                    String username = br.readLine();
                    String productName = br.readLine();
                    String storeName = br.readLine();
                    String description = br.readLine();
                    String quantity = br.readLine();
                    String price = br.readLine();
                    String seller = br.readLine();

                    ArrayList<String[]> cart = new ArrayList<>();

                    synchronized (GATE_KEEPER) {
                        File f = new File("shoppingcart.txt");
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(f.getAbsolutePath()));
                        String line = bufferedReader.readLine();
                        while (line != null) {
                            cart.add(line.split(","));
                            line = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                    }

                    boolean exists = false;
                    int existingProduct = 0;
                    for (int i = 0; i < cart.size(); i++) {
                        if (cart.get(i)[0].equals(username) && cart.get(i)[1].equals(productName) &&
                                cart.get(i)[2].equals(storeName)) {
                            exists = true;
                            existingProduct = i;
                            break;
                        }
                    }

                    ArrayList<String[]> products = new ArrayList<>();
                    synchronized (GATE_KEEPER) {
                        File f2 = new File("Products.txt");
                        BufferedReader br1 = new BufferedReader(new FileReader(f2.getAbsolutePath()));

                        String line = br1.readLine();
                        while (line != null) {
                            if (line.split(",")[0].equals(productName) &&
                                    line.split(",")[1].equals(storeName)) {
                                String[] product = line.split(",");
                                if (Integer.parseInt(product[3]) - Integer.parseInt(quantity) != 0) {
                                    product[3] = String.format("%d",
                                            Integer.parseInt(product[3]) - Integer.parseInt(quantity));
                                    products.add(product);
                                }
                            } else {
                                products.add(line.split(","));
                            }
                            line = br1.readLine();
                        }
                    }

                    synchronized (GATE_KEEPER) {
                        File f2 = new File("Products.txt");
                        BufferedWriter bw1 = new BufferedWriter(new FileWriter(f2.getAbsolutePath(), false));
                        for (int i = 0; i < products.size(); i++) {
                            for (int j = 0; j < products.get(i).length; j++) {
                                bw1.write(products.get(i)[j]);
                                if (j < products.get(i).length - 1) {
                                    bw1.write(",");
                                }
                            }
                            bw1.newLine();
                        }
                        bw1.close();
                    }

                    if (exists) {
                        int newQuantity = Integer.parseInt(quantity) + Integer.parseInt(cart.get(existingProduct)[4]);
                        quantity = Integer.toString(newQuantity);
                        String[] updatedProduct = new String[7];
                        updatedProduct[0] = username;
                        updatedProduct[1] = productName;
                        updatedProduct[2] = storeName;
                        updatedProduct[3] = description;
                        updatedProduct[4] = quantity;
                        updatedProduct[5] = price;
                        updatedProduct[6] = seller;
                        cart.set(existingProduct, updatedProduct);

                        synchronized (GATE_KEEPER) {
                            File f = new File("shoppingcart.txt");
                            BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsolutePath(), false));
                            for (int i = 0; i < cart.size(); i++) {
                                for (int j = 0; j < cart.get(i).length; j++) {
                                    bw.write(cart.get(i)[j]);
                                    if (j < cart.get(i).length - 1) {
                                        bw.write(",");
                                    }
                                }
                                bw.newLine();
                            }
                            bw.close();
                        }

                        pw.println("true");
                        pw.flush();
                    } else {
                        synchronized (GATE_KEEPER) {
                            File f = new File("shoppingcart.txt");
                            BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsolutePath(), true));
                            String write = String.format("%s,%s,%s,%s,%d,%.2f,%s", username, productName, storeName,
                                    description, Integer.parseInt(quantity), Double.parseDouble(price), seller);
                            bw.write(write);
                            bw.newLine();
                            bw.close();
                        }

                        pw.println("true");
                        pw.flush();
                    }
                } else if (process.equals("view sales")) {
                    String username = br.readLine();
                    String option = br.readLine();
                    if (option.equals("Store Sales")) {
                        String store = br.readLine();
                        ArrayList<String> history = new ArrayList<>();

                        synchronized (GATE_KEEPER) {
                            File f = new File("History.txt");
                            FileReader fr = new FileReader(f.getAbsolutePath());
                            BufferedReader bfr = new BufferedReader(fr);
                            String line = bfr.readLine();
                            while (line != null) {
                                String[] temp = line.split(",");
                                if (temp[2].equals(store) && temp[6].equals(username)) {
                                    history.add(line);
                                }
                                line = bfr.readLine();
                            }
                            bfr.close();
                        }

                        if (history.size() == 0) {
                            pw.println("false");
                            pw.flush();
                        } else {
                            pw.println("true");
                            for (String h : history) {
                                String[] temp = h.split(",");
                                pw.printf("Customer: %s; Product bought: %s; Revenue from sale: %.2f\n",
                                        temp[0], temp[1], Integer.parseInt(temp[4]) * Double.parseDouble(temp[5]));
                            }
                            pw.println("end");
                            pw.flush();
                        }
                    } else if (option.equals("Current Customer Carts")) {
                        ArrayList<String> contents = new ArrayList<>();

                        synchronized (GATE_KEEPER) {
                            File f = new File("shoppingcart.txt");
                            FileReader fr = new FileReader(f.getAbsolutePath());
                            BufferedReader bfr = new BufferedReader(fr);
                            String line = bfr.readLine();
                            while (line != null) {
                                String[] temp = line.split(",");
                                if (temp[6].equals(username)) {
                                    contents.add(line);
                                }
                                line = bfr.readLine();
                            }
                            bfr.close();
                        }

                        if (contents.size() == 0) {
                            pw.println("false");
                            pw.flush();
                        } else {
                            pw.println("true");
                            for (String c : contents) {
                                String[] temp = c.split(",");
                                pw.printf("Product: %s, Store: %s, Quantity: %d\n",
                                        temp[1], temp[2], Integer.parseInt(temp[4]));
                            }
                            pw.println("end");
                            pw.flush();
                        }
                    }
                } else if (process.equals("edit user")) {
                    String username = br.readLine();
                    String password = br.readLine();
                    String option = br.readLine();

                    if (option.equals("Edit Account Details")) {
                        String newUsername = br.readLine();
                        String newPassword = br.readLine();

                        ArrayList<String> products = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f = new File("Products.txt");
                            BufferedReader bfr = new BufferedReader(new FileReader(f.getAbsolutePath()));
                            String line = bfr.readLine();
                            while (line != null) {
                                products.add(line);
                                line = bfr.readLine();
                            }
                            bfr.close();
                        }

                        File f = new File("Products.txt");
                        BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsolutePath(), false));
                        for (int i = 0; i < products.size(); i++) {
                            String[] temp = products.get(i).split(",");
                            if (temp[5].equals(username)) {
                                temp[5] = newUsername;
                                String write = String.join(",", temp);
                                bw.write(write);
                                bw.newLine();
                            } else {
                                bw.write(products.get(i));
                                bw.newLine();
                            }
                        }
                        bw.close();


                        ArrayList<String> history = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f2 = new File("History.txt");
                            BufferedReader bfr2 = new BufferedReader(new FileReader(f2.getAbsolutePath()));
                            String line = bfr2.readLine();
                            while (line != null) {
                                history.add(line);
                                line = bfr2.readLine();
                            }
                            bfr2.close();
                        }

                        synchronized (GATE_KEEPER) {
                            File f2 = new File("History.txt");
                            BufferedWriter bw2 = new BufferedWriter(new FileWriter(f2.getAbsolutePath(), false));
                            for (int i = 0; i < history.size(); i++) {
                                String[] temp = history.get(i).split(",");
                                if (temp[6].equals(username)) {
                                    temp[6] = newUsername;
                                    String write = String.join(",", temp);
                                    bw2.write(write);
                                    bw2.newLine();
                                } else {
                                    bw2.write(history.get(i));
                                    bw2.newLine();
                                }
                            }
                            bw2.close();
                        }


                        ArrayList<String> reviews = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f3 = new File("Reviews.txt");
                            BufferedReader bfr3 = new BufferedReader(new FileReader(f3.getAbsolutePath()));

                            String line = bfr3.readLine();
                            while (line != null) {
                                reviews.add(line);
                                line = bfr3.readLine();
                            }
                            bfr3.close();
                        }

                        synchronized (GATE_KEEPER) {
                            File f3 = new File("Reviews.txt");
                            BufferedWriter bw3 = new BufferedWriter(new FileWriter(f3.getAbsolutePath(), false));
                            for (int i = 0; i < reviews.size(); i++) {
                                String[] temp = reviews.get(i).split(";");
                                if (temp[3].equals(username)) {
                                    temp[3] = newUsername;
                                    String write = String.join(";", temp);
                                    bw3.write(write);
                                    bw3.newLine();
                                } else {
                                    bw3.write(reviews.get(i));
                                    bw3.newLine();
                                }
                            }
                            bw3.close();
                        }


                        ArrayList<String> cart = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f4 = new File("shoppingcart.txt");
                            BufferedReader bfr4 = new BufferedReader(new FileReader(f4.getAbsolutePath()));
                            String line = bfr4.readLine();
                            while (line != null) {
                                cart.add(line);
                                line = bfr4.readLine();
                            }
                            bfr4.close();
                        }

                        synchronized (GATE_KEEPER) {
                            File f4 = new File("shoppingcart.txt");
                            BufferedWriter bw4 = new BufferedWriter(new FileWriter(f4.getAbsolutePath(), false));
                            for (int i = 0; i < cart.size(); i++) {
                                String[] temp = cart.get(i).split(",");
                                if (temp[6].equals(username)) {
                                    temp[6] = newUsername;
                                    String write = String.join(",", temp);
                                    bw4.write(write);
                                    bw4.newLine();
                                } else {
                                    bw4.write(cart.get(i));
                                    bw4.newLine();
                                }
                            }
                            bw4.close();
                        }


                        ArrayList<String> login = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f5 = new File("userlogin.txt");
                            BufferedReader bfr5 = new BufferedReader(new FileReader(f5.getAbsolutePath()));
                            String line = bfr5.readLine();
                            while (line != null) {
                                login.add(line);
                                line = bfr5.readLine();
                            }
                            bfr5.close();
                        }

                        synchronized (GATE_KEEPER) {
                            File f5 = new File("userlogin.txt");
                            BufferedWriter bw5 = new BufferedWriter(new FileWriter(f5.getAbsolutePath(), false));
                            for (int i = 0; i < login.size(); i++) {
                                String[] temp = login.get(i).split(",");
                                if (temp[0].equals(username) && temp[1].equals(password)) {
                                    temp[0] = newUsername;
                                    temp[1] = newPassword;
                                    String write = String.join(",", temp);
                                    bw5.write(write);
                                    bw5.newLine();
                                } else {
                                    bw5.write(login.get(i));
                                    bw5.newLine();
                                }
                            }
                            bw5.close();
                        }
                        pw.println("true");
                        pw.flush();

                    } else if (option.equals("Delete Account")) {
                        ArrayList<String> products = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f = new File("Products.txt");
                            BufferedReader bfr = new BufferedReader(new FileReader(f.getAbsolutePath()));
                            String line = bfr.readLine();
                            while (line != null) {
                                products.add(line);
                                line = bfr.readLine();
                            }
                            bfr.close();
                        }

                        synchronized (GATE_KEEPER) {
                            File f = new File("Products.txt");
                            BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsolutePath(), false));
                            for (int i = 0; i < products.size(); i++) {
                                String[] temp = products.get(i).split(",");
                                if (!temp[5].equals(username)) {
                                    String write = products.get(i);
                                    bw.write(write);
                                    bw.newLine();
                                }
                            }
                            bw.close();
                        }


                        ArrayList<String> history = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f2 = new File("History.txt");
                            BufferedReader bfr2 = new BufferedReader(new FileReader(f2.getAbsolutePath()));
                            String line = bfr2.readLine();
                            while (line != null) {
                                history.add(line);
                                line = bfr2.readLine();
                            }
                            bfr2.close();
                        }

                        synchronized (GATE_KEEPER) {
                            File f2 = new File("History.txt");
                            BufferedWriter bw2 = new BufferedWriter(new FileWriter(f2.getAbsolutePath(), false));
                            for (int i = 0; i < history.size(); i++) {
                                String[] temp = history.get(i).split(",");
                                if (!temp[6].equals(username)) {
                                    String write = history.get(i);
                                    bw2.write(write);
                                    bw2.newLine();
                                }
                            }
                            bw2.close();
                        }


                        ArrayList<String> reviews = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f3 = new File("Reviews.txt");
                            BufferedReader bfr3 = new BufferedReader(new FileReader(f3.getAbsolutePath()));
                            String line = bfr3.readLine();
                            while (line != null) {
                                reviews.add(line);
                                line = bfr3.readLine();
                            }
                            bfr3.close();
                        }

                        synchronized (GATE_KEEPER) {
                            File f3 = new File("Reviews.txt");
                            BufferedWriter bw3 = new BufferedWriter(new FileWriter(f3.getAbsolutePath(), false));
                            for (int i = 0; i < reviews.size(); i++) {
                                String[] temp = reviews.get(i).split(";");
                                if (!temp[3].equals(username)) {
                                    String write = reviews.get(i);
                                    bw3.write(write);
                                    bw3.newLine();
                                }
                            }
                            bw3.close();
                        }


                        ArrayList<String> cart = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f4 = new File("shoppingcart.txt");
                            BufferedReader bfr4 = new BufferedReader(new FileReader(f4.getAbsolutePath()));
                            String line = bfr4.readLine();
                            while (line != null) {
                                cart.add(line);
                                line = bfr4.readLine();
                            }
                            bfr4.close();
                        }

                        synchronized (GATE_KEEPER) {
                            File f4 = new File("shoppingcart.txt");
                            BufferedWriter bw4 = new BufferedWriter(new FileWriter(f4.getAbsolutePath(), false));
                            for (int i = 0; i < cart.size(); i++) {
                                String[] temp = cart.get(i).split(",");
                                if (!temp[6].equals(username)) {
                                    String write = cart.get(i);
                                    bw4.write(write);
                                    bw4.newLine();
                                }
                            }
                            bw4.close();
                        }


                        ArrayList<String> login = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f5 = new File("userlogin.txt");
                            BufferedReader bfr5 = new BufferedReader(new FileReader(f5.getAbsolutePath()));
                            String line = bfr5.readLine();
                            while (line != null) {
                                login.add(line);
                                line = bfr5.readLine();
                            }
                            bfr5.close();
                        }

                        synchronized (GATE_KEEPER) {
                            File f5 = new File("userlogin.txt");
                            BufferedWriter bw5 = new BufferedWriter(new FileWriter(f5.getAbsolutePath(), false));
                            for (int i = 0; i < login.size(); i++) {
                                String[] temp = login.get(i).split(",");
                                if (!temp[0].equals(username)) {
                                    String write = login.get(i);
                                    bw5.write(write);
                                    bw5.newLine();
                                }
                            }
                            bw5.close();
                        }

                        pw.println("true");
                        pw.flush();
                    }
                } else if (process.equals("purchases ascending")) {
                    String ascendingSort = SellerStatistics.ascendingSort(usernameMain);
                    pw.println(ascendingSort);
                    pw.flush();
                } else if (process.equals("purchase descending")) {
                    String descendingSort = SellerStatistics.descendingSort(usernameMain);
                    pw.println(descendingSort);
                    pw.flush();
                } else if (process.equals("customer ascending")) {
                    LinkedHashMap<String, Integer> custAscending = SellerStatistics.ascendingSortCust(usernameMain);
                    oos.writeObject(custAscending);
                    oos.flush();
                } else if (process.equals("customer descending")) {
                    LinkedHashMap<String, Integer> custDescending = SellerStatistics.descendingSortCust(usernameMain);
                    oos.writeObject(custDescending);
                    oos.flush();
                } else if (process.equals("sort store sales ascending")) {
                    ArrayList<String> ascendingSort = StatisticsCustomer.ascendingSales();
                    oos.writeObject(ascendingSort);
                    oos.flush();
                } else if (process.equals("sort store sales descending")) {
                    ArrayList<String> descendingSort = StatisticsCustomer.descendingSales();
                    oos.writeObject(descendingSort);
                    oos.flush();
                } else if (process.equals("stores purchased from")) {
                    ArrayList<String> storesPurchasedFrom = StatisticsCustomer.storePurchasedFrom(usernameMain);
                    oos.writeObject(storesPurchasedFrom);
                    oos.flush();
                } else if (process.equals("edit customer")) {
                    String username = br.readLine();
                    String password = br.readLine();
                    String option = br.readLine();

                    if (option.equals("Edit Account Details")) {
                        String newUsername = br.readLine();
                        String newPassword = br.readLine();

                        ArrayList<String> history = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f2 = new File("History.txt");
                            BufferedReader bfr2 = new BufferedReader(new FileReader(f2.getAbsolutePath()));
                            String line = bfr2.readLine();
                            while (line != null) {
                                history.add(line);
                                line = bfr2.readLine();
                            }
                            bfr2.close();
                        }

                        synchronized (GATE_KEEPER) {
                            File f2 = new File("History.txt");
                            BufferedWriter bw2 = new BufferedWriter(new FileWriter(f2.getAbsolutePath(), false));
                            for (int i = 0; i < history.size(); i++) {
                                String[] temp = history.get(i).split(",");
                                if (temp[0].equals(username)) {
                                    temp[0] = newUsername;
                                    String write = String.join(",", temp);
                                    bw2.write(write);
                                    bw2.newLine();
                                } else {
                                    bw2.write(history.get(i));
                                    bw2.newLine();
                                }
                            }
                            bw2.close();
                        }


                        ArrayList<String> reviews = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f3 = new File("Reviews.txt");
                            BufferedReader bfr3 = new BufferedReader(new FileReader(f3.getAbsolutePath()));
                            String line = bfr3.readLine();
                            while (line != null) {
                                reviews.add(line);
                                line = bfr3.readLine();
                            }
                            bfr3.close();
                        }

                        synchronized (GATE_KEEPER) {
                            File f3 = new File("Reviews.txt");
                            BufferedWriter bw3 = new BufferedWriter(new FileWriter(f3.getAbsolutePath(), false));
                            for (int i = 0; i < reviews.size(); i++) {
                                String[] temp = reviews.get(i).split(";");
                                if (temp[0].equals(username)) {
                                    temp[0] = newUsername;
                                    String write = String.join(";", temp);
                                    bw3.write(write);
                                    bw3.newLine();
                                } else {
                                    bw3.write(reviews.get(i));
                                    bw3.newLine();
                                }
                            }
                            bw3.close();
                        }


                        ArrayList<String> cart = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f4 = new File("shoppingcart.txt");
                            BufferedReader bfr4 = new BufferedReader(new FileReader(f4.getAbsolutePath()));
                            String line = bfr4.readLine();
                            while (line != null) {
                                cart.add(line);
                                line = bfr4.readLine();
                            }
                            bfr4.close();
                        }

                        synchronized (GATE_KEEPER) {
                            File f4 = new File("shoppingcart.txt");
                            BufferedWriter bw4 = new BufferedWriter(new FileWriter(f4.getAbsolutePath(), false));
                            for (int i = 0; i < cart.size(); i++) {
                                String[] temp = cart.get(i).split(",");
                                if (temp[0].equals(username)) {
                                    temp[0] = newUsername;
                                    String write = String.join(",", temp);
                                    bw4.write(write);
                                    bw4.newLine();
                                } else {
                                    bw4.write(cart.get(i));
                                    bw4.newLine();
                                }
                            }
                            bw4.close();
                        }


                        ArrayList<String> login = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f5 = new File("userlogin.txt");
                            BufferedReader bfr5 = new BufferedReader(new FileReader(f5.getAbsolutePath()));
                            String line = bfr5.readLine();
                            while (line != null) {
                                login.add(line);
                                line = bfr5.readLine();
                            }
                            bfr5.close();
                        }

                        synchronized (GATE_KEEPER) {
                            File f5 = new File("userlogin.txt");
                            BufferedWriter bw5 = new BufferedWriter(new FileWriter(f5.getAbsolutePath(), false));
                            for (int i = 0; i < login.size(); i++) {
                                String[] temp = login.get(i).split(",");
                                if (temp[0].equals(username) && temp[1].equals(password)) {
                                    temp[0] = newUsername;
                                    temp[1] = newPassword;
                                    String write = String.join(",", temp);
                                    bw5.write(write);
                                    bw5.newLine();
                                } else {
                                    bw5.write(login.get(i));
                                    bw5.newLine();
                                }
                            }
                            bw5.close();
                        }

                        pw.println("true");
                        pw.flush();
                    } else if (option.equals("Delete Account")) {
                        ArrayList<String> history = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f2 = new File("History.txt");
                            BufferedReader bfr2 = new BufferedReader(new FileReader(f2.getAbsolutePath()));
                            String line = bfr2.readLine();
                            while (line != null) {
                                history.add(line);
                                line = bfr2.readLine();
                            }
                            bfr2.close();
                        }

                        synchronized (GATE_KEEPER) {
                            File f2 = new File("History.txt");
                            BufferedWriter bw2 = new BufferedWriter(new FileWriter(f2.getAbsolutePath(), false));
                            for (int i = 0; i < history.size(); i++) {
                                String[] temp = history.get(i).split(",");
                                if (!temp[0].equals(username)) {
                                    String write = history.get(i);
                                    bw2.write(write);
                                    bw2.newLine();
                                }
                            }
                            bw2.close();
                        }


                        ArrayList<String> reviews = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f3 = new File("Reviews.txt");
                            BufferedReader bfr3 = new BufferedReader(new FileReader(f3.getAbsolutePath()));
                            String line = bfr3.readLine();
                            while (line != null) {
                                reviews.add(line);
                                line = bfr3.readLine();
                            }
                            bfr3.close();
                        }

                        synchronized (GATE_KEEPER) {
                            File f3 = new File("Reviews.txt");
                            BufferedWriter bw3 = new BufferedWriter(new FileWriter(f3.getAbsolutePath(), false));
                            for (int i = 0; i < reviews.size(); i++) {
                                String[] temp = reviews.get(i).split(";");
                                if (!temp[0].equals(username)) {
                                    String write = reviews.get(i);
                                    bw3.write(write);
                                    bw3.newLine();
                                }
                            }
                            bw3.close();
                        }


                        ArrayList<String> cart = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f4 = new File("shoppingcart.txt");
                            BufferedReader bfr4 = new BufferedReader(new FileReader(f4.getAbsolutePath()));
                            String line = bfr4.readLine();
                            while (line != null) {
                                cart.add(line);
                                line = bfr4.readLine();
                            }
                            bfr4.close();
                        }

                        ArrayList<String> change = new ArrayList<>();
                        ArrayList<String> toBeAdded = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f4 = new File("shoppingcart.txt");
                            BufferedWriter bw4 = new BufferedWriter(new FileWriter(f4.getAbsolutePath(), false));
                            for (int i = 0; i < cart.size(); i++) {
                                String[] temp = cart.get(i).split(",");
                                if (!temp[0].equals(username)) {
                                    String write = cart.get(i);
                                    bw4.write(write);
                                    bw4.newLine();
                                } else {
                                    String write = cart.get(i).substring(cart.get(i).indexOf(",") + 1);
                                    change.add(write);
                                    toBeAdded.add(write);
                                }
                            }
                            bw4.close();
                        }

                        ArrayList<String> products = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f = new File("Products.txt");
                            BufferedReader bfr = new BufferedReader(new FileReader(f.getAbsolutePath()));
                            String line = bfr.readLine();
                            while (line != null) {
                                products.add(line);
                                line = bfr.readLine();
                            }
                            bfr.close();
                        }

                        for (int i = 0; i < products.size(); i++) {
                            String[] temp = products.get(i).split(",");
                            for (int j = 0; j < change.size(); j++) {
                                String[] temp2 = change.get(j).split(",");
                                if (temp[5].equals(temp2[5]) && temp[1].equals(temp2[1]) && temp[0].equals(temp[0])) {
                                    int add = Integer.parseInt(temp2[3]);
                                    temp[3] = String.valueOf(Integer.parseInt(temp[3]) + add);
                                    products.set(i, String.join(",", temp));
                                    toBeAdded.remove(change.get(j));
                                }
                            }
                        }
                        for (String add : toBeAdded) {
                            products.add(add);
                        }

                        synchronized (GATE_KEEPER) {
                            File f = new File("Products.txt");
                            BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsolutePath(), false));
                            for (int i = 0; i < products.size(); i++) {
                                bw.write(products.get(i));
                                bw.newLine();
                            }
                            bw.close();
                        }


                        ArrayList<String> login = new ArrayList<>();
                        synchronized (GATE_KEEPER) {
                            File f5 = new File("userlogin.txt");
                            BufferedReader bfr5 = new BufferedReader(new FileReader(f5.getAbsolutePath()));
                            String line = bfr5.readLine();
                            while (line != null) {
                                login.add(line);
                                line = bfr5.readLine();
                            }
                            bfr5.close();
                        }

                        synchronized (GATE_KEEPER) {
                            File f5 = new File("userlogin.txt");
                            BufferedWriter bw5 = new BufferedWriter(new FileWriter(f5.getAbsolutePath(), false));
                            for (int i = 0; i < login.size(); i++) {
                                String[] temp = login.get(i).split(",");
                                if (!temp[0].equals(username)) {
                                    String write = login.get(i);
                                    bw5.write(write);
                                    bw5.newLine();
                                }
                            }
                            bw5.close();
                        }

                        pw.println("true");
                        pw.flush();
                    }
                } else if (process.equals("remove from cart")) {
                    String username = br.readLine();
                    String productName = br.readLine();
                    String storeName = br.readLine();

                    ArrayList<String> updatedCart = new ArrayList<>();
                    String returnProducts = "";
                    synchronized (GATE_KEEPER) {
                        File f = new File("shoppingcart.txt");
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(f.getAbsolutePath()));
                        String line = bufferedReader.readLine();

                        while (line != null) {
                            if (line.split(",")[0].equals(username)
                                    && line.split(",")[1].equals(productName)
                                    && line.split(",")[2].equals(storeName)) {
                                returnProducts = line;
                            } else {
                                updatedCart.add(line);
                            }
                            line = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                    }

                    synchronized (GATE_KEEPER) {
                        File f = new File("shoppingcart.txt");
                        BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsolutePath(), false));
                        for (int i = 0; i < updatedCart.size(); i++) {
                            bw.write(updatedCart.get(i));
                            bw.newLine();
                        }
                        bw.close();
                    }


                    ArrayList<String> currentProducts = new ArrayList<>();
                    synchronized (GATE_KEEPER) {
                        File f2 = new File("Products.txt");
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(f2.getAbsolutePath()));
                        String line = bufferedReader.readLine();
                        while (line != null) {
                            currentProducts.add(line);
                            line = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                    }


                    boolean exists = false;
                    int existingIndex = -1;
                    for (int i = 0; i < currentProducts.size(); i++) {
                        if (currentProducts.get(i).split(",")[0].equals(returnProducts.split(",")[1]) &&
                                currentProducts.get(i).split(",")[1].equals(returnProducts.split(",")[2])) {
                            exists = true;
                            existingIndex = i;
                            break;
                        }
                    }
                    int firstComma = returnProducts.indexOf(",");
                    returnProducts = returnProducts.substring(firstComma + 1);

                    if (exists) {
                        int returnProductQuantity = Integer.parseInt(returnProducts.split(",")[3]);
                        String[] currentProductInfo = currentProducts.get(existingIndex).split(",");
                        int currentProductQuantity =
                                Integer.parseInt(currentProductInfo[3]);
                        currentProductInfo[3] = String.format("%d", returnProductQuantity + currentProductQuantity);
                        currentProducts.set(existingIndex, String.format("%s,%s,%s,%s,%s,%s", currentProductInfo[0],
                                currentProductInfo[1], currentProductInfo[2], currentProductInfo[3],
                                currentProductInfo[4], currentProductInfo[5]));

                        synchronized (GATE_KEEPER) {
                            File f2 = new File("Products.txt");
                            BufferedWriter bw = new BufferedWriter(new FileWriter(f2.getAbsolutePath(), false));
                            for (int i = 0; i < currentProducts.size(); i++) {
                                bw.write(currentProducts.get(i));
                                bw.newLine();
                            }
                            bw.close();
                        }
                    } else {
                        synchronized (GATE_KEEPER) {
                            File f2 = new File("Products.txt");
                            BufferedWriter bw = new BufferedWriter(new FileWriter(f2.getAbsolutePath(), true));
                            bw.write(returnProducts);
                            bw.newLine();
                            bw.close();
                        }
                    }
                } else if (process.equals("checkout")) {
                    String username = br.readLine();

                    ArrayList<String> historyProducts = new ArrayList<>();
                    ArrayList<String> keepProducts = new ArrayList<>();
                    synchronized (GATE_KEEPER) {
                        File f = new File("shoppingcart.txt");
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(f.getAbsolutePath()));

                        String line = bufferedReader.readLine();
                        while (line != null) {
                            if (!line.split(",")[0].equals(username)) {
                                keepProducts.add(line);
                            } else {
                                historyProducts.add(line);
                            }
                            line = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                    }

                    synchronized (GATE_KEEPER) {
                        File f = new File("shoppingcart.txt");
                        BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsolutePath(), false));
                        for (int i = 0; i < keepProducts.size(); i++) {
                            bw.write(keepProducts.get(i));
                            bw.newLine();
                        }
                        bw.close();
                    }

                    ArrayList<String> currentHistory = new ArrayList<>();
                    ArrayList<Boolean> exists = new ArrayList<>();
                    ArrayList<Integer> existingIndex = new ArrayList<>();
                    synchronized (GATE_KEEPER) {
                        File f2 = new File("History.txt");
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(f2.getAbsolutePath()));
                        String line = bufferedReader.readLine();
                        while (line != null) {
                            currentHistory.add(line);
                            line = bufferedReader.readLine();
                        }
                    }


                    for (int i = 0; i < historyProducts.size(); i++) {
                        String[] historySplit = historyProducts.get(i).split(",");
                        for (int j = 0; j < currentHistory.size(); j++) {
                            if (historySplit[0].equals(currentHistory.get(j).split(",")[0]) &&
                                    historySplit[1].equals(currentHistory.get(j).split(",")[1]) &&
                                    historySplit[2].equals(currentHistory.get(j).split(",")[2])) {
                                exists.add(true);
                                existingIndex.add(j);
                                break;
                            }
                            if (j == currentHistory.size() - 1) {
                                exists.add(false);
                                existingIndex.add(-1);
                            }
                        }
                    }

                    for (int i = 0; i < historyProducts.size(); i++) {
                        if (exists.get(i)) {
                            synchronized (GATE_KEEPER) {
                                File f2 = new File("History.txt");
                                BufferedWriter bw = new BufferedWriter(new FileWriter(f2.getAbsolutePath(), false));
                                int addingQuantity = Integer.parseInt(historyProducts.get(i).split(",")[4]);
                                String[] existingProduct = currentHistory.get(existingIndex.get(i)).split(",");
                                int existingQuantity = Integer.parseInt(existingProduct[4]);
                                existingProduct[4] = String.format("%d", addingQuantity + existingQuantity);
                                String write = String.format("%s,%s,%s,%s,%s,%s,%s", existingProduct[0],
                                        existingProduct[1], existingProduct[2], existingProduct[3],
                                        existingProduct[4], existingProduct[5], existingProduct[6]);
                                currentHistory.set(existingIndex.get(i), write);

                                for (int j = 0; j < currentHistory.size(); j++) {
                                    bw.write(currentHistory.get(j));
                                    bw.newLine();
                                }
                                bw.close();
                            }

                        } else {
                            synchronized (GATE_KEEPER) {
                                File f2 = new File("History.txt");
                                BufferedWriter bw = new BufferedWriter(new FileWriter(f2.getAbsolutePath(), true));
                                bw.write(historyProducts.get(i));
                                bw.newLine();
                                bw.close();
                            }
                        }
                    }
                } else if (process.equals("view history")) {
                    String username = br.readLine();
                    ArrayList<String> list = new ArrayList<>();

                    synchronized (GATE_KEEPER) {
                        File f = new File("History.txt");
                        BufferedReader bfr = new BufferedReader(new FileReader(f.getAbsolutePath()));
                        String line = bfr.readLine();
                        while (line != null) {
                            String[] temp = line.split(",");
                            if (temp[0].equals(username)) {
                                list.add(line);
                            }
                            line = bfr.readLine();
                        }
                        bfr.close();
                    }

                    if (list.size() == 0) {
                        pw.println("false");
                        pw.flush();
                    } else {
                        pw.println("true");
                        for (String st : list) {
                            String transaction = st.substring(st.indexOf(",") + 1);
                            pw.println(transaction);
                        }
                        pw.println("end");
                        pw.flush();
                    }
                } else if (process.equals("export customer history")) {
                    String username = br.readLine();
                    String filename = br.readLine();
                    ArrayList<String> list = new ArrayList<>();

                    synchronized (GATE_KEEPER) {
                        File f = new File("History.txt");
                        BufferedReader bfr = new BufferedReader(new FileReader(f.getAbsolutePath()));
                        String line = bfr.readLine();
                        while (line != null) {
                            String[] temp = line.split(",");
                            if (temp[0].equals(username)) {
                                list.add(line);
                            }
                            line = bfr.readLine();
                        }
                        bfr.close();
                    }

                    synchronized (GATE_KEEPER) {
                        File f2 = new File(filename);
                        f2.createNewFile();
                        BufferedWriter bw = new BufferedWriter(new FileWriter(f2.getAbsolutePath(), false));
                        for (String st : list) {
                            String transaction = st.substring(st.indexOf(",") + 1);
                            bw.write(transaction);
                            bw.newLine();
                        }
                        bw.close();
                    }

                    pw.println("true");
                    pw.flush();
                } else if (process.equals("leave review")) {
                    String username = br.readLine();
                    ArrayList<String> list = new ArrayList<>();

                    synchronized (GATE_KEEPER) {
                        File f = new File("History.txt");
                        BufferedReader bfr = new BufferedReader(new FileReader(f.getAbsolutePath()));
                        String line = bfr.readLine();
                        while (line != null) {
                            String[] temp = line.split(",");
                            if (temp[0].equals(username)) {
                                list.add(line);
                            }
                            line = bfr.readLine();
                        }
                        bfr.close();
                    }

                    if (list.size() == 0) {
                        pw.println("false");
                        pw.flush();
                    } else {
                        pw.println("true");
                        for (String st : list) {
                            String transaction = st.substring(st.indexOf(",") + 1);
                            pw.println(transaction);
                        }
                        pw.println("end");
                        pw.flush();

                        String check = br.readLine();
                        if (check.equals("review")) {
                            String product = br.readLine();
                            String review = br.readLine();
                            String[] temp = product.split(",");
                            String store = temp[1];
                            String prod = temp[0];
                            String seller = temp[5];
                            String[] actualReview = {username, store, prod, seller, review};
                            String finalReview = String.join(";", actualReview);

                            synchronized (GATE_KEEPER) {
                                File f2 = new File("Reviews.txt");
                                BufferedWriter bw = new BufferedWriter(new FileWriter(f2.getAbsolutePath(), true));
                                bw.write(finalReview);
                                bw.newLine();
                                bw.close();
                            }

                            pw.println("success");
                            pw.flush();
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Checks if the user already exist
     *
     * @param username user's username
     * @param fileContent list of users
     * @return a boolean true if user exists
     */
    public static boolean checkUserExists(String username, ArrayList<String> fileContent) {
        boolean userExists = false;
        for (int i = 0; i < fileContent.size(); i++) {
            ArrayList<String> line = new ArrayList<>();
            for (String part : fileContent.get(i).split(",")) {
                line.add(part);

            }
            if (line.get(0).equalsIgnoreCase(username)) {
                userExists = true;
                return userExists;
            }
        }
        return userExists;
    }

    /**
     * writes into the login file
     *
     * @param username user's username
     * @param password user's password
     * @param userType user type (customer / seller)
     * @return a boolean true if successfully written to file
     */
    public static boolean writeLoginFile(String username, String password, int userType) {
        try {

            synchronized (GATE_KEEPER) {
                File f = new File("userlogin.txt");
                FileOutputStream fos = new FileOutputStream(f.getAbsolutePath(), true);
                PrintWriter pw = new PrintWriter(fos);
                String user = "";
                if (userType == 1) {
                    user = "customer";
                } else if (userType == 2) {
                    user = "seller";
                } else {
                    // System.out.println("error");
                }


                pw.printf("%s,%s,%s\n", username, password, user);
                pw.close();
                return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * creates an account
     *
     * @param username user's username
     * @param password user's password
     * @param userType user type (customer / seller)
     * @param fileContent list of users
     * @return a boolean true if successful
     * @throws UserAlreadyExists
     */
    public static boolean createAccount(String username, String password, int userType, ArrayList<String> fileContent)
            throws UserAlreadyExists {
        //calls readLoginFile to check if user already exists
        boolean userExists = checkUserExists(username, fileContent);
        if (userExists) {
            throw new UserAlreadyExists();
        } else {
            return writeLoginFile(username, password, userType);
        }
    }

    /**
     * logs user in
     *
     * @param username user's username
     * @param password user's password
     * @param fileContent list of users
     * @return an array list of user type and username
     */
    public static ArrayList<String> userLogin(String username, String password, ArrayList<String> fileContent) {
        ArrayList<String> loginReturn = new ArrayList<>();
        for (int i = 0; i < fileContent.size(); i++) {
            ArrayList<String> line = new ArrayList<>();
            for (String part : fileContent.get(i).split(",")) {
                line.add(part);
            }
            if ((line.get(0).equalsIgnoreCase(username)) && (line.get(1).equals(password))) {
                if (line.get(2).equals("customer")) {
                    loginReturn.add("customer");
                    return loginReturn;
                } else if (line.get(2).equals("seller")) {
                    loginReturn.add("seller");
                    return loginReturn;
                } else {
                    // System.out.println("error");
                }
            }
        }
        loginReturn.add("false");
        return loginReturn;
    }
}
