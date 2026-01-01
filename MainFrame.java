package StokTakipJava;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel contentPanel;

    public MainFrame() {
        setTitle("RENS TEKNOLOJİ STOK TAKİP");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("RENS TEKNOLOJİ STOK TAKİP SİSTEMİ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        showLoginPanel();
    }

    public void showLoginPanel() {
        contentPanel.removeAll();
        contentPanel.add(new LoginPanel(this), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void showCreateProductPanel() {
        contentPanel.removeAll();
        contentPanel.add(new CreateProductPanel(this), BorderLayout.CENTER); // this ekledik
        contentPanel.revalidate();
        contentPanel.repaint();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
