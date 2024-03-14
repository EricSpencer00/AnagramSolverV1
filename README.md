SCRABBLE ANAGRAM SOLVER V1

Give a set of letters to the program and it will output a 
set of possible words you can make according to the English 
Scrabble dictionary. 

Wildcards are supported, with a maximum of two. Please note
that adding any more possibilities for wildcards adds a
larger complexity (O(n^2) -> O(n^3)) due to the nature of
the algorithm. I have hard set this for two for this reason.

How to use:

1. Enter letters you have (enter ? for a wildcard, max 2)
2. See output, enter any more letters you have
3. '***' to console to quit.

File Structure:

Main.java
- Program logic

ScrabbleDictionary.txt
- Dictionary of valid Scrabble words
- Taken from [https://github.com/redbo/scrabble/blob/master/dictionary.txt]

Notes:

- Make sure ScrabbleDictionary.txt path is correctly referenced in Main.java when running
- Dictionary can be swapped out for another set of words

Contributing

- Feel free to contribute by adding new features or changing algorithms
