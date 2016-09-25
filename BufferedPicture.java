package genetic;

import java.awt.image.BufferedImage;

public class BufferedPicture extends Picture {

   private final BufferedImage image;

   public BufferedPicture(BufferedImage image) {
      this.image = image;
   }

   @Override
   public int getHeight() {
      return image.getHeight();
   }

   @Override
   public int getWidth() {
      return image.getWidth();
   }

   @Override
   public BufferedImage getImage() {
      return image;
   }

   @Override
   public BufferedImage getFancyImage() {
      return image;
   }
}
