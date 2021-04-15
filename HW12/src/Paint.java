import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


/**
 * A Simple Canvas.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021 -- Homework 11 -- Walkthrough</p>
 *
 * @author Xiaoyu Liu
 * @version April 7, 2021
 */
public class Paint extends JComponent implements Runnable
{
    JButton clearButton; // a button to change paint color
    JButton fillButton; // a button to change paint color
    JButton eraseButton; // a button to change paint color
    JButton randomButton; // a button to change paint color
    Paint paint; // variable of the type ColorPicker
    JTextField hexTextField;
    JButton hexButton;
    JTextField rTextField;
    JTextField gTextField;
    JTextField bTextField;
    JButton rgbButton;
    Color bgColor = Color.white;
    private Image image; // the canvas
    private Graphics2D graphics2D;  // this will enable drawing
    /* action listener for buttons */
    ActionListener actionListener = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == clearButton)
            {
                bgColor = Color.white;
                paint.setPaint(Color.white);
                paint.fillRect();
                paint.setPaint(Color.black);
                hexTextField.setText("#");
                rTextField.setText("");
                gTextField.setText("");
                bTextField.setText("");
                paint.repaint();
            }
            if (e.getSource() == fillButton)
            {
                bgColor = paint.getColor();
                paint.fillRect();
                paint.setPaint(Color.black);
                hexTextField.setText("#");
                rTextField.setText("");
                gTextField.setText("");
                bTextField.setText("");
                paint.repaint();
            }
            if (e.getSource() == eraseButton)
            {
                paint.setPaint(bgColor);
            }
            if (e.getSource() == randomButton)
            {
                Random rand = new Random();
                Color color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
                paint.setPaint(color);
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                rTextField.setText(String.valueOf(r));
                gTextField.setText(String.valueOf(g));
                bTextField.setText(String.valueOf(b));
                hexTextField.setText(String.format("#%02x%02x%02x", r, g, b));

            }
            if (e.getSource() == hexButton)
            {
                String hexStr = hexTextField.getText();
                try
                {
                    Color color = Color.decode(hexStr);
                    paint.setPaint(color);
                    rTextField.setText(String.valueOf(color.getRed()));
                    gTextField.setText(String.valueOf(color.getGreen()));
                    bTextField.setText(String.valueOf(color.getBlue()));
                } catch (NumberFormatException ignored)
                {
                    JOptionPane.showMessageDialog(null, "Not a valid Hex Value", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            if (e.getSource() == rgbButton)
            {
                String rStr = rTextField.getText();
                String gStr = gTextField.getText();
                String bStr = bTextField.getText();
                if (rStr.equals(""))
                {
                    rTextField.setText("0");
                }
                if (gStr.equals(""))
                {
                    gTextField.setText("0");
                }
                if (bStr.equals(""))
                {
                    bTextField.setText("0");
                }
                try
                {
                    int r = Integer.parseInt(rStr);
                    int g = Integer.parseInt(gStr);
                    int b = Integer.parseInt(bStr);
                    Color color = new Color(r, g, b);
                    paint.setPaint(color);
                    hexTextField.setText(String.format("#%02x%02x%02x", r, g, b));
                } catch (NumberFormatException ignored)
                {
                    JOptionPane.showMessageDialog(null, "Not a valid RGB Value", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        }
    };
    private int curX; // current mouse x coordinate
    private int curY; // current mouse y coordinate
    private int oldX; // previous mouse x coordinate
    private int oldY; // previous mouse y coordinate
    public Paint()
    {
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                /* set oldX and oldY coordinates to beginning mouse press*/
                oldX = e.getX();
                oldY = e.getY();
            }
        });


        addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                /* set current coordinates to where mouse is being dragged*/
                curX = e.getX();
                curY = e.getY();

                /* draw the line between old coordinates and new ones */
                graphics2D.drawLine(oldX, oldY, curX, curY);

                /* refresh frame and reset old coordinates */
                repaint();
                oldX = curX;
                oldY = curY;

            }
        });
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Paint());
    }

    private Color getColor()
    {
        return graphics2D.getColor();
    }

    public void fillRect()
    {
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
    }

    public void setPaint(Color color)
    {
        graphics2D.setPaint(color);
    }

    public void run()
    {
        /* set up JFrame */
        JFrame frame = new JFrame("Canvas");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        paint = new Paint();
        content.add(paint, BorderLayout.CENTER);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(actionListener);
        fillButton = new JButton("Fill");
        fillButton.addActionListener(actionListener);
        eraseButton = new JButton("Erase");
        eraseButton.addActionListener(actionListener);
        randomButton = new JButton("Random");
        randomButton.addActionListener(actionListener);

        JPanel topPanel = new JPanel();
        topPanel.add(clearButton);
        topPanel.add(fillButton);
        topPanel.add(eraseButton);
        topPanel.add(randomButton);
        content.add(topPanel, BorderLayout.NORTH);

        hexTextField = new JTextField("#", 10);
        hexButton = new JButton("Hex");
        hexButton.addActionListener(actionListener);
        rTextField = new JTextField("", 5);
        gTextField = new JTextField("", 5);
        bTextField = new JTextField("", 5);
        rgbButton = new JButton("RGB");
        rgbButton.addActionListener(actionListener);


        JPanel bottomPanel = new JPanel();
        bottomPanel.add(hexTextField);
        bottomPanel.add(hexButton);
        bottomPanel.add(rTextField);
        bottomPanel.add(gTextField);
        bottomPanel.add(bTextField);
        bottomPanel.add(rgbButton);
        content.add(bottomPanel, BorderLayout.SOUTH);

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }

    protected void paintComponent(Graphics g)
    {
        if (image == null)
        {
            image = createImage(getSize().width, getSize().height);
            /* this lets us draw on the image (ie. the canvas)*/
            graphics2D = (Graphics2D) image.getGraphics();
            /* gives us better rendering quality for the drawing lines */
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            /* set canvas to white with default paint color */
            graphics2D.setPaint(Color.white);
            graphics2D.fillRect(0, 0, getSize().width, getSize().height);
            graphics2D.setPaint(Color.black);
            graphics2D.setStroke(new BasicStroke(5));
            repaint();
        }
        g.drawImage(image, 0, 0, null);

    }
}