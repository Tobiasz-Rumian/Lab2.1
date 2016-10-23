package jp.laboratorium2;

import java.io.IOException;

/**
 *  Interfejs UserDialog
 *
 *  Prosta biblioteka metod do realizacji
 *  dialogu z użytkownikiem w prostych aplikacjach
 *  bez graficznego interfejsu użytkownika.
 *
 *  Autor: Pawel Rogalinski
 *  Modyfikowany przez: Tobiasz Rumian
 *   Data: 15 pazdziernika 2016 r.
 */
public interface UserDialog{

    /** Komunikat o błędnym formacie wprowadzonych danych. */
    String ERROR_MESSAGE = "Nieprawidłowe dane!\nSpróbuj jeszcze raz.";


    public void printMessage(String message);

    public void printInfoMessage(String message);

    public void printErrorMessage(String message);

    public void clearConsole() throws IOException, InterruptedException;

    public String enterString(String prompt);

    public char enterChar(String prompt);

    public int enterInt(String prompt);

    public float enterFloat(String prompt);

    public double enterDouble(String prompt);

}
