// Krystian Jachna - 7
import java.util.Scanner;
// todo komentarze

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
    }

    public static int getPivotIndex(int l, int r) {
        if (r - l + 1 <= 5)          // dla 5 lub mniej elementow mediana
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
    }

    /*
    public static int sortAndMedian5(int l, int r) {
        for (int i = l + 1; i <= r; ++i) {
            int tmp = tab[i];
            int j = i - 1;
            while (j >= l && tmp < tab[j]) {
                tab[j+1] = tab[j];
                --j;
            }
            tab[j+1] = tmp;
        }
        return (l + r) / 2;
    }
    */

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
    }


    public static int partition(int l, int r, int k, int pivIndex) {
        int pivValue = tab[pivIndex];
        // pivot na koniec
        int tmp = tab[pivIndex];
        tab[pivIndex] = tab[r];
        tab[r] = tmp;
        int idexEqualElems;

        int i = l - 1;
        int j = r;

        while(true) {
            while(tab[++i] < pivValue);
            while(j > l && tab[--j] >= pivValue);

            if (i >= j)
                break;
            else {
                tmp = tab[i];
                tab[i] = tab[j];
                tab[j] = tmp;
            }
        }
        tmp = tab[i];
        tab[i] = tab[r];
        tab[r] = tmp;
        int indexSmallerElems = i;
        idexEqualElems = i;
        ++i;
        for (j = i; j <= r; ++j) {
            if (tab[j] == pivValue) {
                 tmp = tab[j];
                 tab[j] = tab[i];
                 tab[i] = tmp;
                 ++i;
                 ++idexEqualElems;
            }
        }

        if (k < indexSmallerElems)
            return indexSmallerElems;
        if (k <= idexEqualElems)
            return k;
        return idexEqualElems;
    }
}
/*
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

+++

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

---------------

1
20
7 5 5 9 17 9 15 10 13 11 13 10 18 7 17 13 8 7 9 13
4
1 4 8 12

+++

1 5
4 7
8 9
12 11

---------------

1
100
0 57 48 38 28 39 5 69 4 89 58 23 83 72 78 59 88 19 100 55 43 35 16 29 43 49 3 51 0 25 39 23 18 86
32 41 30 6 70 70 69 90 71 18 80 45 25 93 1 80 64 8 66 20 91 46 46 99 61 45 29 87 33 62 57 1 78 22
26 4 88 52 36 48 67 12 9 69 49 40 58 71 10 30 48 91 70 47 77 66 67 22 75 84 63 15 48 14 16 83
5
3 10 40 74 98

+++

3 1
10 8
40 39
74 69
98 93
 */
