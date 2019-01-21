# shopifyApp2019

# Absolute Basic requirements

Get all the products in inventory with inventory_count > 0  
@GET  
http://localhost:8080/ShopifyApp/rest/all-products  
  
To add a product to the inventory  
@POST  
http://localhost:8080/ShopifyApp/rest/add-new-to-inventory?title=someItem&quantity=2&price=3.0  
  
To buy a product (This method will reduce the inventory by 1 if the inventory of someTitle
is bigger than 0;  if the inventory is 0 than a response error of 304 will be returned)  
@PUT  
http://localhost:8080/ShopifyApp/rest/buy-product-inventory?title=someTitle  
  
# Extra credits
  
Tests are under src/tests/
