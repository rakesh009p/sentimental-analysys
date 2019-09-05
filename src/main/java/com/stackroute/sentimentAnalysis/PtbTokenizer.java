package com.stackroute.sentimentAnalysis;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.AnnotatorPool;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.apache.catalina.Pipeline;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class PtbTokenizer {
    static StanfordCoreNLP pipeline;
    private static Properties tweet;

    public static void main(String[] args) throws NullPointerException, IOException {
        String string="java is not java";
        URL url=new URL("http://localhost:8080/tweets");
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        Scanner sc = new Scanner(url.openStream());
        String inline=null;
        while(sc.hasNext())
        {

            inline =sc.nextLine()+inline;
            System.out.println("\n");
        }

        File myFile = new File("/home/cgi/Videos/myfile.txt");
        Writer writer=new FileWriter(myFile);
        BufferedWriter bufferedWriter=new BufferedWriter(writer);
        bufferedWriter.write(inline);


        PTBTokenizer ptbTokenizer = new PTBTokenizer<>(new FileReader(myFile), new CoreLabelTokenFactory(), "");
        while (ptbTokenizer.hasNext()) {
            CoreLabel coreLabel = (CoreLabel) ptbTokenizer.next();
            System.out.println(coreLabel);
        }
        PtbTokenizer ptbTokenizer1=new PtbTokenizer();
        System.out.println(ptbTokenizer1.findSentiment(myFile));
    }

   public static double findSentiment(File line) throws NullPointerException{

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit,pos, parse, sentiment, lemma");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        double mainSentiment = 0;
        if (line != null && line.length() > 0) {
            double longest = 0;
            Annotation annotation = pipeline.process(String.valueOf(line));
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }

            }
        }return Math.ceil(mainSentiment);}


//        if (mainSentiment == 2 || mainSentiment > 4 || mainSentiment < 0) {
//            return null;
//        }
//        TweetWithSentiment tweetWithSentiment = new TweetWithSentiment(line, toCss(mainSentiment));
//        return tweetWithSentiment;
//
//    }




    }

//    public static void init() {
    //            System.out.println(findSentiment(text));
    //        return labels;
//        pipeline = new StanfordCoreNLP("myfile.properties");
//    }
//    public static int findSentiment(String tweet) throws NullPointerException{
//
//        int mainSentiment = 0;
//        if (tweet != null && tweet.length() > 0) {
//            int longest = 0;
//            Annotation annotation = new Annotation(tweet);
//            for (CoreMap sentence : annotation
//                    .get(CoreAnnotations.SentencesAnnotation.class)) {
//                Tree tree = sentence
//                        .get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
//                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
//                String partText = sentence.toString();
//                if (partText.length() > longest) {
//                    mainSentiment = sentiment;
//                    longest = partText.length();
//                }
//
//            }
//        }
//        return mainSentiment;


