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
                // todo magiczne piatki

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

    public static int Select(int l, int r) {
        while (true) {
            if (l == r)
                return l;


        }
    }

    public static int pivot(int l, int r) {
        if (r - l < 5)          // dla 5 lub mniej elementow mediana
            return median5(l, r);

        for (int i = l; i < r; i+=5) {
            int tmpR = i + 4;

            if (tmpR > r)
                tmpR = r;

        }
    }

    public static int median5(int l, int r) {
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

        /*
            Funkcja sortuje za fragment tablicy za pomoca metody InsertionSort
            i zwraca indeks mediany elementow podtablicy.
         */
    }


}

