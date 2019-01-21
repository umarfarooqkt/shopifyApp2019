/**
 * 
 */
package controllers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.eclipse.persistence.exceptions.DatabaseException;
import models.ProductModel;

/**
 * @author umarf
 *
 */
public class DatabaseManager {
	
	private static final String PERSISTENCE_UNIT_NAME = "marketdb";  
    private static final EntityManagerFactory EMF = 
    		Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private static EntityManager em;
    private static EntityTransaction transaction;
    
    //initializing entity manager 
    static {
    	em = EMF.createEntityManager();
    }
    
    /**
     * This method adds a product to database from the model
     * @param title [String]
     * @param inventory_count [int]
     * @param price [double]
     */
    public static void addProduct(String title, int inventory_count, double price) {
    	ProductModel product = new ProductModel();
    	product.setTitle(title);
    	product.setPrice(price);
    	product.setInventory_count(inventory_count);
    	transaction = em.getTransaction();
    	transaction.begin();
    	em.persist(product);
        try {
        	transaction.commit();
        } 
        catch (Exception e) {
        	System.out.println("Sorry that product already exists in the database \n"+e);
        	transaction.rollback();
        }
    }
    
    /**
     * This method updates the job listing matching the id in the database
     * with the new updated ProductModel 
     * @param newProduct
     * @throws Exception
     */
    public static void updateProduct(ProductModel newProduct) {
    	try {
    		transaction = em.getTransaction();
        	transaction.begin();
        	ProductModel oldPorduct = em.find(ProductModel.class, newProduct.getTitle());
        	if(!oldPorduct.equals(newProduct)) {
        		em.remove(oldPorduct);
        		em.persist(newProduct);
        		transaction.commit();
        	}
    	} catch(Exception e) {
    		System.out.println(e.getMessage());
    		transaction.rollback();
    	}
    }
    
    /**
     * This method updates the product's inventory in the database
     * with the new count 
     * @param title
     * @param count
     */
    public static void updateProductInventory(String title, int count) {
    	try {
    		transaction = em.getTransaction();
        	transaction.begin();
        	ProductModel oldProduct = (ProductModel)em.find(ProductModel.class, title);
        	oldProduct.setInventory_count(count);
    	    transaction.commit();
        	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		transaction.rollback();
    	}
    }
    
    /**
     * This method is for deleting the product from the data base; that matches the title provided 
     * @param title
     * @throws Exception
     */
    public static void deleteProduct(String title) throws Exception {
    	transaction = em.getTransaction();
    	transaction.begin();
    	ProductModel oldProduct = em.find(ProductModel.class, title);
    	em.remove(oldProduct);
    	transaction.commit();
    }
    
    /**
     * This method gets all the products
     * @return {List<ProductModel>}
     */
    public static List<ProductModel> retriveAllProducts() {
    	Query query = em.createNativeQuery(""
    			+ "SELECT * FROM product "
    			+ "WHERE inventory_count > 0 "
    			+ "ORDER BY title ASC", ProductModel.class);
    	List<ProductModel> productList = query.getResultList();
    	return productList;
    }
    
    /**
     * This method gets all the products with the specific title
     * @param String [title] => this is the title used to find product
     * @return {List<ProductModel>} The return type for
     */
    public static ProductModel retriveProductWithTitle(String title) {
    	String queryString = "SELECT * FROM product WHERE title = ?1";
    	Query query = em.createNativeQuery(queryString, ProductModel.class);
    	query.setParameter(1, title);
    	List<ProductModel> productList = query.getResultList();
    	return productList.get(0);
    }
}
