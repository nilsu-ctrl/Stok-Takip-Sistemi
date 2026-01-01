//Ravza Yaşaroğlu

package StokTakipJava;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private MainFrame mainFrame;

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("RENS TEKNOLOJİ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(120,0,40,0)); // daha aşağıda
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        formPanel.setBackground(new Color(245,245,245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15,15,15,15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUsername = new JLabel("Kullanıcı Adı:");
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblUsername, gbc);

        JTextField txtUsername = new JTextField(15);
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(txtUsername, gbc);

        JLabel lblPassword = new JLabel("Şifre:");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblPassword, gbc);

        JPasswordField txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(txtPassword, gbc);

        JButton btnLogin = new JButton("Giriş Yap");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setBackground(new Color(0,153,76)); 
        btnLogin.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        formPanel.add(btnLogin, gbc);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(formPanel);
        add(centerPanel, BorderLayout.CENTER);

        
        btnLogin.addActionListener(e -> {
            if ("admin".equals(txtUsername.getText()) &&
                "1234".equals(new String(txtPassword.getPassword()))) {
                JOptionPane.showMessageDialog(this, "Giriş başarılı");
                mainFrame.showCreateProductPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Hatalı giriş");
            }
        });
    }
}


