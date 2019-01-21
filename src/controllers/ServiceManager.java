/**
 * 
 */
package controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.ProductModel;

/**
 * @author umarf
 * This class manages all the requests made to the API
 */

@Path("/rest")
public class ServiceManager {
		
	/**
	 * This method returns all the products
	 * @return response with json
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all-products")
	public Response getAllProducts() {
		List<ProductModel> list = DatabaseManager.retriveAllProducts();
		return Response.ok(list).build();
	}
	
	/**
	 * This method adds a new product to the list
	 * @return response with json
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/add-new-to-inventory")
	public Response addToInventory(@QueryParam("title") String title,
			@QueryParam("quantity") int quantity,
			@QueryParam("price") double price) {
		DatabaseManager.addProduct(title, quantity, price);
		return Response.ok().build();
	}
	
	/**
	 * This method buys a new product from the inventory
	 * @return response with json
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/buy-product-inventory")
	public Response buyProduct(@QueryParam("title") String title) {
		ProductModel product = DatabaseManager.retriveProductWithTitle(title);
		int count = product.getInventory_count();
		if (count > 0) {
			DatabaseManager.updateProductInventory(title, count-1);
			return Response.ok(product).build();
		}
		else {
			return Response.notModified(new EntityTag("Sorry this item is out of inventory")).build();
		}
	}
}
