package pl.edu.pjatk.MPR_projekt_s30136.exceptions;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super("Zle id: " + message);
    }
}
