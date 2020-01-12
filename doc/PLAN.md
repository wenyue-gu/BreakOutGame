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
score = score + the strength at which the ball hit the brick; strength of ball is carried over 
from one level to the next until full reset/game end

* Level 1 (max strength increase = 1+1 = 2):   
0 3 0 0 3 0 
1 1 2 2 1 1  
0 1 1 1 1 0  
1 x 0 0 z 1  
1 0 y z 0 1  
0 1 z 1 1 0  
0 1 0 0 1 0  
0 1 0 0 1 0  
0 0 1 1 0 0  

* Level 2: (max strength 2)  
5 0 0 0 0 5  
0 5 0 0 5 0  
z 4 0 0 4 z  
0 5 0 0 5 0  
0 2 3 3 2 0  
0 5 1 1 5 0  
0 2 2 2 2 0  
1 2 5 5 2 1  
0 y 2 2 z 0  

* Level 3: (max strength 2+1 = 3)  
10 0 10 10 0 10  
10 9 0 0 9 10  
10 8 8 8 8 10  
z z 0 0 z z    
10 5 0 0 5 10  
0 8 5 5 8 0  
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
Hardness 1 through 10, need to be cleared with respective strength  
(eg, ball with a strength of 3 can clear a 10 brick with 4 hits)
Special x and y bricks automatically gives power up (without "power up" dropping):  
Type x gives power up #1  
Type y gives 20 points  
Special z bricks drops power up that player needs to catch (3&4, dropped using 
random number generator)

### Power Up Ideas
1) increase ball strength by 1  
2) change paddle size (*2 or /2) 
(two effects but same image, when paddle size < original only drops *2, when 
paddle size > original only drops /2 (thus min 0.5, max 2))  
3) gives 1 extra life

### Cheat Key Ideas
* Number keys (ie, 1, 2, 3) skip to level 1, 2, 3 respectively (does not reset score life strength)  
* L add 1 life  
* R reset current level (include strength, life, score reset)  
* P pause and play (?)  
* esc Back to start screen  
* E end game and calculate score (auto-win if ended during bonus level; else auto-lose)
* B (sth extra - second ball)

### Something Extra
* Direction of ball flies off depends on where on the paddle it hit; this implementation will make 
the game more flexible since balls can bounce off in more directions (specifically, I'm thinking 
if ball hit left 1/5, the angle it bounces off +30 (if +x axis is 0 and -x is 180), the left 2/5, 
angle +10, mid 3/5, angle does not change, right 4/5, -10, right 5/5, -30)  
* extra ball; does not inherit strength; when >1 ball is in the field and one ball hits 
the floor life does not change but the ball that hit the floor disappear