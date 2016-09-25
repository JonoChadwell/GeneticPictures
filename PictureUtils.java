package genetic;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public final class PictureUtils {

   private static final int FIRST_BYTE_MASK = 0xFF;

   // Hide the public constructor to prevent instantiation
   private PictureUtils() {
   }

   public static BufferedPicture loadImage(String path) {
      try {
         return new BufferedPicture(ImageIO.read(new File(path)));
      } catch (Exception ex) {
         throw new RuntimeException(ex);
      }
   }

   // A score of 0 would be an identical image
   public static int getSimilarity(Picture picOne, Picture picTwo) {
      BufferedImage a = picOne.getImage();
      BufferedImage b = picTwo.getImage();

      int similarity = 0;
      if (a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight()) {
         System.err.println("Checking for similarity on different size images");
      }
      for (int x = 0; x < Math.min(a.getWidth(), b.getWidth()); x++) {
         for (int y = 0; y < Math.min(a.getHeight(), b.getHeight()); y++) {
            similarity += calcSameness(a.getRGB(x, y), b.getRGB(x, y));
         }
      }

      return similarity;
   }

   private static int calcSameness(int a, int b) {
      return Math.abs((a & FIRST_BYTE_MASK) - (b & FIRST_BYTE_MASK)) + Math.abs((a >>> 8 & FIRST_BYTE_MASK) - (b >>> 8 & FIRST_BYTE_MASK))
            + Math.abs((a >>> 16 & FIRST_BYTE_MASK) - (b >>> 16 & FIRST_BYTE_MASK));
   }
}
