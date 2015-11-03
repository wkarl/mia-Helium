package de.prosiebensat1digital.helium.util;

public class CompareUtils {
    private CompareUtils() {
        // Remove ability to instantiate class
    }

    public static <T> boolean equals(Comparable<T> a, Comparable<T> b) {
        if (a == b) return true;
        if (a != null && b != null) {
            return a.equals(b);
        }
        return false;
    }
}
