package renderer;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void writePixel() {
        ImageWriter im = new ImageWriter("testImage", 800, 500);
        for (int i = 0; i < 800; i++) {
            for(int j=0;j<500;j++){
                if(i%50==0||i%50==1||j%50==0||i%50==1){
                    im.writePixel(i,j, new primitives.Color(Color.BLUE));
                }
                else{
                    im.writePixel(i,j, new primitives.Color(Color.GREEN));
                }
            }
        }
        im.writeToImage();
    }
}