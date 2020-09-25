package readability;

import java.math.BigDecimal;
import java.io.IOException;
import java.util.*;
import java.io.File;

public  class Stage4  {
    public static void main(String[] args) {
        // System.out.println(args[1]);

        File file = new File(args[0]);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String input = scanner.nextLine();
                System.out.print("The text is:\n"+input);

                System.out.println("Syllables: "+getSyllables(input));
                System.out.println("Polysyllables: "+getPolysyllables(input));
                System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");
                Scanner scan=new Scanner(System.in);
                String choice=scan.next();
                switch (choice) {
                    case ("ARI"):
                        System.out.printf("Automated Readability Index: %.2f (about " + getAge(getARI(findWords(input), findSentence(input), findCharacters(input))) + " year olds).%n", getARI(findWords(input), findSentence(input), findCharacters(input)));

                        break;
                    case ("FK"):
                        System.out.printf("Flesch–Kincaid readability tests: %.2f (about " + getAge(getFK(findWords(input),findSentence(input),getSyllables(input))) + " year olds).%n", getFK(findWords(input),findSentence(input),getSyllables(input)));

                        break;
                    case ("SMOG"):
                        System.out.printf("Simple Measure of Gobbledygook: %.2f (about " + getAge(getSMOG(getPolysyllables(input),findSentence(input))) + " year olds).%n", getSMOG(getPolysyllables(input),findSentence(input)));

                        break;
                    case ("CL"):
                        System.out.printf("Coleman–Liau index: %.2f (about " + getAge(getCL(input)) + " year olds).%n", getCL(input));

                        break;
                    case ("all"):
                        System.out.printf("Automated Readability Index: %.2f (about " + getAge(getARI(findWords(input), findSentence(input), findCharacters(input))) + " year olds).%n", getARI(findWords(input), findSentence(input), findCharacters(input)));
                        System.out.printf("Flesch–Kincaid readability tests: %.2f (about " + getAge(getFK(findWords(input),findSentence(input),getSyllables(input))) + " year olds).%n", getFK(findWords(input),findSentence(input),getSyllables(input)));
                        System.out.printf("Simple Measure of Gobbledygook: %.2f (about " + getAge(getSMOG(getPolysyllables(input),findSentence(input))) + " year olds).%n", getSMOG(getPolysyllables(input),findSentence(input)));
                        System.out.printf("Coleman–Liau index: %.2f (about " + getAge(getCL(input)) + " year olds).%n", getCL(input));



                        break;

                }

                 System.out.print("This text should be understood in average by "+ (getAge(getARI(findWords(input), findSentence(input), findCharacters(input)))+getAge(getFK(findWords(input),findSentence(input),getSyllables(input)))+getAge(getSMOG(getPolysyllables(input),findSentence(input)))+getAge(getSMOG(getPolysyllables(input),findSentence(input)))+ getAge(getCL(input)))/4+ " year olds.");
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

            for (char j : ch) {

                count++;

            }
        }
        System.out.println("Characters: " + count);
        return count;

    }
    public static double getARI(int w,int s,int c){
        double words =(double)w;
        double characters =(double)c;
        double sentence =(double)s;


        double score = 4.71 *characters / words+0.5* words / sentence -21.43;

        return score;

    }
    public static double getFK(int words,int sentences,int syllables){
        double score=0;
        score = 0.39* words/sentences+11.8*syllables/words-15.59;
        return score;
    }
    public  static double getSMOG(int polysyllables,int sentences){
        double score=0;
        score=1.043*Math.sqrt(polysyllables*30/sentences)+3.1291;
        return score;
    }
    public static double getCL(String input){
        double score=0;
        double L=getL(input);
        double S= getS(input);

        score=0.0588*L-0.296*S-15.8;
        return score;
    }
    public  static double getL(String input){
        double L = 100.0 * findCharacters(input)/findWords(input);
        return L;
    }
    public static double getS(String input){

        double S = 100.0 * findSentence(input)/findWords(input);
        return  S;
    }
    public static int getSyllables(String input) {
        int syllable = 0;
        String[] words = input.replaceAll("e[.,?!\\s]{1}", " ").toLowerCase().split("\\s+");

        for (String i : words) {

            syllable += getSyllablesCount(i);
        } // words
        return syllable;

    }
    public static int getPolysyllables(String input){
        int polysyllable = 0;
        String[] words = input.replaceAll("e[.,?!\\s]{1}", " ").toLowerCase().split("\\s+");

        for (String i : words) {
            if( getSyllablesCount(i) >2)
            polysyllable ++;
        } // words
        return polysyllable;
    }

    public static int getSyllablesCount(String i) {

        char[] ch = i.toCharArray();
        int vowels = 0;

        boolean pre = true;
        for (char j : ch) {

            if (isVowel(j) && pre) {
                // System.out.println(ch[j]);
                ++vowels;
                pre = false;
            } else {
                pre = true;
            }

        } // char

        return vowels;
    }

    public static boolean isVowel(char ch) {
        char[] vovels = { 'a', 'e', 'i', 'o', 'u', 'y' };
        for (int i = 0; i < vovels.length; i++) {
            if (vovels[i] == ch) {
                return true;
            }
        }
        return false;
    }

    public static int getAge(double score){
        int[] age = { 6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 24, 24 };

        int roundedScore = (int) Math.ceil(score);
        int i = 0;
        if (roundedScore > 14) {
            roundedScore=14;
        }
        return age[roundedScore-1];
    }



}


