Game / Application Description
===============================

Info: "He" is used for both genders.

###Overview###
"TreasureHunt" (working title) is a native Android Application.
It will use a server to communicate with the client. 

The goal of the game is to find and loot treasure boxes in your area. 
It is necessery for the user to create and register a account.

Some chests require to solve a puzzle or a quiz to open, but will result in better "loot", depending on the place.
For example, on historical places there will be a special quiz about a specific historical event.

The concrete position of a box is not given, but you will be notified if you are close to a box.
With a little help of a coloured map you will be guided towards the box.

The boxes will "spawn" once a day on predefined positions on the server.
 
You will receive experience points and achievements for opening chests.

On a ranking list you can compete with other people.

#Optionally#
* Possibility of looting vouchers from sponsors
* Displaying virtual chests with the help of "Augmented Reality"
* Anonymous User without registration possible

============================

Gameplay
=============

#Case 1: New user opens the app#
The Login view is displayed. The user has no account, so he has to register. In the register view he has to input
a Username, a Password, Password check, E-Mail. The account is now created. The user is on the login view and can now log in successfully.
The user is now in the main menu. He can view his user profile, settings, the help page, the ranking page and can start the game.
The user decides to start the game. If GPS is deactivated he will be asked to activate the GPS.

(*1*)
The user now is in the map view. Because there is no box in his area, a info text is displayed that no chest is in his area.
The user is moving downtown with the app running and moves towards a box. The app informs the user that a box is close, starts looking for a box and
finally reaches it with our super awesome "hot/cool" algorithm, which determines the approximation to the box.

The user is now in range of opening the box. A big "open" button appears, he presses it and he gets loot and experience. 
User is now happy and is addicted.

#Case 2: User gets a Push notification#
The user gets a push notification that a box is close. Because he choosed "Remember me" he is automatically logged in.
The user is now at (*1*)



