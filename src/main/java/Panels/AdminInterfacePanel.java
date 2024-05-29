package Server.Panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;

public class AdminInterfacePanel extends JPanel {
    private JMenu activityMenu;

    public AdminInterfacePanel(){
        init();
    }
    public void init(){
        setLayout(new MigLayout("fill, insets 0, gap 0"));
        JPanel panel = new JPanel(new MigLayout("fillx, insets 10", "[fill]", "0[150!]0[300!]"));
        JPanel panel1 = new JPanel(new MigLayout("fill, insets 20"));
        panel1.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.WHITE));
        JLabel titleLabel = new JLabel("Team members");
        titleLabel.setFont(new Font("", Font.BOLD,20));

        JButton addNewButton = new JButton("Add New");
        addNewButton.setIcon(new FlatSVGIcon("Pictures/Icon/Pics1/plus.svg"));
        addNewButton.setHorizontalTextPosition(JButton.LEFT);

        JMenuBar activityMenuBar = new JMenuBar();
        activityMenu = new JMenu("Activity");
        JMenuItem menuItemAc1 = new JMenuItem("test1");
        JMenuItem menuItemAc2 = new JMenuItem("test2");
        JMenuItem menuItemAc3 = new JMenuItem("test3");

        activityMenu.add(menuItemAc1);
        activityMenu.add(menuItemAc2);
        activityMenu.add(menuItemAc3);

        activityMenu.setIcon(new FlatSVGIcon("Pictures/Icon/Pics1/down-arrow.svg"));
        activityMenu.setHorizontalTextPosition(JButton.LEFT);
        activityMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                activityMenu.setIcon(new FlatSVGIcon("Pictures/Icon/Pics1/up-arrow.svg"));
            }
            @Override
            public void menuDeselected(MenuEvent e) {
                activityMenu.setIcon(new FlatSVGIcon("Pictures/Icon/Pics1/down-arrow.svg"));
            }
            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });
        activityMenuBar.add(activityMenu);
        activityMenuBar.putClientProperty(FlatClientProperties.STYLE, "selectionArc:10; borderColor: #1e1e1e; background: #1e1e1e");

        JMenuBar filterMenuBar = new JMenuBar();
        JMenu filterMenu = new JMenu("Filter");
        JMenuItem filterMenuItem1 = new JMenuItem("test1");
        JMenuItem filterMenuItem2 = new JMenuItem("test2");
        JMenuItem filterMenuItem3 = new JMenuItem("test3");
        filterMenu.add(filterMenuItem1);
        filterMenu.add(filterMenuItem2);
        filterMenu.add(filterMenuItem3);
        filterMenu.setIcon(new FlatSVGIcon("Pictures/Icon/Pics1/filter.svg"));
        filterMenu.setHorizontalTextPosition(JButton.LEFT);
        filterMenuBar.add(filterMenu);
        filterMenuBar.putClientProperty(FlatClientProperties.STYLE, "selectionArc:10; borderColor: #1e1e1e; background: #1e1e1e");
        JMenuBar sortMenuBar = new JMenuBar();
        JMenu sortMenu = new JMenu("Sort");
        sortMenu.setIcon(new FlatSVGIcon("Pictures/Icon/Pics1/sort.svg"));
        sortMenu.setHorizontalTextPosition(JButton.LEFT);
        JMenuItem sortMenuItem1 = new JMenuItem("test1");
        JMenuItem sortMenuItem2 = new JMenuItem("test2");
        JMenuItem sortMenuItem3 = new JMenuItem("test3");
        sortMenu.add(sortMenuItem1);
        sortMenu.add(sortMenuItem2);
        sortMenu.add(sortMenuItem3);
        sortMenuBar.add(sortMenu);
        sortMenuBar.putClientProperty(FlatClientProperties.STYLE, "selectionArc:10; borderColor: #1e1e1e; background: #1e1e1e");



        JButton searchButton = new JButton();
        searchButton.setIcon(new FlatSVGIcon("Pictures/Icon/Pics1/search.svg"));
        JButton settingButton = new JButton();
        settingButton.setIcon(new FlatSVGIcon("Pictures/Icon/Pics1/setting.svg"));
        panel1.add(titleLabel);
        panel1.add(addNewButton, "wrap, w 120!, right");
        JSeparator separator = new JSeparator();
        separator.setBackground(Color.CYAN);
        panel1.add(separator, "w 95%, span x 2, wrap");

        JPanel menuPanel = new JPanel(new MigLayout());
        menuPanel.add(activityMenuBar);
        menuPanel.add(filterMenuBar);
        menuPanel.add(sortMenuBar);

        panel1.add(menuPanel, "left");

        JPanel buttonPanel = new JPanel(new MigLayout());
        buttonPanel.add(searchButton, "w 50!");
        buttonPanel.add(settingButton, "w 50!, wrap");

        panel1.add(buttonPanel, "right");


        panel.add(panel1,"wrap");
        JPanel panel2 = new JPanel(new MigLayout("fillx, inset 0, wrap 4", "[fill]"));
//        panel2.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.WHITE));
        JLabel checkBoxLabel = new JLabel();
        checkBoxLabel.setIcon(new FlatSVGIcon("Pictures/Icon/Pics1/checkbox.svg"));

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("", Font.BOLD,15));
        JPanel labelPanel = new JPanel(new MigLayout());
        labelPanel.add(checkBoxLabel);
        labelPanel.add(nameLabel);

        JLabel dateLabel = new JLabel("Date added");
        dateLabel.setFont(new Font("", Font.BOLD,15));
        JLabel lastLabel =  new JLabel("Last active");
        lastLabel.setFont(new Font("", Font.BOLD,15));

        JLabel actionLabel = new JLabel("Action");
        actionLabel.setFont(new Font("", Font.BOLD,15));
        JScrollPane scrollPane = new JScrollPane(panel2);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);


        panel2.add(labelPanel,"cell 0 0");
        panel2.add(dateLabel, "cell 1 0");
        panel2.add(lastLabel, "cell 2 0");
        panel2.add(actionLabel, "cell 3 0");
        for(int i = 1; i < 20; i++){
            panel2.add(interfaceObject(0, i), "cell 0 " + i);
            panel2.add(interfaceObject(1, i), "cell 1 " + i);
            panel2.add(interfaceObject(2, i), "cell 2 " + i);
            panel2.add(interfaceObject(3, i), "cell 3 " + i);
//            panel2.add(new InterfaceObjectPanel(i), "wrap, span");
        }

        panel.add(scrollPane);
        add(panel);
    }
    private Component interfaceObject(int location, int index){
        JCheckBox checkBox = new JCheckBox();
        JButton avatarButton = new JButton();
        avatarButton.setIcon(new FlatSVGIcon("Pictures/Icon/Pics1/person.svg", 30, 30));
        avatarButton.putClientProperty(FlatClientProperties.STYLE, "arc:999");
        JLabel nameLabel = new JLabel("Full name"+ index);
        nameLabel.setFont(new Font("", Font.BOLD, 15));
        JLabel emailLabel = new JLabel("quangvinhabcdg@gmail.com");
        JPanel objectPanel = new JPanel(new MigLayout());
        objectPanel.add(checkBox, "cell 0 0, span y 2");
        objectPanel.add(avatarButton, "w 50!, h 50!, cell 1 0,span y 2");
        objectPanel.add(nameLabel, "cell 2 0, left");
        objectPanel.add(emailLabel, "cell 2 1, left");

        JPanel buttonPanel = new JPanel(new MigLayout());
        JButton penButton = new JButton();
        penButton.setIcon(new FlatSVGIcon("Pictures/Icon/Pics1/pencil.svg"));
        JButton binJButton = new JButton();
        binJButton.setIcon(new FlatSVGIcon("Pictures/Icon/Pics1/bin.svg"));

        buttonPanel.add(penButton, "right");
        buttonPanel.add(binJButton, "right");

        JLabel dateAddedLabel = new JLabel("22 Oct, 2022");
        dateAddedLabel.setFont(new Font("", Font.PLAIN, 15));
        JLabel lastActiveLabel =  new JLabel("12 days ago");
        lastActiveLabel.setFont(new Font("", Font.PLAIN, 15));
        if (location == 0) {
            return objectPanel;
        } else if (location == 1) {
            return dateAddedLabel;
        } else if (location == 2) {
            return lastActiveLabel;
        } else if (location == 3) {
            return buttonPanel;
        }
        return new JLabel();
    }

//    public static void main(String[] args) {
//        FlatMacDarkLaf.setup();
//        UIManager.put("defaults",new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
//        EventQueue.invokeLater(() -> new AdminInterfacePanel().setVisible(true));
//    }
}

