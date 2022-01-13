package com.munzi.munzischeduler.util;

import java.util.Random;

/**
 * random utility
 */
public class RandomUtil {
    /**
     * get random integer 0 ~ parameter(max)
     *
     * @param max bound max integer for random
     * @return randomized integer
     */
    public static int randomInt(int max) {
        Random r = new Random();
        return r.nextInt(max);
    }
}
