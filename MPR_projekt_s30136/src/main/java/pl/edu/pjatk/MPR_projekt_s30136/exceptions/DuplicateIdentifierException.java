package pl.edu.pjatk.MPR_projekt_s30136.exceptions;

public class DuplicateIdentifierException extends RuntimeException {
    public DuplicateIdentifierException(String message) {
        super("duplikat id: " + message);
    }
}
