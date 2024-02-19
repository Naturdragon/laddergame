# The O's
**The O's** is a ladder & snakes game themed around cereal O's trying to take a swim in a cereal bowl as fast as possible.
After escaping the cereal box *(Title Screen)*, the players must select their representative character *(Player Selection Screen)*, with the characters spawning in front of the box right after.
Rolling the dice -- both normal and special -- to the finish line is the name of the game *(Game Board Screen)*!
Once all players reach the end, a ranking based on their turns will be displayed, with the winning O taking charge of the cereal bowl *(Winning Screen)*.

> To start the game, you must run TheOs.java (laddergame > src > main > java > com.example.theos > TheOs)!

Some of the commits have been done by one person, even though multiple people worked on the code, since we worked on it together & sometimes used the same device with multiple people.
Additionally, Lisa Freund was unable to commit her code in the early stages of programming, so Marko Zdravkovic committed it for her on his device.

TODO (as an add-on after the project has concluded):
1. More animations to enhance the overall aesthetic of the game. (playFallWaterfall + end animations)
2. Implement possibility to use ladders/snakes if negative fields are rolled with die
3. Correct crossing with new branch (von Martin Schindler)
4. Invert images when going left
5. Spread out characters in bowl once finishing game
6. Make it possible to play full-screen

BUGS:
1. Crossing fields don't work well when rolling negative numbers (Ist man beispielsweise bei field302, würfelt mit Diva eine -3, so wird man nur bis field301 zurückgesetzt & man kann nicht auf field208 gebracht werden.)