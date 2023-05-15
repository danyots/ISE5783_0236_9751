package renderer;

import org.junit.jupiter.api.Test;

import java.awt.*;

/**
 * This is a test class for the writePixel method of the ImageWriter class.
 */
class ImageWriterTest {

    /**
     * Tests the writePixel method of the ImageWriter class.
     * It creates an ImageWriter object with the specified dimensions and filename.
     * It iterates over the pixels of the image and sets the color of each pixel based on its position.
     * Pixels with coordinates divisible by 50 are set to magenta, while others are set to green.
     * Finally, it writes the image to a file using the writeToImage method.
     */
    @Test
    void writePixel() {
        final primitives.Color color1 = new primitives.Color(Color.MAGENTA);
        final primitives.Color color2 = new primitives.Color(Color.GREEN);

        ImageWriter im = new ImageWriter("testImage", 800, 500);
        for (int i = 0; i < im.getNy(); i++)
            for (int j = 0; j < im.getNx(); j++)
                im.writePixel(j, i, i % 50 == 0 || j % 50 == 0 ? color1 : color2);
        im.writeToImage();
    }
}
