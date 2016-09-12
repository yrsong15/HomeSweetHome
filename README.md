# Home Sweet Home :dog: :heart: :strawberry: 
First project for CompSci 308 Fall 2016 by Ray Song(ys101).

## Programming Log

**Sept 11th**
+ Implemented different object-moving algorithm for Level Two.
+ Refined messages between levels.
+ Fixed end-of-game bug triggered by image file size.
+ Final push.

**Sept 10th**
+ Implemented visual timer and time limits for each stage
+ Added power-ups to level 2 (star = invincible, potion = Mimi becomes smaller)
+ Added cheat key: **press Q to become invincible at any point in time**

**Sept 9th**
+ Implemented Level One and Level Two
+ Working on algorithmically different Level Two

**Sept 7th**
+ Implemented levels to enable smooth transitions between modes.
+ Pushed initial version of game

**Sept 6th**
+ Updated image to vector files for better view.

**Sept 5th**
+ Added cheat code to game: Clicking on the vertical-moving strawberry will automatically end game
+ Struggling (mightily) with implementing end-of-game messages.

**Sept 4th**
+ Completed crude version of Intro Page which leads to main game by mouse click input.

**Sept 3rd** 
+ Completed crude version of Level One. Game automatically shuts down when all three strawberries are consumed. 


## Design
The game design has been detailed on DESIGN.txt. It can also be viewed below; I personally prefer *Markdown* to normal *.txt* files.

**Genre**
+ Home Sweet Home is a 2-D Scrolling Platformer game featuring Mimi the fur rat. 
Mimi's favorite thing in the world is lying on the bed and doing nothing.
Her second favorite thing is strawberry jam - her only diet.

+ The player will help Mimi collect 5 jars of strawberry jam before going home,
so that she has something to eat while enjoying Netflix on her bed.

**Story**
+ Mimi is a tiny, 70-year old dog who lives in their New York apartment with Ray and Nicole.
Due to how nice Ray and Nicole treat Mimi, as well as Mimi's old age, Mimi absolutely hates
going outside, even for a 10-minute walk. The only thing that can ever get Mimi out of bed is
strawberry jam, her favorite food in the world.

+ The story begins when Mimi wakes up in the middle of the night, while Ray and Nicole are asleep. She wants to open the fridge to get some jam, but because she is so tiny, she is unable to reach the handle of the fridge. Determined, Mimi sets out to the City just to get strawberry jam.

+ Unfortunately, Mimi is not the brightest fur rat out there, and she quickly finds herself in the middle of the streets. Not only is she hungry, but she also misses home. Plus, just imagine just how mad Ray and Nicole would be when they found out Mimi was out by herself in the middle of the night!

+ Mimi must find five jars of strawberry jam, and get back home before Ray and Nicole 
wake up.


**Goal**
+ The goal is for Mimi to collect five jars of strawberry jam that appear on the screen,
then return home before Ray and Nicole wake up.

**Basic Mechanics**
+ The player will be controlling Mimi with the four directional keys (it's Manhattan, so Mimi can't move diagonally). Strawberry jam will appear randomly on the screen, and Mimi will collect each jar by coming in contact with the jar. 

+ The city is full of wild taxi drivers, whom Mimi must avoid deftly.

+ When all jars are collected, Mimi will then embark on a mission back home before it's too late.

**Levels/Modes**
+ Level One is the game mentioned above. The player wins when Mimi is able to collect three jars within the given space of time. 

+ Level Two, which is reached when the player clears Level One, helps Mimi get back home safely before Ray and Nicole wake up. Unfortunately, the city is full of mice - which Mimi happens to hate, despite being a fur rat herself. And because the rats are a little more unorganized than Taxi drivers, they tend to move around much more randomly.

+ After wandering around, Mimi finally gets a sense of where home (which will appear on screen after some time) is. The game is over when Mimi arrives home safely. 


