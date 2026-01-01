//Eda Nur Alatepe

package StokTakipJava;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class CreateProductPanel extends JPanel {

    private boolean criticalWarned = false;
    private JTextField txtName, txtPurchase, txtSell, txtStock, txtCritical;
    private JComboBox<String> cmbCategory;
    private JTable table;
    private DefaultTableModel model;
    private MainFrame mainFrame;

    public CreateProductPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initUI();
    }

    private void initUI() {

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 18);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 13);

        JLabel title = new JLabel("ÜRÜN YÖNETİM PANELİ", SwingConstants.CENTER);
        title.setFont(titleFont);
        add(title, BorderLayout.NORTH);
        
        JPanel formPanel = new JPanel(new GridLayout(8,2,8,8));
        JPanel buttonPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));

        txtName = new JTextField();
        txtPurchase = new JTextField();
        txtSell = new JTextField();
        txtStock = new JTextField();
        txtCritical = new JTextField();
        cmbCategory = new JComboBox<>(new String[]{"Telefon","Tablet","Laptop","Kulaklık"});

        formPanel.add(new JLabel("Kategori")); formPanel.add(cmbCategory);
        formPanel.add(new JLabel("Ürün Modeli")); formPanel.add(txtName);
        formPanel.add(new JLabel("Stok Sayısı")); formPanel.add(txtStock);
        formPanel.add(new JLabel("Kritik Stok")); formPanel.add(txtCritical);
        formPanel.add(new JLabel("Alış Fiyatı")); formPanel.add(txtPurchase);
        formPanel.add(new JLabel("Satış Fiyatı")); formPanel.add(txtSell);
        
        formPanel.add(new JLabel()); formPanel.add(buttonPanel);


        JButton btnAdd = new JButton("Ürünü Ekle");
        btnAdd.setFont(buttonFont);
        btnAdd.setBackground(new Color(0,153,76));
        btnAdd.setForeground(Color.WHITE);
        
        buttonPanel.add(btnAdd);
        

        btnAdd.addActionListener(e -> {
            try {
                ProductData.addProduct(
                        txtName.getText(),
                        cmbCategory.getSelectedItem().toString(),
                        Double.parseDouble(txtPurchase.getText()),
                        Double.parseDouble(txtSell.getText()),
                        Integer.parseInt(txtStock.getText()),
                        Integer.parseInt(txtCritical.getText())
                );
                loadProducts();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Sayısal alanlara geçerli değer giriniz!",
                        "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        
        
        

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10,10,10,10)
        ));
        leftPanel.add(new JLabel("Yeni Ürün Ekleme", SwingConstants.CENTER), BorderLayout.NORTH);
        leftPanel.add(formPanel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);

        
        String[] columns = {"Id","Adı","Kategori","Alış","Satış","Stok","Kritik","Toplam Kâr"};
        model = new DefaultTableModel(columns,0);
        table = new JTable(model);
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,13));
        table.setFont(new Font("Segoe UI",Font.PLAIN,13));

        
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                int stock = (int) table.getModel().getValueAt(row, 5);
                int critical = (int) table.getModel().getValueAt(row, 6);

                if (stock <= critical) c.setBackground(new Color(255,200,200));
                else c.setBackground(Color.WHITE);
                if (isSelected) c.setBackground(new Color(184,207,229));
                return c;
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JTextField txtSellQty = new JTextField(5);
        JButton btnSell = new JButton("Satış Yap");
        JButton btnDelete = new JButton("Ürünü Sil");
        JButton btnLogout = new JButton("Çıkış Yap"); 

        btnSell.setBackground(new Color(0,102,204)); btnSell.setForeground(Color.WHITE);
        btnDelete.setBackground(new Color(204,0,0)); btnDelete.setForeground(Color.WHITE);
        btnLogout.setBackground(new Color(145,0,0)); btnLogout.setForeground(Color.WHITE); 


        bottom.add(new JLabel("Adet:")); bottom.add(txtSellQty);
        bottom.add(btnSell); bottom.add(btnDelete); bottom.add(btnLogout);
        add(bottom, BorderLayout.SOUTH);
      


        btnSell.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row == -1) return;
            try {
                int qty = Integer.parseInt(txtSellQty.getText());
                int productId = (int) model.getValueAt(row,0);
                ProductData.sellProduct(productId,1,qty);

                double purchase = ProductData.getPurchasePrice(productId);
                double sell = ProductData.getSellPrice(productId);
                double profit = (sell - purchase) * qty;

                JOptionPane.showMessageDialog(this,
                        "Satış başarılı!\nBu satıştan elde edilen kâr: " + profit + " ₺");
                loadProducts();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,"Adet sayısal olmalıdır!","Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row == -1) return;
            int id = (int) model.getValueAt(row,0);
            ProductData.deleteProduct(id);
            loadProducts();
        });

       
        btnLogout.addActionListener(e -> {
            mainFrame.showLoginPanel();
        });

        
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(250); 
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(60);
        table.getColumnModel().getColumn(6).setPreferredWidth(80);
        table.getColumnModel().getColumn(7).setPreferredWidth(100);

        loadProducts();
    }

    private void loadProducts() {
        model.setRowCount(0);
        ResultSet rs = ProductData.getAllProducts();

        try {
            while (rs.next()) {
                int stock = rs.getInt("stock");
                int critical = rs.getInt("critical_stock");
                int id = rs.getInt("id");
                String name = rs.getString("name");

                if (stock <= critical && stock > 0 && !criticalWarned) {
                    JOptionPane.showMessageDialog(this, "Kritik stok seviyesinde ürünler var!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                    criticalWarned = true;
                }

                double purchase = rs.getDouble("purchase_price");
                double sell = rs.getDouble("sell_price");
                int sold = ProductData.getTotalSold(id);
                double profit = (sell - purchase) * sold;

                model.addRow(new Object[]{id,name,rs.getString("category"),purchase,sell,stock,critical,profit});
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}

