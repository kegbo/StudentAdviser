package uk.ac.anglia.student.hilda.studentadviser.model;


/**
 * Created by Ibok on 08/04/2017.
 */

public enum Advisers {


    STUDENT_ADVISER("STUDENT_ADVISER"),
    HEALTH("HEALTH_ADVISER"),
    FINANCE("FINANCE_ADVISER"),
    ACCOMODATION("ACCOMODATION_ADVISER");


    private String adviser;

    Advisers(String advisers) {
        adviser = advisers;
    }

    @Override public String toString() {
        return adviser;
    }
}
