# Shopping List App in Kotlin and SQLite

This Android mobile application is an organizational Shopping List App that allows the user to add and organize shopping items by categorizing them by Stores (i.e. Store A, Store B) as well as item categories (i.e. Fruit, Pet Items). The user can provide an Item Name (i.e. apple, hammer) and add Other Comments (i.e. organic, 12"). 

This project uses the Fragment + ViewModel. The Main Fragment contains a Recycler View where the shopping list items are displayed. The app consists of four fragments and a Dialog Fragment. The database files are under the folder of the same name which will collect and store the information of your list and will update the Recycler View accordingly when you add or delete an item. 

The menu and navigation are implemented using an Android resource file. The navigation architecture allows Main Fragment and Detail Fragment to access all other fragments. However, Info and Settings fragments do not have access to each other.

When the app starts, the Main Activity calls to inflate the Main Fragment. When the "+" button is tapped a Bottom Sheet Dialog Fragment pops up to prompt the user to enter an item to add to their list. The user may then press the "ADD" button to add the item to their list or they may press the "CANCEL" button to back out. The user can also remove a listing by swiping left. An Alert Dialog will pop up notifying the user if the removal was successful.

## Improvements on the work

The settings allow for 3 different preferences, but they are still in progress. I plan to add sounds when you check something off your list as well as when you delete an item. Moreover, when all items on the list are completed, I intend to add a cheerful animation. In order to increase accessibility for a variety of user needs, the settings currently allow the user to choose if they want sound effects, animation effects and larger text. However, I plan to improve the execution of these features in the future. 
