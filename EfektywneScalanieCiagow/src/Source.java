// Krystian Jachna - 7

import java.util.Scanner;

class StackArray {
    private int maxSize;            // rozmiar tablicy zawierajacej stos
    private int[]Elem;              // tablica zawierajaca stos
    private int t;                  // indeks szczytu stosu

    public StackArray(int Size, Scanner sc) {           // konstruktor wczytujacy dodatkowo tablice wejsciowa
        maxSize = Size;
        Elem = new int [maxSize];

        for (int i = 0; i < maxSize; i++)               // wczytanie tablicy wejsciowej
            Elem[i] = sc.nextInt();

        t = 0;                                          // ustawiwa szczyt na pierwszy elemnt tablicy tj. najmniejszy
    }
    public int top() {                                  // zwraca to co jest na szczycie stosu
        if (isEmpty())                                  // jesli jest pusty zwraca -1
            return -1;
        return Elem[t];
    }
    public boolean isEmpty() {                          // sprawdza pustosc stosu
        return (t == maxSize);
    }
    public int pop() {                                  // zwraca szczyt stosu i usuwa element
        if (isEmpty())
            return -1;
        return Elem[t++];
    }

    /*
        Implementacja stosu na wlasne potrzeby, ktroy dziala tak, ze
        poczatkowo jest wczytywana jest tablica wejsciowa i szczytem
        stosu jest jej pierwszy element czyli najmniejszy. Nastepnie
        operacje identyczne jak dla zwyklego stosu.
     */
}


public class Source {
    public static Scanner sc = new Scanner(System.in);
    public static int stringsNumber;

    public static void main(String [] args) {
        int mergedArrLen = 0;                                    // dlugosc tablicy scalonej
        int setsNumber = sc.nextInt();                           // wczytanie ilosci zestawow danych

        while (setsNumber > 0) {                                 // wczytanie kolejnych zestawow danych

            stringsNumber = sc.nextInt();                        // wczytanie ilosci ciagow
            int [] stringsLength = new int[stringsNumber];       // wczytanie dlugosci poszczegolnych stringow

            for (int i = 0; i < stringsNumber; ++i) {
                stringsLength[i] = sc.nextInt();                 // wczytanie dlugosci poszczegolnych ciagow
                mergedArrLen += stringsLength[i];                // obliczanie dlugosci scalonej tablicy
            }

            StackArray [] Strings = new StackArray[stringsNumber];      // tablica stosow

            for (int i = 0; i < stringsNumber; ++i)
                Strings[i] = new StackArray(stringsLength[i], sc);      // Wczytanie stosu bedacego tablica do scalenia


            int [] mergedArr = HeapSort(Strings, mergedArrLen);         // Wywolanie funkcji ktora scala nam tablice i zwraca referencje do niej

            for (int i = 0; i < mergedArrLen; ++i )                     // wypisanie danych wejsciowych
                System.out.print(mergedArr[i] + " ");

            System.out.println("");
            --setsNumber;
            mergedArrLen = 0;
        }
    }

    public static void downHeap (StackArray[] Strings, int k, int n) {
        int j;
        StackArray tmp = Strings[k];
        int tmpV = Strings[k].top();

        while (k < n/2) {
            j = 2*k + 1;
            if (j < n-1 && Strings[j].top() > Strings[j+1].top()) j++;
            if (tmpV <= Strings[j].top()) break;

            Strings[k] = Strings[j];
            k = j;
        }
        Strings[k] = tmp;

        /*
            Podoobnie jak w insertionsort przechodzimy sciezka od wezla k
            do liscia, wybieramy mniejszy z nastepnikow i nadpisujmey wez
            ly do momentu gdy natchniemy sie na poprawna kolejnosc. lub
            dojdziemy do ostatniego wezla bedacego rodzicem
         */
    }

    public static int [] HeapSort(StackArray[] Strings, int mergedArrLen) {
        for (int k = (stringsNumber-1)/2; k >= 0; k--)
            downHeap(Strings, k, stringsNumber);


        int [] sortedArr = new int [mergedArrLen];
        int z = -1;

        while (stringsNumber > 0) {
            sortedArr[++z] = Strings[0].pop();

            if (Strings[0].isEmpty()) {
                StackArray tmp = Strings[0];
                Strings[0] = Strings[stringsNumber - 1];
                Strings[stringsNumber - 1] = tmp;
                stringsNumber--;
                downHeap(Strings,0, stringsNumber);
            } else {
                downHeap(Strings, 0, stringsNumber);
            }
        }
        return sortedArr;

        /*
            Zasada bardzo podobna do zwyklego heapSorta z ta roznica
            ze do naszej tablicy wyjsciowej/scalonej zapisujemy szcz
            yt naszego kopca, w przypadku, kopiec skalda sie ze stos
            ow wiec jezeli stos bedzie pusty dajemy go na ostatnie
            miejsce i zmniejszamy wielkosc kopca, w innym przypadku
            po prostu usuwamy dany element ze stosu i wykonujemy do
            wn heap aby warunek kopca byl zachowany.
         */
    }
}

// TEST.IN
/*
1
3
4 4 4
1 3 5 7
2 4 6 8
0 9 10 11
 */
// TEST.OUT
/*
0 1 2 3 4 5 6 7 8 9 10 11
 */