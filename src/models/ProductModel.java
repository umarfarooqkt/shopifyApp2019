/**
 * 
 */
package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author umarf
 * This is the model class for products in the market
 */

@Entity
@Table(name = "product")
public class ProductModel implements Serializable{

	/**
	 * This is a unique serial id for deSerializing check 
	 */
	private static final long serialVersionUID = 1123518958505579336L;
	
	@Column(name="price")
	private double price;
	
	@Id
	@Column(name="title")
	private String title;

	@Column(name="inventory_count")
	private int  inventory_count;

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the inventory_count
	 */
	public int getInventory_count() {
		return inventory_count;
	}

	/**
	 * @param inventory_count the inventory_count to set
	 */
	public void setInventory_count(int inventory_count) {
		this.inventory_count = inventory_count;
	}

	/** (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductModel [title=" + title + ", price=" + price
				+ ", inventory_count=" + inventory_count + "]";
	}

}
