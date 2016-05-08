# Utils, Loaders and Parsers

I've created a fairly incomplete set of random utilities for Gengine.

## Loaders
- *TiledWorldLoader* - Loads a TiledWorld from a file.
- *TilesetLoader* - Loads a Tileset to be used with a TiledWorld.

There should be way more loaders there, and I plan on making a generalized
one later on.

## Savers
Sadly, there aren't any so far.

## Parsers
I've made a couple of tokenizers to aid me with stuff.

First of them is the aptly named **`Tokenizer`**. It turns a string into a set
of `Tokens` (containers containing a matching rule ID and the matching substring
itself) based on some rules. I've actually snipped this one from one of my
earlier projects, FLike/bloatASM.

The other one of them is a strangely named **`SwitchDecoder`**. It's designed
to tokenize an array of strings based on rules. The name may hint it was 
originally intended for parsing some command line options etc, but it seems
to be somewhat useful for other things as well.
