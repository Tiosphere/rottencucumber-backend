package tk.rottencucumber.backend.util;

import com.github.slugify.Slugify;

public class Slugifier {
    public static Slugify getInstance() {
        return Slugify.builder().lowerCase(true).underscoreSeparator(false).build();
    }
}
