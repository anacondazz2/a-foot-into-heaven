## A Foot Into Heaven
> "A Foot Into Heaven" is a turn based game where the main character embarks on a mission to reclaim the throne from The Being.  

It takes great inspiration from "Fire Emblem", particularly the Game Boy Advance iterations.  

> beginning of the game

![Demo](docs/gif/begin.gif)

> the final boss fight, where "Blade of Eithalon" is attained

![Demo](docs/gif/prodeus.gif)

## Features

### Weapon Triangle
> the crux of any "Fire Emblem" title  

In the game, each character is of a specfic class.  
Each class has unique weapons they can use and a specific amount of tiles they can move each turn.  
The goal of the game is to defeat the enemies using strategy, such as the weapon triangles.  
Much of this is explained in the "Details" tab.  

![Alt text](docs/img/details-tab.png) 

### User Interface
- menu can be accessed during the game through `esc`
- menu provides Details and Controls page  

### Additional
- Pathfinding is used to implement character movement.
- Inheritance was used to share common state and behaviour between the various characters.  
- Units have 5 different stats (MaxHealth, ATK, DEF, EV, SPD).  
These stats were used to calculate damage and crit chance. 

## Assets and Illustrations

### The Art
> all hand-drawn  

Attention was put into making each animation deliver impact, particularly the critical strikes.  

![Demo](docs/gif/fire.gif)  

![Demo](docs/gif/chapter6.gif)

### The Music
> an orginal soundtrack

Most of the songs listed are made by first starting with an AI generated sample, which was then heavily tuned and adjusted.  
[Soundtracks](A-Foot-Into-Heaven/sounds/)  

![Alt text](docs/img/soundtracks.png)

> Lead Game Designer and Artist: Jonathan Zhao (no socials...)  
> Credits to [Danpost](https://www.greenfoot.org/users/2991) for the TextImage class  
> Game runs on "Greenfoot" Java framework.  
> Made for ICS4U (Computer Science) at Pierre Elliott Trudeau High School.
