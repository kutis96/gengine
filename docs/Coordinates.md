# Coordinates
Coordinates are a set of numeric values specifying a position on a given `World` or whatever.

The **`CoordsFixedD`** are `Coords` with a fixed amount of dimensions.
It allows one to do arithmetic on more of such objects, which really turns it
more into a Vector, than anything else.

I don't actually know why did I choose to use my own `Coords` system, but I did.
It always is quite a bother though, so I may actually replace it with something
else in the future.

## TiledWorld Coordinates
Coordinates within the **`TiledWorld`** are counted from the top left corner
of the screen. Say, (2,3,1) coordinate would address a tile that is 2 tiles
to the right of the origin, three tiles down, and one tile up the Z axis.

Entity coordinates within the world use the same 'tile' coordinates, but
obviously, they can use non-integer numbers as well.