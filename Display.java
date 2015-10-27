import javax.swing.*;
import java.awt.*;

public class Display extends JFrame
{   
   private Pane panel;
   public Display(Dimension d)
   {   
      setUndecorated(true);              
      setBackground(new Color(0, 255, 0, 0));
      panel = new Pane(d, this);
      setContentPane(panel);
      pack(); 
      setVisible(true);   
   }   
      
   public static void main(String[] args)
   {
      new Display(Toolkit.getDefaultToolkit().getScreenSize());
   } 
}