import java.util.Scanner;



class Ob {                      // klasa przechowujaca liczbe porownan
    public int compares = 0;
}

class Table {
    // pola klasy
    public int[] a = new int [100];                 // referencja do wektora
    public int Size = 100;                          // dlugosc wektora

    // metody klasy

    public void copy(Table tabToCopy) {                            // kopiowanie tablicy
        for (int i = 0; i < Size; i++) {
            a[i] = tabToCopy.a[i];
        }
    }

    public void rand_vectorIncrising() {           // pseudolosowanie wektora niemalejacego
        a[0] = (int) (java.lang.Math.random() * (10 - 0) + 0);              // losowanie liczby z przedzialu 0 - 10 dla pierwszego elementu
        for (int i = 1; i < Size; i++)
            a[i] = (int) (java.lang.Math.random() * (10 - 0) + 0) + a[i-1]; // dodanie do kazdego kolejnego elementu - element poprzedni i liczby z zakresu 0 - 10
    }

    public void rand_vectorDecresing() {        // pseudolosowanie wektora nierosnacego (metoda taka sama jak poprzednia tylko iterujaca od konca tablicy)
        a[Size-1] = (int) (java.lang.Math.random() * (10 - 0) + 0);         // losowanie liczby z przedzialu 0 - 10 dla ostatniego elementu
        for (int i = Size-2; i >= 0; i--)
            a[i] = (int) (java.lang.Math.random() * (10 - 0) + 0) + a[i+1]; // dodanie do kazdego poprzedniego elementu nastepnego i liczby z zakresu 0 - 10
    }

    public void display()  {                // wypisywanie wektora
        for (int i=0; i< Size; i++ )
        {
            System.out.printf("%5s", a[i] + ",");             // wypisanie tablicy po 20 elementow o odpowiedniej szerokosci
            if((i+1)%20==0)
                System.out.println("") ;
        }
        System.out.println("") ;
    }

    public void bubbleSort() {          // sortowanie babelkowe
        int temp = 0;
        int compares = 0;               // liczba porownan
        int shifts = 0;                 // liczba przesuniec

        System.out.println("Metoda sortowania: BABELKOWE");

        for (int i = 0; i < Size; i++) {
            for (int j = 0; j < Size - 1; j++) {
                compares++;
                if(a[j] > a[j+1]) {             // swap gdy elementy w zlej kolejnosci
                    shifts++;
                    temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                }
            }
        }

        System.out.println("Tablica po uporzadkowaniu: ");
        display();
        System.out.printf("Dlugosc tablicy: " + Size + ", zlozonosc: " + Size*Size + "\n");
        System.out.printf("Liczba porownan: " +  compares + "\n");
        System.out.printf("Liczba przesuniec: " +  shifts + "\n\n");
    }

    public void selectionSort() {
        int compares = 0;               // liczba porownan
        int shifts = 0;                 // liczba przesuniec
        int min = 0;                    // indeks wartosci minimalnej
        int temp = 0;

        System.out.println("Metoda sortowania: PROSTEGO WYBORU");

        for (int i = 0; i < Size - 1; i++) {
            min = i;                            // indeks wartosci minimalnej
            for (int j = i + 1; j < Size; j++) {
                compares++;
                if (a[j] < a[min]) {            // gdy a[j] mniejsze, mamy nowe minimum
                    min = j;
                }
            }
            temp = a[i];                // zamiana elementow minimalnego i obecnie sprawdzanego
            a[i] = a[min];
            a[min] = temp;
            shifts++;
        }
        System.out.println("Tablica po uporzadkowaniu: ");
        display();
        System.out.printf("Dlugosc tablicy: " + Size + ", zlozonosc: " + Size*Size + "\n");
        System.out.printf("Liczba porownan: " +  compares + "\n");
        System.out.printf("Liczba przesuniec: " +  shifts + "\n\n");
    }

    public void insertionSort() {
        int compares = 0;               // liczba porownan
        int shifts = 0;                 // liczba przesuniec
        int temp = 0;
        int j = 0;

        System.out.println("Metoda sortowania: PRZEZ WSTAWIANIE");

        for (int i = 1; i < Size; i++) {        // petla zewnetrzna, gdzie i to pierwszy nieposortowany element
            temp = a[i];
            j = i - 1;                          // zaczynamy od elementu o 1 mniejszego od i
            compares++;
            while (j >= 0 && temp < a[j]) {     // dopoki nie wyszlismy poza poczatek tablicy i temp jest mniejszy od a[j]
                a[j+1] = a[j];                  // przesuwany a[j] element w prawo
                j--;                            // sprawdzenie poprzedniego elementu
                shifts++;
            }
            a[j+1] = temp;                      // wstawianie wyroznionego elementu po a[j]
        }
        System.out.println("Tablica po uporzadkowaniu: ");
        display();
        System.out.printf("Dlugosc tablicy: " + Size + ", zlozonosc: " + Size*Size + "\n");
        System.out.printf("Liczba porownan: " +  compares + "\n");
        System.out.printf("Liczba przesuniec: " +  shifts + "\n\n");
    }

    public void CocktailSort() {
        boolean swapped = true;             // flaga sprawdzajaca czy doszklo do wymiany w danej iteracji
        int start = 0;
        int end = Size - 1;
        int compares = 0;               // liczba porownan
        int shifts = 0;                 // liczba przesuniec
        int temp = 0;

        System.out.println("Metoda sortowania: KOKTAJLOWE");

        while(swapped == true) {            // dopoki tablica nie jest jeszcze posortowana
            swapped = false;                // wyzerowanie flagi

            for (int i = start; i < end; i++) { // podobnie jak w buublesort petla od lewej do prawej
                compares++;
                if(a[i] > a[i+1]) {             // jesli nie posortowane to zamien
                    shifts++;
                    swapped = true;             // doszlo do wymiany
                    temp = a[i];
                    a[i] = a[i+1];
                    a[i+1] = temp;
                }
            }



            if (swapped = false) {              // jesli nie doszlo do wymiany to koniec
                break;
            }


            // jednak ustawic na ostatnia wartosc gdzie bylo przesuniecie


            for (int i = end - 1; i > start; i--) {    // od prawej do lewej porownujac jak poprzednio
                compares++;
                if(a[i-1] > a[i]) {             // jesli nie posortowane to zamien
                    shifts++;
                    swapped = true;             // doszlo do wymiany
                    temp = a[i];
                    a[i] = a[i - 1];
                    a[i - 1] = temp;
                }
            }


            // tak samo ze startem jak z endem
            start++;                            // przesuniecie poczatku poniewaz po ostatniej petli jest on na dobrej pozycji

        }
        System.out.println("Tablica po uporzadkowaniu: ");
        display();
        System.out.printf("Dlugosc tablicy: " + Size + ", zlozonosc: " + Size*Size + "\n");
        System.out.printf("Liczba porownan: " +  compares + "\n");
        System.out.printf("Liczba przesuniec: " +  shifts + "\n\n");
    }

    public void stableSelectionSort() {
        int compares = 0;               // liczba porownan
        int shifts = 0;                 // liczba przesuniec
        int min = 0;                    // indeks wartosci minimalnej
        int temp = 0;

        System.out.println("Metoda sortowania: PROSTEGO WYBORU STABILNA WERSJA");

        for (int i = 0; i < Size - 1; i++) {
            min = i;                            // indeks wartosci minimalnej
            for (int j = i + 1; j < Size; j++) {
                compares++;
                if (a[j] < a[min]) {            // gdy a[j] mniejsze, mamy nowe minimum
                    min = j;
                }
            }

            temp = a[min];
            while (min > i) {               // przesuniecie elementu minimalnego na i
                shifts++;
                a[min] = a[min-1];
                min--;
            }
            a[i] = temp;

        }
        System.out.println("Tablica po uporzadkowaniu: ");
        display();
        System.out.printf("Dlugosc tablicy: " + Size + ", zlozonosc: " + Size*Size + "\n");
        System.out.printf("Liczba porownan: " +  compares + "\n");
        System.out.printf("Liczba przesuniec: " +  shifts + "\n\n");
    }

    public void binaryInstertionSort() {
        int shifts = 0;                 // liczba przesuniec
        int temp = 0;
        int j = 0;
        int place = 0;                  // lokalizacja danego elementu
        Ob compares = new Ob();         // liczba porownan

        for (int i = 1; i < Size; i++) {
            temp = a[i];
            j = i - 1;

            place = binarySearch(temp, j, compares);

            while (j >= place) {            // zrobienie miejsca na element
                shifts++;
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = temp;                // wstawienie elementu

        }
        System.out.println("Tablica po uporzadkowaniu: ");
        display();
        System.out.printf("Dlugosc tablicy: " + Size + ", zlozonosc: " + Size*Size + "\n");
        System.out.printf("Liczba porownan: " +  compares.compares + "\n");
        System.out.printf("Liczba przesuniec: " +  shifts + "\n\n");

    }

    private int binarySearch(int elem, int rb, Ob copmares) {
        int lb = 0;         // lewa granica
        int mid = 0;

        while (lb <= rb) {
            copmares.compares++;
            mid = lb + (rb - lb) / 2;

            if (elem == a[mid])
                return mid + 1;
            else if (elem > a[mid])
                lb = mid + 1;
            else
                rb = mid - 1;
        }

        return lb;

    }


}

public class SimpleSortApp {
    public static void main(String args[]) {
        int choice = 0;                                 // do nawigacji po menu
        Scanner cin = new Scanner(System.in);
        Table TabInc = new Table();                     // stworzenie tablicy niemalejacej
        Table TabDec = new Table();                     // stworzenie tablicy nierosnacej
        TabInc.rand_vectorIncrising();                  // wylosowanie elementow tablicy w kolejnosci niemalejacej
        TabDec.rand_vectorDecresing();                  // wylosowanie elementow tablicy w kolejnosci nierosnacej

        System.out.println("Pseudolosowa tablica uporzadkowana niemalejaco: ");
        TabInc.display();                                           // wypisanie tablicy uporzadkowanej niemalejaco
        System.out.println("Pseudolosowa tablica uporzadkowana nierosnaco: ");
        TabDec.display();

        do {
            menu();

            Table TabIncCp = new Table();           // referencja do tablicy niemalejacej
            TabIncCp.copy(TabInc);                  // kopiowanie wyslosowanej tablicy
            Table TabDecCp = new Table();           // referencja do tablicy nierosnacej
            TabDecCp.copy(TabDec);                  // kopiowanie wyslosowanej tablicy

            System.out.print("Wbierz opcje: ");
            choice = cin.nextInt();

            switch (choice) {
                case 1:
                {
                    System.out.println("\n-------------------Dla tablicy uporzadkowanej nierosnaco-------------------\n");
                    TabDecCp.bubbleSort();
                    System.out.println("\n-------------------Dla tablicy uporzadkowanej niemalejaco-------------------\n");
                    TabIncCp.bubbleSort();
                    break;
                }
                case 2:
                {
                    System.out.println("\n-------------------Dla tablicy uporzadkowanej nierosnaco-------------------\n");
                    TabDecCp.selectionSort();
                    System.out.println("\n-------------------Dla tablicy uporzadkowanej niemalejaco-------------------\n");
                    TabIncCp.selectionSort();
                    break;
                }
                case 3:
                {
                    System.out.println("\n-------------------Dla tablicy uporzadkowanej nierosnaco-------------------\n");
                    TabDecCp.insertionSort();
                    System.out.println("\n-------------------Dla tablicy uporzadkowanej niemalejaco-------------------\n");
                    TabIncCp.insertionSort();
                    break;
                }
                case 4: {
                    System.out.println("\n-------------------Dla tablicy uporzadkowanej nierosnaco-------------------\n");
                    TabDecCp.CocktailSort();
                    System.out.println("\n-------------------Dla tablicy uporzadkowanej niemalejaco-------------------\n");
                    TabIncCp.CocktailSort();
                    break;
                }
                case 5:
                {
                    System.out.println("\n-------------------Dla tablicy uporzadkowanej nierosnaco-------------------\n");
                    TabDecCp.stableSelectionSort();
                    System.out.println("\n-------------------Dla tablicy uporzadkowanej niemalejaco-------------------\n");
                    TabIncCp.stableSelectionSort();
                    break;
                }
                case 6:
                {
                    System.out.println("\n-------------------Dla tablicy uporzadkowanej nierosnaco-------------------\n");
                    TabDecCp.binaryInstertionSort();
                    System.out.println("\n-------------------Dla tablicy uporzadkowanej niemalejaco-------------------\n");
                    TabIncCp.binaryInstertionSort();
                    break;
                }
                case 0:
                {
                    System.out.println("KONIEC");
                    break;
                }
                default : System.out.println("Zla opcja!");
            }
        }while(choice != 0);
    }



    public static void menu(){
        System.out.println("_____________Menu______________");
        System.out.println(" Operacje: ");
        System.out.println("_______________________________");
        System.out.println(" 1. Sortowanie bąbelkowe ");
        System.out.println(" 2. Sortowanie przez prosty wybor ");
        System.out.println(" 3. Sortowanie przez proste wstawianie ");
        System.out.println(" 4. Sortowanie koktajlowe ");
        System.out.println(" 5. Sortowanie przez prosty wybor STABINLNY ");
        System.out.println(" 6. Sortowanie przez wstawianie z WYSZUKIWANIEM BINARNYM ");
        System.out.println(" 0. KONIEC ");
        System.out.println("_______________________________");
    }
}















