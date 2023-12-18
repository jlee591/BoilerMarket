# Project-5
Project 5 of CS180
## Getting Started

### Prerequisites
Install Java (JDK 19) and IntelliJ to your computer.

### Installation/Set up
1. Clone the repository to your local computer
```
git clone https://github.com/imayblum/Project-5.git
```
2. Open IntelliJ and open Project-5 from the menu
3. On the left, click on the src folder and then the Server.java
4. On the top right of IntelliJ, click the play button to run the server
5. On the left, click on the Client.java and click the play button to run the client

### File Placement
1. Make sure to take the text files out of the source folder and put them in the project folder.

### Import Products
- Format for import in the file should be: product_name,store_name,product_description,product_quantity,product_price with no spaces in between the commas

### Run Multiple Threads
1. Run the Server class by pressing the green triangle at the top right of Intellij.
2. Run the Main class by pressing the green triangle at the top right of Intellij.
3. Click the rectangle on the left from the "run" button at the top right.
4. Once you click it, there should be an edit configurations button. Click it.
5. On the right of the popup screen, click on "modify options".
6. Under Operating Systems, click "Allow Multiple Instances".
7. Then, press "apply" and "ok".
8. When you click on the "run" button again, there should be multiple instance of the program.

## Submissions
- Joseph Lee - Submitted Report
- Iddo Mayblum - Vocareum Workspace
- Iddo Mayblum - Presentation Submission

### Other important information
- Upon initalization, the project creates files and adds starter info to them
- check .txt files to see existing information

## Descriptions of Classes
### Customer
The main function of the Customer class was to provide the functionality for the customer to view the products and reviews, sort products by price in ascending/descending order, and sort products by quantity in ascending/descending order. The sorting methods were all the same as in project 4, and the group was able to successfully implement them in Client.java. The displayProduct() method utilizes GridBagConstraints() to format the appearance of the product frame in an organized fashion. Also, JButtons, JLabels, JFrames, and JPanels were used to provide the product frame with buttons that allow the customer to add to the shopping cart and view reviews. Also, labels were used to organize the information. For display reviews, the group used BoxLayout() to organize the information of the product and review line by line. The client class implements all of the methods by creating objects of the Customer class and utilizing the created methods.

### Seller
The seller class is used to provide the functionality for the creation of a certain product. The seller in the marketplace has the ability to create its own items. This class helps the seller create the product by asking for the product name, store name, product description, quantity, and price of the product. The seller is utilized in the Clientclass by calling the method and providing the connection for the user to use.
### Reviews
The main functionality for this class is to allow Customer.java to create a reviews object for the displayReviews() and reviewMaker() methods. The method has 5 instance variables for the product name, store, seller, review, and customer name. The constructor allows the object to point to its respective elements, and the getters allow the Customer.java class to access them.
### Product
The Product class serves as a placeholder for many of the methods the group used throughout the implementation of the other classes. The object of this project would contain the characteristics of one product, such as product name, store, description, quantity, price, and seller. Also, there are many accessor and mutator methods for each of the specified characteristics of the product object. In the real world, this class would be seen as a toolbox for workers to utilize to create other structures or masterpieces.

### ShoppingCart
The ShoppingCart class provides the functionality for displaying the shopping cart. The user can see what product information of the products they have added to their cart. They also have the option to remove items and purchase items by pressing the provided buttons. The Client class implements the displayCart() method to display the functionality.
### StatisticsCustomer
​​The statistics customer class has methods that allow the customer to view a list of stores rearranged in ascending/descending based on their sales. It also has methods that allow the customer to view a list of stores that they have purchased. Since this class interacted with text files and made alterations, it had to be synchronized to eliminate race conditions. The class is used in Client and uses the history.txt file.
### SellerStatistics
The statistics seller class has methods that allow the user to view stores by total purchases and by total customer purchases in an ascending/descending manner. This class is synchronized due to the fact that it interacted with text files that could form race conditions. The Client class later implements the different methods and displays the information by utilizing GUIs.

### Client
The run() method runs the client() and login() method of the program. The client() method connects the program to the server and the login() method handles the user login. Also, the class has an ActionListener method that does certain actions when the user performs a specific action. For example, when the user logs in, he/she will be actually able to login instead of just being able to press a button. The other methods in the client class create frames and the overall layout of the customer and seller screen. Also, they call the methods from other classes to run the entire functionality of the program. Many of the methods that were in the various classes in Project 4 were moved to the client class to make it easier to work with. The client sends information to the server for every change the customer or seller makes to the marketplace.

### Server
The server class updates the files and data by accepting information from the Client class. Since it makes changes to files/data, many of the methods needed to be synchronized to eliminate any errors and race conditions that could occur when running multiple clients. The main method in the server runs an infinite loop that allows for multiple threads to work. The information from the client side is stored in files to keep the data from multiple users.


### UserAlreadyExists
The UserAlreadyExists class extends Exception and has a constructor that calls the exception class. This class is used in the main method when creating or editing an account.

### FileOverwrittenException
The FileOverwrittenException class extends Exception and has a constructor that calls the exception class. This class is used in the customer and seller classes. 
