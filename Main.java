package genetic;

public class Main {

   private static String PICTURE = "C:/Users/Jono/Desktop/Picture.png";

   public static void main(String[] args) {
      Picture pic = PictureUtils.loadImage(PICTURE);
      GeneticPicture matchPic = new GeneticPicture(pic.getWidth(), pic.getHeight());
      PictureDisplay display = new PictureDisplay(matchPic);
      new Thread(() -> {
         for (;;) {
            matchPic.evolveToFit(pic);
            display.repaint();
         }
      }).start();
   }
}
