/**
 * Project5 -- FileOverwrittenException
 *
 * @author Peter Kang, Iddo Mayblum, Chaewon Lee, Marco Zhang, Joseph Lee, lab LC05
 * @version December 10, 2022
 */
public class FileOverwrittenException extends Exception {
    public FileOverwrittenException(String message) {
        super(message);
    }

    public FileOverwrittenException() {
        super();
    }
}