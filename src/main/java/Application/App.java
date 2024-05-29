package Server.Main;

import Features.CustomNotification;
import Features.FormsManager;
import Features.Notifications.popup.GlassPanePopup;
import Server.Database.DatabaseConnection;
import Values.Value;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class Admin extends JFrame {
    public Admin () {
        GlassPanePopup.install(this);
        init();
    }
    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                databaseConnectEvent();
            }
        });
//        setSize((int) Value.dimension.getWidth(), (int) (Value.dimension.getHeight() - Value.taskBarSize));
        setSize(new Dimension(750, 500));
        System.out.println(Value.dimension);
        Notifications.getInstance().setJFrame(this);
        CustomNotification customNotification = new CustomNotification();
        customNotification.setJFrame(this);
//        setContentPane(new AdminInterfacePanel());
        setLocationRelativeTo(null);
//        setMinimumSize(new Dimension((int) (Value.dimension.getWidth() / 2), (int) Value.dimension.getHeight() * 3 / 5));
    }

    private void databaseConnectEvent() {
        try {
            DatabaseConnection.getInstance().connectToDatabase();
        } catch (SQLException ex) {
            JPanel panel = new JPanel(new MigLayout("fill, insets 50"));
            JLabel label = new JLabel();
            label.setIcon(new FlatSVGIcon("Pictures/Icon/Pics1/error.svg", 40, 40));
            JTextPane textPane = new JTextPane();
            textPane.setBackground(null);
            textPane.setFont(new Font("", Font.PLAIN, 18));
            String error = ex.getMessage();
            textPane.setText(error);
            panel.add(label, "right");
            panel.add(textPane, "gap left 15");
            FormsManager.getInstance().showForm(panel);
        }
    }


    public static void main(String[] args) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("Source.Properties");
        FlatMacDarkLaf.setup();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        EventQueue.invokeLater(() -> new Admin().setVisible(true));
    }
}
