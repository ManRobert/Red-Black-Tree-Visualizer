import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * RBTreeVisualizationApp is the main class for a Red-Black Tree visualization application.
 * It provides a graphical user interface (GUI) for user interaction, enables RBTree operations visualization,
 * and maintains a step-by-step record of these operations.
 * <p>
 * visualizer: Instance of RBTreeVisualizer for visualizing the RBTree.
 * steps: List holding RBTree instances.
 * stringSteps: List with text of operations.
 * step: Integer - current step in RBTree operations.
 */
public class RBTreeVisualizationApp {
    private static RBTreeVisualizer visualizer;
    private static List<RBTree> steps = new ArrayList<>();
    private static List<String> stringSteps = new ArrayList<>();
    private static int step = -1;


    /**
     * Update the list of steps for the Red-Black Tree.
     *
     * @param rbTree new Rb tree
     * @param text   new operation text
     */
    public static void updateSteps(RBTree rbTree, String text) {
        steps.add(rbTree.deepCopy());
        stringSteps.add(text);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RBTree rbTree = new RBTree();

            JFrame frame = new JFrame("RB Tree Visualization");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            visualizer = new RBTreeVisualizer(rbTree);
            mainPanel.add(visualizer, BorderLayout.CENTER);

            JButton previousButton = new JButton("Previous");
            JButton nextButton = new JButton("Next");


            JPanel inputPanel = new JPanel();
            JTextField dataField = new JTextField(10);
            JButton insertButton = new JButton("Insert Node");
            JButton deleteButton = new JButton("Delete Node");
            JButton searchButton = new JButton("Search Node");
            JButton finalResult = new JButton("Final result");
            JButton predecessor = new JButton("Predecessor");
            JButton successor = new JButton("Successor");


            finalResult.addActionListener(e -> {
                try {
                    if (steps.size() > 0) {
                        step = steps.size() - 1;
                        visualizer.setTree(steps.get(step), stringSteps.get(step));
                        visualizer.repaint();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter an integer.");
                }
            });


            insertButton.addActionListener(e -> {
                try {
                    steps.clear();
                    stringSteps.clear();
                    step = -1;
                    int data = Integer.parseInt(dataField.getText());
                    rbTree.RBInsert(rbTree.createNode(data));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter an integer.");
                }
            });

            deleteButton.addActionListener(e -> {
                try {
                    steps.clear();
                    stringSteps.clear();
                    step = -1;
                    int data = Integer.parseInt(dataField.getText());
                    RBNode node = rbTree.search(rbTree.getRoot(), data);
                    if (!rbTree.isNil(node)) {
                        rbTree.delete(node);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter an integer.");
                }
            });

            searchButton.addActionListener(e -> {
                try {
                    steps.clear();
                    stringSteps.clear();
                    int data = Integer.parseInt(dataField.getText());
                    rbTree.search(rbTree.getRoot(), data);
                    step = -1;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter an integer.");
                }
            });

            previousButton.addActionListener(e -> {
                if (step > 0) {
                    step--;
                    visualizer.setTree(steps.get(step), stringSteps.get(step));
                    visualizer.repaint();
                }
            });

            nextButton.addActionListener(e -> {
                if (step < steps.size() - 1) {
                    step++;
                    visualizer.setTree(steps.get(step), stringSteps.get(step));
                    visualizer.repaint();
                }

            });

            predecessor.addActionListener(e -> {
                steps.clear();
                step = -1;
                stringSteps.clear();
                int data = Integer.parseInt(dataField.getText());
                RBNode rbNode = rbTree.search(rbTree.getRoot(), data);
                rbNode = rbTree.predecessor(rbNode);
                rbNode.selected = true;
                if (!rbTree.isNil(rbNode))
                    RBTreeVisualizationApp.updateSteps(rbTree, "Predecessor found: " + rbNode.key);
                else
                    RBTreeVisualizationApp.updateSteps(rbTree, "Predecessor not found: ");

                rbNode.selected = false;

            });

            successor.addActionListener(e -> {
                steps.clear();
                step = -1;
                stringSteps.clear();
                int data = Integer.parseInt(dataField.getText());
                RBNode rbNode = rbTree.search(rbTree.getRoot(), data);
                rbNode = rbTree.successor(rbNode);
                rbNode.selected = true;
                if (!rbTree.isNil(rbNode))
                    RBTreeVisualizationApp.updateSteps(rbTree, "Successor found: " + rbNode.key);
                else
                    RBTreeVisualizationApp.updateSteps(rbTree, "Successor not found: ");

                rbNode.selected = false;

            });

            inputPanel.add(new JLabel("Enter Node Data:"));
            inputPanel.add(dataField);
            inputPanel.add(insertButton);
            inputPanel.add(deleteButton);
            inputPanel.add(searchButton);
            inputPanel.add(previousButton);
            inputPanel.add(nextButton);
            inputPanel.add(finalResult);
            inputPanel.add(predecessor);
            inputPanel.add(successor);


            mainPanel.add(inputPanel, BorderLayout.SOUTH);

            frame.getContentPane().add(mainPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

