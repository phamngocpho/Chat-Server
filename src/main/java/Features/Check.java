package Features;

import Core.MethodUtil;
import Values.Value;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.UIScale;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.regex.*;

public class Check extends JPanel {


    private JPasswordField passwordField;
    private DocumentListener documentListener;
    private JLabel label;
    private int type;

    public Check() {
        init();
    }

    private void init() {
        putClientProperty(FlatClientProperties.STYLE, "background:null");
        setLayout(new MigLayout("fill,insets 0", "3[100,fill,grow0][]", "[fill,grow 0]"));
        label = new JLabel("none");
        label.setVisible(false);
        add(new LabelStatus());
        add(label);
    }

    private Color getStrengthColor(int type) {
        if (type == 1) {
            return Value.bright_red;
        } else if (type == 2) {
            return Value.bright_orange;
        } else {
            return Value.bright_green;
        }
    }

    private void checkPassword(String password) {
        this.type = password.isEmpty() ? 0 : MethodUtil.checkPasswordStrength(password);
        if (type == 0) {
            label.setText("none");
            label.setVisible(false);
        } else {
            label.setVisible(true);
            if (type == 1) {
                label.setText("Too weak");
            } else if (type == 2) {
                label.setText("Medium");
            } else {
                label.setText("Strong");
            }
            label.setForeground(getStrengthColor(type));
        }
        repaint();
    }

    public void initPasswordField(JPasswordField txt) {
        if (documentListener == null) {
            documentListener = new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    checkPassword(String.valueOf(txt.getPassword()));
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    checkPassword(String.valueOf(txt.getPassword()));
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    checkPassword(String.valueOf(txt.getPassword()));
                }
            };
        }
        if (passwordField != null) {
            passwordField.getDocument().removeDocumentListener(documentListener);
        }
        txt.getDocument().addDocumentListener(documentListener);
        passwordField = txt;
    }
    public boolean isMatch (JPasswordField pass1, JPasswordField pass2) {
        String pw1 = String.valueOf(pass1.getPassword());
        String pw2 = String.valueOf(pass2.getPassword());
        return pw1.equals(pw2);
    }
    public void isMatch (JPasswordField txtPassword, JPasswordField txtConfirmPassword, JLabel lbMatch) {
        String password1 = new String(txtPassword.getPassword());
        String password2 = new String(txtConfirmPassword.getPassword());

        if (password1.equals(password2) && !password1.isEmpty()) {
            lbMatch.setText("Passwords match");
            lbMatch.setForeground(Color.GREEN);
        } else {
            lbMatch.setText("Passwords do not match");
            lbMatch.setForeground(Color.RED);
        }
        if (password1.isEmpty() || password2.isEmpty()) {
            lbMatch.setText(null);
        }
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isMatch(txtPassword, txtConfirmPassword, lbMatch);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isMatch(txtPassword, txtConfirmPassword, lbMatch);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isMatch(txtPassword, txtConfirmPassword, lbMatch);
            }
        };
        txtPassword.getDocument().addDocumentListener(documentListener);
        txtConfirmPassword.getDocument().addDocumentListener(documentListener);
    }

    public boolean isEmail (String email) {
        String ex = "^[a-zA-Z]+[_a-zA-Z0-9.]*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]+$";
        Pattern pattern = Pattern.compile(ex);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
    public boolean isPhoneNumber (String phoneNumber) {
        String ex = "^(84|0)([35789]\\d{8})$";
        Pattern pattern = Pattern.compile(ex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.find();
    }
    private class LabelStatus extends JLabel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();
            int size = (int) (height * 0.3f);
            Graphics2D g2 = (Graphics2D) g.create();
            FlatUIUtils.setRenderingHints(g2);
            int gap = UIScale.scale(5);
            int w = (width - gap * 2) / 3;
            int y = (height - size) / 2;
            Color disableColor = Color.decode(FlatLaf.isLafDark() ? "#404040" : "#CECECE");
            if (type >= 1) {
                g2.setColor(getStrengthColor(1));
            } else {
                g2.setColor(disableColor);
            }
            FlatUIUtils.paintComponentBackground(g2, 0, y, w, size, 0, 999);
            if (type >= 2) {
                g2.setColor(getStrengthColor(2));
            } else {
                g2.setColor(disableColor);
            }
            FlatUIUtils.paintComponentBackground(g2, w + gap, y, w, size, 0, 999);
            if (type >= 3) {
                g2.setColor(getStrengthColor(3));
            } else {
                g2.setColor(disableColor);
            }
            FlatUIUtils.paintComponentBackground(g2, (w + gap) * 2, y, w, size, 0, 999);
            g2.dispose();
        }
    }
}
