import java.awt.*;


/**
 * Red black tree
 */
public class RBTree {
    private RBNode root;

    public RBTree() {
        root = RBNode.Nil;
    }

    public RBNode createNode(int key) {
        return new RBNode(key, RBNode.Nil, RBNode.Nil, RBNode.Nil, Color.BLACK, false);
    }

    /**
     * @return root
     */
    public RBNode getRoot() {
        return this.root;
    }

    /**
     * verify if a n node is nil
     *
     * @param n node to verify
     * @return true/false
     */
    public boolean isNil(RBNode n) {
        return (n == RBNode.Nil);
    }

    public RBNode search(RBNode w, int key) {
        if (isNil(w) || w.key == key) {
            w.selected = true;
            if (!isNil(w))
                RBTreeVisualizationApp.updateSteps(this, "Node found : " + key);
            else RBTreeVisualizationApp.updateSteps(this, "Node " + key + " not found");

            w.selected = false;
            return w;
        }
        w.selected = true;
        RBTreeVisualizationApp.updateSteps(this, key + " was compared with " + w.key + " (searching for the node)");
        w.selected = false;

        return search((key < w.key) ? w.left : w.right, key);
    }

    /**
     * Make a deep copy of this rb tree for steps of operations
     *
     * @return deep copy of RBtree
     */
    public RBTree deepCopy() {

        RBTree copiedTree = new RBTree();
        copiedTree.root = deepCopyNode(this.root);

        return copiedTree;
    }

    /**
     * Make a deep copy of all the nodes
     *
     * @param originalNode node to be copied
     * @return deepCopy of RBNode
     */
    private RBNode deepCopyNode(RBNode originalNode) {
        if (originalNode == RBNode.Nil)
            return RBNode.Nil;

        RBNode copiedNode = originalNode.deepCopy();
        copiedNode.left = deepCopyNode(originalNode.left);
        copiedNode.right = deepCopyNode(originalNode.right);

        return copiedNode;
    }

    private void LeftRotate(RBNode x) {
        RBNode y = x.right;
        x.right = y.left;
        if (y.left != RBNode.Nil)
            y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == RBNode.Nil)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

    private void RightRotate(RBNode y) {
        RBNode x = y.left;
        y.left = x.right;
        if (x.right != RBNode.Nil)
            x.right.parent = y;
        x.parent = y.parent;
        if (y.parent == RBNode.Nil)
            root = x;
        else if (y == y.parent.left)
            y.parent.left = x;
        else
            y.parent.right = x;
        x.right = y;
        y.parent = x;
    }

    public RBNode maximum(RBNode w) {
        RBNode x = w;
        x.selected = true;
        RBTreeVisualizationApp.updateSteps(this, x.key + " was visited (searching for predecessor) ");
        x.selected = false;
        while (!isNil(x.right)) {
            x = x.right;
            x.selected = true;
            RBTreeVisualizationApp.updateSteps(this, x.key + " was visited ");
        }
        x.selected = false;
        return x;
    }

    public RBNode minimum(RBNode w) {
        RBNode x = w;
        x.selected = true;
        RBTreeVisualizationApp.updateSteps(this, x.key + " was visited (searching for successor) ");
        x.selected = false;
        while (!isNil(x.left)) {
            x = x.left;
            x.selected = true;
            RBTreeVisualizationApp.updateSteps(this, x.key + " was visited ");
            x.selected = false;
        }
        return x;
    }

    public RBNode successor(RBNode w) {
        if (isNil(w)) return w;
        RBNode x = w;
        if (!isNil(x.right))
            return minimum(x.right);
        RBNode y = x.parent;
        while (!isNil(y) && x == y.right) {
            x = y;
            y = x.parent;

            y.selected = true;
            RBTreeVisualizationApp.updateSteps(this, y.key + " was visited (searching for successor) ");
            y.selected = false;
        }
        return y;
    }

    public RBNode predecessor(RBNode w) {
        if (isNil(w)) return w;
        RBNode x = w;
        if (!isNil(x.left))
            return maximum(x.left);
        RBNode y = x.parent;
        while (!isNil(y) && x == y.left) {
            x = y;
            y = x.parent;

            y.selected = true;
            RBTreeVisualizationApp.updateSteps(this, y.key + " was visited (searching for predecessor) ");
            y.selected = false;
        }
        return y;
    }

    public void RBInsert(RBNode z) {
        RBNode y = RBNode.Nil;
        RBNode x = root;

        root.selected = true;
        RBTreeVisualizationApp.updateSteps(this, z.key + " was compared with " + root.key);
        root.selected = false;

        while (!isNil(x)) {
            y = x;
            x = (z.key < x.key) ? x.left : x.right;

            if (!isNil(x)) {
                x.selected = true;
                RBTreeVisualizationApp.updateSteps(this, z.key + " was compared with " + x.key);
                x.selected = false;
            }
        }

        z.parent = y;
        if (isNil(y)) {
            root = z;
        } else if (z.key < y.key) {
            y.left = z;
        } else {
            y.right = z;
        }
        z.left = z.right = RBNode.Nil;
        z.col = Color.RED;


        z.selected = true;
        RBTreeVisualizationApp.updateSteps(this, z.key + " was inserted");
        z.selected = false;

        RBInsertFixup(z);
    }


    public RBNode delete(RBNode z) {
        RBNode y = (isNil(z.left) || isNil(z.right)) ? z : successor(z);

        y.selected = true;
        if (y != z)
            RBTreeVisualizationApp.updateSteps(this, " Succesor found :" + y.key);
        else
            RBTreeVisualizationApp.updateSteps(this, "Same node because the node does not have 2 children : " + y.key);
        y.selected = false;

        RBNode x = !isNil(y.left) ? y.left : y.right;
        x.parent = y.parent;
        if (isNil(y.parent)) {
            root = x;
        } else {
            if (y == y.parent.left)
                y.parent.left = x;
            else
                y.parent.right = x;
        }
        if (y != z) {
            z.key = y.key;
        }

        RBTreeVisualizationApp.updateSteps(this, " Delete node replaced");

        if (y.col == Color.BLACK)
            RBDeleteFixup(x);
        return y;
    }

    public int depth(RBNode x) {
        if (isNil(x)) {
            return -1;
        } else {
            int lDepth = depth(x.left);
            int rDepth = depth(x.right);
            return (lDepth < rDepth ? rDepth : lDepth) + 1;
        }
    }

    public int depth() {
        return depth(root);
    }

    public int countBlacks(RBNode x) {
        if (isNil(x)) return 1;
        int n = countBlacks(x.left);
        return ((x.col == Color.RED) ? n : n + 1);
    }

    private int bh(RBNode x) {
        if (isNil(root)) {
            return 0;
        }

        if (isNil(x)) {
            return 1;
        }

        int left = bh(x.left);
        int right = bh(x.right);

        int maxHeight = left >= right ? left : right;

        return x.col == Color.BLACK && x != root ? maxHeight + 1 : maxHeight;
    }

    public int bh() {
        return bh(root);
    }

    public int maxBlackKey() {
        if (isNil(root)) {
            return -1000;
        }

        RBNode currentNode = root;
        int maxKey = currentNode.key;

        while (!isNil(currentNode.right)) {
            currentNode = currentNode.right;
            if (currentNode.col == Color.BLACK) {
                maxKey = currentNode.key;
            }
        }

        return maxKey;
    }

    public int maxRedKey() {
        if (isNil(root)) {
            return -1000;
        }

        RBNode currentNode = root;
        int maxKey = -1000;

        while (!isNil(currentNode.right)) {
            currentNode = currentNode.right;
            if (currentNode.col == Color.RED) {
                maxKey = currentNode.key;
            }
        }

        return maxKey;
    }

    private void RBInsertFixup(RBNode z) {
        while (z.parent.col == Color.RED) {
            if (z.parent.parent.left == z.parent) {
                RBNode y = z.parent.parent.right;

                if (y.col == Color.RED) {
                    z.parent.col = Color.BLACK;
                    RBTreeVisualizationApp.updateSteps(this, z.parent.key + " was colored black");

                    y.col = Color.BLACK;
                    RBTreeVisualizationApp.updateSteps(this, y.key + " was colored black");

                    z.parent.parent.col = Color.RED;
                    RBTreeVisualizationApp.updateSteps(this, z.parent.parent.key + " was colored red");

                    z = z.parent.parent;
                } else if (z.parent.right == z) {
                    z = z.parent;
                    int key = z.key;
                    LeftRotate(z);
                    RBTreeVisualizationApp.updateSteps(this, "The tree was rotated to the left from" + key);

                }

                if (z.parent.col != Color.BLACK) {
                    z.parent.col = Color.BLACK;
                    RBTreeVisualizationApp.updateSteps(this, z.parent.key + " was colored black");
                }

                if (!isNil(z.parent)) {
                    if (!isNil(z.parent.parent)) {

                        if (z.parent.parent.col != Color.RED) {
                            z.parent.parent.col = Color.RED;
                            RBTreeVisualizationApp.updateSteps(this, z.parent.parent.key + " was colored red");
                        }
                        int key = z.parent.parent.key;
                        RightRotate(z.parent.parent);
                        RBTreeVisualizationApp.updateSteps(this, "The tree was rotated to the right from " + key);

                    }
                }
            } else {
                RBNode y = z.parent.parent.left;

                if (y.col == Color.RED) {

                    z.parent.col = Color.BLACK;
                    RBTreeVisualizationApp.updateSteps(this, z.parent.key + " was colored black");

                    y.col = Color.BLACK;
                    RBTreeVisualizationApp.updateSteps(this, y.key + " was colored black");

                    z.parent.parent.col = Color.RED;
                    RBTreeVisualizationApp.updateSteps(this, z.parent.parent.key + " was colored red");

                    z = z.parent.parent;

                } else if (z.parent.left == z) {
                    z = z.parent;
                    int key = z.key;
                    RightRotate(z);
                    RBTreeVisualizationApp.updateSteps(this, "The tree was rotated to the right from " + key);

                }

                if (z.parent.col != Color.BLACK) {
                    z.parent.col = Color.BLACK;
                    RBTreeVisualizationApp.updateSteps(this, z.parent.key + " was colored black");
                }

                if (!isNil(z.parent)) {
                    if (!isNil(z.parent.parent)) {

                        if (z.parent.parent.col != Color.RED) {
                            z.parent.parent.col = Color.RED;
                            RBTreeVisualizationApp.updateSteps(this, z.parent.parent.key + " was colored red");
                        }

                        int key = z.parent.parent.key;
                        LeftRotate(z.parent.parent);
                        RBTreeVisualizationApp.updateSteps(this, "The tree was rotated to the left from " + key);


                    }
                }
            }

        }

        if (root.col != Color.BLACK) {
            root.col = Color.BLACK;
            RBTreeVisualizationApp.updateSteps(this, "The root was colored black");
        }


    }

    private void RBDeleteFixup(RBNode x) {
        while (x != root && x.col == Color.BLACK) {
            if (x == x.parent.left) {
                RBNode w = x.parent.right;
                if (w.col == Color.RED) {
                    w.col = Color.BLACK;
                    RBTreeVisualizationApp.updateSteps(this, w.key + "  was colored black");


                    x.parent.col = Color.RED;
                    RBTreeVisualizationApp.updateSteps(this, x.parent.key + "  was colored red");

                    int key = x.parent.key;
                    LeftRotate(x.parent);
                    RBTreeVisualizationApp.updateSteps(this, "The tree was left rotated from " + key);

                    w = x.parent.right;
                }
                if (w.left.col == Color.BLACK && w.right.col == Color.BLACK) {
                    w.col = Color.RED;
                    RBTreeVisualizationApp.updateSteps(this, w.key + "  was colored red");

                    x = x.parent;
                } else {
                    if (w.right.col == Color.BLACK) {
                        w.left.col = Color.BLACK;
                        RBTreeVisualizationApp.updateSteps(this, w.left.key + "  was colored black");

                        w.col = Color.RED;
                        RBTreeVisualizationApp.updateSteps(this, w.key + "  was colored red");

                        int key = w.key;
                        RightRotate(w);
                        RBTreeVisualizationApp.updateSteps(this, "The tree was right rotated from " + key);

                        w = x.parent.right;
                    }

                    w.col = x.parent.col;
                    RBTreeVisualizationApp.updateSteps(this, w.key + "  was colored with " + x.parent.col.toString());

                    x.parent.col = Color.BLACK;
                    RBTreeVisualizationApp.updateSteps(this, x.parent.key + "  was colored black");

                    w.right.col = Color.BLACK;
                    RBTreeVisualizationApp.updateSteps(this, w.key + "  was colored black");

                    int key = x.parent.key;
                    LeftRotate(x.parent);
                    RBTreeVisualizationApp.updateSteps(this, "The tree was rotatet to the left from " + key);

                    x = root;
                }
            } else {
                RBNode w = x.parent.left;
                if (w.col == Color.RED) {
                    w.col = Color.BLACK;
                    RBTreeVisualizationApp.updateSteps(this, w.key + "  was colored black");

                    x.parent.col = Color.RED;
                    RBTreeVisualizationApp.updateSteps(this, x.key + "  was colored red");

                    int key = x.parent.key;
                    RightRotate(x.parent);
                    RBTreeVisualizationApp.updateSteps(this, "The tree was rotated to the right from " + key);

                    w = x.parent.left;
                }
                if (w.right.col == Color.BLACK && w.left.col == Color.BLACK) {
                    w.col = Color.RED;
                    RBTreeVisualizationApp.updateSteps(this, w.key + "  was colored red");

                    x = x.parent;
                } else {
                    if (w.left.col == Color.BLACK) {
                        w.right.col = Color.BLACK;
                        RBTreeVisualizationApp.updateSteps(this, w.right.key + "  was colored black");

                        w.col = Color.RED;
                        RBTreeVisualizationApp.updateSteps(this, w.key + "  was colored red");

                        int key = w.key;
                        LeftRotate(w);
                        RBTreeVisualizationApp.updateSteps(this, "The tree was rotated to the left from " + key);

                        w = x.parent.left;
                    }
                    w.col = x.parent.col;
                    RBTreeVisualizationApp.updateSteps(this, w.key + "  was colored " + w.parent.col.toString());

                    x.parent.col = Color.BLACK;
                    RBTreeVisualizationApp.updateSteps(this, x.parent.key + "  was colored black");

                    w.left.col = Color.BLACK;
                    RBTreeVisualizationApp.updateSteps(this, w.left.key + "  was colored black");

                    int key = x.parent.key;
                    RightRotate(x.parent);
                    RBTreeVisualizationApp.updateSteps(this, "The tree was rotated to the right from " + key);

                    x = root;
                }
            }
        }
        x.col = Color.BLACK;

        RBTreeVisualizationApp.updateSteps(this, x.key + " was colored with black");

    }
}