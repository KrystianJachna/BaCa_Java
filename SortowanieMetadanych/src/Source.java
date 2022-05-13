// Krystian Jachna - 7

import java.util.Scanner;

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

    /*
        Przechowuje wyjscie i wypisuje cale jednoczesnie pod
        koniec dzialania programu
     */

}

class Params {
    int rowsNumber;                 // liczba wierszy
    int colToSort;                  // kolumna ktora ma zostac posortowana indeksowana od 1
    byte sortDirect;                // kierunek sortowania, -1 - odwrotne, 1 - normalne

    Params(int rowsNumber, int colToSort, byte sortDirect) {
        this.rowsNumber = rowsNumber;
        this.colToSort = colToSort;
        this.sortDirect = sortDirect;
    }

    /*
        Klasa przechowujaca parametry kazdego zestawu, posiada
        ona miedzy innymi konstruktor ktory uzueplnia wszystk-
        ie przechowujace przez nia dane.
     */
}

class Metadata {
    String str;                 // napis
    int anInt;                  // liczba naturalna
    boolean isStr;              // flaga

    Metadata(String str) {
        this.str = str;
        this.isStr = true;
    }

    Metadata(int anInt) {
        this.anInt = anInt;
        this.isStr = false;
    }

    /*
        Klasa przechowujaca metadane, przechowuje String lub
        int w zaleznosci od tego co zostalo podane, przelad-
         owany konstruktor pozwala latwo uzueplniac pojedyn-
         cze dane.

     */
}

class MetaArr {
    Metadata [] Arr;
    int size;

    MetaArr(int size) {
        this.size = size;
        Arr = new Metadata[this.size];
    }
    /*
        Klasa umozliwiajaca stworzenie tablicy dwuwymiarowej
        metadanych, umozliwa miedzy innymi po utworzeniu ta-
        blicy tablic (2D) przepinanie wierszy przy zmienia
        kolejnosci w sortowaniu. Posiada konstruktor uzupel-
        niajacy przechowywane dane.
     */
}

public class Source {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        byte setsNum = sc.nextByte();           // liczba zestawow
        sc.nextLine();
        Params setParams;                       // parametry zestawu;
        String[] headers;                       // naglowki
        String[] tmpHeaders;                    // tymczasowe naglowki jeszcze odpowiednio nie 'posortowane'
        String line;                            // pojedyncza linijka wejscia
        MetaArr[] datasArr2D;                   // array 2D do przechowywania metadanych

        while (setsNum > 0) {
            line = sc.nextLine();
            setParams = parseParams(line);                                  // podzielenie lini i wyluskanie odpowiednich danych

            line = sc.nextLine();
            tmpHeaders = line.split(",");                             // analogicznie z naglowkami
            headers = new String[tmpHeaders.length];
            headers[0] = tmpHeaders[setParams.colToSort - 1];               // uzupelnienie pierwszej kolumny na ta wedlug ktorej bedziemy sortowali

            for (int i = 1, k = 0; i < tmpHeaders.length; ++i, ++k) {       // uzupelnienie reszty naglowkow w odpowiedniej kolejnosci
                if (k == setParams.colToSort - 1)
                    k++;
                headers[i] = tmpHeaders[k];
            }

            datasArr2D = readMetas(headers.length, setParams);              // wczytuje dane do tablicy 2D
            sort(setParams, datasArr2D);                                    // sortowanie danych

            for (int i = 0; i < headers.length; ++i) {                      // wczytanie wyjscia (naglowkow) do buffora w odpowiedni sposob
                if (i == headers.length - 1)
                    Buffor.add(headers[i] + "\n");
                else
                    Buffor.add(headers[i] + ",");
            }
            for (int i = 0; i < setParams.rowsNumber; ++i) {                // wczytania wyjscia (tablicy 2d) do buffora w odpowiedni sposob
                for (int j = 0; j < headers.length; ++j) {
                    if (j == headers.length - 1) {
                        if (datasArr2D[i].Arr[j].isStr)
                            Buffor.add(datasArr2D[i].Arr[j].str + "\n");
                        else
                            Buffor.add(datasArr2D[i].Arr[j].anInt + "\n");
                    } else {
                        if (datasArr2D[i].Arr[j].isStr)
                            Buffor.add(datasArr2D[i].Arr[j].str + ",");
                        else
                            Buffor.add(datasArr2D[i].Arr[j].anInt + ",");
                    }
                }
            }
            Buffor.add("\n");
            --setsNum;
        }
        Buffor.flush();                                                        // wypisanie wyjscia
        sc.close();                                                            // zamkniecie skanera
    }

    public static Params parseParams(String line) {
        String[] seperatedLine = line.split(",");                                          // podzielenie linii wdg przecinka na tablce stringow
        return new Params(Integer.parseInt(seperatedLine[0]), Integer.parseInt(seperatedLine[1]), Byte.parseByte(seperatedLine[2]));

        /*
            Funkcja tworzy i zwraca referencje, do obiekt z elementami podanymi
            jako dane wejsciowe, poczatkowo sa one rozdzielane za pomoca odpow-
            iedniej funkcji do tablic Stringow, a nastepnie zamienione ze Stri-
            nga do odpowiednich typow i przekazane do konstruktora.
         */
    }

    public static MetaArr[] readMetas(int colNumber, Params params) {
        MetaArr[] MetaArr2d = new MetaArr[params.rowsNumber];                  // stworzenie nowego obiektu imitujacego tablice 2D obiektow metadana

        for (int i = 0; i < params.rowsNumber; ++i)                              // dla kazdego elementu (wiersza) tablicy, tworzony jest nowy obiekt posiadajacy tablice
            MetaArr2d[i] = new MetaArr(colNumber);

        String line;                                                           // wczytana linia
        String[] parsedLine;                                                   // podzielona wczytana linia

        line = sc.nextLine();
        parsedLine = line.split(",");                                 // podzielenie linijki wzgledem przecinka

        if (isInt(parsedLine[params.colToSort - 1]))                         // poczatkowo jest uzupelniana kolumna ktora bedzie sortowana, sprawdzane jest dodatkowo czy jest intem...
            MetaArr2d[0].Arr[0] = new Metadata(Integer.parseInt(parsedLine[params.colToSort - 1]));
        else                                                                // ... czy stringiem
            MetaArr2d[0].Arr[0] = new Metadata(parsedLine[params.colToSort - 1]);

        for (int i = 1, k = 0; i < colNumber; ++i, ++k) {                   // uzupelniane sa kolejne linjki
            if (k == params.colToSort - 1)                                   // z pominieciem juz wpisanej kolumny
                ++k;

            if (isInt(parsedLine[k]))                                        // sprawdzanie czy jest intem i zapisanie...
                MetaArr2d[0].Arr[i] = new Metadata(Integer.parseInt(parsedLine[k]));
            else                                                            // czy tez stringiem
                MetaArr2d[0].Arr[i] = new Metadata(parsedLine[k]);
        }
        /*
            Specjalnie traktowana pierwsza linia, ktora definiuje czy dana kolumna
            bedzie typu int czy tez string, wykorzystuje do tego specjalna funkcje
            isInt, kolejne wiersze bazuja na tej, pierwszej linii. W przypadku gd-
            yby liczba wierszy byla mniejsza od zera... W zaleznosci od tego czy
            mozliwa jest 0 liczba wierszy.
         */

        for (int i = 1; i < params.rowsNumber; ++i) {                           // kolejno uzupelniane sa kolejne wiersze z ta roznica ze...
            line = sc.nextLine();                                               // ... sprawdzany jest pierwszy element danej kolumny poniewaz kolumny sa tego samego typu
            parsedLine = line.split(",");

            if (MetaArr2d[0].Arr[0].isStr)                                       // pierwsza kolumna, ta wedlug ktorej sortujemy
                MetaArr2d[i].Arr[0] = new Metadata(parsedLine[params.colToSort - 1]);
            else
                MetaArr2d[i].Arr[0] = new Metadata(Integer.parseInt(parsedLine[params.colToSort - 1]));

            for (int j = 1, k = 0; j < colNumber; ++j, ++k) {                   // reszta kolumn na tej samej zasadzie
                if (k == params.colToSort - 1)
                    ++k;

                if (MetaArr2d[0].Arr[j].isStr)
                    MetaArr2d[i].Arr[j] = new Metadata(parsedLine[k]);
                else
                    MetaArr2d[i].Arr[j] = new Metadata(Integer.parseInt(parsedLine[k]));


            }
            /*
                Uzupelnione sa kolejne wiersze struktury bazujac typ w zaleznosci
                od pierwszego wiersza. Wykorzystuje sie przeladowane kontruktory
                klasy metadane. Dodatkowo najpierw uzupelniana jest pierwsza kol-
                umna, dopiero pozniej kolejne
             */
        }
        return MetaArr2d;
        /*
            Funkcja zwraca referencje do tablicy tablic metadanych odpowiednio
            sklasyfikowanych, wczytywanych z wejscia.
         */
    }

    public static boolean isInt(String toCheck) {
        if (toCheck == null) {
            return false;
        }
        try {                                       // sprawdza czy jest mozliwe przepisania stringa do inta
            Integer.parseInt(toCheck);
        } catch (NumberFormatException nfe) {         // jesli nie to zwraca falsz...
            return false;
        }
        return true;                                // ... w przeciwnym wypadku prawda

        /*
            Funkcja sprawdza za pomaca try - catch czy String jest liczba
            czy napisem. Zwraca odpowiednia wartosc w zaleznosci czy lic-
            ba jest intem czy nie.
         */
    }

    public static void sort(Params params, MetaArr[] datasArr2D) {
        boolean sortInc = true;                             // dwie flagi ulatwiajace wykonanie odpowiedniej akcji
        boolean stringSort = true;

        if (params.sortDirect == -1)                        // sprawdza kierunek sortowania
            sortInc = false;
        if (!datasArr2D[0].Arr[0].isStr)                    // sprawdza na jakim typie ma zostac wykonane sortowanie
            stringSort = false;

        if (sortInc && stringSort)                          // wywoluje odpowiednia metode sortowania w zaleznosci od kierunku i typu sortowania
            quickSort_STR(params.rowsNumber, datasArr2D, true);
        else if (sortInc)
            quickSort_INT(params.rowsNumber, datasArr2D, true);
        else if (stringSort)
            quickSort_STR(params.rowsNumber, datasArr2D, false);
        else
            quickSort_INT(params.rowsNumber, datasArr2D, false);

        /*
            Funkcja przejsciowa majaca za zadanie jedynie ustalic
            w jakim kierunku i na jakich danych ma zostac wywolan
            rekurencja, po czym wywoluje odpowiednia funkcjie so-
            rtujaca.
         */
    }

    // DO OBSLUGI INTA
    // ponizsze funkcje sa praktycznie identyczne roznia sie obsluga stringa i inta
    public static void quickSort_INT(int rowsNumber, MetaArr[] datasArr2D, boolean isInc) {
        int l = 0;                                                                  // lewa granica
        int r = rowsNumber - 1;                                                     // prawa granica
        int i = 0;                                                                  // iterator
        int m;                                                                      // indeks pivota wzgledem ktorego podzielone sa czesci tablicy
        int tmp = r;                                                                // tymczasowa prawa granica
        boolean leftPart = false;                                                   // do sprawdzenia czy czesc jest juz mniejsza niz 5
        boolean rightPart = false;                                                  // do sprawdzenia czy czesc jest juz mniejsza niz 5

        if (isInc) {                                                                // w zaleznosci czy sortowanie jest rosnace czy...
            while (true) {
                i--;
                while (l < tmp) {
                    m = partitionINC(l, tmp, datasArr2D);                           // wyznacza indeks wedlug ktorego podzielone sa dane mniejsze i wieksze

                    if (tmp - m + 1 <= 5) {                                         // jesli prawa czesc ma mniej elementow niz 6
                        SelectionSortIncInt(m, tmp, datasArr2D);                    // jest sortowana SelectionSortem
                        rightPart = true;
                    }
                    if (m  - l <= 5) {                                              // to samo z lewa
                        SelectionSortIncInt(l, m - 1, datasArr2D);
                        leftPart = true;
                    }
                    if (rightPart && leftPart)                                      // jesli obie czesci zostaly posortowane to ta czesc talbicy jest upozadkowana
                        l = tmp + 1;
                    else if (leftPart) {                                            // jesli tylko lewa czesc zostala posortowana to juz sie nia nie zajmujemy
                        l = m;
                        ++i;
                    }
                    else if (rightPart) {                                           // jesli prawa czesc zostala posortowana to juz sie nia nie zajmujemy
                        datasArr2D[tmp].Arr[0].anInt = -datasArr2D[tmp].Arr[0].anInt;
                        tmp = m - 1;
                        ++i;
                    }
                    else {                                                          // w innym przypadku sygnalizujemy koniec podzielonej czesci zmieniajac wartosc na -
                        datasArr2D[tmp].Arr[0].anInt = -datasArr2D[tmp].Arr[0].anInt;
                        tmp = m - 1;
                        ++i;
                    }
                    leftPart = false;
                    rightPart = false;
                }
                if (i < 0)
                    break;
                tmp = findNextPiv(l, rowsNumber, datasArr2D);
                if (tmp != -1)
                    datasArr2D[tmp].Arr[0].anInt = -datasArr2D[tmp].Arr[0].anInt;

            }
        } else {                                                                      // ... malejace rozniace sie wywolywaniem funkcji dla obslugi sortowania malejacego
            while (true) {
                i--;
                while (l < tmp) {
                    m = partitionDEC(l, tmp, datasArr2D);

                    if (tmp - m + 1 <= 5) {
                        SelectionSortDecInt(m, tmp, datasArr2D);
                        rightPart = true;
                    }
                    if (m  - l <= 5) {
                        SelectionSortDecInt(l, m - 1, datasArr2D);
                        leftPart = true;
                    }
                    if (rightPart && leftPart)
                        l = tmp + 1;
                    else if (leftPart) {
                        l = m;
                        ++i;
                    }
                    else if (rightPart) { // prawa czesc
                        datasArr2D[tmp].Arr[0].anInt = -datasArr2D[tmp].Arr[0].anInt;
                        tmp = m - 1;
                        ++i;
                    }
                    else {
                        datasArr2D[tmp].Arr[0].anInt = -datasArr2D[tmp].Arr[0].anInt;
                        tmp = m - 1;
                        ++i;
                    }
                    leftPart = false;
                    rightPart = false;
                }
                if (i < 0)
                    break;
                tmp = findNextPiv(l, rowsNumber, datasArr2D);
                if (tmp != -1)
                    datasArr2D[tmp].Arr[0].anInt = -datasArr2D[tmp].Arr[0].anInt;
            }
        }
        /*
            Funkcja dziala na zasadzie QuickSorta wyznacza miejsce w tablicy wedlug ktorej
            ustawia elementy mniejsze/wieksze z lewej i wieksze/mniejsze z prawej strony,
            sygnalizuje miejsce konca podzialu ustawieniem wartosci na ujemna. Gdy podpr-
            oblem jest mniejszy rowny 5 wywoluje sortowanie metoda SelectionSort.
         */
    }

    private static void SelectionSortIncInt(int l, int r, MetaArr[] datasArr2D) {
        int min;                        // indeks najmniejszego elementu
        MetaArr tmp;

        for (int i = l; i < r; ++i) {
            min = i;
            for (int j = i+1; j <= r; ++j)
                if (datasArr2D[j].Arr[0].anInt < datasArr2D[min].Arr[0].anInt)
                    min = j;
            tmp = datasArr2D[i];
            datasArr2D[i] = datasArr2D[min];
            datasArr2D[min] = tmp;
        }
        /*
            Funkcja sortujaca wiersze metoda SelectionSort
            Wywolywana gdy rozmiar podproblemu jest mniej-
            szy rowny 5.
         */

    }
    // kopia poprzedniej dla malejacych
    private static void SelectionSortDecInt(int l, int r, MetaArr[] datasArr2D) {
        int min;
        MetaArr tmp;

        for (int i = l; i < r; ++i) {
            min = i;
            for (int j = i+1; j <= r; ++j)
                if (datasArr2D[j].Arr[0].anInt > datasArr2D[min].Arr[0].anInt)
                    min = j;
            tmp = datasArr2D[i];
            datasArr2D[i] = datasArr2D[min];
            datasArr2D[min] = tmp;
        }
    }

    private static int findNextPiv(int l, int rowsNumber, MetaArr[] datasArr2D) {
        for (int i = l; i < rowsNumber; ++i) {
            if (datasArr2D[i].Arr[0].anInt < 0)
                return i;
        }
        return - 1;
        /*
            Szuka kolejnej ujemnej wartosci
         */
    }

    private static int partitionINC(int l, int r, MetaArr[] datasArr2D) {
        int pivot = datasArr2D[(l + r) / 2].Arr[0].anInt;   // wartosc pivota

        while (l <= r) {
            while (datasArr2D[r].Arr[0].anInt > pivot)
                r--;
            while (datasArr2D[l].Arr[0].anInt < pivot)
                l++;
            if (l <= r) {
                MetaArr tmp = datasArr2D[r];
                datasArr2D[r] = datasArr2D[l];
                datasArr2D[l] = tmp;
                l++;
                r--;
            }
        }
        return l;
        /*
            Funkcja bierze srodkowa wartosc jako wartosc pivota i ustawia elementy
            wieksze od niego z prawej strony i mniejsze z lewej a nastepnie zwraca
            pozycje wedle ktorej zostala podzielona tablica, w tym przypadku dla
            rosnacego sortowania
         */
    }
    // kopia poprzedniej dla malejacych
    private static int partitionDEC(int l, int r, MetaArr[] datasArr2D) {
        int pivot = datasArr2D[(l + r) / 2].Arr[0].anInt;
        while (l <= r) {
            while (datasArr2D[r].Arr[0].anInt < pivot)
                r--;
            while (datasArr2D[l].Arr[0].anInt > pivot)
                l++;
            if (l <= r) {
                MetaArr tmp = datasArr2D[r];
                datasArr2D[r] = datasArr2D[l];
                datasArr2D[l] = tmp;
                l++;
                r--;
            }
        }
        return l;
    }



    // DO OBSLUGI STRINGA, jedyna roznica polega na obsludze tego typu
    public static void quickSort_STR(int rowsNumber, MetaArr[] datasArr2D, boolean isInc) {
        int l = 0;
        int r = rowsNumber - 1;
        int m;
        int i = 0;
        int tmp = r;
        boolean leftPart = false;
        boolean rightPart = false;

        if (isInc) {
            while (true) {
                i--;
                while (l < tmp) {
                    m = partitionINCstr(l, tmp, datasArr2D);

                    if (tmp - m + 1 <= 5) {
                        SelectionSortIncStr(m, tmp, datasArr2D);
                        rightPart = true;
                    }
                    if (m  - l <= 5) {
                        SelectionSortIncStr(l, m - 1, datasArr2D);
                        leftPart = true;
                    }
                    if (rightPart && leftPart)
                        l = tmp + 1;
                    else if (leftPart) {
                        l = m;
                        ++i;
                    }
                    else if (rightPart) {
                        StringBuilder sb = new StringBuilder(datasArr2D[tmp].Arr[0].str);           // zmiana pierwszego chara na wartosc ujemna
                        sb.setCharAt(0, (char) (-(byte) datasArr2D[tmp].Arr[0].str.charAt(0)));
                        datasArr2D[tmp].Arr[0].str = sb.toString();
                        tmp = m - 1;
                        ++i;
                    }
                    else {
                        StringBuilder sb = new StringBuilder(datasArr2D[tmp].Arr[0].str);           // zmiana pierwszego chara na wartosc ujemna
                        sb.setCharAt(0, (char) (-(byte) datasArr2D[tmp].Arr[0].str.charAt(0)));
                        datasArr2D[tmp].Arr[0].str = sb.toString();
                        tmp = m - 1;
                        ++i;
                    }
                    leftPart = false;
                    rightPart = false;
                }
                if (i < 0)
                    break;
                tmp = findNextPivstr(l, rowsNumber, datasArr2D);
                if (tmp != -1) {
                    StringBuilder sb = new StringBuilder(datasArr2D[tmp].Arr[0].str);
                    sb.setCharAt(0, (char) (-(byte) datasArr2D[tmp].Arr[0].str.charAt(0)));
                    datasArr2D[tmp].Arr[0].str = sb.toString();
                }
            }
        } else {
            while (true) {
                i--;
                while (l < tmp) {
                    m = partitionDECstr(l, tmp, datasArr2D);

                    if (tmp - m + 1 <= 5) {
                        SelectionSortDecStr(m, tmp, datasArr2D);
                        rightPart = true;
                    }
                    if (m  - l <= 5) {
                        SelectionSortDecStr(l, m - 1, datasArr2D);
                        leftPart = true;
                    }
                    if (rightPart && leftPart)
                        l = tmp + 1;
                    else if (leftPart) {
                        l = m;
                        ++i;
                    }
                    else if (rightPart) {
                        StringBuilder sb = new StringBuilder(datasArr2D[tmp].Arr[0].str);           // zmiana pierwszego chara na wartosc ujemna
                        sb.setCharAt(0, (char) (-(byte) datasArr2D[tmp].Arr[0].str.charAt(0)));
                        datasArr2D[tmp].Arr[0].str = sb.toString();
                        tmp = m - 1;
                        ++i;
                    }
                    else {
                        StringBuilder sb = new StringBuilder(datasArr2D[tmp].Arr[0].str);           // zmiana pierwszego chara na wartosc ujemna
                        sb.setCharAt(0, (char) (-(byte) datasArr2D[tmp].Arr[0].str.charAt(0)));
                        datasArr2D[tmp].Arr[0].str = sb.toString();
                        tmp = m - 1;
                        ++i;
                    }
                    leftPart = false;
                    rightPart = false;
                }
                if (i < 0)
                    break;
                tmp = findNextPivstr(l, rowsNumber, datasArr2D);
                if (tmp != -1) {
                    StringBuilder sb = new StringBuilder(datasArr2D[tmp].Arr[0].str);
                    sb.setCharAt(0, (char) (-(byte) datasArr2D[tmp].Arr[0].str.charAt(0)));
                    datasArr2D[tmp].Arr[0].str = sb.toString();
                }
            }
        }
    }

    private static void SelectionSortIncStr(int l, int r, MetaArr[] datasArr2D) {
        int min;
        MetaArr tmp;

        for (int i = l; i < r; ++i) {
            min = i;
            for (int j = i+1; j <= r; ++j)
                if (datasArr2D[j].Arr[0].str.compareTo(datasArr2D[min].Arr[0].str) < 0)
                    min = j;
            tmp = datasArr2D[i];
            datasArr2D[i] = datasArr2D[min];
            datasArr2D[min] = tmp;
        }
    }

    private static void SelectionSortDecStr(int l, int r, MetaArr[] datasArr2D) {
        int min;
        MetaArr tmp;

        for (int i = l; i < r; ++i) {
            min = i;
            for (int j = i+1; j <= r; ++j)
                if (datasArr2D[j].Arr[0].str.compareTo(datasArr2D[min].Arr[0].str) > 0)
                    min = j;
            tmp = datasArr2D[i];
            datasArr2D[i] = datasArr2D[min];
            datasArr2D[min] = tmp;
        }
    }

    private static int findNextPivstr(int l, int rowsNumber, MetaArr[] datasArr2D) {
        for (int i = l; i < rowsNumber; ++i) {
            if ((byte) datasArr2D[i].Arr[0].str.charAt(0) < 0)
                return i;
        }
        return - 1;
    }

    private static int partitionINCstr(int l, int r, MetaArr[] datasArr2D) {
        String pivot = datasArr2D[(l + r) / 2].Arr[0].str;
        while (l <= r) {
            while ((datasArr2D[r].Arr[0].str.compareTo(pivot) > 0))
                r--;
            while ((datasArr2D[l].Arr[0].str.compareTo(pivot) < 0))
                l++;
            if (l <= r) {
                MetaArr tmp = datasArr2D[r];
                datasArr2D[r] = datasArr2D[l];
                datasArr2D[l] = tmp;
                l++;
                r--;
            }
        }
        return l;
    }

    private static int partitionDECstr(int l, int r, MetaArr[] datasArr2D) {
        String pivot = datasArr2D[(l + r) / 2].Arr[0].str;
        while (l <= r) {
            while ((datasArr2D[r].Arr[0].str.compareTo(pivot) < 0))
                r--;
            while ((datasArr2D[l].Arr[0].str.compareTo(pivot) > 0))
                l++;
            if (l <= r) {
                MetaArr tmp = datasArr2D[r];
                datasArr2D[r] = datasArr2D[l];
                datasArr2D[l] = tmp;
                l++;
                r--;
            }
        }
        return l;
    }
}


/*
// TEST.IN
3
3,1,-1
Album,Year,Songs,Length
Stadium Arcadium,2006,28,122
Unlimited Love,2022,17,73
Californication,1999,15,56
3,2,1
Album,Year,Songs,Length
Stadium Arcadium,2006,28,122afkjlkjagv
Unlimited Love,2022,17,73
Californication,1999,15,56
3,4,-1
Album,Year,Songs,Length
Stadium Arcadium,2006,28,122
Unlimited Love,2022,17,73
Californication,1999,15,56

1
3,2,-1
Album,Nazwa piosenki,Ocena
Monster,Albanski raj,10
Krol Albanii,Ballada o modelkach gownojadach,11
Heavyweight,Bbaran,1

1
10,1,1
OCENA
sdxrg
nwmjl
lwvmw
amyli
dpsxr
ibsao
cdoag
kyezl
ygrmg
rqfng

1
100,1,1
OCENA
UJclf
DChbG
jLDwH
GkbKn
unayI
SQqXA
kAyXn
QKxVn
gwzYZ
aXTwR
haTnV
HufGX
uPASX
lqbGz
eVWVp
RbpHg
xKSnz
bjpmr
nBNZC
yjJfO
JGbXa
LhjrW
HjpXZ
QwxLM
BCnyV
qrVwA
oUzUz
jJkiy
SYduI
OTzhu
pooGm
MGZww
pPnEW
xmBjL
sPuuz
GxKvU
FxcXx
oWmiB
Upqwd
FNrEg
TubyT
rWMOl
RQNFM
HOWPx
bpFjM
pcgoo
AOGQx
cxQtR
eKSxB
VbdkJ
lXVZc
yqgfO
JhoFI
QFocU
muJnr
HznlQ
WLfEx
vepZO
UQNrM
cgZAM
MoknC
yvklv
aqcCi
ERIJP
YzeEz
zugrr
nMghP
KptBw
gLxGH
YJAJR
EuJAI
TkxbW
BNYKo
zdusb
gbyln
QwXbQ
tCHUI
UHevY
UrNaU
CAAbw
Noffg
WMzZb
glvLE
DcXdy
lzRuZ
NLcVG
FVyHm
pMgWT
otMak
WnFhF
JhyLN
ijMBt
EREEj
detfU
IEqgC
GZWuT
MqwYV
VuKvJ
oJwQj
bLAbD
cwudc
vNnEi
BeHWP
Mabhf
SCjzB
BmhWu
GaeQT
RoMba
lpxvU
tcFBf
wrzAR
exzCN
jlqxe
ZVpXM
mpkIC
RFLah
KKKWV
TyPqn
zxwGG
Vncyn
rVjsC

1
100,1,1
OCENA
72
90
22
18
89
95
100
18
99
1
1
7
18
97
77
40
33
86
94
33
90
7
47
88
67
9
72
75
69
64
99
57
35
95
90
75
57
52
93
84
82
46
18
34
8
82
3
15
40
74
99
54
77
69
71
76
60
89
32
37
17
64
41
5
27
5
15
88
33
33
91
19
6
69
42
51
70
54
15
70
1
95
5
49
15
9
85
61
21
72
48
78
14
3
65
19
89
18
29
67

//TEST.OUT
Album,Year,Songs,Length
Unlimited Love,2022,17,73
Stadium Arcadium,2006,28,122
Californication,1999,15,56

Year,Album,Songs,Length
1999,Californication,15,56
2006,Stadium Arcadium,28,122
2022,Unlimited Love,17,73

Length,Album,Year,Songs
122,Stadium Arcadium,2006,28
73,Unlimited Love,2022,17
56,Californication,1999,15

Nazwa piosenki,Album,Ocena
Bbanany,Heavyweight,1
Ballada o modelkach,Krol Albanii,11
Albanski raj,Monster,10


OCENA
amyli
cdoag
dpsxr
ibsao
kyezl
lwvmw
nwmjl
rqfng
sdxrg
ygrmg


OCENA
AOGQx
BCnyV
BNYKo
CAAbw
DChbG
DcXdy
EREEj
ERIJP
EuJAI
FNrEg
FVyHm
FxcXx
GZWuT
GkbKn
GxKvU
HOWPx
HjpXZ
HufGX
HznlQ
IEqgC
JGbXa
JhoFI
JhyLN
KptBw
LhjrW
MGZww
MoknC
MqwYV
NLcVG
Noffg
OTzhu
QFocU
QKxVn
QwXbQ
QwxLM
RQNFM
RbpHg
SQqXA
SYduI
TkxbW
TubyT
UHevY
UJclf
UQNrM
Upqwd
UrNaU
VbdkJ
VuKvJ
WLfEx
WMzZb
WnFhF
YJAJR
YzeEz
aXTwR
aqcCi
bLAbD
bjpmr
bpFjM
cgZAM
cxQtR
detfU
eKSxB
eVWVp
gLxGH
gbyln
glvLE
gwzYZ
haTnV
ijMBt
jJkiy
jLDwH
kAyXn
lXVZc
lqbGz
lzRuZ
muJnr
nBNZC
nMghP
oJwQj
oUzUz
oWmiB
otMak
pMgWT
pPnEW
pcgoo
pooGm
qrVwA
rWMOl
sPuuz
tCHUI
uPASX
unayI
vepZO
xKSnz
xmBjL
yjJfO
yqgfO
yvklv
zdusb
zugrr

OCENA
1
1
1
3
3
5
5
5
6
7
7
8
9
9
14
15
15
15
15
17
18
18
18
18
18
19
19
21
22
27
29
32
33
33
33
33
34
35
37
40
40
41
42
46
47
48
49
51
52
54
54
57
57
60
61
64
64
65
67
67
69
69
69
70
70
71
72
72
72
74
75
75
76
77
77
78
82
82
84
85
86
88
88
89
89
89
90
90
90
91
93
94
95
95
95
97
99
99
99
100






 */