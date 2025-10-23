import java.applet.Applet;
import java.awt.*;

public class SmileyApplet extends Applet {
    @Override
    public void paint(Graphics g)
     {
        // enable slightly better rendering on some JREs
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        // background
        setBackground(Color.WHITE);

        // face circle
        g2.setColor(new Color(255, 230, 120)); // pale yellow
        g2.fillOval(10, 10, 180, 180);
        g2.setColor(Color.BLACK);
        g2.drawOval(10, 10, 180, 180);

        // eyes
        g2.setColor(Color.BLACK);
        g2.fillOval(55, 60, 16, 16);  // left eye
        g2.fillOval(129, 60, 16, 16); // right eye

        // smile (arc)
        g2.setStroke(new BasicStroke(3));
        g2.drawArc(50, 80, 100, 70, 200, 140);

        // optional: small nose
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(100, 75, 100, 95);

        // label (optional)
        g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g2.drawString(":) Smiley", 75, 200);
    }
}
