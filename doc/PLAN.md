# Game Plan
Wenyue Gu wg74
## NAME
PowerBreak

### Breakout Variant
* Bricks n Balls - although the paddle was completely removed in this game, 
times the brick needs to be hit and the ball number dramatically increased. The most 
interesting portion of this game is that player has perfect control over the angle 
at which the balls were shot, which then can possibly results in the balls staying in the 
air and clearing all bricks in one try if fired at the correct angle.

* Vortex - this variant of breakout changes how the paddle, bricks, and ball locate and move 
spatially, making it more challenging than the original while still maintaining the 
basics rules and configuration of the classic game.


### General Level Descriptions
Four levels in total; player starts with 3 lives; every time ball hits bottom floor life -1; 
score = score + the strength at which the ball hit the brick; 

* Level 1 (max strength increase = 1+1 = 2):   
0 1 0 0 1 0 
1 1 1 1 1 1  
0 1 1 1 1 0  
1 x 0 0 z 1  
1 0 y z 0 1  
0 1 z 1 1 0  
0 1 0 0 1 0  
0 1 0 0 1 0  
0 0 1 1 0 0  

* Level 2: (max strength 2+1 = 3)  
5 0 0 0 0 5  
0 5 0 0 5 0  
z 2 0 0 2 z  
0 5 0 0 5 0  
0 2 2 2 2 0  
0 5 1 1 5 0  
0 2 2 2 2 0  
1 2 5 5 2 1  
0 y 2 2 x 0  

* Level 3: (max strength 3+1 = 4)  
10 0 10 10 0 10  
10 10 0 0 10 10  
10 10 10 10 10 10  
z z 0 0 z z    
10 5 0 0 5 10  
0 10 5 5 10 0  
5 5 5 5 5 5  
0 10 0 0 10 0  
10 5 y x 5 10 

* Bonus level (level 4):  
10 10 10 10 10 10  
10 10 10 10 10 10  
10 10 10 10 10 10  
10 10 10 10 10 10  
10 10 10 10 10 10  
10 10 10 10 10 10  
10 10 10 10 10 10  
10 10 10 10 10 10  
0 0 0 l 0 0

Final score = score + #life left * 10

### Bricks Ideas
Hardness 1, 2, 5, 10, need to be cleared with respective strength  
(eg, ball with a strength of 9 can clear a 100 brick with 12 hits)
Special bricks drop power up:  
Type x drops power up #1 
Type y drops power up #2
Type z drops randomly all other kind of power-ups (random number generator, <0.4 #2, 
0,4< <0.7 #4, 0.7< <1 #5 power-up respectively)  

### Power Up Ideas
1) increase ball strength by 1  
2) increase a ball (for current level only, does not carry to next level)
3) change paddle size (*2 or /2) 
(two effects but same image, when paddle size < 1/2 original only drops *2, when 
paddle size > 1 only drops /2 (thus min 0.25, max 2))  
4) gives 1 extra life
5) gives 20 points

### Cheat Key Ideas
* Number keys (ie, 1, 2, 3) skip to level 1, 2, 3 respectively (does not reset score life strength)  
* L add 1 life  
* R reset current level (include strength, life, score reset)  
* P pause and play (?)  
* B Back to start screen/restart the entire game  
* E end game and calculate score (auto-win if ended during bonus level; else auto-lose)

### Something Extra
* Particle/bullet shooting power up (drop by Brick l)  
* Direction of ball flies off depends on where on the paddle it hit