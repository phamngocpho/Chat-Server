package Features.Notifications.drawer.component;

import Features.Notifications.drawer.component.footer.SimpleFooterData;
import Features.Notifications.drawer.component.header.SimpleHeader;
import Features.Notifications.drawer.component.header.SimpleHeaderData;
import Features.Notifications.utils.FlatLafStyleUtils;
import Features.Notifications.drawer.component.footer.SimpleFooter;
import Features.Notifications.drawer.component.menu.SimpleMenu;
import Features.Notifications.drawer.component.menu.SimpleMenuOption;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;

public abstract class SimpleDrawerBuilder implements DrawerBuilder {

    protected SimpleHeader header;
    protected JSeparator headerSeparator;
    protected JScrollPane menuScroll;
    protected SimpleMenu menu;
    protected SimpleFooter footer;


    public SimpleDrawerBuilder() {
        header = new SimpleHeader(getSimpleHeaderData());
        headerSeparator = new JSeparator();
        SimpleMenuOption simpleMenuOption = getSimpleMenuOption();
        menu = new SimpleMenu(simpleMenuOption);
        menuScroll = createScroll(menu);
        footer = new SimpleFooter(getSimpleFooterData());
    }

    protected JScrollPane createScroll(JComponent component) {
        JScrollPane scroll = new JScrollPane(component);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        String background = FlatLafStyleUtils.getStyleValue(component, "background", "null");
        scroll.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:" + background);
        scroll.getVerticalScrollBar().setUnitIncrement(10);
        scroll.getHorizontalScrollBar().setUnitIncrement(10);
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "" +
                "width:9;" +
                "trackArc:999;" +
                "thumbInsets:0,3,0,3;" +
                "trackInsets:0,3,0,3;" +
                "background:" + background);
        if (!background.equals("null")) {
            FlatLafStyleUtils.appendStyleIfAbsent(scroll.getVerticalScrollBar(), "" +
                    "track:" + background);
        }
        scroll.setBorder(BorderFactory.createEmptyBorder());
        return scroll;
    }

    @Override
    public Component getHeader() {
        return header;
    }

    @Override
    public Component getHeaderSeparator() {
        return headerSeparator;
    }

    @Override
    public Component getMenu() {
        return menuScroll;
    }

    @Override
    public Component getFooter() {
        return footer;
    }

    @Override
    public int getDrawerWidth() {
        return 275;
    }

    public void build(DrawerPanel drawerPanel) {
    }

    public void rebuildMenu() {
        if (menu != null) {
            menu.rebuildMenu();
        }
    }

    public abstract SimpleHeaderData getSimpleHeaderData();

    public abstract SimpleMenuOption getSimpleMenuOption();

    public abstract SimpleFooterData getSimpleFooterData();
}
