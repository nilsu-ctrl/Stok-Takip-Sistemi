//Selda Şahin

package StokTakipJava;

public class Product {

	protected int id;
	protected String name;
	protected String category;
	protected int stock;
	protected int criticalstock;
	protected double purchasePrice;
	protected double salePrice;
	


	public Product(int id, String name, String category, double purchasePrice,
			double salePrice,int stock, int criticalstock) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.stock = stock;
		this.criticalstock = criticalstock;
		this.purchasePrice = purchasePrice;
		this.salePrice = salePrice;
	}
	

	public void sellProduct(int quantity) {
		
		if (quantity > stock) {
			System.out.println("Yeterli ürün bulunmamaktadır.");
		}else {
			stock = (stock-quantity);
		}
		
	}
	
	
	public boolean isStockCritical() {
		
		if (stock <= criticalstock) {
			return true;
		}else {
			return false;
		}
	}
	
	public double calculateProfitPerUnit() {
	     return salePrice - purchasePrice;
	}
	
	
	public String getName() {
		return name;
	}

	public int getStock() {
		return stock;
	}
	
	public double getSalePrice() {
		return salePrice;
	}

	public String getCategory() {
		return category;
	}

	public int getCriticalstock() {
		return criticalstock;
	}

	public double getPurchasePrice() {
		return purchasePrice;
	}

	
}
