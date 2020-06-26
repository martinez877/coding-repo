# Two Games Android Application in Kotlin

## Welcome to Two Games!
This Android Application features two games: Monty (a card game), and classic Tic Tac Toe. 

This two-games app starts by displaying an empty activity layout with two buttons. The user may then press either the Cards or the Tic Tac Toe button to select their desired game. The activity consists of two frame layouts: one contains buttons that will remain static while the other switches the fragment to the selected game. 

In order to play Monty, the user must press play and click one of three cards displayed on the screen (face down) in an attempt to guess which is the ace. A model is implemented to generate the random placement of the face down ace. 

The tic tac toe game appears as a blank 3x3 board. To play, the user must first click on one of any of the tiles. The screen will then display X and then O as to mimic the different turns for the game and will let you click on it again if you want to switch between the two.

The Games Played Counter increments each time the user switches between the games or starts a new card game. This is done utilizing data binding communication between the two fragments and by using the Main Activity as the intermediary to preserve state. 

### Notes for UX improvement
The screen orientation is locked to landscape for both games.

The Monty game features card animation!

Enjoy :)
