package renderer;

import org.junit.jupiter.api.Test;

import java.awt.*;

class ImageWriterTest {

    @Test
    void writePixel() {
        ImageWriter im = new ImageWriter("testImage", 800, 500);
        for (int i = 0; i < im.getNy(); i++) {
            for (int j = 0; j < im.getNx(); j++) {
                if (i % 50 == 0 || j % 50 == 0) {
                    im.writePixel(j, i, new primitives.Color(Color.MAGENTA));
                } else {
                    im.writePixel(j, i, new primitives.Color(Color.GREEN));
                }
            }
        }
        im.writeToImage();
    }
}