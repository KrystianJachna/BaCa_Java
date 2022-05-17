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

            while (questionNumber > 0) {
                int k = sc.nextInt();
                if (k <= tab.length)
                    System.out.println(k + " " + select(0, tab.length - 1, k - 1));
                else
                    System.out.println(k + " brak");
                --questionNumber;
            }


            --setsNumber;
        }
    }

    public static void readTab() {
        int tabSize = sc.nextInt();
        tab = new int [tabSize];

        for (int i = 0; i < tab.length; ++i)
            tab[i] = sc.nextInt();

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
            int pivIndex = getPivotIndex(l, r, k);
            pivIndex = partition(l, r, k, pivIndex);
            if (k == pivIndex)
                return k;
            else if (k < pivIndex)
                r = pivIndex - 1;
            else
                l = pivIndex + 1;

        }
    }

    public static int getPivotIndex(int l, int r, int k) {
        if (r - l < 5)          // dla 5 lub mniej elementow mediana
            return sortAndMedian5(l, r);

        for (int i = l; i < r; i+=5) {
            int tmpR = i + 4;

            if (tmpR > r)
                tmpR = r;
            int median5 = sortAndMedian5(i, tmpR);

            int tmp = tab[median5];
            tab[median5] = tab[l + (i - l)/5];
            tab[l + (i - l)/5] = tmp;
        }
        int mid = (r - l) / 10 + l + 1;
        return select(l, l + (r - l)/5, k);
    }

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

    public static int partition(int l, int r, int k, int pivIndex) {
        int pivValue = tab[pivIndex];
        // pivot na koniec
        int tmp = tab[pivIndex];
        tab[pivIndex] = tab[r];
        tab[r] = tmp;

        int i = l - 1;
        int j = r;

        while(true) {
            while(tab[++i] < pivValue);
            while(j > l && tab[--j] >= pivValue);

            if (i >= j)
                break;
            else {
                int temp = tab[i];
                tab[i] = tab[j];
                tab[j] = tmp;
            }
        }
        int temp = tab[i];
        tab[i] = tab[j];
        tab[j] = tmp;
        int indexSmallerElems = i;
        ++i;
        for (j = i; j <= r; ++j) {
            if (tab[j] == pivValue) {
                 tmp = tab[j];
                 tab[j] = tab[i];
                 tab[i] = tmp;
                 ++i;
            }
        }
        int indexEqualElems = i;

        if (k < indexSmallerElems)
            return indexSmallerElems;
        if (k <= indexEqualElems)
            return k;
        return indexEqualElems;
    }
}

