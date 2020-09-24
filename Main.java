package readability;

import java.math.BigDecimal;
import java.io.IOException;
import java.util.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        // System.out.println(args[1]);

        File file = new File(args[0]);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String input = scanner.nextLine();
                System.out.print("The text is:\n" + input);
                double score = findScore(findWords(input), findSentence(input), findCharacters(input));

                System.out.printf("The score is: %.2f ", score);
                getAge(score);

                // System.out.print(scanner.nextLine() + " ");
            }
        } catch (Exception e) {
            System.out.println("No file found: " + args[0]);
        }
    }

    public static int findWords(String input) {

        int count = 0;

        String[] words = input.split(" \\s|\\pZ");

        count += words.length;

        System.out.println("\n\nWords: " + count);
        return count;
    }

    public static int findSentence(String input) {
        int count = 0;
        String[] sentence = input.split("[.!?]");
        System.out.println("Sentences: " + sentence.length);
        return sentence.length;
    }

    public static int findCharacters(String input) {
        int count = 0;
        String[] words = input.split(" \\s|\\pZ");

        for (String i : words) {

            char[] ch = i.toCharArray();
            ;
            // System.out.println(ch);
            for (char j : ch) {
                // if(Character.isAlphabetic(j)){
                count++;
                // }
            }
        }
        System.out.println("Characters: " + count);
        return count;

    }

    public static double findScore(int w, int s, int c) {
        double words = (double) w;
        double characters = (double) c;
        double sentence = (double) s;

        double score = 4.71 * characters / words + 0.5 * words / sentence - 21.43;

        return score;

    }

    public static void getAge(double score) {
        int[] age = { 6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 24, 24 };

        int roundedScore = (int) Math.ceil(score);
        int i = 0;
        if (roundedScore > 14) {
            roundedScore = 14;
        }
        System.out.println("\nThis text should be understood by " + age[roundedScore - 2] + "-" + age[roundedScore - 1]
                + " year olds. ");
    }

}
