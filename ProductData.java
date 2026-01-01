//Nilsu Şirinyurt

package StokTakipJava;

import java.sql.*;
import javax.swing.JOptionPane;

public class ProductData {

    public static void addProduct(String name, String category,
                                  double purchasePrice, double sellPrice,
                                  int stock, int criticalStock) {
        String sql = "INSERT INTO product (name, category, purchase_price, sell_price, stock, critical_stock) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, category);
            ps.setDouble(3, purchasePrice);
            ps.setDouble(4, sellPrice);
            ps.setInt(5, stock);
            ps.setInt(6, criticalStock);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteProduct(int id) {
        String deleteSales = "DELETE FROM sales WHERE product_id=?";
        String deleteProduct = "DELETE FROM product WHERE id=?";

        try (Connection con = DatabaseConnection.getConnection()) {

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Bu ürüne ait satış kayıtları da silinecek. Emin misiniz?",
                    "Silme Onayı",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) return;

            try (PreparedStatement ps1 = con.prepareStatement(deleteSales);
                 PreparedStatement ps2 = con.prepareStatement(deleteProduct)) {

                ps1.setInt(1, id);
                ps1.executeUpdate();

                ps2.setInt(1, id);
                ps2.executeUpdate();

                JOptionPane.showMessageDialog(null, "Ürün başarıyla silindi.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sellProduct(int productId, int sellerId, int quantity) {
        try (Connection con = DatabaseConnection.getConnection()) {

            String checkStock = "SELECT stock FROM product WHERE id=?";
            try (PreparedStatement psCheck = con.prepareStatement(checkStock)) {
                psCheck.setInt(1, productId);
                ResultSet rs = psCheck.executeQuery();
                if (!rs.next()) return;
                int currentStock = rs.getInt("stock");

                if (quantity > currentStock) {
                    JOptionPane.showMessageDialog(null, "Yetersiz stok!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            String updateStock = "UPDATE product SET stock=stock-? WHERE id=?";
            try (PreparedStatement ps1 = con.prepareStatement(updateStock)) {
                ps1.setInt(1, quantity);
                ps1.setInt(2, productId);
                ps1.executeUpdate();
            }

            String insertSale = "INSERT INTO sales (product_id, seller_id, quantity) VALUES (?, ?, ?)";
            try (PreparedStatement ps2 = con.prepareStatement(insertSale)) {
                ps2.setInt(1, productId);
                ps2.setInt(2, sellerId);
                ps2.setInt(3, quantity);
                ps2.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getAllProducts() {
        try {
            Connection con = DatabaseConnection.getConnection();
            Statement st = con.createStatement();
            return st.executeQuery("SELECT * FROM product");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static double getPurchasePrice(int productId) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT purchase_price FROM product WHERE id=?")) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) return rs.getDouble("purchase_price");
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public static double getSellPrice(int productId) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT sell_price FROM product WHERE id=?")) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) return rs.getDouble("sell_price");
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public static int getTotalSold(int productId) {
        String sql = "SELECT SUM(quantity) FROM sales WHERE product_id=?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
}
