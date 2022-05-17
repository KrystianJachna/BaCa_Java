// Krystian Jachna - 7
import java.util.Scanner;


public class Source {
    public static Scanner sc = new Scanner(System.in);
    public static int [] tab;                           // referencja do tablicy

    public static void main(String[] args) {
        int setsNumber = sc.nextInt();                  // ilosc zestawow

        while (setsNumber > 0) {
            readTab();                                  // wczytanie tablicy

            int questionNumber = sc.nextInt();          // liczba zapytan

            while (questionNumber > 0) {                // obliczanie k-tej wartosci w tablicy dla dowolnego zapytania
                int k = sc.nextInt();                   // wczytanie wartosci k
                if (k > 0 && k <= tab.length)           // jesli k miesci sie w tablicy...
                    System.out.println(k + " " + (tab[select(0, tab.length - 1, k - 1)]));      // wypisz k i wartosc tablicy dla oblicznego indeksu
                else
                    System.out.println(k + " brak");                                                    // w innym przypadku wypisz odpowiednia informacje
                --questionNumber;
            }
            --setsNumber;
        }
        sc.close();
        /*
            Main wywolujacy odpowiednie metody i wypisujacy
            odpowiednie informacje na ekran
         */
    }

    public static void readTab() {
        int tabSize = sc.nextInt();                 // wczytaj dlugosc tablicy
        tab = new int [tabSize];

        for (int i = 0; i < tab.length; ++i)
            tab[i] = sc.nextInt();                  // wczytaj odpowiednie wartosci tablicy

        /*
            Funkcja odpowiedzialna za czytanie z wejscia rozmiaru tablicy
            nastepnie tworzy nowa tablice ktora uzupelnia wartosciami z
            wejscia.
         */

    }

    public static int select(int l, int r, int k) {
        while (true) {
            if (l == r)
                return l;
            int pivIndex = getPivotIndex(l, r);
            pivIndex = partition(l, r, k, pivIndex);
            if (k == pivIndex)
                return k;
            else if (k < pivIndex)
                r = pivIndex - 1;
            else
                l = pivIndex + 1;
        }
        /*
            Select wywoluje funkcje szukajaca pivota, ktory dzieli array na piatki,
            W piatkach obliczane sa mediany, ktore nastepnie ustawiane sa na pocz-
            atku array.
         */
    }

    public static int getPivotIndex(int l, int r) {
        if (r - l + 1 <= 5)                             // dla 5 lub mniej elementow mediana
            return sortAndMedian5(l, r);

        for (int i = l; i <= r; i+=5) {
            int tmpR = i + 4;

            if (tmpR > r)
                tmpR = r;
            int median5 = sortAndMedian5(i, tmpR);

            int tmp = tab[median5];
            tab[median5] = tab[l + ((i - l)/5)];
            tab[l + ((i - l)/5)] = tmp;
        }
        int mid = (r - l) / 10 + l + 1;
        return select(l, l + ((r - l)/5), mid);
        /*
            Funkcja tworzy podtablice po 5 elementow, a nastepnie liczy mediane
            kazdej z tych podtablic.
         */
    }

    public static int sortAndMedian5(int l, int r) {
        boolean swapped = false;

        for (int i = l; i < r; ++i) {
            for (int j = l + 1; j <= r; ++j) {
                if (tab[i] > tab[j]) {
                    int tmp = tab[i];
                    tab[i] = tab[j];
                    tab[j] = tmp;
                    swapped = true;
                }
            }
            if (!swapped)
                break;
            swapped = false;
        }
        return (l + r) / 2;
        /*
            Bubble sort dla pieciu lub mniej elementow. Z dodatkowa
            flaga, aby sprawdzac czy zostala wykonana jakas zamiana
            Funkcja zwraca indeks mediany podtablicy.
         */
    }


    public static int partition(int l, int r, int k, int pivIndex) {
        int pivValue = tab[pivIndex];       // wartosc pivota

        int tmp = tab[pivIndex];            // pivot na koniec
        tab[pivIndex] = tab[r];
        tab[r] = tmp;
        int idexEqualElems;

        int i = l - 1;                      // do algorytmu Hora
        int j = r;

        while(true) {
            while(tab[++i] < pivValue);                  // szuka pierwszego z lewej wiekszego od pivota elementu
            while(j > l && tab[--j] >= pivValue);        // szuka pierwszego z prawej mniejszego elementu od pivota

            if (i >= j)
                break;
            else {
                tmp = tab[i];
                tab[i] = tab[j];
                tab[j] = tmp;
            }
        }
        tmp = tab[i];                                   // wstawia pivota na odpowiednie miejsce
        tab[i] = tab[r];
        tab[r] = tmp;

        int indexSmallerElems = i;                      // indeks pivota
        idexEqualElems = i;                             // indeks elementow konca elementow rownych pivotowi
        ++i;
        for (j = i; j <= r; ++j) {                      // przenosi za pivota elementy rowne mu
            if (tab[j] == pivValue) {
                 tmp = tab[j];
                 tab[j] = tab[i];
                 tab[i] = tmp;
                 ++i;
                 ++idexEqualElems;
            }
        }

        if (k < indexSmallerElems)                      // w zaleznosci w ktorej czesci jest nasze k zwraca odpowiedni element
            return indexSmallerElems;
        if (k <= idexEqualElems)
            return k;
        return idexEqualElems;

        /*
            Funkcja wyznacza pivot podtablicy i podobnie jak w QuickSort
            ustawia elementy mniejsze od pivota na lewo od niego, wieksz
            na prawo z ta roznica ze rowne mu zostaja na miejsciu za pi-
            votem.
         */
    }
}
/*
TEST.IN
1
100
-5 7 19 18 10 20 5 17 19 14 -1 15 -9 -12 15 -14 4 -11 -18 19 -8 15 6 11 -17 4 -13 -11 -1 19 15 3 13 -13 -20 4 -1 4 13 -9 -9 16 4 1 7 -3 9 12 -14 18 7 -17 11 1 -15 -12 15 13 12 -12 13 -20 19 1 -2 9 -20 1 -16 20 4 9 -18 -6 -12 -6 16 -20 8 -8 -15 -13 15 -2 18 -2 0 -11 8 11 -4 -9 2 -11 9 7 8 -5 17 20
5
13 30 78 88 100

3
5
1 2 3 4 5
3
1 2 3
5
5 3 4 4 3
5
2 5 1 3 4
10
1 1 1 1 1 1 1 1 1 1
5
1 10 0 20 11


1
20
7 5 5 9 17 9 15 10 13 11 13 10 18 7 17 13 8 7 9 13
4
1 4 8 12
 */
/*
TEST.OUT
13 -14
30 -8
78 13
88 17

100 20


1 1
2 2
3 3
2 3
5 5
1 3
3 4
4 4
1 1
10 1
0 brak
20 brak
11 brak




1 5
4 7
8 9
12 11
 */