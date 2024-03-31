import javax.swing.*;
import java.awt.*;


/**
 * The class that implements jpanel. It is responsible for creating the red-black tree visualization for
 * each step + the text from a certain operation
 */
public class RBTreeVisualizer extends JPanel {
    private static final int DIAMETER = 30;
    private static final int PADDING_Y = 50;

    private RBTree rbTree;
    private String dynamicText = "";

    /**
     * Constructor
     * @param rbTree new Rbtree
     */
    public RBTreeVisualizer(RBTree rbTree) {
        this.rbTree = rbTree;
        setPreferredSize(new Dimension(800, 600));
    }

    /**
     * Updates the step to be displayed
     * @param rbTree rbTree step
     * @param dynamicText operations text
     */
    public void setTree(RBTree rbTree, String dynamicText) {
        this.rbTree = rbTree;
        this.dynamicText = dynamicText;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (rbTree != null) {
            drawTree(g, getWidth() / 2, PADDING_Y, rbTree.getRoot(), getWidth() / 4);
        }
        drawDynamicText(g);
    }

    private void drawTree(Graphics g, int x, int y, RBNode root, int xOffset) {
        if (root != null) {
            drawNode(g, x, y, root.col, root.key, root.selected);

            if (root.left != null) {
                int newX = x - xOffset;
                int newY = y + PADDING_Y;
                drawEdge(g, x, y, newX, newY);
                drawTree(g, newX, newY, root.left, xOffset / 2);
            }

            if (root.right != null) {
                int newX = x + xOffset;
                int newY = y + PADDING_Y;
                drawEdge(g, x, y, newX, newY);
                drawTree(g, newX, newY, root.right, xOffset / 2);
            }
        }
    }

    private void drawNode(Graphics g, int x, int y, Color color, int data, boolean selected) {
        g.setColor(color);
        g.fillOval(x - DIAMETER / 2, y - DIAMETER / 2, DIAMETER, DIAMETER);

        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(data), x - 5, y + 5);


        if (selected) {
            g.setColor(Color.blue);
            g.drawOval(x - DIAMETER / 2 - 5, y - DIAMETER / 2 - 5, DIAMETER + 10, DIAMETER + 10);
            g.drawOval(x - DIAMETER / 2 - 6, y - DIAMETER / 2 - 6, DIAMETER + 12, DIAMETER + 12);
            g.drawOval(x - DIAMETER / 2 - 7, y - DIAMETER / 2 - 7, DIAMETER + 14, DIAMETER + 14);
        }
    }

    private void drawEdge(Graphics g, int x1, int y1, int x2, int y2) {
        g.setColor(Color.BLACK);
        g.drawLine(x1, y1 + DIAMETER / 2, x2, y2 - DIAMETER / 2);
    }

    /**
     * Draw the text for the operation step
     * @param g graphic
     */
    private void drawDynamicText(Graphics g) {
        g.setColor(Color.RED);
        g.drawString("Prev step: " + dynamicText, getWidth() / 2 - 100, getHeight() - 20);
    }
}