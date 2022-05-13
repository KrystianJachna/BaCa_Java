// Krystian Jachna - 7
import java.util.Scanner;

class Matrix{                                       // klasa macierz
    // ==================== pola klasy =========================
    public int r;                                   // liczba wierszy
    public int c;                                   // liczba kolumn
    public int[][] M;                               // referencja do macierzy

    public int maxRectSum = -1;                     // najwieksza suma prostokatku poczatkowo zainicjalizowana wartoscia -1 w celu odroznienia czy znaleziona zostala maksymalna podtablica
    public int leftPointer = 100;                   // wskaznik na lewa krawedz prostokatu
    public int rightPointer = 100;                  // wskaznik na prawa krawedz prostokatu
    public int topPointer = 100;                    // wskaznik na gorna krawedz prostokatu
    public int botPointer = 100;                    // wskaznik na dolna krawedz prostokatu
    public int rectSize = 10000;                    // wskaznik na rozmiar najmniejszej macierzy poczatkowo ustawiona na najwieksza mozliwa w celu znalezienia najmniejszej
    // ==================== metody =========================

    public Matrix(int rows, int cols){              // konstruktor
        r = rows;                                   // przypisanie wartosci poczatkowych - wierszy
        c = cols;                                      // przypisanie wartosci poczatkowych - kolumn
        M = new int[rows][cols];                    // utworzenie macierzy o podanych wartosciach: wierszy i kolumn
    }

    public void readMatrix() {                      // czytanie poszczegolnych komorek macierzy
        for (int i = 0; i < r; i++) {               // iteracja po wierszach
            for (int j = 0; j < c; j++) {           // iteracja po kolumnach
                M[i][j] = Source.sc.nextInt();      // czytanie z wejscia wartosci do komorki Macierzy[wiersz][kolumna]
            }
        }
    }

    public void findMaxRectangle(int currentSet) {  // szukanie najmniejszej podmacierzy o najwiekszej sumie
        int [] rowSums;                             // referencja do tablicy do przechowywania sum wierszy
        rowSums = new int [r];                      // definicja tablicy o dlugosci rownej ilosci wierszy, ktora bedzie przechowywala sumy poszczegolnych wierszy
        int curMaxRectSum = 0;                      // suma obecnej podmacierzy
        int curLeftPointer = 0;                     // obecny wskaznik na lewa krawedz
        int curRightPointer = 0;                    // obecny wskaznik na prawa krawedz
        int curTopPointer = 0;                      // obecny wskaznik n gorna krawedz
        int curBotPointer = 0;                      // obecny wskaznik na dolna krawedz
        int curSize = 10000;                        // obecna wielkosc sprawdzanej podmacierzy
        boolean flag = true;                        // flaga ktora sygnalizuje znalezienia ewentualnego zera w przypadku gdy suma najwiekszej podtablicy jest zerem
        boolean flag2 = false;                      // flaga ktora sygnalizuje spelnienie wszystkich warunkow aby podmienic obecnie sprawdzana podmacierz z wczesniejsza maksymalna podmacierza

        for (int i = 0; i < c; i++) {                                       // iteracja po wszystkich kolumnach - wskaznik na lewa krawdedz
            for (int j = i; j < c; j++) {                                   // iteracja po kolumnach od lewej krawedzi = wskaznik na prawa krawedz
                for (int k = 0; k < r; k++) {                               // iteracja po wierszach - dolny wskaznik
                    rowSums[k] += M[k][j];                                  // wypelnianie tablicy sum wierszy poszczegolnymi komorkami z kazdego wiersza  i kazdej kolumny z kazdym obrotem petli
                    curMaxRectSum = curMaxRectSum + rowSums[k];             // uzupelnianie sumny podmacierzy o dana komorke

                        if (flag == true && M[k][j] == 0 && 0 >= maxRectSum && ((k < topPointer) ||(k == topPointer && k < botPointer) || (k == topPointer && k == botPointer && i < leftPointer) || (k == topPointer && k == botPointer && i == leftPointer && j < rightPointer))) {// specjalna obsluga wartosci 0 - w przypadku gdyby miala to byc maksymalna podmacierz, sprawdzenie kolejnosci leksykograficznej i sprawdzanie w kazdej kolumnie
                        flag = false;                                       // ustawienie flagi na falsz, aby dalej warunek nie byl juz sprawdzany
                        maxRectSum = 0;                                     // podmienienie maksymalnej sumy, suma obecna - w tym przypadku 0
                        topPointer = k;                                     // podmienienie gornego wskaznika na obecny
                        leftPointer = j;                                    // podmienienie lewego wskaznika na obecny
                        rightPointer = j;                                   // podmienienie prawego wskaznika na obecny
                        botPointer = k;                                     // podmienienie dolnego wskaznika na obecny
                        rectSize = 1;                                       // podmienienie rozmiaru podmacierzy na becny
                    }
                    if (curMaxRectSum <= 0) {                               // w przypadku gdy sumna podmacierzy jest mniejsza lub rowna 0, sprawdzanie kolejnej podmacierzy poeniwaz ta juz nie bedzie najmniejsza z najwieksza suma (zmodyfikowany algorytm z wykladu)
                        curMaxRectSum = 0;                                  // ustawienie obecnej sumy na 0 bo sprawdzana bedzie kolejna podmacierz
                        curTopPointer = k+1;                                // przestawienie wskaznika na gore obecnej krawedzi nizej
                    }
                    else {                                                  // sprawdzanie czy mozna podmienic aktualnie najwieksza podmacierz
                        curSize = (k - curTopPointer + 1) * (j - i + 1);    // usalenie wielkosci aktualnie sprawdzanej podmacierzy
                        if (curMaxRectSum > maxRectSum) {                   // sprawdzanie czy suma jest wieksza niz wczesniejsza
                            flag2 = true;                                   // ustawianie flagi2 na true w przypadku gdy warunek jest spelniny
                        }
                        else if (curMaxRectSum == maxRectSum) {             // sprawdzenie w  przypadku gdy sumy sa rowne
                            if (curSize < rectSize) {                       // porownywanie wielkosci obecnej podmacierzy z poprzednia
                                flag2 = true;                               // ustawianie flagi2 na true w przypadku gdy warunek jest spelniony
                            }
                            else if (curSize == rectSize) {                 // w przypadku gdy rozmiary sa rowne sprawdzana jest kolejnosc leksykograficzna poszczegolnych wskaznikow
                                if (curTopPointer < topPointer) {           // sprawdzenie po kolei - najpierw gorny wskaznik
                                    flag2 = true;                           // ustawienie flagi2 na true w przypadku gdy warunek jest spelniony
                                }
                                else if (curTopPointer == topPointer) {     // gdyby gorne wskazniki byly takie same, sprawdzanie kolejno dolnego wskaznika
                                    if (k < botPointer) {                   // sprawdzanie dolnego wskaznika
                                        flag2 = true;                       // ustawienie flagi2 na true w przypadku gdy warunek jest spelniony
                                    }
                                    else if (k == botPointer) {             // gdyby i gorne i dolne wskazniki byly takie same sprawdzanie kolejnego - lewego wskaznika
                                        if (i <leftPointer) {               // sprawdzanie lewego wskaznika
                                            flag2 = true;                   // ustawienie flagi2 na true w przypadku gdy warunek jest speliony
                                        }
                                        else if (i == leftPointer) {        // gdyby i gorne i dolne i lewe wskazniki byly takie same sprawdzanie ostatniego - prawego wskaznika
                                            if (j < rightPointer) {         // sprawezdenie prawego wskaznika
                                                flag2 = true;               // ustawienie flagi 2 na true w przypadku gdy warunek jest spelionyny
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (flag2 == true) {                                // jesli warunki podmiany macierzy zostaly spelonine macierz zostaje podmieniona
                            maxRectSum = curMaxRectSum;                     // wpisanie maksymalnej sumy
                            topPointer = curTopPointer;                     // ustawienie gornego wskaznika
                            leftPointer = i;                                // ustawienie dolnego wskaznika
                            rightPointer = j;                               // ustawienie prawego wskaznika
                            botPointer = k;                                 // ustawienie dolnego wskaznika
                            rectSize = curSize;                             // ustawienie rozmiaru podmacierzy
                        }
                        flag2 = false;                                      // ustawienie flagi2 na 0, aby warunki w kolejnym obrocie petli mogly znowu byc sprawdzane
                    }
                }
                curMaxRectSum = 0;                                          // wyzerowanie obecnych wartosci sumy do kolejnych obrotow petli
                curTopPointer = 0;                                          // wyzerwoanie wskaznika na gorny element
            }
            for (int z = 0; z < r; z++) {                                   // petla iterujaca po tablicy sumy wierszy
                rowSums[z] = 0;                                             // wyzerowanie tablicy
            }
            flag = true;
        }

        if (maxRectSum != -1) {                                             // specjalne wypisywanie gdy nie znaleziono podmacierzy o maksymalnej sumy
            System.out.printf(
                    "%d: n = %d m = %d, s = %d, mst = a[%d..%d][%d..%d]\n",
                    currentSet, r, c, maxRectSum, topPointer, botPointer, leftPointer, rightPointer
            );
        }
        else {
            System.out.printf(                                              // wypisywanie w odpowiedni sposob znalezionej podmacierzy
                    "%d: n = %d m = %d, s = 0, mst is empty\n",
                    currentSet, r, c
            );
        }

        flag = true;
    }
}

public class Source {                                                       // glowna klasa
    public static Scanner sc = new Scanner(System.in);                      // tworzenie jednego obiektu sc
    public static void main( String [] args ){

        int sets = 0;                                                       // liczba zestawow
        int currentSet = 0;                                                 // obecny zestaw
        int r = 0;                                                          // liczba wierszy
        int c = 0;                                                          // liczba kolumn
        String colon;                                                       // do czytania znakow specjalnych
        sets = sc.nextInt();                                                // czytanie liczby zestawow jakie przyjmie nasz program
        Matrix matrix;                                                      // tworzenie obiektu klast Matrix

        while (sets > 0) {                                                  // petla czytajaca kolejne zestawy
            currentSet = sc.nextInt();                                      // odczytywanie z klawiatury numeru zestawu
            colon = sc.next();                                              // przeczytanie drukropka
            r = sc.nextInt();                                               // przeczytanie liczby wierszy
            c = sc.nextInt();                                               // przeczytanie liczby kolumn
            matrix = new Matrix(r, c);                                      // utworzenie nowej macierzy o odpowiedniej liczby wierszy i kolumn
            matrix.readMatrix();                                            // przeczytanie kolejnych wartosci komorek macierzy
            matrix.findMaxRectangle(currentSet);                            // wywolanie metody szukajacej i wypisujacej maksymalna podmacierz

            sets--;                                                         // zmniejszenie liczy pozostalych zestawow
        }
    }
}
/*
============= test0.in =============
10
1 : 1 6
-2 7 -4 8 -5 4
2 : 2 5
 1 1 -1 -1 0
 1 1 -1 -1 4
3 : 2 5
 0 -1 -1 1 1
 4 -2 -2 1 1
4 : 2 5
 0 -1 -1 4 0
 4 -2 -2 0 0
5 : 2 5
 -1 -2 -3 -1 -2
 -1 -1 -1 -1 -5
6 : 2 5
 0 0 0 0 0
 0 0 0 0 0
7 : 1 6
-1 -2 -3 0 -5 0
8 : 4 5
6 -5 -7 4 -4
-9 3 -6 5 2
-10 4 7 -6 3
-8 9 -3 3 -7
9 : 2 2             (przechodzi bace z bledem)
-1 0
0 -1
10 : 6 6
1 2 3 4 5 6
7 8 9 10 11
12 13 14 15 16 17
18 19 20 21 22 23
24 25 26 27 28 29
30 31 32 33 34 35
======================================
 */
/* ============= test0.out =============
n = 1 m = 6, s = 11, mst = a[0..0][1..3]
n = 2 m = 5, s = 4, mst = a[1..1][4..4]
n = 2 m = 5, s = 4, mst = a[1..1][0..0]
n = 2 m = 5, s = 4, mst = a[0..0][3..3]
n = 2 m = 5, s = 0, mst is empty
n = 2 m = 5, s = 0, mst = a[0..0][0..0]
n = 1 m = 6, s = 0, mst = a[0..0][3..3]
n = 4 m = 5, s = 17, mst = a[2..3][1..2]
n = 2 m = 2, s = 0, mst = a[0..0][1..1]
n = 6 m = 6, s = 633, mst = a[0..5][0..5]
======================================
 */