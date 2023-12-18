/**
 * Project5 -- Reviews
 *
 * Initializes Reviews object.
 *
 * @author Chaewon Lee, Peter Kang, Marco Zhang, Iddo Mayblum, Joseph Lee, lab sec LC5
 *
 * @version December 10, 2022
 *
 */

public class Reviews {
    private String productName;
    private String productStore;
    private String productSeller;
    private String productReview;
    private String productCustomerName;

    public Reviews(String productCustomerName, String productName, String productStore,
                   String productSeller, String productReview) {
        this.productCustomerName = productCustomerName;
        this.productName = productName;
        this.productStore = productStore;
        this.productSeller = productSeller;
        this.productReview = productReview;
    }
    public String getProductCustomerName() {
        return productCustomerName;
    }
    public String getProductName() {
        return productName;
    }
    public String getProductStore() {
        return productStore;
    }
    public String getProductSeller() {
        return productSeller;
    }
    public String getProductReview() {
        return productReview;
    }

}

