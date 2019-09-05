package com.stackroute.sentimentAnalysis;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.List;

public class StringSentiment {
    public static void main(String[] args) throws NullPointerException{
        String line="Hello this is NOt Good";
        CoreDocument coreDocument=new CoreDocument(line);
        StanfordCoreNLP.getExistingAnnotator(String.valueOf(coreDocument));
        List<CoreSentence> sentences=coreDocument.sentences();
        for (CoreSentence sentence:sentences){
            String sentiment=sentence.sentiment();
            System.out.println(sentiment+"\t"+sentence);
        }

    }

}
