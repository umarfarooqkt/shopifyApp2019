/**
 * 
 */
package controllers;

import java.util.HashMap;
import models.ProductModel;

/**
 * @author umarf
 *
 */
public class Cart {
	
	// Attributes
	private HashMap<ProductModel,Integer> productList;
	private double totalCost;
	
	/**
	 * Default constructor that initializes the total
	 */
	public Cart() {
		this.totalCost = 0.0;
		this.productList = new HashMap<ProductModel,Integer>();
	}

	/**
	 * @return the productList
	 */
	public HashMap<ProductModel, Integer> getProductList() {
		return productList;
	}

	/**
	 * @param productList the productList to set
	 */
	public void setProductList(HashMap<ProductModel, Integer> productList) {
		this.productList = productList;
	}

	/**
	 * @return the totalCost
	 */
	public double getTotalCost() {
		return totalCost;
	}

	/**
	 * @param totalCost the totalCost to set
	 */
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	/**
	 * Adds the product corresponding to the given title
	 * to the cart
	 * @param title
	 * @param count
	 */
	public void addProductToCart(String title, int count) {
		ProductModel product = DatabaseManager.retriveProductWithTitle(title);
		if (product.getInventory_count() >= count) {
			System.out.println("Getting product -> " + product.toString());
			this.productList.put(product, count);
			this.totalCost = this.totalCost + product.getPrice() * count;
		} else {
			String message = product.getTitle() + " is out of stock :( sorry!!";
			System.out.println(message);
		}		
	}
	
	/**
	 * Removing the product specified by title from cart
	 * @param title
	 */
	public void removeProductFromCart(String title) {
		
	}
	
	/**
	 * This method returns a receipt list like representation 
	 * for the cart
	 * @return the cart info
	 */
	public String cartInfo() {
		String cart = "";
		ProductModel product;
		int count;
		for (Object productIterator : this.getProductList().keySet().toArray()) {
			product = (ProductModel)productIterator;
			count = this.getProductList().get(product);
			cart += product.getTitle() + "   $" + count * product.getPrice() + "   for quantity   " + count + "\n";
		}
		cart += "Total Amount   $" + this.totalCost + "\n";
		return cart;
	}
	
	/**
	 * This method completes the order by committing changes to the
	 * database for the inventory
	 */
	public void completeCart() {
		ProductModel product;
		for (Object productIterator : this.getProductList().keySet().toArray()) {
			product = (ProductModel)productIterator;
			product.setInventory_count(product.getInventory_count() - this.productList.get(product));
			DatabaseManager.updateProductInventory(product.getTitle(), product.getInventory_count());
			this.productList.remove(product);
		}
		this.totalCost = 0.0;
	}
}
