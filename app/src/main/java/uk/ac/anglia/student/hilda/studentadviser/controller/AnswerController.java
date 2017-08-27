package uk.ac.anglia.student.hilda.studentadviser.controller;

import uk.ac.anglia.student.hilda.studentadviser.model.*;
import uk.ac.anglia.student.hilda.studentadviser.util.Extractor;
import uk.ac.anglia.student.hilda.studentadviser.util.Parser;

import uk.ac.anglia.student.hilda.studentadviser.model.User;



public class AnswerController {

    private Parser answerDAO;


    public String index(String email, String name) {
        return "Welcome to Hildas Student adviser";
    }


    public String answer( String question) {

        String theanswer = "We cannot provide adequate infomation on your question.Pls book a meeting with the student adviser";
        String links = "http://web.anglia.ac.uk/anet/student_services/student_advisers/contact.phtml";
        Extractor infoExtractor = new Extractor(question);{
            if(infoExtractor.isKeyWord || infoExtractor.isContext){
                String key = infoExtractor.getKeyword();
                String context = infoExtractor.getContext();

                try {

                    Answer answer = answerDAO.findbyKey(key);
                    System.out.println(answer.toString());

                    switch (context) {
                        case "how":
                            theanswer = answer.getR_how();
                            break;
                        case "howmuch":
                            theanswer = answer.getR_how_much();
                            break;
                        case "what":
                            theanswer = answer.getR_what();
                            break;
                        case "when":
                            theanswer = answer.getR_when();
                            break;
                        case "where":
                            theanswer = answer.getR_where();
                            break;
                        case "who":
                            theanswer = answer.getR_who();
                            break;
                        default:
                            theanswer = "We cannot provide adequate infomation on your.Pls book a meeting with the student adviser";
                    }
                }
                catch (Exception ex) {
                    System.out.println(ex);

                    return "Chill I am taking a nap";
                }
            }
        }
        return theanswer + "\n" + links;
    }
}
