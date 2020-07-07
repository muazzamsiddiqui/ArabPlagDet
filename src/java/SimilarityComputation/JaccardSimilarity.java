/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimilarityComputation;

import Common.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Hamza
 */
public class JaccardSimilarity {

    public JaccardSimilarity() {
        // do nothing.
    }

    private void JaccardSimilarityRoutine() {
        String document1 = "", document2 = "";
        double jaccardSimility = 0.0;
        //double threshold =0.5;
        double threshold = 0.15;
        jaccardSimility = ComputeJaccardSimility(document1, document2);
        ArrayList<ArrayList<Double>> simility = new ArrayList<ArrayList<Double>>();
        if (jaccardSimility >= threshold) {
            List<String> listOfSentences1 = new ArrayList<String>(Arrays.asList(document1.split("[\n.]", -1)));
            listOfSentences1 = Util.removeEmptyString(listOfSentences1);
            List<String> listOfSentences2 = new ArrayList<String>(Arrays.asList(document2.split("[\n.]", -1)));
            listOfSentences2 = Util.removeEmptyString(listOfSentences2);
            for (int i = 0; i < listOfSentences1.size(); i++) {
                simility.add(new ArrayList<Double>());
                for (int j = 0; j < listOfSentences2.size(); j++) {
                    double temp = ComputeJaccardSimility(listOfSentences1.get(i), listOfSentences2.get(j));
                    simility.get(i).add(temp);
                }
            }
        }
        //String h="";
    }

    public static double ComputeJaccardSimility(String document1, String document2) {
        if (document1.trim().length() == 0 || document2.trim().length() == 0) {
            return 0;
        }

        String[] word_seq_text1 = document1.split("\\s+");
        String[] word_seq_text2 = document2.split("\\s+");
        Set<String> Distinct_words_text1 = new HashSet<>();
        Set<String> Distinct_words_text2 = new HashSet<>();

        for (int i = 0; i < word_seq_text1.length; i++) {
            String tmp_wd = word_seq_text1[i].trim();
            if (tmp_wd.length() > 0) {
                Distinct_words_text1.add(tmp_wd.toLowerCase());
            }
        }

        for (int i = 0; i < word_seq_text2.length; i++) {
            String tmp_wd = word_seq_text2[i].trim();
            if (tmp_wd.length() > 0) {
                Distinct_words_text2.add(tmp_wd.toLowerCase());
            }
        }

        if (Distinct_words_text1.isEmpty() || Distinct_words_text2.isEmpty()) {
            return 0.0;
        }

        Set<String> unionXY = new HashSet<>(Distinct_words_text1);
        unionXY.addAll(Distinct_words_text2);
        Set<String> intersectionXY = new HashSet<>(Distinct_words_text1);
        intersectionXY.retainAll(Distinct_words_text2);
        double t = intersectionXY.size() / (double) unionXY.size();

        return (double) Math.round(t * 100) / 100;
    }

    public static double ComputeJaccardSimility2(String document1, String document2) {
        if (document1.trim().length() == 0 || document2.trim().length() == 0) {
            return 0;
        }

        String[] word_seq_text1 = document1.split("\\s+");
        String[] word_seq_text2 = document2.split("\\s+");
        Set<String> Distinct_words_text1 = new HashSet<>();
        Set<String> Distinct_words_text2 = new HashSet<>();

        for (int i = 0; i < word_seq_text1.length; i++) {
            String tmp_wd = word_seq_text1[i].trim();
            if (tmp_wd.length() > 0) {
                Distinct_words_text1.add(tmp_wd.toLowerCase());
            }
        }
        for (int i = 0; i < word_seq_text2.length; i++) {
            String tmp_wd = word_seq_text2[i].trim();
            if (tmp_wd.length() > 0) {
                if (Distinct_words_text1.contains(tmp_wd.toLowerCase())) {
                    Distinct_words_text2.add(tmp_wd.toLowerCase());
                }
            }
        }

        if (Distinct_words_text1.isEmpty() || Distinct_words_text2.isEmpty()) {
            return 0.0;
        }

        Set<String> unionXY = new HashSet<>(Distinct_words_text1);
        Set<String> intersectionXY = new HashSet<>(Distinct_words_text1);
        intersectionXY.retainAll(Distinct_words_text2);
        double t = intersectionXY.size() / (double) unionXY.size();

        return (double) Math.round(t * 100) / 100;
    }

}
