import java.io.*;
import java.util.*;

/**
 * Project 5 -- SellerStatistics
 *
 * displays statistics for sellers
 *
 * @author Peter Kang, Chaewon Lee, Joseph Lee, Iddo Mayblum, Marco Zhang, lab sec LC5
 * @version December 6, 2022
 */
public class SellerStatistics {

    private final static Object GATE_KEEPER = new Object();


    /**
     * This returns an ArrayList of String of store names with no repeats
     *
     * @param sellerName it takes a String that is the seller's name
     * @return an ArrayList of Strings
     */
    public static ArrayList<String> uniqueStores(String sellerName, ArrayList<String> fileContent) {
        HashSet<String> uniqueStores = new HashSet<>();
        for (int i = 0; i < fileContent.size(); i++) {
            ArrayList<String> line = new ArrayList<>();
            for (String word : fileContent.get(i).split(",")) {
                line.add(word);
            }
            if (line.get(6).equals(sellerName)) {
                uniqueStores.add(line.get(2));
            }

        }
        ArrayList<String> uniqueStoreNames = new ArrayList<>(uniqueStores);
        return uniqueStoreNames;
    }

    /**
     * This returns a hashtable of stores with products with sales
     *
     * @param sellerName it takes a String that is the Seller's name
     * @return a Hashtable<String, Hashtable<String, Integer>>
     */
    public static Hashtable<String, Hashtable<String, Integer>> storesWithProductsWithSales(String sellerName) {
        Hashtable<String, Hashtable<String, Integer>> storesAndProducts = new Hashtable<>();
        ArrayList<String> fileContent = readFile();
        ArrayList<String> storeNames = uniqueStores(sellerName, fileContent);


        for (int store = 0; store < storeNames.size(); store++) {
            Hashtable<String, Integer> productAndPrice = new Hashtable<>();
            for (int file = 0; file < fileContent.size(); file++) {
                if (fileContent.get(file).contains(storeNames.get(store))) {
                    ArrayList<String> line = new ArrayList<>();
                    for (String word : fileContent.get(file).split(",")) {
                        line.add(word);
                    }
                    if (line.get(6).equals(sellerName)) {
                        if (productAndPrice.containsKey(line.get(1))) {
                            productAndPrice.put(line.get(1), productAndPrice.get(line.get(1)) +
                                    Integer.parseInt(line.get(4)));
                        } else {
                            productAndPrice.put(line.get(1), Integer.valueOf(line.get(4)));
                        }
                    }
                }

            }
            storesAndProducts.put(storeNames.get(store), productAndPrice);

        }

        return storesAndProducts;

    }

    /**
     * creates and returns a String representation of the list sorted in ascending order
     *
     * @param sellerName takes a String that is the seller's name
     * @return a String
     */

    public static String ascendingSort(String sellerName) {
        String sortedString = "";

        Hashtable<String, Hashtable<String, Integer>> storesWithProductsWithSales =
                storesWithProductsWithSales(sellerName);
        ArrayList<Integer> sortedList = new ArrayList<>();


        int counter = 0;
        for (Map.Entry<String, Hashtable<String, Integer>> mapElement : storesWithProductsWithSales.entrySet()) {

            Hashtable<String, Integer> values = mapElement.getValue();

            // Adding some bonus marks to all the students
            for (Map.Entry<String, Integer> mapElementTwo : values.entrySet()) {
                int value = mapElementTwo.getValue();
                sortedList.add(value);
            }
            Collections.sort(sortedList);
            sortedString += ":" + mapElement.getKey() + ":";
            ArrayList<String> itemsDone = new ArrayList<>();


            for (int i = 0; i < sortedList.size(); i++) {
                for (Map.Entry<String, Integer> mapElementThree : values.entrySet()) {

                    if (i == sortedList.size()) {
                        String line = mapElementThree.getKey() + "," + mapElementThree.getValue();
                        if (!sortedString.contains(line)) {
                            sortedString += line;
                        }
                    } else if (mapElementThree.getValue() == sortedList.get(i)) {
                        String line = "";
                        line = mapElementThree.getKey() + "," + mapElementThree.getValue() + ";";

                        if (!sortedString.contains(line)) {
                            sortedString += line;
                        }
                    }
                }
            }
        }
        return sortedString;
    }


    /**
     * creates and returns a String representation of the list sorted in descending order
     *
     * @param sellerName takes a String that is the seller's name
     * @return a String
     */
    public static String descendingSort(String sellerName) {
        Hashtable<String, Hashtable<String, Integer>> storesWithProductsSorted = new Hashtable<>();
        Hashtable<String, String> sortedMap = new Hashtable<>();
        String sortedString = "";

        Hashtable<String, Hashtable<String, Integer>> storesWithProductsWithSales =
                storesWithProductsWithSales(sellerName);
        ArrayList<Integer> sortedList = new ArrayList<>();


        for (Map.Entry<String, Hashtable<String, Integer>> mapElement : storesWithProductsWithSales.entrySet()) {
            String key = mapElement.getKey();
            Hashtable<String, Integer> values = mapElement.getValue();

            // Adding some bonus marks to all the students
            for (Map.Entry<String, Integer> mapElementTwo : values.entrySet()) {
                String product = mapElementTwo.getKey();
                int value = mapElementTwo.getValue();
                sortedList.add(value);
            }
            Collections.sort(sortedList, Collections.reverseOrder());
            sortedString += ":" + mapElement.getKey() + ":";
            for (int i = 0; i < sortedList.size(); i++) {
                for (Map.Entry<String, Integer> mapElementThree : values.entrySet()) {
                    if (i == sortedList.size()) {
                        String line = mapElementThree.getKey() + "," + mapElementThree.getValue();
                        if (!sortedString.contains(line)) {
                            sortedString += line;
                        }
                    } else if (mapElementThree.getValue() == sortedList.get(i)) {
                        String line = mapElementThree.getKey() + "," + mapElementThree.getValue() + ";";
                        if (!sortedString.contains(line)) {
                            sortedString += line;
                        }
                    }
                }

            }

        }
        return sortedString;
    }


    /**
     * reads the file History.txt
     *
     * @return an ArrayList
     */
    public static ArrayList<String> readFile() {
        ArrayList<String> fileContent = new ArrayList<>();
        try {
            synchronized (GATE_KEEPER) {
                File history = new File("History.txt");
                String absolutePathHistory = history.getAbsolutePath();
                BufferedReader bufferedReader = new BufferedReader(new FileReader(absolutePathHistory));
                String line = bufferedReader.readLine();
                while (line != null) {
                    fileContent.add(line);
                    line = bufferedReader.readLine();
                }
            }
            return fileContent;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * returns a list of customers who have purchased from the store and the number of purchases they have
     *
     * @param sellerName takes a String that is the seller's name
     * @return a Hashtable
     */
    public static Hashtable<String, Integer> listOfCustomers(String sellerName) {
        Hashtable<String, Integer> customersWithQuantity = new Hashtable<>();
        ArrayList<String> fileContent = readFile();

        for (int i = 0; i < fileContent.size(); i++) {
            ArrayList<String> line = new ArrayList<>();
            for (String part : fileContent.get(i).split(",")) {
                line.add(part);
            }
            if (line.get(6).equals(sellerName)) {
                customersWithQuantity.put(line.get(0), 0);
            }
        }

        for (int i = 0; i < fileContent.size(); i++) {
            ArrayList<String> line = new ArrayList<>();
            for (String part : fileContent.get(i).split(",")) {
                line.add(part);
            }
            for (Map.Entry<String, Integer> mapElement : customersWithQuantity.entrySet()) {
                if (mapElement.getKey().equals(line.get(0))) {
                    int value = mapElement.getValue();
                    customersWithQuantity.put(line.get(0), Integer.valueOf(value + Integer.parseInt(line.get(4))));
                }
            }
        }
        return customersWithQuantity;
    }

    /**
     * creates and returns a linked hash map that sorted the customer names in ascending order
     * based on the number of purchases they have
     *
     * @param sellerName takes a String that is the seller's name
     * @return a Linked Hashmap
     */
    public static LinkedHashMap<String, Integer> ascendingSortCust(String sellerName) {
        LinkedHashMap<String, Integer> sortedList = new LinkedHashMap<>();
        ArrayList<Integer> number = new ArrayList<>();
        Hashtable<String, Integer> listOfCustomers = listOfCustomers(sellerName);
        for (Map.Entry<String, Integer> mapElement : listOfCustomers.entrySet()) {
            number.add(mapElement.getValue());
        }

        Collections.sort(number);
        for (int i = 0; i < number.size(); i++) {
            for (Map.Entry<String, Integer> mapElement : listOfCustomers.entrySet()) {
                if (mapElement.getValue() == number.get(i)) {
                    sortedList.put(mapElement.getKey(), mapElement.getValue());
                }
            }
        }

        return sortedList;
    }

    /**
     * creates and returns a Linked Hashmap that sorted the customer names in descending order
     * based on the number of purchases they have
     *
     * @param sellerName takes a String that is the seller's name
     * @return a Linked HashMap
     */
    public static LinkedHashMap<String, Integer> descendingSortCust(String sellerName) {
        LinkedHashMap<String, Integer> sortedList = new LinkedHashMap<>();
        ArrayList<Integer> number = new ArrayList<>();
        Hashtable<String, Integer> listOfCustomers = listOfCustomers(sellerName);
        for (Map.Entry<String, Integer> mapElement : listOfCustomers.entrySet()) {
            number.add(mapElement.getValue());
        }

        Collections.sort(number, Collections.reverseOrder());
        for (int i = 0; i < number.size(); i++) {
            for (Map.Entry<String, Integer> mapElement : listOfCustomers.entrySet()) {
                if (mapElement.getValue() == number.get(i)) {
                    sortedList.put(mapElement.getKey(), mapElement.getValue());
                }
            }
        }

        return sortedList;
    }

}
