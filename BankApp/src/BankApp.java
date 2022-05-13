// Plik: BankApp.java
// Przykład demonstruje podstawy składni obiektowej
// Uruchomienie programu: java BankApp
//=======================================================
import java.io.*;

import static java.lang.Math.pow;

class BankAccount {
    private double balance; // saldo konta
    public BankAccount(double openingBalance) { // konstruktor
        balance = openingBalance; // inicjowanie konta
    }
    public void deposit(double amount) { // dokonuje wpłaty
        balance = balance + amount;
    }
    public void withdraw(double amount) { // dokonuje wypłaty
        if(balance - amount >= 0) {  // sprawdza czy możliwe jest wypłacenie środków
            balance = balance - amount;
        }
        else {
            System.out.println("Brak wystarczających środków");
        }

    }
    public void display() { // wypisuje saldo
        System.out.println("saldo=" + balance);
    }
    public void finalCapital(double amount, double percent, int time) { // oblicza kapitał końcowy po różnych okresach kapitalizacji za pomocą wzoru
        double yearPeriod = amount * pow((1 + percent/100), ((double)time)/12);
        double halfyearPeriod = amount * pow((1 + (percent/100)/2), 2*((double)time)/12);
        double quarterPeriod = amount * pow((1 + (percent/100)/4), 4*((double)time)/12);

        System.out.printf("Kapitał końcowy przy odsetkach dopisywanych po 12 miesiącach: %.2f\n" , yearPeriod);
        System.out.printf("Kapitał końcowy przy odsetkach dopisywanych po 6 miesiącach: %.2f\n" ,  halfyearPeriod);
        System.out.printf("Kapitał końcowy przy odsetkach dopisywanych po 3 miesiącach: %.2f\n" ,  quarterPeriod);
    }

    public void percent(double startAmount, int time, double endAmount){    // oblicza oprocentowania w różnych okresach kapitalizacji za pomocą wzoru
        double yearPeriod = 100 * (pow(endAmount / startAmount, 1 / ((double) time/ 12)) - 1);
        double halfyearPeriod = 100 * (pow(endAmount / startAmount, 1 / (2 * ((double) time) / 12)) - 1) * 2;
        double quarterPeriod = 100 * (pow(endAmount / startAmount, 1 / (4 * ((double) time) / 12)) - 1) * 4;

        System.out.printf("Oprecentowanie przy 12 miesięcznym okresie kapitalizacji: %.2f%%\n",  yearPeriod);
        System.out.printf("Oprecentowanie przy 6 miesięcznym okresie kapitalizacji: %.2f%%\n", halfyearPeriod);
        System.out.printf("Oprecentowanie przy 3 miesięcznym okresie kapitalizacji: %.2f%%\n", quarterPeriod);
    }

    public void credit(int years, double amount, double percent){ // oblicza łączną wartość odsetek za pomocą wzoru
        double installment = (amount*(percent/100))/(4*(1-pow(4/(4+percent/100), years*4))); // oblicza pojedyńczą ratę
        double cost = installment*years*4;                                                   // oblicza całkowity kosz kredytu
        double sumCreitCost = cost - amount;                                                 // oblicza odsetki

        System.out.printf("Łączna wartość odsetek kredytu: %.2f" , sumCreitCost);
        System.out.println("");
    }

    public void installments(int years, double amount, double percent){ // oblicza kwotę pierwszej i ostatniej raty w przypadku rat malejących i ratę w przypadku rat równych za pomocą wzoru
        double installment = (amount*(percent/100))/(4*(1-pow(4/(4+percent/100), years*4)));
        double firstInstallment = amount/(years * 4) + ((percent/100)/4 * amount);
        double secondInstallment = amount/(years * 4) + ((percent/100)/4 *amount/(years * 4));

        System.out.printf("W kwartalnych ratach malejących, wartość pierwszej raty będzie równa: %.2f\n" , firstInstallment);
        System.out.printf("W kwartalnych ratach malejących, wartość ostatniej raty będzie równa: %.2f\n" , secondInstallment);
        System.out.printf("W kwartalnych ratach równych, wartość każdej raty będzie równa: %.2f\n" , installment);


    }
} // koniec klasy BankAccount
//==========================================================
class BankApp {
    public static void main(String[] args) throws IOException {
        BankAccount ba1 = new BankAccount(100.00);
        // tworzymy obiekt o nazwie ba1 (konto)
        int op; double n; double t;
        System.out.printf("\n", "Przed transakcjami, ");
        ba1.display(); // wypisujemy saldo
        do {
            menu();
            System.out.print("Wybierz cos: ");
            op = getInt();
            switch (op) {
                case 1: // wplata
                {
                    System.out.print("Podaj kwote: ");
                    n = getDouble();
                    ba1.deposit(n);
                    System.out.print("Po transakcji: ");
                    ba1.display();
                    break;
                }
                case 2: // wyplata
                {
                    System.out.print("Podaj kwote: ");
                    n = getDouble();
                    ba1.withdraw(n);
                    System.out.print("Po transakcji: ");
                    ba1.display();
                    break;
                }
                case 3: // kapitał końcowy
                {
                    System.out.print("Podaj kapitał początkowy: ");
                    n = getDouble();
                    System.out.print("Podaj oprocentowanie banku: ");
                    t = getDouble();
                    System.out.print("Podaj okres (w miesiącach) po jakim interesuje Cię kapitał końcowy: ");
                    op = getInt();
                    ba1.finalCapital(n, t, op);
                    break;
                }
                case 4: // oprocentowanie banku
                {
                    System.out.print("Podaj kapitał początkowy: ");
                    n = getDouble();
                    System.out.print("Podaj kapitał końcowy: ");
                    t = getDouble();
                    System.out.print("Podaj okres (w miesiącach) w jakim kapitał urósł: ");
                    op = getInt();
                    ba1.percent(n,op,t);
                    break;
                }
                case 5: // łączna wartość odsetek
                {
                    System.out.print("Podaj kwotę kredytu: ");
                    n = getDouble();
                    System.out.print("Podaj wysokość rocznej stopy procentowej: ");
                    t = getDouble();
                    System.out.print("Podaj okres (w latach) na spłatę kredytu: ");
                    op = getInt();
                    ba1.credit(op, n, t);
                    break;
                }
                case 6: // wyosokość pierwszej i ostatniej raty w przypadku rat malejących i wysokość raty w przypadku rat stałych
                {
                    System.out.print("Podaj kwotę kredytu: ");
                    n = getDouble();
                    System.out.print("Podaj wysokość rocznej stopy procentowej: ");
                    t = getDouble();
                    System.out.print("Podaj okres (w latach) na spłatę kredytu: ");
                    op = getInt();
                    ba1.installments(op, n, t);
                    break;
                }
                case 0: // koniec
                {
                    System.out.println("Koniec transakcji ");
                    System.out.print("Aktualny stan: ");
                    ba1.display();
                    break;
                }
                default: System.out.println("Zla opcja !!!");
            } // koniec switch
        }while (op != 0) ;
    } // koniec main()
    //--------------------------------------------------------
    public static void  menu(){
        System.out.println("\n_____________________________");
        System.out.println(" Operacje ");
        System.out.println("_______________________________");
        System.out.println(" 1. Wplata ");
        System.out.println(" 2. Wyplata ");
        System.out.println(" 3. Kapitał końcowy ");
        System.out.println(" 4. Oprocentowanie w banku");
        System.out.println(" 5. Odestki od kredytu");
        System.out.println(" 6. Wysokość rat kredytu");
        System.out.println(" 0. Koniec programu ");
        System.out.println("===============================");
    }
    //--------------------------------------------------------
    public static String getString() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String line = br.readLine();
        return line;
    }
    //--------------------------------------------------------
    public static int getInt() throws IOException {
        String s = getString();
        int lnum = Integer.parseInt(s);
        return lnum;
    }
    //--------------------------------------------------------
    public static double getDouble() throws IOException {
        String s = getString();
        double adoub = Double.valueOf(s);
        return adoub;
    }
    //--------------------------------------------------------
    public static float getFloat() throws IOException {
        String s = getString();
        float aflo = Float.valueOf(s);
        return aflo;
    }
    //--------------------------------------------------------
} // koniec klasy BankApp

/*
======================= test.in =========================
Kapitał końcowy:
K0 = 1000
p% = 5%
okres = 12 miesięcy

Oprocentowanie:
K0 = 1000
K = 1200
okres = 12 miesięcy

Odsetki od kredytu:
K = 1000
p% = 3%
okres = 4 lata

Wysokość rat kredytu:
K = 1000
p% = 3%
okres = 4 lata
==========================================================


======================= test.out =========================
1050,00
1050,63
1050,95

20,00%
19,09%
18,65%

64,94

70,00
62,97
66,56
=========================================================

 */