// Krystian Jachna - 7
/*
    Zlozonosc prezentowanego algorytmu to O(Nlog(N)), dziala on na zasadzie dziel
    i zwyciezaj, z czego wynika log(N), za kazdym wywolaniem rekurenyjnym dzieli
    problem na dwa mniejsze podproblemy i na kazdym z nich wykonujemy operacje,
    miedzy innymi jest to przesuniecie elementow o zlozonosci O(n) co razem daje
    wymagana w zadaniu zlozonosc.
 */
import java.util.Scanner;

public class Source {
    public static Scanner sc = new Scanner(System.in);

    public static void main ( String [] args ) {
        int setsNumber = 0;                 // liczba zestawow
        setsNumber = sc.nextInt();          // wczytanie liczby zestawow
        String [] tab;                      // referencja tablicy stringow
        int tabSize = 0;                    // rozmiar tablicy
        String prefix = "";                 // najdluzszy prefix

        while (setsNumber != 0 ) {          // petla wczytujaca zestawy danych
            prefix = "";                    // wyzerowanie prefixu
            tabSize = sc.nextInt();         // wczytanie rozmiaru tablicy
            tab = new String[tabSize];      // utworzenie nowej tablicy
            for (int i = 0; i < tabSize; ++i)
                tab[i] = sc.next();         // odczytanie elementow tablicy
            prefix = shuffleWithPrefix(tab, 0, tabSize - 1);    // tasowanie tablicy wraz z wyznaczeniem prefixu
            for (int i = 0; i < tabSize; ++i)
                System.out.print(tab[i] + " ");                          // wypisanie zawartosci potasowanej tablicy
            System.out.println();
            System.out.println(prefix);
            setsNumber--;
        }
        /*
            Odczytanie kolejnych danych wejsciowych i zapisanie ich
            do tablicy, dodatkowo wywolanie kolejnych funkcji tasu-
            jacych i wypisanie wyniku dla poszczegolnego zestawu.
            Rownoczesnie z tasowaniem odbywa sie wyznaczanie wspol-
            nego prefixu dla piosenek obu plyt.
     */
    }

    public static String commonPrefix(String str1, String str2) {
        String commonPart = "";                                                     // czesc wspolna

        for (int i = 0, j = 0; i < str1.length() && j < str2.length(); ++i, ++j) {  // porownywanie kazdego znaku az do napotkania znaku roznego
            if (str1.charAt(i) != str2.charAt(j))
                break;
            else
                commonPart += str1.charAt(i);
        }
        return commonPart;                                                          // zwraca wspolna czesc
        /*
            Przekazujac do funkcji dwa napisy idac po kazdym znaku
            obu z nich funkcja zatrzymuje sie przy pierwszym napo-
            tkanym znaku rozniacym sie od siebie. Nastepnie funkc-
            ja zwraca wspolna czesc ktora udalo sie jej zapisac.
            Ewentualnie jest to pusty string.
         */
    }

    public static String shuffleWithPrefix(String [] tab, int left, int right)  {
        int size = right - left + 1;                    // rozmiar tablicy

        if (size <= 4) {                                // przy rozmiarze mniejszym rownym 4
            String prefix = tab[left];                  // wyznacza wspolny prefix
            for (int i = left; i < right; ++i)
               prefix = commonPrefix(prefix, tab[i+1]); // idac po wszystkich stringach podtablicy

            String tmp = "";
            if (size > 2) {                             // recznie przesuwa 2 i 3 element podtablicy
                tmp = tab[left + 1];
                tab[left + 1] = tab[left + 2];
                tab[left + 2] = tmp;
            }
            return prefix;                              // zwraca czesc wspolna
        }

        int mid = findBinaryIndex(left, right);                 // limit bedacy najwieksza potega 2 mieszczaca sie w tablicy dzielaca ja na dwie czesci
        int scQ = (mid - left + 1) / 2 + left;                  // granica drugiej cwiartki w przypadku nie rownych polow => prawa > lewej
        int thQ = ((right - mid) % 2 == 0 ?  ((right - mid)  / 2 + mid) : ((right - mid) / 2) + mid + 1);        // granica czwartej cwiartki w przypadku nie rownych polow => lewa > prawej
        int shiftAmount = thQ - mid;
        /*
            Liczba przesuniec elementow dwoch srodkowych
            czesci tablicy, bedaca iloscia elementow tr-
            zeciej trzeciego fragmentu.
         */

        shift(tab, scQ, thQ, shiftAmount);                      // przesuniecie dwoch srodkowych gragmentow talicy

        return commonPrefix(shuffleWithPrefix(tab, left, mid ), shuffleWithPrefix(tab, mid + 1, right)); // wywolanie rekurenyjne wraz ze znalezieniem wspolnego prefixu
        /*
            Funkcja szuka najwiekszej potegi 2 ktora miesci sie w tablicy i dzieli
            ja na dwie czesci, pierwsza czesc jest potega 2, natomiast druga jest
            reszta tablicy. Kolejno obie te czesci dzieli na polowy. W przypadku
            gdy nie jest mozliwe podzielenie na polowy brane sa wiekszes czesci.
            Nastepnie dwie srodkowe czesci przesuwa sie w lewo o tyle elementow
            ile posiada czesc trzecia. W przypadku gdy elementow jest <= 4.
            Nastepuje reczne przesuniecie elementow i znalezienie wspolnej czes-
            ci kazdej z 4 plyt. Przy wywolaniu rekurencyjnym znajdowane sa rown-
            iez wsolne czesci kazdej czesci na ktora tablica jest podzielona.
         */
    }

    public static int findBinaryIndex(int leftBorder, int rightBorder) {
        int size = rightBorder - leftBorder + 1;    // rozmiar tablicy
        int midBorder = 1;                          // najmniejsza potega dwojki
        while (midBorder < size)
            midBorder *=2;                          // domnazanie granicy
        return midBorder / 2 - 1 + leftBorder;      // zwrocenie indeksu
         /*
            Funckja szukajaca najwiekszej potegi dwojki, mniejszej
            od rozmiaru tablicy.
        */
    }

    public static void shift(String [] tab, int leftBorder, int rightBorder, int shiftAmount) {
        int startingIndex = leftBorder;          // indeks od ktorego zaczynamy, aby przeciwdzialac zapetlaniu
        int curIndex = leftBorder;               // obecny indeks
        int size = rightBorder - leftBorder + 1; // rozmiar podtablicy
        String tmp1 = "";                        // tymczasowa zmienna sluzaca swap
        String tmp2 = "";                        // -||-

        for (int i = 0; i < size; ++i) {         // tyle przesuniec ile elementow
            tmp2 = tab[curIndex];                // zapisanie przenoszonego elementu
            tab[curIndex] = tmp1;                // nadpisanie nowej wartosci
            tmp1 = tmp2;                         // swap
            curIndex = leftShiftedIndex(shiftAmount, size, curIndex, leftBorder); // obliczanie nowego indeksu

            if (curIndex == startingIndex) {     // gdyby mialo sie zapetlac
                tab[curIndex] = tmp1;
                curIndex++;                      // przechodzimy dalej
                startingIndex++;                 // -||-
                tmp1 = tab[leftShiftedIndex(-shiftAmount, size, curIndex, leftBorder)]; // zapisanie elementu ktory zostanie przeniesiony z prawej strony
            }
        }
        /*
            Zadaniem procedury jest przesuniecie elementow o odpowiednia ilosc
            miejsc poslugujac sie podtablica na zasadzie tablicy cyklicznej.
         */
    }

    public static int leftShiftedIndex(int shift, int size, int curIndex, int left) {
        int afterShift = curIndex - shift - left;   // indeks po przesunieciu w lewo
        if (afterShift < 0)                         // osobna obsluga dla ujemnych wartosci
            return size + afterShift % size + left;
        else if (size == 0) {                       // blad w przypadku dzielenia przez 0
            System.out.println("error");
            return 0;
        }
        else
            return afterShift % size + left;        // zwyczajne modulo
        /*
            Funkcja znajduje indeks fragmentu tablicy po
            przesunieciu go o odpowiednia wartosc cykli-
            cznie.
         */
    }
}
// Test in
/*
100
1
a1
2
a1
b1
3
a1 a2
b1
4
a1 a2
b1 b2
5
a1 a2 a3
b1 b2
6
a1 a2 a3
b1 b2 b3
7
a1 a2 a3 a4
b1 b2 b3
8
a1 a2 a3 a4
b1 b2 b3 b4
9
a1 a2 a3 a4 a5
b1 b2 b3 b4
10
a1 a2 a3 a4 a5
b1 b2 b3 b4 b5
11
a1 a2 a3 a4 a5 a6
b1 b2 b3 b4 b5
12
a1 a2 a3 a4 a5 a6
b1 b2 b3 b4 b5 b6
13
a1 a2 a3 a4 a5 a6 a7
b1 b2 b3 b4 b5 b6
14
a1 a2 a3 a4 a5 a6 a7
b1 b2 b3 b4 b5 b6 b7
15
a1 a2 a3 a4 a5 a6 a7 a8
b1 b2 b3 b4 b5 b6 b7
16
a1 a2 a3 a4 a5 a6 a7 a8
b1 b2 b3 b4 b5 b6 b7 b8
17
a1 a2 a3 a4 a5 a6 a7 a8 a9
b1 b2 b3 b4 b5 b6 b7 b8
18
a1 a2 a3 a4 a5 a6 a7 a8 a9
b1 b2 b3 b4 b5 b6 b7 b8 b9
19
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10
b1 b2 b3 b4 b5 b6 b7 b8 b9
20
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10
21
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10
22
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11
23
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11
24
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12
25
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12
26
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13
27
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13
28
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14
29
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14
30
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15
31
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15
32
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16
33
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16
34
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17
35
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17
36
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18
37
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18
38
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19
39
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19
40
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20
41
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20
42
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21
43
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21
44
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22
45
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22
46
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23
47
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23
48
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24
49
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24
50
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25
51
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25
52
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26
53
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26
54
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27
55
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27
56
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28
57
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28
58
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29
59
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29
60
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30
61
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30
62
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31
63
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31
64
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32
65
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32
66
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33
67
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33
68
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34
69
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34
70
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35
71
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35
72
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36
73
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36
74
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37
75
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37
76
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38
77
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38
78
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39
79
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39
80
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40
81
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40
82
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40 b41
83
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41 a42
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40 b41
84
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41 a42
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40 b41 b42
85
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41 a42 a43
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40 b41 b42
86
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41 a42 a43
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40 b41 b42 b43
87
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41 a42 a43 a44
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40 b41 b42 b43
88
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41 a42 a43 a44
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40 b41 b42 b43 b44
89
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41 a42 a43 a44 a45
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40 b41 b42 b43 b44
90
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41 a42 a43 a44 a45
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40 b41 b42 b43 b44 b45
91
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41 a42 a43 a44 a45 a46
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40 b41 b42 b43 b44 b45
92
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41 a42 a43 a44 a45 a46
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40 b41 b42 b43 b44 b45 b46
93
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41 a42 a43 a44 a45 a46 a47
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40 b41 b42 b43 b44 b45 b46
94
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41 a42 a43 a44 a45 a46 a47
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40 b41 b42 b43 b44 b45 b46 b47
95
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41 a42 a43 a44 a45 a46 a47 a48
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40 b41 b42 b43 b44 b45 b46 b47
96
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41 a42 a43 a44 a45 a46 a47 a48
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40 b41 b42 b43 b44 b45 b46 b47 b48
97
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41 a42 a43 a44 a45 a46 a47 a48 a49
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40 b41 b42 b43 b44 b45 b46 b47 b48
98
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41 a42 a43 a44 a45 a46 a47 a48 a49
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40 b41 b42 b43 b44 b45 b46 b47 b48 b49
99
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41 a42 a43 a44 a45 a46 a47 a48 a49 a50
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40 b41 b42 b43 b44 b45 b46 b47 b48 b49
100
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 a13 a14 a15 a16 a17 a18 a19 a20 a21 a22 a23 a24 a25 a26 a27 a28 a29 a30 a31 a32 a33 a34 a35 a36 a37 a38 a39 a40 a41 a42 a43 a44 a45 a46 a47 a48 a49 a50
b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20 b21 b22 b23 b24 b25 b26 b27 b28 b29 b30 b31 b32 b33 b34 b35 b36 b37 b38 b39 b40 b41 b42 b43 b44 b45 b46 b47 b48 b49 b50
 */
// Test Out
/*
a1
a1
a1 b1
a1 b1 a2
a1 b1 a2 b2
a1 b1 a2 b2 a3
a1 b1 a2 b2 a3 b3
a1 b1 a2 b2 a3 b3 a4
a1 b1 a2 b2 a3 b3 a4 b4
a1 b1 a2 b2 a3 b3 a4 b4 a5
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41 b41
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41 b41 a42
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41 b41 a42 b42
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41 b41 a42 b42 a43
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41 b41 a42 b42 a43 b43
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41 b41 a42 b42 a43 b43 a44
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41 b41 a42 b42 a43 b43 a44 b44
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41 b41 a42 b42 a43 b43 a44 b44 a45
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41 b41 a42 b42 a43 b43 a44 b44 a45 b45
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41 b41 a42 b42 a43 b43 a44 b44 a45 b45 a46
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41 b41 a42 b42 a43 b43 a44 b44 a45 b45 a46 b46
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41 b41 a42 b42 a43 b43 a44 b44 a45 b45 a46 b46 a47
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41 b41 a42 b42 a43 b43 a44 b44 a45 b45 a46 b46 a47 b47
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41 b41 a42 b42 a43 b43 a44 b44 a45 b45 a46 b46 a47 b47 a48
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41 b41 a42 b42 a43 b43 a44 b44 a45 b45 a46 b46 a47 b47 a48 b48
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41 b41 a42 b42 a43 b43 a44 b44 a45 b45 a46 b46 a47 b47 a48 b48 a49
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41 b41 a42 b42 a43 b43 a44 b44 a45 b45 a46 b46 a47 b47 a48 b48 a49 b49
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41 b41 a42 b42 a43 b43 a44 b44 a45 b45 a46 b46 a47 b47 a48 b48 a49 b49 a50
a1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12 a13 b13 a14 b14 a15 b15 a16 b16 a17 b17 a18 b18 a19 b19 a20 b20 a21 b21 a22 b22 a23 b23 a24 b24 a25 b25 a26 b26 a27 b27 a28 b28 a29 b29 a30 b30 a31 b31 a32 b32 a33 b33 a34 b34 a35 b35 a36 b36 a37 b37 a38 b38 a39 b39 a40 b40 a41 b41 a42 b42 a43 b43 a44 b44 a45 b45 a46 b46 a47 b47 a48 b48 a49 b49 a50 b50

 */

