game
====

This project implements the game of Breakout.

Name: Breakout

### Timeline  

Start Date: 2020/1/10

Finish Date: 2020/1/19

Hours Spent: 15?

### Resources Used  

* https://www.programcreek.com/java-api-examples/?class=javafx.scene.Scene&method=setOnKeyPressed  
* https://stackoverflow.com/questions/18448671/how-to-avoid-concurrentmodificationexception-while-removing-elements-from-arr  
* https://docs.oracle.com/javase/8/javafx/api/javafx/scene/image/ImageView.html#getX--  
* https://docs.oracle.com/javase/8/javafx/api/javafx/animation/Timeline.html  
* https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/text-settings.htm  
* https://stackoverflow.com/questions/21453308/select-one-of-three-numbers-with-equal-probability  
* https://www.jetbrains.com/help/idea/tutorial-introduction-to-refactoring.html
* https://stackoverflow.com/questions/43457079/adding-multiple-items-at-once-to-arraylist-in-java
* https://stackoverflow.com/questions/26949985/reading-files-with-intellij-idea-ide
* https://www.geeksforgeeks.org/switch-statement-in-java/

### Running the Program

#### Main class:   
Game

#### Data files needed:   
lvn.txt (n = 0 to 4) in the game_wg74 folder for level configuration  
ntype.gif (n = 1 to 3) in resources for powerup  
ball.gif and pad.gif for paddle  
kind1lifen.gif (n from 1 to 9), kind2life1.gif, kind3life1.gif, kind4life1.gif in resources for brick  

#### Key Inputs:  
* While you are on welcome page:  
    - M to show rules
    - C to show cheat sheet
    - SPACE to begin level 1
* While the game is actively going on (ie the animation is playing):  
    - C clears the round and goes to the next level, changing your score as if you cleared the level yourself  
    - L gives extra life  
    - A gives extra strength/attack power  
    - P pauses the game  
    - LEFT changes the paddle direction to go left  
    - RIGHT changes the paddle direction to go right  
    - DOWN stops the paddle from moving  
* While the game is paused:  
    - P resumes the game  
    - F ends the game: if you are on level 0, 1, 2, or 3, it is an auto lose; if you are on level 4, it is an auto win  
    - press 0 through 4 to enter level 0, 1, 2, 3, and 4 respectively (if you press 5 through 9 the game 
    shows level 4)  
* While you are on the END pages (win/lose):    
    - S to go back to welcome page
    

#### Known Bugs:  
There was once my intelli j crashed because I clicked the screen too many times  

#### Extra feature:  
Clicking screen to get an extra clone ball with 1 life

### Notes/Assumptions  

Most methods that requires input assumed that the information passed to them are non-empty 
and valid  


### Impressions  

This project really, really, really takes a long time to go through