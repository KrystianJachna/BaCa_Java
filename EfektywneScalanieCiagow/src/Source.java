// Krystian Jachna - 7


import java.util.Scanner;

class StackArray {
    private int maxSize;            // rozmiar tablicy zawierajacej stos
    private int[]Elem;              // tablica zawierajaca stos
    private int t;                  // indeks szczytu stosu

    public StackArray(int Size, Scanner sc) {           // konstruktor wczytujacy dodatkowo tablice wejsciowa
        maxSize = Size;
        Elem = new int [maxSize];

        for (int i = 0; i < maxSize; i++)
            Elem[i] = sc.nextInt();

        t = 0;                                          // ustawiwa szczyt na pierwszy elemnt tablicy tj. najmniejszy
    }
    public int top() {                                  // zwraca to co jest na szczycie stosu
        if (isEmpty())
            return -1;
        return Elem[t];
    }
    public boolean isEmpty() {                          // sprawdza pustosc stosu
        return (t == maxSize);
    }
    public int pop() {                                  // zwraca szczyt stosu
        if (isEmpty())
            return -1;
        return Elem[t++];
    }
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


            int [] mergedArr = HeapSort(Strings, mergedArrLen);

            for (int i = 0; i < mergedArrLen; ++i )
                System.out.print(mergedArr[i] + " ");

            System.out.println("");
            --setsNumber;
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
    }
}
