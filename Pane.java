import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Pane extends JPanel
{
   //left and right x/y positions
   private int startX, startY, currX, currY;
   private boolean hasPressed;
   private JFrame parent;
   
   public Pane(Dimension d, JFrame parent)
   {
      this.parent = parent;
      hasPressed = false;
      startX = 0;
      startY = 0;
      currX = 0;
      currY = 0;
      setPreferredSize(d);
      setOpaque(false);
      setBackground(Color.WHITE);
      addMouseListener(new MouseAdapter()
      {
         public void mousePressed(MouseEvent e)
         {
            System.out.println("Mouse Pressed");
            hasPressed = true;
            startX = e.getXOnScreen();
            startY = e.getYOnScreen();
            currX = startX;
            currY = startY;
         }
         
         public void mouseReleased(MouseEvent e)
         {
            System.out.println("Mouse Released");
            parent.setVisible(false);
            try
            {          
               Robot r = new Robot();
               BufferedImage bi = r.createScreenCapture(new Rectangle(startX, startY, currX - startX, currY - startY));
               ImageIO.write(bi, "png", new File("C:\\Users\\micha\\Documents\\screenshot.png"));
            }
            catch(AWTException ex) {}
            catch(IOException ex) {}         
            parent.dispose();
         }                                
      });
      
      addMouseMotionListener(new MouseMotionAdapter()
      {
         public void mouseDragged(MouseEvent e) 
         {
            currX = e.getXOnScreen();
            currY = e.getYOnScreen();
            repaint();         
         } 
      });
   }
      
   public void paint(Graphics g)
   {
      Graphics2D g2d = (Graphics2D) g.create();
      // 50% transparent Alpha
      g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
      
      g2d.setColor(getBackground());
      g2d.fill(getBounds());
      
      g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
      g2d.setColor(Color.GRAY);
      if(hasPressed)
      {
         int tempx = getTopLeftX();
         int tempy = getTopLeftY();
         int tempWidth = getBoxWidth(tempy);
         int tempHeight = getBoxHeight(tempy);
         g2d.fillRect(startX, startY, currX - startX, currY - startY);     
      }
      
      g2d.dispose();      
   }
   
   private int getTopLeftX()
   {
      if(currX - startX > 0)
      {
         return startX;
      }
      return currX;
   }
   
   private int getTopLeftY()
   {
      if(currY - startY > 0)
      {
         return startY;
      }
      return currY;
   }
   
   private int getBoxWidth(int tempx)
   {
      int width = startX - currX;
      if(tempx == startX)
      {
         return width;
      }
      return width * -1;
   }
   
   private int getBoxHeight(int tempy)
   {
      int height = startY - currY;
      if(tempy == startY)
      {
         return height * -1;
      }
      return height;
   }
}