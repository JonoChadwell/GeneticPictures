package genetic;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PictureDisplay {

   private static final int SCALING_FACTOR = 4;

   private JPanel panel;
   private JFrame frame;
   private Picture img;

   public PictureDisplay(Picture img) {
      frame = new JFrame();
      panel = new JPanel() {
         @Override
         protected void paintComponent(Graphics g) {
            g.clearRect(0, 0, panel.getWidth(), panel.getHeight());
            super.paintComponent(g);
            g.drawImage(img.getFancyImage(), 0, 0, img.getWidth() * SCALING_FACTOR, img.getHeight() * SCALING_FACTOR, null);
         }
      };
      this.img = img;
      panel.setPreferredSize(new Dimension(img.getWidth() * SCALING_FACTOR, img.getHeight() * SCALING_FACTOR));
      frame.setContentPane(panel);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setResizable(false);
      frame.pack();
      frame.setVisible(true);
   }

   public void repaint() {
      frame.repaint();
   }

   public boolean isVisible() {
      return frame.isVisible();
   }
}
