package uk.ac.anglia.student.hilda.studentadviser.util;

/**
 * Created by Ibok on 07/04/2017.
 */

public class Extractor {

    private String keyword;
    private String context;
    //private int context;
    private static final String[] context_array ={"how much","how","when","where","who","whom","what"};
    private static final String[] keywords_array ={"examination","results","modules","mitigation","resit","extension",
            "graduation","tutor","library","benefits","accomodations","scholarship","busary","sponsor","attendance",
            "visa","benefits","withdrawal"};
    public Boolean isContext = false;
    public Boolean isKeyWord = false;

    public Extractor(String question){
        for (int i = 0 ; i < context_array.length;i++){
            if(check(question,context_array[i])){
                setContext(context_array[i]);
                this.isContext = true;
                break;
            }
            else{
                setContext("non");
            }
        }

        for (int i = 0 ; i < keywords_array.length;i++){
            if(check(question,keywords_array[i])){
                setKeyword(keywords_array[i]);
                this.isKeyWord = true;
                break;
            }
            else{
                setKeyword("non");
            }
        }
    }


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {

        this.context = context;
    }

    private Boolean check(String question, String word)
    {
        Boolean isFound = true;
        if ( question.toLowerCase().indexOf(word.toLowerCase()) == -1 ) {
            isFound = false;
        }
        return isFound;
    }

}
