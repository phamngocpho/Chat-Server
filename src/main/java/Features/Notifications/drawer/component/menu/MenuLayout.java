package Features.Notifications.drawer.component.menu;

import java.awt.*;

public class MenuLayout implements LayoutManager {

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            // Use parent width to avoid the issues right-to-left scroll pane
            int width = parent.getParent().getWidth();
            int height = insets.top + insets.bottom;
            int count = parent.getComponentCount();
            for (int i = 0; i < count; i++) {
                Component com = parent.getComponent(i);
                if (com.isVisible()) {
                    height += com.getPreferredSize().height;
                    width = Math.max(width, com.getPreferredSize().width);
                }
            }
            return new Dimension(width, height);
        }
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            return new Dimension(0, 0);
        }
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int width = parent.getWidth() - (insets.left + insets.right);
            int x = insets.left;
            int y = insets.top;
            int count = parent.getComponentCount();
            for (int i = 0; i < count; i++) {
                Component com = parent.getComponent(i);
                if (com.isVisible()) {
                    int h = com.getPreferredSize().height;
                    com.setBounds(x, y, width, h);
                    y += h;
                }
            }
        }
    }
}
