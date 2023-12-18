# Test Cases
## General
### Test 1: Creating Account (Seller)
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller10@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Create Account" button.
7. User selects the "Seller" button.

Expected result: Application passes username "seller10@gmail.com" and password "password" to server and creates a new seller user in the "userlogin.txt" file, then loads homepage automatically.

Test Status: Passed
### Test 2: Creating Account (Customer)
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "customer10@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Create Account" button.
7. User selects the "Customer" button.

Expected result: Application passes username "customer10@gmail.com" and password "password" to server and creates a new customer user in the "userlogin.txt" file, then loads homepage automatically.

Test Status: Passed
### Test 3: Editing Account (Seller)
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller10@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Edit User" button.
8. User selects the "Edit Account Details" option from the drop-down menu using the mouse.
9. User selects the "OK" button.
10. User enters "seller11@gmail.com" via the keyboard.
11. User enters "amazingpassword" via the keyboard.

Expected result: Application passes user information to server, edited user login information is added to the "userlogin.txt" file. Old user login information is removed.

Test Status: Passed
### Test 4: Editing Account (Customer)
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "customer10@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Edit User" button.
8. User selects the "Edit Account Details" option from the drop-down menu using the mouse.
9. User selects the "OK" button.
10. User enters "customer11@gmail.com" via the keyboard.
11. User enters "amazingpassword" via the keyboard.

Expected result: Application passes user information to server, edited user login information is added to the "userlogin.txt" file. Old user login information is removed.

Test Status: Passed
### Test 5: Deleting Account (Seller)
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller11@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "amazingpassword" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Edit User" button.
8. User selects the "Delete Account" option from the drop-down menu using the mouse.
9. User selects the "OK" button.

Expected result: Application passes user information to server, user login information is removed from the "userlogin.txt" file.

Test Status: Passed
### Test 6: Deleting Account (Customer)
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "customer11@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "amazingpassword" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Edit/Delete User" button.
8. User selects the "Delete Account" option from the drop-down menu using the mouse.
9. User selects the "OK" button.

Expected result: Application passes user information to server, user login information is removed from the "userlogin.txt" file.

Test Status: Passed
### Test 7: Logging Into Account
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.

Expected result: Application verifies the user's username and password and loads their homepage automatically.

Test Status: Passed
## Seller
### Test 8: Creating Product
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Create Product" button.
8. User selects the "Enter product name" textbox.
9. User enters "testproduct" via the keyboard.
10. User selects the "Enter store name" textbox.
11. User enters "store1" via the keyboard.
12. User selects the "Enter product description" textbox.
13. User enters "testdescription" via the keyboard.
13. User selects the "Enter quantity available" textbox.
14. User enters "10" via the keyboard.
15. User selects the "Enter price of product" textbox.
16. User enters "10.0" via the keyboard.
17. User selects the "Create Product" button.

Expected result: Application passes product name, store name, product description, product quantity, product price, and seller name to the server. Application adds product to the "Products.txt" file and closes the "Create Product" window.

Test Status: Passed
### Test 9: Editing Product Name
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Edit Product" button.
8. User selects "testproduct" from the drop-down menu using the mouse.
9. User selects the "OK" button.
9. User selects "Edit Product Name" from the drop-down menu using the mouse.
10. User selects the "OK" button.
10. User selects the "Enter edited field" textbox.
11. User enters "product10" via the keyboard.
12. User selects the "OK" button.

Expected result: Application passes edited product to server and server changes product in the "Products.txt" file. Application shows a window saying "Product Edited!" with an "OK" button.

Test Status: Passed
### Test 10: Editing Store Name
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Edit Product" button.
8. User selects "product10" from the drop-down menu using the mouse.
9. User selects the "OK" button.
9. User selects "Edit Store Name" from the drop-down menu using the mouse.
10. User selects the "OK" button.
10. User selects the "Enter edited field" textbox.
11. User enters "store10" via the keyboard.
12. User selects the "OK" button.

Expected result: Application passes edited product to server and server changes product in the "Products.txt" file. Application shows a window saying "Product Edited!" with an "OK" button.

Test Status: Passed
### Test 11: Editing Product Description
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Edit Product" button.
8. User selects "product10" from the drop-down menu using the mouse.
9. User selects the "OK" button.
9. User selects "Edit Product Description" from the drop-down menu using the mouse.
10. User selects the "OK" button.
10. User selects the "Enter edited field" textbox.
11. User enters "description10" via the keyboard.
12. User selects the "OK" button.

Expected result: Application passes edited product to server and server changes product in the "Products.txt" file. Application shows a window saying "Product Edited!" with an "OK" button.

Test Status: Passed
### Test 12: Editing Product Quantity
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Edit Product" button.
8. User selects "product10" from the drop-down menu using the mouse.
9. User selects the "OK" button.
9. User selects "Edit Quantity Available" from the drop-down menu using the mouse.
10. User selects the "OK" button.
10. User selects the "Enter edited field" textbox.
11. User enters "100" via the keyboard.
12. User selects the "OK" button.

Expected result: Application passes edited product to server and server changes product in the "Products.txt" file. Application shows a window saying "Product Edited!" with an "OK" button.

Test Status: Passed
### Test 13: Editing Product Price
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Edit Product" button.
8. User selects "product10" from the drop-down menu using the mouse.
9. User selects the "OK" button.
9. User selects "Edit Price" from the drop-down menu using the mouse.
10. User selects the "OK" button.
10. User selects the "Enter edited field" textbox.
11. User enters "100.0" via the keyboard.
12. User selects the "OK" button.

Expected result: Application passes edited product to server and server changes product in the "Products.txt" file. Application shows a window saying "Product Edited!" with an "OK" button.

Test Status: Passed
### Test 14: Deleting Product
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Delete Product" button.
8. User selects "product10" from the drop-down menu using the mouse.
9. User selects the "OK" button.
10. User selects the "Yes" button.

Expected result: Application passes product to server, server removes product from the "Products.txt" file. Application shows a window saying "Product Deleted!" with an "OK" button.

Test Status: Passed
### Test 15: Importing Products
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Import Product(s)" button.
8. User enters "imports.txt" via the keyboard.
9. User selects the "OK" button.

Expected result: All products in the "imports.txt" file will be written to the "Products.txt" file.

Test Status: Passed
### Test 16: Exporting Products
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Export Product(s)" button.
8. User enters "exported.txt" via the keyboard.
9. User selects the "OK" button.

Expected result: All products associated with seller1 will be written to the "exported.txt" file.

Test Status: Passed
### View Seller Dashboard
#### Test 17: Total Purchases, Ascending
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "View Dashboard" button.
8. User selects the "Sort By Total Purchases Ascending" button.

Expected result: All products associated with seller1 will be shown, sorted first into separate groups of stores, then sorted by quantity of each product purchased, ascending.

Test Status: Passed
#### Test 18: Total Purchases, Descending
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "View Dashboard" button.
8. User selects the "Sort By Total Purchases Descending" button.

Expected result: All products associated with seller1 will be shown, sorted first into separate groups of stores, then sorted by quantity of each product purchased, descending.

Test Status: Passed
#### Test 19: Customer Purchases, Ascending
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "View Dashboard" button.
8. User selects the "Sort By Customer Purchases Ascending" button.

Expected result: All customers who have purchased a product from seller1 will be shown, sorted first into separate groups of stores, then sorted by quantity purchased, ascending.

Test Status: Passed
#### Test 20: Customer Purchases, Descending
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "View Dashboard" button.
8. User selects the "Sort By Customer Purchases Descending" button.

Expected result: All customers who have purchased a product from seller1 will be shown, sorted first into separate groups of stores, then sorted by quantity purchased, descending.

Test Status: Passed
### Test 21: View Store Sales
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "View Sales" button.
8. User selects the "Store Sales" option from the drop-down menu.
9. User selects the "OK" button.
10. User enters "store1" via the keyboard.
11. User selects the "OK" button.

Expected result: Drop-down menu showing sales history including customer email, product bought, and revenue from sale for each purchase in the "History.txt" associated with store1 is shown.

Test Status: Passed
### Test 22: View Customer Carts
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "View Sales" button.
8. User selects the "Current Customer Carts" option from the drop-down menu.
9. User selects the "OK" button.

Expected result: Drop-down menu showing products associated with the seller that are currently in the "shoppingcart.txt" file, including product name, store name, and quantity of item in cart.

Test Status: Passed
### Test 23: View Reviews
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "View Reviews" button.

Expected result: Drop-down menu showing reviews and products they are associated with is shown.

Test Status: Passed
### Test 24: Refreshing Page
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Refresh" button.

Expected result: Any changes made before the refresh will be reflected in the new product dashboard.

Test Status: Passed
## Customer
### Test 25: Selecting Product
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "customer1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "pass" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Select psychology" button.

Expected result: A window is displayed, with product name, store name, description, quantity, price, and seller name displayed for that product, in that order. Below the information are two buttons labelled "Add to Cart" and "View Reviews".

Test Status: Passed
### Test 26: Adding to Shopping Cart
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "customer1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "pass" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Select psychology" button.
8. User selects the "Add To Cart" button.
9. User enters "1" via the keyboard.
10. User selects the "OK" button.

Expected result: Application passes product and quantity to server, server adds product and quantity to the "shoppingcart.txt" file. Application shows a window saying "Product Added to Cart!" with an "OK" button.

Test Status: Passed
### Test 27: Removing from Shopping Cart
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "customer1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "pass" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Select Psychology" button.
8. User selects the "Add to Cart" button.
9. User enters "1" via the keyboard.
10. User selects the "OK" button.
11. User selects the "OK" button.
7. User selects the "View Shopping Cart" button.
8. User selects the "Remove" button on the same line as "Psychology".

Expected result: Application passes product to server, server removes product from the "shoppingcart.txt" file and adds the quantity in the cart to the quantity of the product available for purchase. Application refreshes Shopping Cart window with product removed.

Test Status: Passed
### Test 28: Purchasing Shopping Cart
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "customer1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "pass" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "View Shopping Cart" button.
8. User selects the "Purchase Items" button.

Expected result: Items are purchased from the shoppingcart.txt file, removed from there, and added to the History.txt file.

Test Status: Passed

### Test 29: View Product Reviews
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "customer1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "pass" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Select psychology" button.
8. User selects the "View Reviews" button.

Expected result: A window is displayed, with reviews for psychology listed, showing customer name, store name, product name, seller name, and review contents, in that order.

Test Status: Passed
### Test 30: Leave Product Review
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "customer1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "pass" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Leave Reviews" button.
8. User selects the "OK" button.
8. User enters "Great Product" view the keyboard.
9. User selects the "OK" button.

Expected result: A window is displayed, showing the "Review Left!" message. Review is added to the Reviews.txt file.

Test Status: Passed

### Test 31: Sorting Products (Price Ascending)
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "customer1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "pass" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Price Ascending" button to sort by price, ascending.

Expected result: The products visible to the customer become sorted by their purchase price, from lowest to highest, left to right.

Test Status: Passed
### Test 32: Sorting Products (Price Descending)
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "customer1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "pass" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Price Descending" button to sort by price, descending.

Expected result: The products visible to the customer become sorted by their purchase price, from highest to lowest, left to right.

Test Status: Passed
### Test 33: Sorting Products (Quantity Ascending)
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "customer1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "pass" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Quantity Ascending" button to sort by quantity, ascending.

Expected result: The products visible to the customer become sorted by their quantity available for sale, from lowest to highest, left to right.

Test Status: Passed
### Test 34: Sorting Products (Quantity Descending)
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "customer1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "pass" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Quantity Descending" button to sort by quantity, descending.

Expected result: The products visible to the customer become sorted by their quantity available for sale, from highest to lowest, left to right.

Test Status: Passed
### Test 35: Searching Products
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "customer1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "pass" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the textbox at the top left of the screen, to the left of the "Search" button.
8. User enters "psychology" via the keyboard.
9. User selects the "Search" button.

Expected result: Only psychology becomes visible to the user.

Test Status: Passed
### Test 36: Refreshing Page
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "customer1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "pass" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Refresh" button.

Expected result: Any changes made to the "Products.txt" file will be reflected in the products shown.

Test Status: Passed
## Error Messages
### Test 37: No Email on Account Creation
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Create Account" button.

Expected result: An "Account Creation Error" window is shown with the "Invalid Username!" error message.

Test Status: Passed
### Test 38: Incorrect Username on Login
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.

Expected result: A "Login Error" window is shown with the "Invalid Username or Password" error message.

Test Status: Passed
### Test 39: Incorrect Password on Login
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.

Expected result: A "Login Error" window is shown with the "Invalid Username or Password" error message.

Test Status: Passed
### Test 40: Importing Invalid File
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Import Product(s)" button.
8. User enters "dummyfile" via the keyboard.
9. User selects the "OK" button.

Expected result: A "Error" window is shown with the "Failed to import file" error message.

Test Status: Passed
### Test 41: Editing Username Without Email (Seller)
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Edit User" button.
8. User selects the "OK" button.
9. User enters "dummyuser" via the keyboard.
10. User selects the "OK" button.

Expected result: A "Error" window is shown with the "Invalid Username!" error message.

Test Status: Passed
### Test 42: Editing Username Without Email (Customer)
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "customer1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "pass" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Edit User" button.
8. User selects the "OK" button.
9. User enters "dummyuser" via the keyboard.
10. User selects the "OK" button.

Expected result: A "Error" window is shown with the "Invalid Username!" error message.

Test Status: Passed
### Test 43: Creating Product With Invalid Quantity
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Create Product" button.
8. User selects the "Enter product name" textbox.
9. User enters "testproduct" via the keyboard.
10. User selects the "Enter store name" textbox.
11. User enters "store1" via the keyboard.
12. User selects the "Enter product description" textbox.
13. User enters "testdescription" via the keyboard.
13. User selects the "Enter quantity available" textbox.
14. User enters "dummyquantity" via the keyboard.
15. User selects the "Enter price of product" textbox.
16. User enters "10.0" via the keyboard.
17. User selects the "Create Product" button.

Expected result: A "Error" window is shown with the "Invalid quantity or price" error message.

Test Status: Passed
### Test 44: Creating Product With Invalid Price
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Create Product" button.
8. User selects the "Enter product name" textbox.
9. User enters "testproduct" via the keyboard.
10. User selects the "Enter store name" textbox.
11. User enters "store1" via the keyboard.
12. User selects the "Enter product description" textbox.
13. User enters "testdescription" via the keyboard.
13. User selects the "Enter quantity available" textbox.
14. User enters "10" via the keyboard.
15. User selects the "Enter price of product" textbox.
16. User enters "dummyprice" via the keyboard.
17. User selects the "Create Product" button.

Expected result: A "Error" window is shown with the "Invalid quantity or price" error message.

Test Status: Passed
### Test 45: Viewing Sales For Invalid Store
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "seller1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "password" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "View Sales" button.
8. User selects the "Store Sales" option from the drop-down menu.
9. User selects the "OK" button.
10. User enters "dummystore" via the keyboard.
11. User selects the "OK" button.

Expected result: A "Error" window is shown with the "No sales for this store!" error message.

Test Status: Passed
### Test 46: Viewing Reviews When None Exist
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "customer1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "pass" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Select psychology" button.
8. User selects the "View Reviews" button.

Expected result: A "No Reviews" window is shown with the "No reviews on this product" error message.

Test Status: Passed
### Test 47: Purchasing More Products Than Exist
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "customer1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "pass" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Select psychology" button.
8. User selects the "Add To Cart" button.
9. User enters "10" via the keyboard.
10. User selects the "OK" button.

Expected result: A "Error" window is shown with the "Requested Amount Exceeds Quantity Available" error message.

Test Status: Passed
### Test 48: Leave Empty Review
Steps:
1. User launches application.
2. User selects the "username" textbox.
3. User enters "customer1@gmail.com" via the keyboard.
4. User selects the "password" textbox.
5. User enters "pass" via the keyboard.
6. User selects the "Sign In" button.
7. User selects the "Leave Reviews" button.
8. User selects the "OK" button.
9. User selects the "OK" button.

Expected result: A "Enter Review" window is shown with the "Please Enter a Review!" error message.

Test Status: Passed