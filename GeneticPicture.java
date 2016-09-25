package genetic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.FactoryConfigurationError;

public class GeneticPicture extends Picture {

   private static final int FANCY_SCALING = 6;

   private static final int GENERATION_SIZE = 10;
   private static final Color[] LIGHT_FANCY_COLORS = { Color.WHITE, Color.decode("0xFFAAAAA"), Color.decode("0xAAFFAA"), Color.decode("0xAAAAFF")  };
   private static final Color[] DARK_FANCY_COLORS = { Color.decode("0x111111"), Color.decode("0x200000"), Color.decode("0x002000"), Color.decode("0x000020")};

   private int height;
   private int width;

   private static class Oval {
      public int width;
      public int height;
      public int x;
      public int y;
      public Color color;
      public Color fancyColor;

      public Oval mutate() {
         Oval result = new Oval();
         result.width = this.width;
         result.height = this.height;
         result.x = this.x;
         result.y = this.y;
         double field = Math.random();
         double difference = (Math.random() - 0.5) * 10;
         if (field < 0.25) {
            result.width += difference;
         } else if (field < 0.5) {
            result.height += difference;
         } else if (field < 0.75) {
            result.x += difference;
         } else {
            result.y += difference;
         }
         result.color = color;
         result.fancyColor = fancyColor;
         return result;
      }

      public static Oval generate(int viewWidth, int viewHeight) {
         Oval result = new Oval();
         int centerX = (int) (Math.random() * viewWidth);
         int centerY = (int) (Math.random() * viewHeight);
         int width = (int) (Math.random() * 20);
         int height = (int) (Math.random() * 20);
         result.width = width;
         result.height = height;
         result.x = centerX - width / 2;
         result.y = centerY - height / 2;

         if (Math.random() < 0.8) {
            result.color = Color.BLACK;
            result.fancyColor = LIGHT_FANCY_COLORS[(int) (LIGHT_FANCY_COLORS.length * Math.random())];
         } else {
            result.color = Color.WHITE;
            result.fancyColor = DARK_FANCY_COLORS[(int) (DARK_FANCY_COLORS.length * Math.random())];
         }

         return result;
      }
   }

   private List<Oval> components = new ArrayList<>();

   public GeneticPicture(int width, int height) {
      this.height = height;
      this.width = width;
   }

   private GeneticPicture(int width, int height, List<Oval> components) {
      this.height = height;
      this.width = width;
      this.components = components;
   }

   @Override
   public int getHeight() {
      return height;
   }

   @Override
   public int getWidth() {
      return width;
   }

   @Override
   public BufferedImage getImage() {
      BufferedImage result = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
      Graphics g = result.getGraphics();

      g.setColor(Color.WHITE);
      g.fillRect(0, 0, width, height);

      for (Oval o : components) {
         g.setColor(o.color);
         g.fillOval(o.x, o.y, o.width, o.height);
      }
      return result;
   }

   @Override
   public BufferedImage getFancyImage() {
      BufferedImage result = new BufferedImage(getWidth() , getHeight() , BufferedImage.TYPE_4BYTE_ABGR);
      Graphics g = result.getGraphics();

      g.setColor(Color.BLACK);
      g.fillRect(0, 0, width , height );

      for (Oval o : components) {
         g.setColor(o.fancyColor);
         g.fillOval(o.x , o.y , o.width , o.height );
      }
      return result;
   }

   private GeneticPicture createChild() {
      List<Oval> newOvals = new ArrayList<>(components);
      double chance = Math.random();

      // Remove an oval
      if (chance < 0.2 && !newOvals.isEmpty()) {
         newOvals.remove((int) (Math.random() * newOvals.size()));
      }
      // Change an oval
      else if (chance < 0.6 && !newOvals.isEmpty()) {
         int index = (int) (Math.random() * newOvals.size());
         newOvals.set(index, newOvals.get(index).mutate());
      }
      // Add an oval
      else {
         newOvals.add(Oval.generate(getWidth(), getHeight()));
      }

      return new GeneticPicture(width, height, newOvals);
   }

   public void evolveToFit(Picture reference) {
      int bestScore = PictureUtils.getSimilarity(this, reference);
      GeneticPicture bestChild = this;
      for (int i = 0; i < GENERATION_SIZE; i++) {
         GeneticPicture child = createChild();
         int childScore = PictureUtils.getSimilarity(child, reference);
         if (childScore < bestScore) {
            bestScore = childScore;
            bestChild = child;
         }
      }
      System.out.println("Best Score: " + bestScore);
      this.components = bestChild.components;
   }
}
