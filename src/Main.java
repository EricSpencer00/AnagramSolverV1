import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Scrabble Anagrams!");
        System.out.println("Please enter the letters you have, including '?' for a wildcard (max of 2 ?'s):");

        boolean run = true;
        while(run) {

            String userLetters = scanner.nextLine().toUpperCase();
            if(userLetters.equals("***")) {
                break;
            }
            printAllAnagrams(userLetters);
            System.out.println("Keep entering letters or submit '***' to quit:");
        }
        scanner.close();
    }

    public static void printAllAnagrams(String wordWithWildcards) {
        List<String> words = readWordsFromFile("src/ScrabbleDictionary.txt");

        if (words != null) {
            Set<String> uniqueWords = new HashSet<>();

            if(wordWithWildcards.contains("???") || wordWithWildcards.length() > 11) {
                System.out.println("Too many characters!");
            }
            else if (wordWithWildcards.contains("??")) {
                for (char c1 = 'A'; c1 <= 'Z'; c1++) {
                    for (char c2 = 'A'; c2 <= 'Z'; c2++) {
                        String modifiedWord = wordWithWildcards.replaceFirst("\\?\\?", Character.toString(c1) + Character.toString(c2));
                        addValidWordsToSet(words, modifiedWord, uniqueWords);
                    }
                }
            } else if (wordWithWildcards.contains("?")) {
                for (char c = 'A'; c <= 'Z'; c++) {
                    String modifiedWord = wordWithWildcards.replace('?', c);
                    addValidWordsToSet(words, modifiedWord, uniqueWords);
                }
            } else {
                addValidWordsToSet(words, wordWithWildcards, uniqueWords);
            }

            if (!uniqueWords.isEmpty()) {
                Map<Integer, List<String>> sortedWordsByLength = new HashMap<>();

                for (String word : uniqueWords) {
                    int length = word.length();
                    sortedWordsByLength.computeIfAbsent(length, k -> new ArrayList<>()).add(word);
                }

                sortedWordsByLength.forEach((length, wordsList) -> {
                    System.out.println("Words with length " + length + ": " + wordsList);
                });
                
            } else {
                System.out.println("No valid words found");
            }
        }
    }

    private static void addValidWordsToSet(List<String> words, String modifiedWord, Set<String> uniqueWords) {
        List<String> validWords = findValidWords(words, modifiedWord);

        if (!validWords.isEmpty()) {
            uniqueWords.addAll(validWords);
        }
    }



    private static List<String> readWordsFromFile(String fileName) {
        List<String> words = new ArrayList<>();

        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            while(fileScanner.hasNext()) {
                words.add(fileScanner.next().toUpperCase());
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.err.println(STR."File not found: \{fileName}");
            return null;
        }

        return words;
    }

    private static List<String> findValidWords(List<String> words, String availableLetters) {
        List<String> validWords = new ArrayList<>();

        for(String word : words) {
            if (canCreateWord(word, availableLetters)) {
                validWords.add(word);
            }
        }

        return validWords;
    }

    private static boolean canCreateWord(String word, String availableLetters) {
        Map<Character, Integer> letterCounts = new HashMap<>();
        for (char letter : availableLetters.toUpperCase().toCharArray()) {
            letterCounts.put(letter, letterCounts.getOrDefault(letter, 0) + 1);
        }

        for (char letter : word.toUpperCase().toCharArray()) {
            if (!letterCounts.containsKey(letter) || letterCounts.get(letter) == 0) {
                return false; // Letter not available or used up
            }
            letterCounts.put(letter, letterCounts.get(letter) - 1); // Use the letter
        }
        return true; // All letters in the word can be accounted for
    }


    public static int scoreWord(String word) {
        int score = 0;

        for(char letter : word.toCharArray()) {
            score += getLetterScore(letter);
        }

        return score;
    }

    private static int getLetterScore(char letter) {
        return switch (letter) {
            case 'E', 'A', 'I', 'O', 'N', 'R', 'T', 'L', 'S', 'U' -> 1;
            case 'D', 'G' -> 2;
            case 'B', 'C', 'M', 'P' -> 3;
            case 'F', 'H', 'V', 'W', 'Y' -> 4;
            case 'K' -> 5;
            case 'J', 'X' -> 8;
            case 'Q', 'Z' -> 10;
            default -> 0; // Blank tiles (or any other characters) score 0 points
        };
    }

}