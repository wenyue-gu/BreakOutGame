# Game Plan
Wenyue Gu wg74
## NAME
PowerBreak

### Breakout Variant
Bricks n Balls is a variant of Breakout listed on the website that I personally really like 
playing so I thought I should mention it. The notion of the paddle sort of got deleted in this 
game, but brick strength and ball numbers were increased and could be very challenging though in 
a different way from normal Breakout.  
Vortex is an extremely interesting variant. I really like the configuration where the ball goes "in" 
and the brick moves by themselves; I think it's more challenging and thus more interesting  than normal 
breakout game but also manages to maintain a close relationship with the original/classic game.

### General Level Descriptions
I am intending to build three levels starting with 3 lives. Life -1 if ball hits floor, score + strength 
every time ball hit a brick  
* Level 1 (max strength = 1+1 = 2):   
0 2 0 0 2 0 
2 1 2 2 1 2  
0 2 2 2 2 0  
1 x 0 0 3 1  
1 0 1 1 0 1  
0 1 z 2 1 0  
0 1 2 y 1 0  
0 1 2 2 1 0  
0 1 2 2 1 0
1 0 0 0 0 1 

* Level 2: (max strength 2+4 = 6)  
10 0 0 0 0 10  
0 10 0 0 10 0  
z 2 0 0 2 z  
x 5 0 0 5 x  
0 2 2 2 2 0  
0 5 1 1 5 0  
0 2 2 2 2 0  
1 2 5 5 2 1  
0 x 2 2 x 0  
0 0 z z 0 0 

* Level 3: (max strength 6+4 = 10)  
10 0 100 100 0 10  
10 10 0 0 10 10  
10 10 10 10 10 10  
x z 0 0 z x    
10 5 0 0 5 10  
0 10 5 5 10 0  
5 5 5 5 5 5  
x 5 0 0 5 x  
0 10 0 0 10 0  
10 5 y 10 5 10 

* Bonus level (level 4):  
100 100 100 100 100 100  
100 100 100 100 100 100  
100 100 100 100 100 100  
100 100 100 100 100 100  
100 100 100 100 100 100  
100 100 100 100 100 100  
100 100 100 100 100 100  
100 100 100 100 100 100  
100 100 100 100 100 100  
0 0 y l 0 0

Final score = score + #life * 100

### Bricks Ideas
Hardness 1, 2, 5, 10, 100  
Special bricks drop power up:  
Type x drops power up 3 (to kinda ensures that by the end of level 3 player will have a strength of 10)  
Type y drops power up 1; each level has one type 2  
Type z drops randomly all other kind of powerups (random number generator, <0.4 paddle size change, 
0,4< <0.7 1 life, 0.7< <1 50 points)  

### Power Up Ideas
1) increase a ball for this level only (a maximum of 3 balls can exist at the same time?)
2) change paddle size (*2 or /2) 
(two effects but same image, when paddle size < 1/2 original only drops *2, when 
paddle size > 1 only drops /2 (thus min 0.25, max 2))  
3) increase ball strength by 1 (if a brick has hardness 5 every time ball hits brick's hardness decrease 
by ball's strength. max strength 10)
4) 1 extra life
5) 50 points

### Cheat Key Ideas
* Number keys (ie, 1, 2, 3) skip to level 1, 2, 3 respectively (does not reset score life strength)  
* L add 1 life  
* R reset current level (include strength, life, score reset)  
* P pause and play (?)  
* B Back to start screen/restart the entire game  

### Something Extra
* Particle/bullet shooting power up (drop by Brick l)  
* (if I have time/end up being able to do this) direction of ball flies off depends on where on the 
paddle it hit