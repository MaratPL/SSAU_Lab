package ssau.view;

import javax.swing.*;
import java.awt.*;

/**
 * User: morozov
 * Date: 02.12.11
 * Time: 1:11
 */
public final class UIHelper {
    public static Box createHorizontalBox(Object... args) {
        Box hBox = Box.createHorizontalBox();

        addToHorizontalBox(hBox, args);

        return hBox;
    }

    public static Box hBox(Object... args) {
        return createHorizontalBox(args);
    }

    public static void addToHorizontalBox(Box hBox, Object... args) {
        for (Object o : args) {
            if (o instanceof Integer) {
                hBox.add(Box.createHorizontalStrut(Integer.parseInt(o.toString())));
            } else if (o instanceof String) {
                hBox.add(new JLabel(o.toString()));
            } else {
                hBox.add((Component) o);
            }
        }
    }

    public static Box createVerticalBox(Object... args) {
        Box vBox = Box.createVerticalBox();

        addToVerticalBox(vBox, args);

        return vBox;
    }

    public static Box vBox(Object... args) {
        return createVerticalBox(args);
    }

    public static void addToVerticalBox(Box vBox, Object... args) {
        for (Object o : args) {
            if (o instanceof Integer) {
                vBox.add(Box.createVerticalStrut(Integer.parseInt(o.toString())));
            } else if (o instanceof String) {
                vBox.add(new JLabel(o.toString()));
            } else {
                vBox.add((Component) o);
            }
        }
    }

    public static JPanel packInPanel(JComponent component) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }


    public static JPanel packInPanel(JComponent component, Object constraints) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(component, constraints);
        return panel;
    }

    public static JPanel onTop(JComponent component) {
        return packInPanel(component, BorderLayout.NORTH);
    }

    public static JPanel onTop(String value) {
        return packInPanel(new JLabel(value), BorderLayout.NORTH);
    }

    public static JPanel packInTitledPanel(JComponent component, Object constraints, String title) {
        return packInTitledPanel(component, constraints, title, true);
    }

    public static JPanel packInTitledPanel(JComponent component, Object constraints, String title, boolean withBorder) {
        JPanel result = packInPanel(component, constraints);

        if (withBorder) {
            result.setBorder(BorderFactory.createTitledBorder(" " + title + " "));
        } else {
            result.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), " " + title + " "));
        }
        return result;
    }

    public static JPanel packInShiftedPanel(JComponent component, int top, int bottom, int right, int left) {
        JPanel panel = packInPanel(component);
        panel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        return panel;
    }

    public static JPanel packInBorderedPanel(JComponent component) {
        JPanel panel = packInPanel(component);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return panel;
    }

    public static Frame findMainFrame(Class mainFrameClazz) {
        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            if (mainFrameClazz.isAssignableFrom(frame.getClass())) {
                return frame;
            }
        }

        return null;
    }

    public static void addToPanel(JPanel panel, Object... components) {
        for (Object component : components) {
            if (component instanceof String) {
                panel.add(new JLabel(component.toString()));
            } else {
                panel.add((JComponent) component);
            }
        }
    }

    public static JPanel createBorderLayoutPanel(JComponent[] components, Object[] constraints) {
        if (components.length != constraints.length) {
            throw new IllegalArgumentException("components.length != constraints.length");
        }

        JPanel result = new JPanel(new BorderLayout());

        for (int i = 0; i < components.length; i++) {
            result.add(components[i], constraints[i]);
        }

        return result;
    }

    public static JPanel createGridPanel(int initialX, int initialY, int xPad, int yPad, int rows, int cols, Object...
            components) {
        JPanel panel = new JPanel(new SpringLayout());

        for (Object component : components) {
            if (component instanceof String) {
                panel.add(new JLabel(component.toString()));
            } else if (component == null) {
                System.err.println("Component is null. Replaced with empty JPanel");

                panel.add(new JPanel());
            } else {
                panel.add((JComponent) component);
            }
        }

        makeCompactGrid(panel, rows, cols, initialX, initialY, xPad, yPad);

        return panel;
    }

    private static JComponent extractComponent(Object component) {
        if (component instanceof JComponent) {
            return (JComponent) component;
        } else if ((component instanceof String) || (component == null)) {
            return new JLabel((String) component);
        }

        throw new IllegalArgumentException("Cannot extract component for " + component.getClass().getSimpleName());
    }

    public static ButtonGroup createButtonGroup(AbstractButton... buttonz) {
        ButtonGroup result = new ButtonGroup();

        for (AbstractButton b : buttonz) {
            result.add(b);
        }

        return result;
    }

    public static JFrame packInFrame(String title, JComponent component, Object constraint) {
        JFrame frame = new JFrame(title);

        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(component, constraint);
        frame.pack();
        frame.setLocationRelativeTo(null);

        return frame;
    }

    public static void fixSize(Component component) {
        Dimension size = component.getPreferredSize();
        size.width = component.getMaximumSize().width;
        component.setMaximumSize(size);
    }

    public static void fixSize(Component component, Dimension size) {
        component.setMinimumSize(size);
        component.setMaximumSize(size);
        component.setPreferredSize(size);
    }

    public static void setGroupAligmentX(JComponent[] cs, float aligment) {
        for (JComponent c : cs) {
            c.setAlignmentX(aligment);
        }
    }

    public static void setGroupAligmentY(JComponent[] cs, float aligment) {
        for (JComponent c : cs) {
            c.setAlignmentY(aligment);
        }
    }

    public static int getComponentIndex(JComponent parent, Component component) {
        for (int i = parent.getComponents().length; --i >= 0; ) {
            if (parent.getComponents()[i] == component) {
                return i;
            }
        }
        return -1;
    }

    public static void makeGrid(Container parent,
                                int rows, int cols,
                                int initialX, int initialY,
                                int xPad, int yPad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout) parent.getLayout();
        } catch (ClassCastException exc) {
            System.err.println("The first argument to makeGrid must use SpringLayout.");
            return;
        }

        Spring xPadSpring = Spring.constant(xPad);
        Spring yPadSpring = Spring.constant(yPad);
        Spring initialXSpring = Spring.constant(initialX);
        Spring initialYSpring = Spring.constant(initialY);
        int max = rows * cols;

        Spring maxWidthSpring = layout.getConstraints(parent.getComponent(0)).
                getWidth();
        Spring maxHeightSpring = layout.getConstraints(parent.getComponent(0)).
                getWidth();
        for (int i = 1; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(
                    parent.getComponent(i));

            maxWidthSpring = Spring.max(maxWidthSpring, cons.getWidth());
            maxHeightSpring = Spring.max(maxHeightSpring, cons.getHeight());
        }

        for (int i = 0; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(
                    parent.getComponent(i));

            cons.setWidth(maxWidthSpring);
            cons.setHeight(maxHeightSpring);
        }

        SpringLayout.Constraints lastCons = null;
        SpringLayout.Constraints lastRowCons = null;
        for (int i = 0; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(
                    parent.getComponent(i));
            if (i % cols == 0) { //start of new row
                lastRowCons = lastCons;
                cons.setX(initialXSpring);
            } else { //x position depends on previous component
                cons.setX(Spring.sum(lastCons.getConstraint(SpringLayout.EAST),
                        xPadSpring));
            }

            if (i / cols == 0) { //first row
                cons.setY(initialYSpring);
            } else { //y position depends on previous row
                cons.setY(Spring.sum(lastRowCons.getConstraint(SpringLayout.SOUTH),
                        yPadSpring));
            }
            lastCons = cons;
        }

        //Set the parent's size.
        SpringLayout.Constraints pCons = layout.getConstraints(parent);
        pCons.setConstraint(SpringLayout.SOUTH,
                Spring.sum(
                        Spring.constant(yPad),
                        lastCons.getConstraint(SpringLayout.SOUTH)));
        pCons.setConstraint(SpringLayout.EAST,
                Spring.sum(
                        Spring.constant(xPad),
                        lastCons.getConstraint(SpringLayout.EAST)));
    }

    private static SpringLayout.Constraints getConstraintsForCell(
            int row, int col,
            Container parent,
            int cols) {
        SpringLayout layout = (SpringLayout) parent.getLayout();
        Component c = parent.getComponent(row * cols + col);
        return layout.getConstraints(c);
    }

    public static void makeCompactGrid(Container parent,
                                       int rows, int cols,
                                       int initialX, int initialY,
                                       int xPad, int yPad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout) parent.getLayout();
        } catch (ClassCastException exc) {
            System.err.println("The first argument to makeCompactGrid must use SpringLayout.");
            return;
        }

        //Align all cells in each column and make them the same width.
        Spring x = Spring.constant(initialX);
        for (int c = 0; c < cols; c++) {
            Spring width = Spring.constant(0);
            for (int r = 0; r < rows; r++) {
                width = Spring.max(width,
                        getConstraintsForCell(r, c, parent, cols).
                                getWidth());
            }
            for (int r = 0; r < rows; r++) {
                SpringLayout.Constraints constraints =
                        getConstraintsForCell(r, c, parent, cols);
                constraints.setX(x);
                constraints.setWidth(width);
            }
            x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)));
        }

        //Align all cells in each row and make them the same height.
        Spring y = Spring.constant(initialY);
        for (int r = 0; r < rows; r++) {
            Spring height = Spring.constant(0);
            for (int c = 0; c < cols; c++) {
                height = Spring.max(height,
                        getConstraintsForCell(r, c, parent, cols).
                                getHeight());
            }
            for (int c = 0; c < cols; c++) {
                SpringLayout.Constraints constraints =
                        getConstraintsForCell(r, c, parent, cols);
                constraints.setY(y);
                constraints.setHeight(height);
            }
            y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)));
        }

        //Set the parent's size.
        SpringLayout.Constraints pCons = layout.getConstraints(parent);
        pCons.setConstraint(SpringLayout.SOUTH, y);
        pCons.setConstraint(SpringLayout.EAST, x);
    }
}
