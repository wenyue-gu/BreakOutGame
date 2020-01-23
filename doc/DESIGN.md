###Names of all people who worked on the project  

Wenyue (Lucy) Gu  
I also used code from lab_bounce as starters so perhaps Dr Duvall counts 

###each person's role in developing the project  

I wrote the code.

###What are the project's design goals, specifically what kinds of new features did you want to make easy to add  

Features that should be easy to add in later include new powerups (
with new image and new status changes when triggered), new levels (with
a new level configuration (of bricks))


###Describe the high-level design of your project, focusing on the purpose and interaction of the core classes  

The game consists of a ball, a pane of bricks, a paddle, powerups, and texts for displaying status. 
The balls bounce off bricks, the paddle, and each side of the screen; the paddle is controlled by the 
player; the brick loses "life" when attacked by balls and drops powerup; powerup can be caught by 
the paddle and affects the status of elements in the game such as paddles and balls. All the while the 
text display shows the player data regarding the status of the objects.

###What assumptions or decisions were made to simplify your project's design, especially those that affected adding required features  

Using Javafx, timeline, scene stage.


###Describe, in detail, how to add new features to your project, especially ones you were not able to complete by the deadline  

To add a new powerup, add the corresponding image to the resources folder, change NUM_POWERUP to 4, add a case 4 in the 
giveEffect method, and the file should now be able to run with the newly added powerup.  

To add a new level, add the txt doc with the level configuration to the folder, change MAX_LEVEL in Game to 5 
(or whatever number you desire) and the game should be able to run with the number of levels specified. The 
code WILL probably break if the MAX_LEVEL is set too high and there isn't a txt doc associated with all the levels though.