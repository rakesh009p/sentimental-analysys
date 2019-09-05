package com.stackroute.sentimentAnalysis;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreNLPProtos;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.DocumentProcessor;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.apache.catalina.Pipeline;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;


public class Tokenizer {
    public static void main(String[] args) throws IOException {
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

        DocumentPreprocessor documentProcessor= new DocumentPreprocessor(inline);
        for (List sentence : documentProcessor) {
            System.out.println(sentence);
            }
       Tokenizer tokenizer=new Tokenizer();
        System.out.println(tokenizer.findSentiment(inline));
//        System.out.println(ptbTokenizer1.findSentiment(inline));

        }
    public double findSentiment(String line) throws NullPointerException{

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit,pos, parse, sentiment, lemma");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        double mainSentiment = 0;
        if (line != null && line.length() > 0) {
            double longest = 0;
            Annotation annotation = pipeline.process((line));
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


}


