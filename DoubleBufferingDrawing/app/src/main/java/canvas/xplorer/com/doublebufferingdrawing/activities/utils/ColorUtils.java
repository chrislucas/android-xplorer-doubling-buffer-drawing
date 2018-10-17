package canvas.xplorer.com.doublebufferingdrawing.activities.utils;

import java.util.Random;

/**
 * Created by r028367 on 12/01/2018.
 */

public class ColorUtils {

    private static final Random random = new Random();

    public static int getRandomColorRGB255(int lowerBound, int upperBound) {
        lowerBound = lowerBound < 0 ? 0 : lowerBound;
        upperBound = upperBound < 0 ? 0 : upperBound;
        lowerBound = lowerBound > 255 ? lowerBound % 255 : lowerBound;
        upperBound = upperBound > 255 ? upperBound % 255 : upperBound;
        // swap usando XOR operator
        if(upperBound < lowerBound) {
            upperBound ^= lowerBound;
            lowerBound ^= upperBound;
            upperBound ^= lowerBound;
        }
        int r = random.nextInt(upperBound - lowerBound) + lowerBound;
        int g = random.nextInt(upperBound - lowerBound) + lowerBound;
        int b = random.nextInt(upperBound - lowerBound) + lowerBound;
        //System.out.printf("(%d, %d, %d)\n", r, g, b);
        return 0xff000000 | ((r << 16) & 0x00FF0000) | ((g << 8) & 0x0000FF00) | (b & 0x000000FF);
    }
}
