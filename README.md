# The O's
**The O's** is a ladder & snakes game themed around cereal O's trying to take a swim in a cereal bowl as fast as possible.
After escaping the cereal box *(Title Screen)*, the players must select their representative character *(Player Selection Screen)*, with the characters spawning in front of the box right after.
Rolling the dice -- both normal and special -- to the finish line is the name of the game *(Game Board Screen)*!
Once all players reach the end, a ranking based on their turns will be displayed, with the winning O taking charge of the cereal bowl *(Winning Screen)*.

> To start the game, you must run TheOs.java (laddergame > src > main > java > com.example.theos > TheOs)!

Some of the commits have been done by one person, even though multiple people worked on the code, since we worked on it together & sometimes used the same device with multiple people.
Additionally, Lisa Freund was unable to commit her code in the early stages of programming, so Marko Zdravkovic committed it for her on his device.

TO-DO / ADD-ONS:
1. [Mateo] : Implement more animations to enhance the overall aesthetic of the game (playFallWaterfall + end animations)
2. [Aleyna] Implement possibility to use ladders/snakes if negative fields are rolled with die
3. FIN - [Martin] : Merge new branch for crossing fields with main branch (movePlayer())
4. FIN - [Lisa] : Spread out finished players in bowl
5. FIN - [Lisa] : Implement visual aid for remaining fields at crossing
6. FIN - [Marko] : Implement fullscreen
7. FIN - [Marko] : Add missing music button in winning screen
8. (Invert images when going left)

BUGS:
1. FIN - [Martin] Crossing fields don't work well when rolling negative numbers (Ist man beispielsweise bei field302, würfelt mit Diva eine -3, so wird man nur bis field301 zurückgesetzt & man kann nicht auf field208 gebracht werden)
2. FIN - [Martin] When players cross the crossing the turn order is wrong.
3. [Martin] The Character Walks while the player selects the path with the arrows.
4. [Martin] A null pointer exception gets thrown when a player is moving to the crossing field.