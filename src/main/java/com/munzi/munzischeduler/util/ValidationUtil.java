package com.munzi.munzischeduler.util;

/**
 * validation utility
 */
public class ValidationUtil {

    /**
     * Check value is empty
     *
     * @param value value to check
     * @return Whether it's empty
     */
    public static boolean isBlank(String value) {
        return value == null || value.isEmpty();
    }

    /**
     * Check value is exists
     *
     * @param value value to check
     * @return Whether it exists
     */
    public static boolean isExists(String value) {
        return value != null && !value.isEmpty();
    }

    /**
     * Check value is exists
     *
     * @param value value to check
     * @return Whether it exists
     */
    public static boolean isExists(Integer value) {
        return value != null && value > 0;
    }
}
