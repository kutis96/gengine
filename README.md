# Gengine
Gengine is a *work-in-progress* "RPG game engine" originally
intended as a project for the A0B36PR2 course at CTU in Prague,
written by *Richard Kutina*.

My original design goal of this project was to make it almost
as generic as possible, as the assignment did not call for any
particular concrete requirements, other than making it an RPG-style thing. 
Alas, pretty much anything could be called an RPG.

Sadly enough, this generalization has lead to my boredom in the few early
weeks or months, not exactly expanding this project by much. 
It could also be the cause of some missing features, such as extended
interaction handling and more/better GUI elements.

This project is probably going to keep on expanding regardless of the
May 8 deadline, as I don't exactly feel like dropping my 6.5 kLOC straight
into the trashbin.

---

## Features
- Implements the ubiquitous MVC model
    - Mostly generalized design
    - Recently added, can definitely be improved
- Square grid (top-down) rendering
    - Optional Isometric rendering (currently dropped, will be re-added soon)
- Supports animations of various things
- Relatively simple to embed into GUI applications
    - Uses Swing framework
- Various event generators
    - Proximity events
    - Keyboard and Mouse events
    - Visibility events\*
- Overlays
    - Extensible
    - Show various information around entities with ease!
- Generic utils to aid development
    - Loaders for `K:V` and `K:V[]` files
    - Ready-to-use generic string tokenizer
    - Ready-to-use command-line parameter tokenizer
    - Easy-to-extend `Workers`
    - Soon: Map converter from [Tiled][tiled] world editor
    - Self-made monospace text renderer using [Maze.io fonts][maze]

    \*) *Not exactly working atm.*

##Planned Features
- Improved event generation
    - Visibility event generation enabling simple pathfinders, automatic sentries, whatever!
- Improved and extensible loaders
    - Easier-to-use World loaders
    - Consolidation of map and tileset definitions
    - Native support for [Tiled][tiled] files
    - Built as both Loaders and Savers, possibly using some file structure description
- More renderers
    - Bring back the `IsometricRenderer`
- Easy-to-use interaction framework
- More GUI prototypes and generic Views
    - Easy-to-extend Menu View
    - View blending
- Built-in scripting engine
    - Enable true external entity loading
    - Enable modding and ease debugging

---

## Acknowledgements
* Lukáš Zounek
    - Demo map and tilesets
    - [Tiled][tiled] map converter
* [Tiled][tiled] map editor
    - Excellent map editor, supporting both 2D grid and isometric world editing,
      as well as object placement etc. Lovely stuff.
* [Maze.io][maze]
    - Thank you for freely providing the font I'm using.
* Jakub Mrva
    - Thanks for not killing me much after always coming late... \*cough\* and I'm sorry this project is no exception.
* You
    - Yes, you. Thank you for reading this.


[tiled]: <http://www.mapeditor.org/>
[maze]:  <https://maze.io/piece/font/>