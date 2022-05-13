// Krystian Jachna - 7

/*
Po wczytaniu odpowiednich danych, podana tablice sortuje metoda bombelkowa o zlozonosci O(n^2). Nastepnie przechodze do pierwszego elementu dodatniego w prostej
petli o zlozonosci O(n). Poczawszy od tego elementu za pomoca 2 petli for biore po 2 elementy. Zlozonosc tych dwoch petli to O(n^2). Teraz zaczynajac od drugiego elementu,
az po koniec tablicy indeksu trzeciego elementu najwiekszego ktory bedzie mniejszy od sumy pozostalych dwoch mniejszych, tak aby warunek trojkata byl spelniony. Wyszukiwanie
trzeciego elementu robie z pomoca wyszukiwania binarnego zmodyfikowanego do znalezienia indeksu elementu najwiekszego, mniejszego od podanego. Jej zlozonosc to logn. Nastepnie
gdy taki indeks zostanie znaleziony, zwiekszam liczbe znalezionych trojkatow o odpowiednia wartosc wyliczona przez proste odejmowanie. I wypisuje odpowiednie trojki. Zlozonosc
takiego algorytumu to O(n^2) dla sortowania + O(n) dla przejscia po niedodatnich wartosciach + [ O(n^2) wybieranie dwoch elementow * ( O(logn) wyszukiwanie binarne + O(1) wypisywanie
10 elementow ] W lacznie daje to wymagana zlozonosc O(n^2 * logn)
*/

import java.util.Scanner;

public class Source {                                                       // glowna klasa
    public static Scanner sc = new Scanner(System.in);                      // tworzenie jednego obiektu sc

    public static void main(String[] args) {

        int sets = 0;                                                       // liczba zestawow danych
        int size = 0;                                                       // ilosc odcinkow
        int stretchTab[];                                                   // referencja do tablicy odcinkow
        int i = 1;                                                          // numer zestawu i iteracja petli czytajacej zestawy
        sets = sc.nextInt();                                                // wczytanie liczby zestawow;

        while (i <= sets) {                                                 // petla czytajaca kolejne zestawy
            size = sc.nextInt();                                            // wczytanie liczby odcinkow
            stretchTab = new int[size];                                     // utworzenie tablicy o ilosci elementow rownej ilosci odcinkow

            readTab(stretchTab, size, sc);                                  // odczytanie dlugosci kolejnych odcinkow z wejscia i wpisanie do tablicy
            sortTab(stretchTab, size);                                      // posortowanie tablicy dlugosci odcinkow

            displayInfo(stretchTab, size, i);                               // wyswietlanie posortowanej tablicy, numeru zestawu, liczby elementow tablicy
            findTriangles(stretchTab, size);                                // szukanie i zliczanie mozliwych trojkatow

            i++;
        }
        sc.close();
    }

    public static void readTab(int tab[], int size, Scanner sc) {          // funkcja sluzaca odczytaniu kolejnych elementow tablicy podanych na wejscie
        for (int i = 0; i < size; i++) {                                   // iteracja po kolejnych elementach tablicy
            tab[i] = sc.nextInt();                                         // wczytanie do tablicy kolejnych wartosci z wejscia
        }
    }


    public static void sortTab(int tab[], int size) {                       // sortowanie metoda bombelkowa
        int tmp = 0;                                                        // zmienna tymczasowa potrzebna do ewentualniej zamiany poszczegolnych wartosci tablicy
        boolean flag = false;                                               // do sprawdzanie czy potrzebna byla zamiana zeby zoptymalizowac algorytm sortujacy
        for (int i = 0; i < size; i++) {                                    // przejscie po tablicy tyle razy ile jest elementow
            for (int j = 0; j < size - 1; j++)                                // wybor dwoch kolejnych elementow
                if (tab[j] > tab[j + 1]) {                                    // porownywanie dwoch kolejnych elementow i zamiana jesli sa w zlej kolejnosci
                    tmp = tab[j];                                             // przypisanie do zmiennej tymczasowej elementu tablicy w celu tymczasowego zapamietania go
                    tab[j] = tab[j + 1];                                      // podmiana elementu na kolejny
                    tab[j + 1] = tmp;                                         // podmiana elementu na poprzedni pamietany przez zmienna tymczasowa
                    flag = true;
                }
            if (flag != true)                                                 // sprawdzanie czy nastapila zamiana elementow w innym przypadku koniec
                break;
            flag = false;                                                     // wyzerowanie flagi
        }
    }

    public static void displayInfo(int tab[], int size, int i) {             // wyswietlanie posortowanej tablicy, numeru zestawu, liczby elementow tablicy
        System.out.printf(i + ": n= " + size + "\n");                        // wyswietlenie numery zestwu i liczby elementow tablicy
        int j = 0;
        for (j = 0; j < size; j++) {                                         // petla iterujaca po kolejnych elementach tablicy
            System.out.printf(tab[j] + " ");                                 // wyswietlanie danego elementu tablicy
            if ((j + 1) % 25 == 0)                                           // przejscie do nowej lini po 25 elemencie
                System.out.println("");
        }
        if ((j) % 25 != 0)
            System.out.println("");
    }

    public static void findTriangles(int tab[], int size) {                 // szukanie trojkatow
        int i = 0;                                                          // iterator

        int trianglesNumber = 0;                                            // liczba trojkatow
        int lowersSum = 0;                                                  // suma dwoch mniejszych odcinkow
        int rp = 0;                                                         // wskaznik na prawy indeks
        int written = 0;                                                    // licznik wypisanych elementow

        for (; i < size - 2; i++) {                                         // iteracja po kolejnych elementach tablicy do wyboru pierwszego odcinka
            for (int j = i + 1; j < size - 1; j++) {                        // wybieranie drugiego odcinka
                lowersSum = tab[i] + tab[j];                                // suma dwoch krotszych odcinkow
                rp = findLim(tab, size, j + 1, lowersSum);               // szukanie indeksu ostatniego odcinka mniejszego niz suma dwoch mniejszych odcinkow za pomoca wyszukowania binarnego

                if (rp != -1) {                                             // gdy znaleziony zostal indeks ostatniego trzeciego elementu
                    trianglesNumber += (rp - j);                            // zwiekszenie ilosci trojkatow
                    int k = j + 1;                                          // zmienna do iterowaniu po kolejnych elementach tablicy tworzacych trojkat
                    while (written < 10 && k < rp + 1) {                    // wypisanie do 10 trojek
                        System.out.printf("(" + i + "," + j + "," + k + ") ");
                        k++;
                        written++;
                    }

                }
            }
        }

        if (trianglesNumber == 0) {                                          // wypisanie odpowiedniej informacji w przypadku gdy nieznaleziono trojkata
            System.out.println("Triangles cannot be built");
            return;
        }
        System.out.println("");
        System.out.printf("Number of triangles: " + trianglesNumber + "\n" ); // wypisanie odpowiedniej informacji w przypadku gdy znaleziono jakies trojkaty
    }

    public static int findLim (int tab[], int size, int lp, int max) {        // wyszukiwanie binarne dla szukania najwiekszego indeksu odcinka, mniejszego niz zadana wartosc
        int rp = size - 1;                                                    // ograniczenie gorne
        int m = 0;                                                            // aktualnie sprawdzany indeks

        while (lp <= rp) {                                                    // warunek konca petli jezli podtablica bedzie pusta
            m = (lp + rp) / 2;                                                // (low + upp) >> 1

            if (tab[m] < max)
                lp = m + 1;                                                   // jest w prawej polowie
            else
                rp = m - 1;                                                   // jest w lewej polowie lub jest rowny
        }
        return rp;                                                            // zwraca ograniczenie gorne
    }
}


/*
============= test0.in =============
10
1 2 3 4 5 6 7 8 9 10
10
10 9 8 7 6 5 4 3 2 1
4
1 1 9 10
4
4 6 3 7
7
10 21 22 100 101 200 300
3
3 5 4
4
7 3 6 4
======================================
 */

/* ============= test0.out =============
1 2 3 4 5
(1,2,3) (1,3,4) (2,3,4)
liczba trojkatow: 3
5 4 3 2 1
(1,2,3) (1,3,4) (2,3,4)
liczba trojkatow: 3
1 1 9 10
nie da sie zbudowac
3 4 6 7
(0,1,2) (0,2,3) (1,2,3)
liczba trojkatow: 3
10 21 22 100 101 200 300
(0,1,2) (0,3,4) (1,3,4) (2,3,4) (3,4,5) (4,5,6)
liczba trojkatow: 6
3 4 5
(0,1,2)
liczba trojkatow: 1
3 4 6 7
(0,1,2) (0,2,3) (1,2,3)
liczba trojkatow: 3
======================================
 */

