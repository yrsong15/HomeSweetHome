# Home Sweet Home :dog: :heart: :strawberry: 

## Design Goals

The goal was to design a functional game according to the Initial Design Plan that can be seen below.

## Adding Features

Currently, the vast majority of code lies in a single file, TheGame.java. In this current state of design, a new level would be added by appending an additional *if-statement* to the step() method within **TheGame.java**, and calling the new level(which would likely be in the form of a method) from within the if-statement.

## Tradeoffs

+ For me, one of the most important goals of this assignment was being able to deploy a working model of the game in time. As such, I resorted to a single file that contains the bulk of my code, which did serve its purpose, but ended up being a very poor design in retrospect.

+ Another characteristic of the game is how I don't have separate classes for my Sprites. 
	* **This was done on purpose** because I didn't feel the need for a separate Sprite class, especially because there were no additional features that my Sprites required. 
	* Pros: I was able to keep the functionalities of my objects without creating a separate class, mainly because my methods were able to handle my objects exactly the way I wanted it to be.
	* Cons: This was one of the reasons **TheGame.java** became a very long file. Had I created separate classes for each object, the length of the file would have been significantly reduced, especially because I would not have had to refer to each image file from within **SplashPage.java**.
	* Now that I think about it, it would have been better design to implement a different class for each object just so I can keep **TheGame.java** just for game-related algorithms.

## Initial Design Plan
**Genre**

+ Home Sweet Home is a 2-D Scrolling Platformer game featuring Mimi the fur rat. 
+ Mimi's favorite thing in the world is lying on the bed and doing nothing.
+ Her second favorite thing is strawberry jam - her only diet.
+ The player will help Mimi collect 5 jars of strawberry jam before going home, so that she has something to eat while enjoying Netflix on her bed.

**Story**

+ Mimi is a tiny, 70-year old dog who lives in a New York apartment with Ray and Nicole.
+ Due to how nice Ray and Nicole treat Mimi, as well as Mimi's old age, Mimi absolutely hates going outside, even for a 10-minute walk. The only thing that can ever get Mimi out of bed is strawberry jam, her favorite food in the world.
+ The story begins when Mimi wakes up in the middle of the night while Ray and Nicole are asleep. She wants to open the fridge to get some jam, but because she is so tiny, she is unable to reach the handle of the fridge. Determined, Mimi sets out to the City just to get strawberry jam.
+ Unfortunately, Mimi is not the brightest fur rat out there, and she quickly finds herself in the middle of the streets. Not only is she hungry, but she also misses home. Plus, just imagine just how mad Ray and Nicole would be when they found out Mimi was out by herself in the middle of the night!
+ Mimi must find five jars of strawberry jam, and get back home before Ray and Nicole wake up.


**Goal**

+ The goal is for Mimi to collect five jars of strawberry jam that appear on the screen, then return home before Ray and Nicole wake up.

**Basic Mechanics**

+ The player will be controlling Mimi with the four directional keys (it's Manhattan, so Mimi can't move diagonally). Strawberry jam will appear randomly on the screen, and Mimi will collect each jar by coming in contact with the jar. 
+ The city is full of wild taxi drivers, whom Mimi must avoid deftly.
+ When all jars are collected, Mimi will then embark on a mission back home before it's too late.

**Levels/Modes**

+ Level One is the game mentioned above. The player wins when Mimi is able to collect three jars within the given space of time. 
+ Level Two, which is reached when the player clears Level One, helps Mimi get back home safely before Ray and Nicole wake up. Unfortunately, the city is full of mice - which Mimi happens to hate, despite being a fur rat herself. And because the rats are a little more unorganized than Taxi drivers, they tend to move around much more randomly.
+ After wandering around, Mimi finally gets a sense of where home (which will appear on screen after some time) is. The game is over when Mimi arrives home safely. 