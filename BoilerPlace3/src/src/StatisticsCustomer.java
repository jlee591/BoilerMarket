import java.io.*;
import java.util.*;

/**
 * Project 5 -- StatisticsCustomer
 *
 * displays statistics for customer
 *
 * @author Peter Kang, Chaewon Lee, Joseph Lee, Iddo Mayblum, Marco Zhang, lab sec LC5
 * @version December 6, 2022
 */
public class StatisticsCustomer {

    private final static Object GATE_KEEPER = new Object();
    private static ArrayList<String> fileContent = readFile();

    /**
     * returns a list of stores with sales with no repeats
     *
     * @return a ArrayList of Strings
     */
    public static ArrayList<String> storesWithSales() {
        String currentStoreName = "";
        Hashtable<String, Integer> storeNameWithSales = new Hashtable<>();
        ArrayList<String> storePlusSales = new ArrayList<>();
        for (int i = 0; i < fileContent.size(); i++) {
            ArrayList<String> line = new ArrayList<>();
            for (String part : fileContent.get(i).split(",")) {
                line.add(part);
            }
            currentStoreName = line.get(2);
            int currentStoreSales = Integer.parseInt(line.get(4));


            if (i == 0) {
                storeNameWithSales.put(currentStoreName, currentStoreSales);
            } else if (storeNameWithSales.containsKey(currentStoreName) == false) {
                storeNameWithSales.put(currentStoreName, currentStoreSales);
            } else if (storeNameWithSales.containsKey(currentStoreName)) {
                Set<String> keys = storeNameWithSales.keySet();
                for (String key : keys) {
                    if (key.equals(currentStoreName)) {
                        int sales = storeNameWithSales.get(currentStoreName);
                        storeNameWithSales.put(currentStoreName, sales + currentStoreSales);
                    }
                }
            }
        }
        Set<String> keys = storeNameWithSales.keySet();
        for (String key : keys) {
            String line = key + "," + storeNameWithSales.get(key);
            storePlusSales.add(line);
        }
        return storePlusSales;
    }

    /**
     * sales sorted in ascending order
     *
     * @return list of stores in ascending sales
     */
    public static ArrayList<String> ascendingSales() {
        ArrayList<String> ascendingList = new ArrayList<>();
        ArrayList<String> storePlusSales = storesWithSales();

        ArrayList<Integer> sales = new ArrayList<>();
        for (int i = 0; i < storePlusSales.size(); i++) {
            ArrayList<String> line = new ArrayList<>();
            for (String part : storePlusSales.get(i).split(",")) {
                line.add(part);
            }
            sales.add(Integer.parseInt(line.get(1)));
        }

        Collections.sort(sales);
        for (int i = 0; i < sales.size(); i++) {
            for (int j = 0; j < storePlusSales.size(); j++) {
                ArrayList<String> line = new ArrayList<>();
                for (String part : storePlusSales.get(j).split(",")) {
                    line.add(part);
                }
                if (Integer.parseInt(line.get(1)) == sales.get(i)) {
                    String sellerName = getSellerName(line.get(0));
                    ascendingList.add(line.get(0) + " of " + sellerName + " has " + line.get(1) + " sales");
                    storePlusSales.remove(j);
                }
            }
        }
        return ascendingList;
    }


    /**
     * reads history.txt
     *
     * @return a ArrayList of Strings
     */
    public static ArrayList<String> readFile() {
        ArrayList<String> fileContentList = new ArrayList<>();
        try {
            synchronized (GATE_KEEPER) {
                File history = new File("History.txt");
                String absolutePath = history.getAbsolutePath();
                BufferedReader bufferedReader = new BufferedReader(new FileReader(absolutePath));
                String line = bufferedReader.readLine();
                while (line != null) {
                    fileContentList.add(line);
                    line = bufferedReader.readLine();
                }
            }
            return fileContentList;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * gets the seller name
     *
     * @param storeName name of store
     * @return a String seller name
     */
    public static String getSellerName(String storeName) {
        for (int i = 0; i < fileContent.size(); i++) {
            ArrayList<String> line = new ArrayList<>();
            for (String part : fileContent.get(i).split(",")) {
                line.add(part);
            }
            if (storeName.equals(line.get(2))) {
                return line.get(6);
            }
        }
        return "";
    }

    /**
     * sales sorted in descending order
     *
     * @return list of stores in descending sales
     */
    public static ArrayList<String> descendingSales() {
        ArrayList<String> descendingList = new ArrayList<>();
        ArrayList<String> storePlusSales = storesWithSales();

        ArrayList<Integer> sales = new ArrayList<>();
        for (int i = 0; i < storePlusSales.size(); i++) {
            ArrayList<String> line = new ArrayList<>();
            for (String part : storePlusSales.get(i).split(",")) {
                line.add(part);
            }
            sales.add(Integer.parseInt(line.get(1)));
        }

        Collections.sort(sales, Collections.reverseOrder());
        for (int i = 0; i < sales.size(); i++) {

            for (int j = 0; j < storePlusSales.size(); j++) {
                ArrayList<String> line = new ArrayList<>();
                for (String part : storePlusSales.get(j).split(",")) {
                    line.add(part);
                }
                if (Integer.parseInt(line.get(1)) == sales.get(i)) {
                    String sellerName = getSellerName(line.get(0));
                    descendingList.add(line.get(0) + " of " + sellerName + " has " + line.get(1) + " sales");
                    storePlusSales.remove(j);
                }
            }
        }
        return descendingList;
    }

    /**
     * stores the customer has purchased from
     *
     * @param customerName Name of customer
     * @return list of stores customer has purchased from
     */
    public static ArrayList<String> storePurchasedFrom(String customerName) {
        ArrayList<String> storesPurchasedFrom = new ArrayList<>();
        HashSet<String> storeName = new HashSet<>();

        for (int i = 0; i < fileContent.size(); i++) {
            ArrayList<String> line = new ArrayList<>();
            for (String part : fileContent.get(i).split(",")) {
                line.add(part);
            }
            if (line.get(0).equals(customerName)) {
                storeName.add(line.get(2));
            }
        }

        for (String store : storeName) {
            String sellerName = "";
            for (int i = 0; i < fileContent.size(); i++) {
                ArrayList<String> line = new ArrayList<>();
                for (String part : fileContent.get(i).split(",")) {
                    line.add(part);
                }
                if (store.equals(line.get(2))) {
                    sellerName = line.get(6);
                }
            }
            storesPurchasedFrom.add("You have purchased from " + store + " of " + sellerName);
        }
        return storesPurchasedFrom;
    }

}
