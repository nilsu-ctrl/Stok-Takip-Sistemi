//Selda Şahin

package StokTakipJava;

public class Sale {

	protected Product productname;
	protected int quantity;
	protected int totalprice;
	
	
	public Sale(Product productname, int quantity, int totalprice) {
		super();
		this.productname = productname;
		this.quantity = quantity;
		this.totalprice = totalprice;
	}

	
	public Product getProductname() {
		return productname;
	}

	public int getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}
	
	
}
