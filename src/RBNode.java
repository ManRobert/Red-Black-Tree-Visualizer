import java.awt.*;

/**
 * Red-Black Tree node
 */
public class RBNode {
    public int key;
    public RBNode parent, left, right;
    public Color col;
    public boolean selected;
    public static RBNode Nil = createNilNode();

    public RBNode(int k, RBNode l, RBNode r, RBNode p, Color c, boolean selected) {
        key = k;
        left = l;
        right = r;
        parent = p;
        col = c;
        this.selected = selected;
    }

    public String toString() {
        return key + ((col == Color.RED) ? ":r" : ":b");
    }

    public static RBNode createNilNode() {
        return new RBNode(0, Nil, Nil, Nil, Color.BLACK, false);
    }

    /**
     * Make a deep copy of this node
     * @return copy of the node
     */
    public RBNode deepCopy() {
        return new RBNode(this.key, Nil, Nil, this.parent, this.col, this.selected);
    }

}