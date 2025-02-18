Changes from HW3:
* I created an abstract scoring class of its own instead of having the scoring logic nested in the
PokerTriangles class. I originally was going to do this for HW2, but I ran out of time. I then
implemented the scoring analogous to the structure of PokerBasicPolygons to 1: Remove duplicate code
when scoring PokerTriangles and PokerRectangles, and 2: To separate the nuances of scoring each type
of game in a clean manner.
* I also abstracted the implementations of PokerTriangles and PokerRectangles as there was a lot of
duplicate code, so I factored out the duplicated code into the abstract PokerBasicPolygons class.