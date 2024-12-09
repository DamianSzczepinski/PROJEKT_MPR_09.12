package pl.edu.pjatk.MPR_projekt_s30136.services;

import org.springframework.stereotype.Service;

@Service
public class StringUtilsService {

    public String toUpperCase(String text) {
        return text != null ? text.toUpperCase() : null;
    }

    public String toCapitalized(String text) {
        if (text == null || text.isEmpty()) return text;
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }
}
