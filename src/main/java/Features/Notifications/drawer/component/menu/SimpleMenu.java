package Features.Notifications.drawer.component.menu;

import Features.Notifications.drawer.component.menu.data.Item;
import Features.Notifications.drawer.component.menu.data.MenuItem;
import Features.Notifications.popup.GlassPanePopup;
import Features.Notifications.utils.FlatLafStyleUtils;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.ColorFunctions;
import com.formdev.flatlaf.util.UIScale;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Arrays;

public class SimpleMenu extends JPanel {

    private final SimpleMenuOption simpleMenuOption;

    public SimpleMenu(SimpleMenuOption simpleMenuOption) {
        this.simpleMenuOption = simpleMenuOption;
        init();
    }

    public void rebuildMenu() {
        removeAll();
        buildMenu();
    }

    private void init() {
        setLayout(new MenuLayout());
        if (simpleMenuOption.simpleMenuStyle != null) {
            simpleMenuOption.simpleMenuStyle.styleMenu(this);
        }
        buildMenu();
    }

    private void buildMenu() {
        Features.Notifications.drawer.component.menu.data.MenuItem[] menus = simpleMenuOption.menus;
        if (menus != null) {
            int index = 0;
            int validationIndex = -1;
            for (int i = 0; i < menus.length; i++) {
                Features.Notifications.drawer.component.menu.data.MenuItem menuItem = menus[i];
                if (menuItem.isMenu()) {
                    Item item = (Item) menuItem;
                    int[] arrIndex = {index};
                    if (item.isSubmenuAble()) {
                        // Create submenu
                        int[] arrValidationIndex = {++validationIndex};
                        boolean validation = simpleMenuOption.menuValidation.menuValidation(copyArray(arrValidationIndex));
                        if (validation) {
                            add(createSubmenuItem(item, arrIndex, arrValidationIndex, 0));
                        }
                        if (validation || simpleMenuOption.menuValidation.keepMenuValidationIndex) {
                            index++;
                        }
                    } else {
                        // Create single menu item
                        int[] arrValidationIndex = {++validationIndex};
                        boolean validation = simpleMenuOption.menuValidation.menuValidation(arrValidationIndex);
                        if (validation) {
                            JButton button = createMenuItem(item.getName(), item.getIcon(), arrIndex, 0);
                            applyMenuEvent(button, arrIndex);
                            add(button);
                        }
                        if (validation || simpleMenuOption.menuValidation.keepMenuValidationIndex) {
                            index++;
                        }
                    }
                } else {
                    // Create label
                    if (checkLabelValidation(i, index)) {
                        Item.Label label = (Item.Label) menuItem;
                        add(createLabel(label.getName()));
                    }
                }
            }
        }
    }

    private int[] copyArray(int[] arr) {
        return Arrays.copyOf(arr, arr.length);
    }

    private String getBasePath() {
        if (simpleMenuOption.baseIconPath == null) {
            return "";
        }
        if (simpleMenuOption.baseIconPath.endsWith("/")) {
            return simpleMenuOption.baseIconPath;
        } else {
            return simpleMenuOption.baseIconPath + "/";
        }
    }

    protected Icon getIcon(String icon, int menuLevel) {
        if (icon != null) {
            String path = getBasePath();
            float iconScale;
            if (menuLevel < simpleMenuOption.iconScale.length) {
                iconScale = simpleMenuOption.iconScale[menuLevel];
            } else {
                iconScale = simpleMenuOption.iconScale[simpleMenuOption.iconScale.length - 1];
            }
            Icon iconObject = simpleMenuOption.buildMenuIcon(path + icon, iconScale);
            return iconObject;
        } else {
            return null;
        }
    }

    protected JButton createMenuItem(String name, String icon, int[] index, int menuLevel) {
        JButton button = new JButton(name);
        Icon iconObject = getIcon(icon, menuLevel);
        if (iconObject != null) {
            button.setIcon(iconObject);
        }
        button.setHorizontalAlignment(JButton.LEADING);
        if (simpleMenuOption.simpleMenuStyle != null) {
            simpleMenuOption.simpleMenuStyle.styleMenuItem(button, copyArray(index));
        }
        FlatLafStyleUtils.appendStyleIfAbsent(button, "" +
                "arc:0;" +
                "margin:6,20,6,20;" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0;" +
                "background:null;" +
                "iconTextGap:5;");
        return button;
    }

    protected void applyMenuEvent(JButton button, int[] index) {
        button.addActionListener(e -> {
            MenuAction action = runEvent(index);
            if (action != null) {
                // Next update on action menu selected
            }
        });
    }

    private MenuAction runEvent(int[] index) {
        if (!simpleMenuOption.events.isEmpty()) {
            MenuAction action = new MenuAction();
            if (simpleMenuOption.menuItemAutoSelect) {
                action.selected();
            }
            for (MenuEvent event : simpleMenuOption.events) {
                event.selected(action, copyArray(index));
            }
            return action;
        }
        return null;
    }

    protected Component createSubmenuItem(Item menu, int index[], int[] validationIndex, int menuLevel) {
        JPanel panelItem = new SubMenuItem(menu, index, validationIndex, menuLevel);
        return panelItem;
    }

    protected boolean checkLabelValidation(int labelIndex, int menuIndex) {
        if (simpleMenuOption.menuValidation.labelValidation(labelIndex)) {
            if (simpleMenuOption.menuValidation.removeLabelWhenEmptyMenu) {
                boolean fondMenu = false;
                for (int i = labelIndex + 1; i < simpleMenuOption.menus.length; i++) {
                    MenuItem menuItem = simpleMenuOption.menus[i];
                    if (menuItem.isMenu()) {
                        if (simpleMenuOption.menuValidation.menuValidation(new int[]{menuIndex})) {
                            fondMenu = true;
                            break;
                        }
                    } else {
                        break;
                    }
                    menuIndex++;
                }
                return fondMenu;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    protected Component createLabel(String name) {
        JLabel label = new JLabel(name);
        if (simpleMenuOption.simpleMenuStyle != null) {
            simpleMenuOption.simpleMenuStyle.styleLabel(label);
        }
        FlatLafStyleUtils.appendStyleIfAbsent(label, "" +
                "border:8,10,8,10;" +
                "[light]foreground:lighten($Label.foreground,30%);" +
                "[dark]foreground:darken($Label.foreground,30%)");
        return label;
    }

    public SimpleMenuOption getSimpleMenuOption() {
        return simpleMenuOption;
    }

    protected class SubMenuItem extends JPanel {

        private int menuLevel;
        private int levelSpace = 18;
        private SubmenuLayout menuLayout;
        private boolean menuShow;
        private final Item menu;
        private final int[] index;
        private final int[] validationIndex;
        private int iconWidth;

        public void setAnimate(float animate) {
            menuLayout.setAnimate(animate);
        }

        public SubMenuItem(Item menu, int[] index, int[] validationIndex, int menuLevel) {
            this.menu = menu;
            this.index = index;
            this.validationIndex = validationIndex;
            this.menuLevel = menuLevel;
            init();
        }

        private void init() {
            menuLayout = new SubmenuLayout();
            setLayout(menuLayout);
            // Use opaque true on the first submenu panel to fix g2d draw arrow line
            setOpaque(menuLevel == 0);
            if (simpleMenuOption.simpleMenuStyle != null) {
                simpleMenuOption.simpleMenuStyle.styleMenuPanel(this, copyArray(this.index));
            }
            FlatLafStyleUtils.appendStyleIfAbsent(FlatClientProperties.STYLE, "" +
                    "background:null");

            iconWidth = 22;
            int index = 0;
            int validationIndex = -1;
            int nextMenuLevel = menuLevel + 1;
            // Create menu item
            JButton mainButton;
            if (menuLevel == 0) {
                // Create first level menu item
                mainButton = createMenuItem(menu.getName(), menu.getIcon(), this.index, menuLevel);
            } else {
                int addSpace = menuLevel > 1 ? (menuLevel - 1) * levelSpace : 0;
                mainButton = createSubMenuItem(menu.getName(), this.index, iconWidth + addSpace);
            }
            if (mainButton.getIcon() != null) {
                iconWidth = UIScale.unscale(mainButton.getIcon().getIconWidth());
            }
            createMainMenuEvent(mainButton);
            applyMenuEvent(mainButton, this.index);
            add(mainButton);
            for (int i = 0; i < menu.getSubMenu().size(); i++) {
                int[] arrIndex = createArrayIndex(this.index, index);
                int[] arrValidationIndex = createArrayIndex(this.validationIndex, ++validationIndex);
                boolean validation = simpleMenuOption.menuValidation.menuValidation(copyArray(arrValidationIndex));
                if (validation) {
                    Item item = menu.getSubMenu().get(i);
                    if (item.isSubmenuAble()) {
                        add(createSubmenuItem(item, arrIndex, arrValidationIndex, nextMenuLevel));
                    } else {
                        // Create single menu item
                        int addSpace = menuLevel * levelSpace;
                        JButton button = createSubMenuItem(item.getName(), arrIndex, iconWidth + addSpace);
                        applyMenuEvent(button, arrIndex);
                        add(button);
                    }
                }
                if (validation || simpleMenuOption.menuValidation.keepMenuValidationIndex) {
                    index++;
                }
            }
        }

        private int[] createArrayIndex(int[] index, int subIndex) {
            int[] newArr = new int[index.length + 1];
            for (int i = 0; i < index.length; i++) {
                newArr[i] = index[i];
            }
            newArr[newArr.length - 1] = subIndex;
            return newArr;
        }

        private void createMainMenuEvent(JButton button) {
            button.addActionListener(e -> {
                menuShow = !menuShow;
                new MenuAnimation(this).run(menuShow);
            });
        }


        protected JButton createSubMenuItem(String name, int[] index, int gap) {
            JButton button = new JButton(name);
            button.setHorizontalAlignment(JButton.LEADING);
            if (simpleMenuOption.simpleMenuStyle != null) {
                simpleMenuOption.simpleMenuStyle.styleMenuItem(button, copyArray(index));
            }
            boolean ltr = GlassPanePopup.isInit() == false || GlassPanePopup.getMainFrame().getComponentOrientation().isLeftToRight();
            String margin = ltr ? ("7," + (gap + 25) + ",7,30") : ("7,30,7," + (gap + 25));
            FlatLafStyleUtils.appendStyleIfAbsent(button, "" +
                    "arc:0;" +
                    "margin:" + margin + ";" +
                    "borderWidth:0;" +
                    "focusWidth:0;" +
                    "innerFocusWidth:0;" +
                    "background:null");
            return button;
        }

        @Override
        protected void paintChildren(Graphics g) {
            super.paintChildren(g);
            if (getComponentCount() > 0) {
                boolean ltr = getComponentOrientation().isLeftToRight();
                Color foreground = getComponent(0).getForeground();
                int menuHeight = getComponent(0).getHeight();
                int width = getWidth();
                int height = getHeight();
                Graphics2D g2 = (Graphics2D) g.create();
                FlatUIUtils.setRenderingHints(g2);
                // Create submenu line
                int last = getLastLocation();
                int round = UIScale.scale(8);
                int gap = UIScale.scale((20 + (iconWidth / 2)) + (levelSpace * menuLevel));
                Path2D.Double p = new Path2D.Double();
                int x = ltr ? gap : width - gap;
                p.moveTo(x, menuHeight);
                p.lineTo(x, last - round);
                int count = getComponentCount();
                for (int i = 1; i < count; i++) {
                    Component com = getComponent(i);
                    int y;
                    if (com instanceof SubMenuItem) {
                        y = com.getY() + ((SubMenuItem) com).getFirstItemLocation();
                    } else {
                        y = com.getY() + (com.getHeight() / 2);
                    }
                    p.append(createCurve(round, x, y, ltr), false);
                }
                Color color = ColorFunctions.mix(getBackground(), foreground, 0.7f);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(UIScale.scale(1f)));
                g2.draw(p);
                // Create arrow
                paintArrow(g2, width, menuHeight, menuLayout.getAnimate(), ltr);
                g2.dispose();
            }
        }

        private int getLastLocation() {
            Component com = getComponent(getComponentCount() - 1);
            if (com instanceof SubMenuItem) {
                SubMenuItem subMenuItem = (SubMenuItem) com;
                return com.getY() + subMenuItem.getFirstItemLocation();
            } else {
                return com.getY() + com.getHeight() / 2;
            }
        }

        private int getFirstItemLocation() {
            if (getComponentCount() == 0) {
                return 0;
            }
            return getComponent(0).getHeight() / 2;
        }

        private Shape createCurve(int round, int x, int y, boolean ltr) {
            Path2D p2 = new Path2D.Double();
            p2.moveTo(x, y - round);
            p2.curveTo(x, y - round, x, y, x + (ltr ? round : -round), y);
            return p2;
        }

        private void paintArrow(Graphics2D g2, int width, int height, float animate, boolean ltr) {
            int arrowWidth = UIScale.scale(10);
            int arrowHeight = UIScale.scale(4);
            int gap = UIScale.scale(15);
            int x = ltr ? (width - arrowWidth - gap) : gap;
            int y = (height - arrowHeight) / 2;
            Path2D p = new Path2D.Double();
            p.moveTo(0, animate * arrowHeight);
            p.lineTo(arrowWidth / 2, (1f - animate) * arrowHeight);
            p.lineTo(arrowWidth, animate * arrowHeight);
            g2.translate(x, y);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2.draw(p);
        }

        protected class SubmenuLayout implements LayoutManager {

            private float animate;

            public void setAnimate(float animate) {
                this.animate = animate;
                revalidate();
            }

            public float getAnimate() {
                return animate;
            }


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
                    int width = insets.left + insets.right;
                    int height = insets.top + insets.bottom;
                    int count = parent.getComponentCount();
                    int first = -1;
                    for (int i = 0; i < count; i++) {
                        Component com = parent.getComponent(i);
                        if (com.isVisible()) {
                            if (first == -1) {
                                first = com.getPreferredSize().height;
                            }
                            height += com.getPreferredSize().height;
                        }
                    }
                    int space = height - first;
                    height = (int) (first + space * animate);
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
                    int x = insets.left;
                    int y = insets.top;
                    int width = parent.getWidth() - (insets.left + insets.right);
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
    }
}
