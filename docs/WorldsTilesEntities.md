# A few words about Words, Tiles, and Entities
Worlds are just storage containers containing whatever the World is made of,
and some WorldEntities.

**`WorldEntities`** are objects that can move around and do things withing a
world. They of course don't have to be moving around, they can do whatever they
want, but the thing is, they're the only things within a World that can be added
on the fly, removed, catching events, whatever.

In **`TiledWorld`s** there is another mildly interesting type stored in the
world. And as the name suggest, it's a set of **`Tile`s**.

a **`Tile`** is a static piece of something in a `TiledWorld` that's there
simply as a scenery. Currently, they're only stored in a fixed grid.
Any other scenery that's meant to be offset is somewhat meant to be a static
`WorldEntity`.