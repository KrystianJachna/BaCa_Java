// Krystian Jachna - 7

import java.util.Scanner;

// kalsa plecak zawierajaca zawartosc plecaka i informacje i flage czy nalezy jeszcze szukac
class Backpack {
    String elements;
    boolean doSearch;
    //int i;
    Backpack () {
        elements = "";
        doSearch = true;
        //i = 0;
    }
}
// parametry wywolania funkcji, obiekty lokalne tj. zmienne stale i parametry
class Params {
    Params prev;                // referencja na poprzedni element na stosie
    // parametry
    public int i;
    public int spaceLeft;
    public String curBag;
    public Backpack backpack;

    public boolean flag;                    // zmienna do przechowywania informacji czy odpowiednia czesc kodu zostala juz wykonana w poprzednim wykonaniu
    public int addr;                        // adres powrotu

    Params (int arg_i, int arg_spaceLeft, String arg_curBag, Backpack arg_backpack, int ra) {   // konstruktor
        i = arg_i;
        spaceLeft = arg_spaceLeft;
        curBag = arg_curBag;
        backpack = arg_backpack;

        flag = true;

        addr = ra;
        prev = null;
    }
}
// stos parametrow do funkcji iteracyjnej
class Stack {
    Params top;

    Stack () {
        top = null;
    }
    public void push(Params toPush) {       // wkladanie kolejnego elementu na stos
        Params tmp = top;
        top = toPush;
        toPush.prev = tmp;
    }
    public Params pop() {                   // usuniecie elementu ze stosu
        Params tmp = top;
        top = top.prev;
        return tmp;
    }
    public Params top() {                   // odczytanie szczytu stosu
        return top;
    }
}


// stos parametrow
class Elem {
    int waga;
    Elem prev;

    Elem (int w) {
        waga = w;
        prev = null;
    }
}
// stos do zapisywania elementow plecaka
class StackBag {
    Elem top;

    StackBag () {                           // konstruktor
        top = null;
    }
    public void push(Elem toPush) {       // wkladanie kolejnego elementu na stos
        Elem tmp = top;
        top = toPush;
        toPush.prev = tmp;
    }
    public Elem pop() {                   // usuniecie elementu ze stosu
        Elem tmp = top;
        top = top.prev;
        return tmp;
    }
    public boolean isEmpty() {              // czy stos jest pusty
        return (top == null);
    }
    public Elem top() {                   // odczytanie szczytu stosu
        return top;
    }       // zwraca szczyt
 }



// przechowuje wyjscie
class Buffor {

    public static final int size = 1024;
    public static StringBuilder content = new StringBuilder(size);

    public static void add (String x) {
        if ( content.length() + x.length() > size )
            flush();
        content.append(x);
    }

    public static void flush () {
        System.out.print( content.toString() );
        content = new StringBuilder(size);
    }

}

public class Source {
    static int [] tab;      // tablica wejsciowa
    static int Size;        // rozmiar plecaka
    static StackBag sw;     // stos plecak

    static Params Par;      // parametry wywylowywanie funkcji iteracyjnej
    static int label;       // jak na wykladzie
    static Stack S;         // stos symulujacy rekurencje
    static String incBag;
    static int incSpace;

    public static Scanner sc = new Scanner(System.in);

    public static int iloscWywolan = 0;

    public static void main ( String [] args ) {

        int Space = 0;                                               // maksymalna waga plecaka
        String tmp;
        Backpack bagIter;
        int setsNumber = sc.nextInt();                              // ilosc zestawow

        while (setsNumber > 0) {

            sw = new StackBag();
            bagIter = new Backpack();

            Space = sc.nextInt();                                   // odczytanie kolejnych wartosci
            Size = sc.nextInt();

            tab = new int[Size];
            readElems();                                                    // odczytanie elementow tablicy

            if (!rec_pakuj(0, Space)) {                                // jesli nie odnaleziono plecaka
                Buffor.add("BRAK" + '\n');
            }
            else {                                                          // wypisanie odpowiednich wartosci
                StackBag Ans = new StackBag();                              // nowy stos do odwrocenia kolejnosci odpowiedzi
                while (sw.isEmpty() == false ) {                            // przepisanie w odwrotnej kolejnosci
                    Ans.push(sw.pop());
                }

                Buffor.add("REC:  " + Space + " =");
                while (Ans.isEmpty() == false) {                            // dodanie na wyjscie
                    Buffor.add(" " + Ans.pop().waga);
                }
                Buffor.add("\n");


                tmp = new String();
                iter_pakuj(0, Space, tmp, bagIter);    // pakowanie iteracyjne
                Buffor.add("ITER: " + Space + " =" + Par.backpack.elements + '\n');

            }
            setsNumber--;
        }
        Buffor.flush();                                                 // wypisanie
        sc.close();
    }


    // czytanie elementow tablicy
    public static void readElems() {
        for (int i = 0; i < Size; ++i)
            tab[i] = sc.nextInt();
    }

// szukanie plecaka
    public static boolean rec_pakuj(int i, int spaceLeft) {
        if (spaceLeft < 0)              // jesli nie ma juz miejsca koniec
            return false;
        else if (tab[i] == spaceLeft) { // jesli znaleziony element zapelnia plecak
            Elem curWynik = new Elem(tab[i]);
            sw.push(curWynik);          // dodanie na stos
            return true;                // koniec
        }


        if (i + 1 < Size) {             // jesli sa jeszcze dostepne elementy
            Elem curWynik = new Elem(tab[i]);   // sprawdzenie gdy plecak zawiera element
            sw.push(curWynik);
            if (rec_pakuj(i + 1 , spaceLeft - tab[i])) // jesli znajdzie
                return true;                                        // koniec
            sw.pop();                                               // usuniecie elementu
            return rec_pakuj(i + 1, spaceLeft);                  // sprawdzenie plecaka nie zawierajacego danego elementu
        }
        else return false;
    }

    // pakowanie iteracyjne (symulacja rekurencji)
    public static void iter_pakuj(int i, int spaceLeft, String curBag, Backpack backpack) {
        label = 1;
        S = new Stack();

        while ( step(i, spaceLeft, backpack) == false );
           // backpack.i++;                                                    // symulacja rekurencji
    }

    public static boolean step(int i, int spaceLeft, Backpack backpack) {

        switch (label) {                                        // symulacja rekurencji
            case 1:
                Par = new Params(i, spaceLeft, "", backpack, 6); // poczatkowe wywolanie funkcji
                S.push(Par);
                label = 2;
                break;
            case 2:                                                                 // "wejscie" do metody
                Par = S.top();
                if (Par.flag) {                                                     // sprawdzanie czy ten kod zostal juz wykonany
                    incBag = Par.curBag + " " + tab[Par.i];                   // plecak zawierajacy sprawdzany element
                    incSpace = Par.spaceLeft - tab[Par.i];                      // reszta miejsca plecaka zawierajacy sprawdzany element

                    if (!backpack.doSearch) {                                       // jesli znaleziono juz plecak
                        return true;                                                  // nie trzeba dalej szukac
                    }
                    if (incSpace == 0) {                                            // znaleziono plecak
                        Par.curBag = Par.curBag + " " + tab[Par.i];                         // zapisanie wartosci
                        backpack.elements = Par.curBag;
                        backpack.doSearch = false;
                        return true;
                    }
                    if (++Par.i < Size) {
                        if (incSpace > 0) {
                            label = 3;                                              // wejscie w rekurencje
                            Par.flag = false;                                       // sygnalizowanie ze ta czesc kodu zostala wykonana
                            break;
                        }
                    }
                }
                if ((!Par.flag || Par.i < Size) && backpack.doSearch )          // jesli caly czas szukamy plecaka i poprzedni kod zostal wykonany lub warunek wyzej nie jest spelniony i nie doszlismy do konca tablicy
                        if (Par.spaceLeft > 0) {
                            label = 4;                                              // wejscie w rekurencje
                            break;
                        }
                label = 5;
                break;
            case 3:                                                                  // wywolanie rekurencji z odpowiednimi parametrami
                Params newPar = new Params(Par.i, incSpace, incBag, backpack, 2);
                S.push(newPar);
                label = 2;                                                           // wejscie do metody
                break;
            case 4:                                                                 // wywolanie rekurencji z odpowiednimi parametrami
                Params new2Par = new Params(Par.i, Par.spaceLeft, Par.curBag, backpack, 5);
                S.push(new2Par);
                label = 2;                                                          // wejscie do metody
                break;
            case 5:
                Par = S.top();                                                      // wyjscie z metody
                label = Par.addr;
                S.pop();
                break;
            case 6:
                 return true;                                                       // zakonczenie dzialania metody
        }
        return false;
    }


}


// test.in
/*
30
11
18
6 2 2 6 3 5 9 11 7 3 7 7 3 3 5 6 5 4
9
6
6 2 3 4 6 9
12
6
3 5 6 5 6 9
26
16
13 18 10 5 11 19 11 16 22 7 24 23 12 25 5 12
13
4
13 8 5 11
11
12
2 8 7 5 11 1 2 4 2 11 7 8
24
8
7 17 9 23 8 15 1 13
14
3
3 5 9
9
8
1 8 9 9 6 9 3 9
21
17
11 6 8 1 13 3 13 5 19 17 6 3 17 2 8 4 7
20
18
16 11 16 10 15 9 10 20 14 3 15 18 17 15 15 14 3 1
8
3
2 4 5
8
3
8 8 1
20
17
5 15 19 19 10 12 2 19 18 10 10 9 11 18 6 10 4
26
10
5 23 16 12 9 17 18 18 4 5
9
18
9 6 5 7 1 1 9 3 9 4 2 7 8 6 4 7 6 8
15
13
8 14 7 14 3 2 3 13 2 7 10 5 4
20
20
19 15 3 7 5 12 6 1 20 8 9 5 14 3 10 8 12 15 19 1
30
10
10 14 7 24 14 26 18 27 4 27
30
13
10 30 25 1 23 2 10 17 19 7 9 6 18
11
18
6 10 9 3 7 7 9 2 5 5 2 1 10 11 7 7 9 9
22
20
11 15 11 3 5 18 7 21 10 17 14 18 20 10 21 10 8 9 19 17
10
20
1 4 10 10 2 4 7 8 6 9 1 5 1 2 9 4 1 8 3 10
13
11
1 10 13 4 5 8 7 6 6 10 10
11
5
6 10 10 10 8
8
15
8 6 6 3 1 4 4 5 4 4 5 8 2 1 3
18
13
1 16 16 17 15 14 10 12 8 5 14 18 17
13
18
2 11 8 1 13 13 8 12 12 3 13 9 6 10 3 7 11 8
22
19
19 16 10 10 8 22 16 4 22 20 5 11 14 2 7 8 7 16 15
24
19
14 18 12 13 8 3 4 15 17 15 24 22 1 19 23 22 21 19 6
 */
// test.out
/*
REC:  11 = 6 2 3
ITER: 11 = 6 2 3
REC:  9 = 6 3
ITER: 9 = 6 3
REC:  12 = 3 9
ITER: 12 = 3 9
REC:  26 = 10 5 11
ITER: 26 = 10 5 11
REC:  13 = 13
ITER: 13 = 13
REC:  11 = 2 8 1
ITER: 11 = 2 8 1
REC:  24 = 7 17
ITER: 24 = 7 17
REC:  14 = 5 9
ITER: 14 = 5 9
REC:  9 = 1 8
ITER: 9 = 1 8
REC:  21 = 11 6 1 3
ITER: 21 = 11 6 1 3
REC:  20 = 16 3 1
ITER: 20 = 16 3 1
BRAK
REC:  8 = 8
ITER: 8 = 8
REC:  20 = 5 15
ITER: 20 = 5 15
REC:  26 = 5 16 5
ITER: 26 = 5 16 5
REC:  9 = 9
ITER: 9 = 9
REC:  15 = 8 7
ITER: 15 = 8 7
REC:  20 = 19 1
ITER: 20 = 19 1
REC:  30 = 26 4
ITER: 30 = 26 4
REC:  30 = 10 1 2 10 7
ITER: 30 = 10 1 2 10 7
REC:  11 = 6 3 2
ITER: 11 = 6 3 2
REC:  22 = 11 11
ITER: 22 = 11 11
REC:  10 = 1 4 2 1 1 1
ITER: 10 = 1 4 2 1 1 1
REC:  13 = 1 4 8
ITER: 13 = 1 4 8
BRAK
REC:  8 = 8
ITER: 8 = 8
REC:  18 = 1 17
ITER: 18 = 1 17
REC:  13 = 2 11
ITER: 13 = 2 11
REC:  22 = 16 4 2
ITER: 22 = 16 4 2
REC:  24 = 14 3 1 6
ITER: 24 = 14 3 1 6
 */