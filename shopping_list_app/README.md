# Shopping List App in Kotlin and SQLite

My app is a Shopping List App. Where the user can choose from a shopping category spinner, for example “Fruit”, “Pet Food”, etc and it will update the icon picture in each listing. The user has 3 options for text input: Item Name, Store, Other Comments. If "other comments" is left blank it will be filled in with the food category.

This project uses the Fragment + ViewModel. The main fragment contains a recycler view where the shoppings list items are displayed.
The app consists of four fragments + a dialog fragment. The database files are under the folder of the same name which will collect and store the information of your list and will update the recycler view accordingly when you add or delete an item. The menu and navigation are implemented using an Android resource file. Navigation allows Main Fragment and Detail Fragment to have access to all the other fragments but Info and Settings fragments not to have access to each other.
When the app starts the main activity calls the fragment which inflates the main fragment. When the "+" button is tapped a bottom sheet dialog fragment pops up to prompt you to enter an item of your list. Then the user may press the "ADD" button to add the item to their list or they may press the "CANCEL" button to back out.
To allow the user to remove a listing, you can swipe left and an Alert Dialog will pop up notifying you of the successful removal.

## Improvements on the work

The settings are set up to do 3 different preferences, but they are not yet working. I am planning to add sounds when you check something off your list as well as when you delete an item, and when you completed all items in the list I want to do some cheerful animation. The settings will allow the user to choose if they want sound effects, animation effects and then a third one for big text in case the text was too small for the reader.
