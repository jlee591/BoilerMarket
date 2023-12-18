/**
 * Project5 -- UserAlreadyExists
 *
 * @author Peter Kang, Iddo Mayblum, Chaewon Lee, Marco Zhang, Joseph Lee, lab LC5
 * @version December 10, 2022
 */
public class UserAlreadyExists extends Exception {
    public UserAlreadyExists(String message) {
        super(message);
    }

    public UserAlreadyExists() {
        super();
    }
}
