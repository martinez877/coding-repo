# Two Games Android Application in Kotlin

## Welcome to Two Games!
This game includes Monty (a card game), and Tic Tac Toe. 

Two games app starts by displayign an empty activity layout with two buttons, then you press either the Cards or the Tic Tac Toe button to select your game. The activity consists of two frame layouts, one containing the buttons that will remain static and the other that will switch the fragment to the selected game. 
For the cards game press play and click either card to find the ACE, the game is animated. A model is implemented, to generate the random card that will have the ace. The tic tac toe game will appear as a blank 3x3 board, click on any of the tiles and it will display X and then O as to mimic the different turns for the game and will let you click on it again if you want to switch between the two.
The games played counter increments when you switch between the games or start a new card game. This is done by communication between the two fragments throught the main activity to preserve state. 
Screen orientation is locked to landscape.

Enjoy :)
