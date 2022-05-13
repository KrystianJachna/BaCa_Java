// Krystian Jachna - 7

import java.util.Scanner;

class Car {                                     // klasa wagon
    public String carName;
    public Car next;                            // referencja na nastpeny wagonik
    public Car previous;                        // referencja na poprzedni wagonik

    public Car(String name) {                   // konstruktor
        carName = name;
    }

}

class Train {                                           // klasa pociag
    public String trainName;
    public Car firstCar;                                // referencja na pierwszy wagonik
    public Train next;                                  // referencja na nastepny pociag

    public Train(String name){                          // konstruktor
        trainName = name;
    }

}

class TrainsList {                                                      // lista pociagow
    public Train firstTrain;                                            // referencja pierwszego pociagu

    public TrainsList() {                                               // konstruktor
        firstTrain = null;
    }

    boolean alreadyExist(String trainName) {                            // sprawdza czy pociag juz istnieje
        Train walkingElem = firstTrain;

        while (walkingElem != null) {                                   // przechodzi po calej liscie
            if (trainName.equals(walkingElem.trainName))                // jesli nazwa pociagu juz istnieje
                return true;                                            // zwraca true
            walkingElem = walkingElem.next;
        }                                                               // w przeciwnym przypadku
        return false;                                                   // zwraca false
    }

    void newTrain (String trainName, String carName) {                  // dodanie nowego pociagu
        if (!alreadyExist(trainName)) {                                 // sprawdzenie czy pociag istnieje, jesli nie
            Train newTrain = new Train(trainName);                      // stworzenie nowego obiektu pociag
            Train prevFirst = firstTrain;                               // zapamietanie wczesniejszego pociagu

            firstTrain = newTrain;                                      // ustawienie pierwszego pociagu na nowo stworzony
            firstTrain.next = prevFirst;                                // wskaznik na kolejny pociag ustawiony na ten poprzedni
            firstTrain.firstCar = new Car(carName);                     // utworzenie nowego wagoniku o zadanej nazwie
            firstTrain.firstCar.next = firstTrain.firstCar;             // poczatkowo wagonik z racji cyklicznosci wskazuje na samego siebie
            firstTrain.firstCar.previous = firstTrain.firstCar;         // poczatkowo wagonik z racji cyklicznosci wskazuje na samego siebie
        }
        else
            System.out.println("Train " + trainName + " already exists");   // w przeciwnym razie wypisanie odpowiedniej informacji
    }

    void InsertFirst(String trainName, String carName) {                // wstawienie na poczatek pociagu wagonika
        Train trainToMod = findTrain(trainName);                        // znaduje zadany pociag

        if (trainToMod != null) {                                           // jesli znajdzie pociag
            Car prevFirstCar = trainToMod.firstCar;                         // zapisanie tymczasowo poprzednegi pierwszego elementu

            trainToMod.firstCar = new Car(carName);                         // ustawienie nowego pierwszego wagoniku
            trainToMod.firstCar.previous = prevFirstCar.previous;           // ustawienei wskaznika previous nowego wagonika na poprzedni poprzedniego wagonika
            trainToMod.firstCar.next = prevFirstCar;                        // ustawienie wskaznika next nowego wagonika na poprzedni wagonik
            prevFirstCar.previous.next = trainToMod.firstCar;               // ustawienie wskaznika ostatniego wagoniku na ten nowy
            prevFirstCar.previous = trainToMod.firstCar;                    // ustawienie wskaznika previus starego wagonika na ten nowy
        }
        else                                                                // w przeciwnym razie wypisuje odpowiednia informacje
            System.out.println("Train " + trainName + " does not exist");
    }

    void InsertLast(String trainName, String carName) {                 // wstawienie na koniec pociagu wagonika
        Train trainToMod = findTrain(trainName);                        // znajduje zadany pociag

        if (trainToMod != null) {
            Car newCar = new Car(carName);                                         // stworzenie nowego wagonika
            newCar.previous = trainToMod.firstCar.previous;                        // ustawia wskaznik ostatniego elementu na poprzedni ostatni
            trainToMod.firstCar.previous.next = newCar;                            // wstawia nowy wagonik na ostatnia pozycje
            trainToMod.firstCar.previous = newCar;                                 // ustawia wskaznik na wagonik poprzedzajacy pierwszy (czyli ostatni) na ten nowy
            newCar.next = trainToMod.firstCar;                                     // ustawia wskaznik ostatniego elementu na pierwszy
        }
        else                                                                       // w przeciwnym razie wypisuje odpowiedni komunikat
            System.out.println("Train " + trainName + " does not exist");
    }

    void Display(String trainName) {                                           // wypisanie wszystkich wagonow pociagu
        Train trainToDisp = findTrain(trainName);                              // szukanie pociagu o zadanej nazwie

        if (trainToDisp != null) {                                             // jesli zostatnie znaleziony...
            Car walkingElem = trainToDisp.firstCar;
            Car prevElem = trainToDisp.firstCar.previous;                      // referencja do poprzedniego elementu z petli
            System.out.print(trainName + ": ");

            if (trainToDisp.firstCar.next.previous == trainToDisp.firstCar) {      // jezli pociag jest w poprawnej kolejnosci
            do {
                System.out.print(walkingElem.carName + " ");                   // wypisuje kolejne wagoniki
                walkingElem = walkingElem.next;
            } while (walkingElem != trainToDisp.firstCar);
            }
            else {                                                              // w przypadku gdy wagoniki sa w zlej kolejnosci
                do {
                    System.out.print(walkingElem.carName + " ");                // wypisanie wagonika

                    if (walkingElem.next == prevElem)                           // w przypadku gdy kolejny element wskazuje na poprzedni trzeba go naprawic
                        walkingElem.next = walkingElem.previous;                // podmiana next na previous
                    walkingElem.previous = prevElem;                            // ustawienie poprzedniego elementu na faktycznie poprzedni
                    prevElem = walkingElem;                                     // zapisanie poprzedniego elementu
                    walkingElem = walkingElem.next;
                } while (walkingElem != trainToDisp.firstCar);
            }
            System.out.println("");
        }
        else                                                                    // w przeciwnym wypadku wypisanie odpowiedniej informacji
            System.out.println("Train " + trainName + " does not exist");
    }

    void DisplayTrains() {                                                      // wypisuje identyfikatory wszystkich pociagow
        Train walkingElem = firstTrain;

        System.out.print("Trains: ");

        while(walkingElem != null) {                                            // przejscie po wszystkich elementach listy pociagow
            System.out.print(walkingElem.trainName + " ");                      // wypisanie nazwy pociagu
            walkingElem = walkingElem.next;
        }
        System.out.println("");
    }

    Train findTrain(String trainName) {                                 // szuka pociagu o zadanej nazwie
        Train walkingElem = firstTrain;

        while (walkingElem != null) {                                   // przechodzi po calej liscie
            if (trainName.equals(walkingElem.trainName))                // jesli znajdzie pociag
                return walkingElem;                                     // zwraca jego adres
            walkingElem = walkingElem.next;
        }
        return null;                                                    // w przeciwnym razie zwraca null
    }

    void deleteTrain(Train trainBefore) {                                                               // usuwa pociag majac referencje do poprzedniego
        if (trainBefore == null)                                                                        // sprawdza czy pociag usuwany nie byl pierwszy na liscie
            firstTrain = firstTrain.next;                                                               // jesli tak to pierwszym elementem jest drugi z kolei
        else
            trainBefore.next = trainBefore.next.next;                                                   // w innym przypadku przesuwa wskaznik na kolejny element o 2 miejsca
    }

    void Union(String t1Name, String t2Name) {                                      // pociag t2 dolacza na koniec pociagu t1 i usuwa t2
        Train t1 = null;                                                            // referencja do t1
        Train t2 = null;                                                            // referencja do t2
        Car tmpCar = null;
        boolean findT2 = false;                                                     // czy t2 udalo sie znalezc
        Train beforeT2 = null;                                                      // referencja do pociagu poprzedzajacego t2
        Train tempTrain = null;                                                     // tymczasowa zmienna do zapisania referencji pociagu poprzedzajacego
        Train walkingElem = firstTrain;

        while (walkingElem != null) {
            if (t1Name.equals(walkingElem.trainName))                               // jesli znajdzie pociag pierwszy
                t1 = walkingElem;                                                   // zapisuje  jego adres

            if (t2Name.equals(walkingElem.trainName)) {                             // jesli nazwa sprawdzanego pociagu jest taka sama jak tego zadanego
                beforeT2 = tempTrain;
                findT2 = true;// zwraca referencje do niego
            }
            tempTrain = walkingElem;
            walkingElem = walkingElem.next;
        }

        if(t1 != null && findT2) {                                                  // jesli oba pociagi zostaly znalezione
            if (beforeT2 == null)                                                   // ustawia wskaznik na drugi pociag
                t2 = firstTrain;
            else
                t2 = beforeT2.next;
                                                                                    // przylacza wagony z drugiego pociagu do pierwszego
            t2.firstCar.previous.next = t1.firstCar;                                // ustawia referencje ostatniego wagonu drugiego pociagu na pierwszhy wagon pierwszego pociagu
            t1.firstCar.previous.next = t2.firstCar;                                // ustawia referencje ostatniego wagonu pierwszego pociagu na pierwszy wagon drugiego pociagu
            tmpCar = t1.firstCar.previous;                                          // zapisuje referencje na ostatni wagon pierwszego pociagu
            t1.firstCar.previous = t2.firstCar.previous;                            // podpina do ostatniego wagonu pierwszego pociagu pierwsy wagon drugiego
            t2.firstCar.previous = tmpCar;                                          // podpina pierwszy wagon drugiego pociagu do ostatniego pierwszego
            deleteTrain(beforeT2);                                                  // usuwa pociag drugi
        }
        else {                                                                      // w innym przypadku wypisuje ktore pociagi nie zostaly znalezione
            if (!findT2)
                System.out.println("Train " + t2Name + " does not exist");
            else
                System.out.println("Train " + t1Name + " does not exist");
        }
    }

    void DeleteFirst(String t1Name, String t2Name) {                                // usuwa pierwszy wagon z pociagu T1 i tworzy z niego nowy pociag

        Train t1 = null;                                                            // referencja do t1
        Train t2 = null;                                                            // referencja do t2
        boolean findT1 = false;                                                     // czy t1 udalo sie znalezc
        boolean findT2 = false;                                                     // czy t2 udalo sie znalezc
        Train beforeT1 = null;                                                      // referencja do pociagu poprzedzajacego t1
        Train tempTrain = null;                                                     // tymczasowa zmienna do zapisania referencji pociagu poprzedzajacego
        String carName = "";
        Train walkingElem = firstTrain;

        while (walkingElem != null) {
            if (t2Name.equals(walkingElem.trainName))                               // jesli znajdzie pociag drugi
                findT2 = true;

            if (t1Name.equals(walkingElem.trainName)) {                             // jesli nazwa sprawdzanego pociagu jest taka sama jak tego zadanego
                beforeT1 = tempTrain;                                               // zapisuje poprzedni wagoink
                findT1 = true;
            }
            tempTrain = walkingElem;
            walkingElem = walkingElem.next;
        }

        if (findT1 && !findT2) {                                                    // jesli istnieje pociag t1 i nie istnieje pociag t2
            if (beforeT1 == null)                                                   // ustawia wskaznik na pierwszy pociag
                t1 = firstTrain;
            else
                t1 = beforeT1.next;

            carName = t1.firstCar.carName;

            if (t1.firstCar.next != t1.firstCar) {                              // jesli wagonik nie jest jedyny
                newTrain(t2Name, carName);     // tworzy nowy pociag
                t1.firstCar.previous.next = t1.firstCar.next;                   // ustawia referencje na pierwszy wagonik z ostatniego na drugi
                t1.firstCar.next.previous = t1.firstCar.previous;               // ustawia referencje drugiego elementu na ostatni
                t1.firstCar = t1.firstCar.next;                                 // ustawia referencje na pierwszy wagonik na drugi
            }
            else {                                                              // w przeciwnym razie
                deleteTrain(beforeT1);                                          // usuwa pociag t1
                newTrain(t2Name, carName);                                      // tworzy nowy pociag
            }

        }
        else {                                                      // w innym przypadku daje odpowiednie komunikaty
            if (findT2)
                System.out.println("Train " + t2Name +  " already exists");
            else {
                System.out.println("Train " + t1Name + " does not exist");
            }
        }
    }

    void DeleteLast(String t1Name, String t2Name) {                  // usuwa ostatni wagon z pociagu T1 i tworzy z niego nowy pociag
        Train t1 = null;                                                            // referencja do t1
        Train t2 = null;                                                            // referencja do t2
        boolean findT1 = false;                                                     // czy t1 udalo sie znalezc
        boolean findT2 = false;                                                     // czy t2 udalo sie znalezc
        Train beforeT1 = null;                                                      // referencja do pociagu poprzedzajacego t1
        Train tempTrain = null;                                                     // tymczasowa zmienna do zapisania referencji pociagu poprzedzajacego
        String carName = "";
        Train walkingElem = firstTrain;

        while (walkingElem != null) {
            if (t2Name.equals(walkingElem.trainName))                               // jesli znajdzie pociag drugi
                findT2 = true;

            if (t1Name.equals(walkingElem.trainName)) {                             // jesli nazwa sprawdzanego pociagu jest taka sama jak tego zadanego
                beforeT1 = tempTrain;                                               // zapisuje adres poprzedzajacego wagonika
                findT1 = true;
            }
            tempTrain = walkingElem;
            walkingElem = walkingElem.next;
        }

        if (findT1 && !findT2) {                                                    // jesli istnieje pociag t1 i nie istnieje pociag t2
            if (beforeT1 == null)                                                   // ustawia wskaznik na pierwszy pociag
                t1 = firstTrain;
            else
                t1 = beforeT1.next;

            carName = t1.firstCar.previous.carName;

            if (t1.firstCar.next != t1.firstCar) {                                  // jesli wagonik nie jest jedyny
                newTrain(t2Name, carName);                                          // tworzy nowy pociag
                t1.firstCar.previous.previous.next = t1.firstCar;                   // ustawia referencje na pierwszy wagonik z ostatniego na drugi
                t1.firstCar.previous = t1.firstCar.previous.previous;               // ustawia referencje drugiego elementu na ostatni
            }
            else {                                                                  // w przeciwnym razie
                deleteTrain(beforeT1);                                              // usuwa pociag t1
                newTrain(t2Name, carName);                                          // tworzy nowy pociag
            }

        }
        else {                                                                      // w innym przypadku daje odpowiednie komunikaty
            if (findT2)
                System.out.println("Train " + t2Name +  " already exists");
            else
                System.out.println("Train " + t1Name + " does not exist");

        }
    }

    void Reverse(String trainName) {
        Train toChange = findTrain(trainName);                                      // szukanie pociagu o zadanej nazwie

        // zamienia miejscami pierwszy i ostatni wagonik
        if (toChange != null) {                                                     // jesli pociag sie znajdzie...
            Car prevFirstCar = toChange.firstCar;

            toChange.firstCar = prevFirstCar.previous;                              // zamiania pierwszy wagonik na ostatni
            toChange.firstCar.next = prevFirstCar.previous.previous;                // ustawia polaczenie do nastepnego wagonika od pierwszego na drugi wagon
            toChange.firstCar.previous = prevFirstCar;                              // ustawia polaczenie od poprzedniego wagonika do poprzedniego na poprzedni piewrszy

            prevFirstCar.previous = prevFirstCar.next;                              // nowy ostatni wagonik ustawia poprzedni na drugi
            prevFirstCar.next = toChange.firstCar;                                  // nastepny na pierwszy
        }
        else                                                                        // jesli nie wyswietla odpowiednie komunikaty
            System.out.println("Train " + trainName + " does not exist");
    }
}

public class Source {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        int z = 0;                                                  // liczba zestawow danych
        int n = 0;                                                  // liczba operacji
        String op = "";                                             // wybor operacji
        String t1 = "";                                             // parametr nazwa pociagu t1
        String t2 = "";                                             // parametr nazwa pociagu t2
        String w = "";                                              // parametr nazwa wagonu

        z = sc.nextInt();

        while (z > 0) {

            TrainsList Trains = new TrainsList();                   // definicja listy pociagow
            n = sc.nextInt();

            while (n > 0) {

                op = sc.next();

                switch (op) {                                       // wybor operacji

                    case "New":
                    {
                        t1 = sc.next();
                        w = sc.next();
                        Trains.newTrain(t1, w);
                        break;
                    }
                    case "InsertFirst":
                    {
                        t1 = sc.next();
                        w = sc.next();
                        Trains.InsertFirst(t1, w);
                        break;
                    }
                    case "InsertLast":
                    {
                        t1 = sc.next();
                        w = sc.next();
                        Trains.InsertLast(t1, w);
                        break;
                    }
                    case "Display":
                    {
                        t1 = sc.next();
                        Trains.Display(t1);
                        break;
                    }
                    case "Trains":
                    {
                        Trains.DisplayTrains();
                        break;
                    }
                    case "Reverse":
                    {
                        t1 = sc.next();
                        Trains.Reverse(t1);
                        break;
                    }
                    case "Union":
                    {
                        t1 = sc.next();
                        t2 = sc.next();
                        Trains.Union(t1, t2);
                        break;
                    }
                    case "DelFirst":
                    {
                        t1 = sc.next();
                        t2 = sc.next();
                        Trains.DeleteFirst(t1, t2);
                        break;
                    }
                    case "DelLast":
                    {
                        t1 = sc.next();
                        t2 = sc.next();
                        Trains.DeleteLast(t1, t2);
                        break;
                    }
                    default:
                        System.out.println("error");
                }
                n--;
            }
            z--;
        }
    }
}

/* TEST.IN
1
65
New T1 W2
InsertLast T1 W3
InsertFirst T1 W1
Display T1
New T2 W6
InsertLast T2 W5
InsertLast T2 W4
Display T2
Reverse T2
Display T2
Trains
Union T1 T2
Trains
Display T1
Display T2
Reverse T1
Display T1
Reverse T1
Display T1
New T2 W7
InsertFirst T2 W8
Reverse T2
Display T2
Union T1 T2
Trains
Display T1
InsertFirst T1 W0
Display T1
InsertLast T1 W9
Display T1
Reverse T1
Display T1
Reverse T1
Display T1
New T3 W10
InsertLast T3 W11
InsertLast T3 W12
InsertLast T3 W13
Union T3 T1
Trains
Display T3
Reverse T3
Display T3
DelFirst T3 T1
DelLast T3 T2
Trains
Display T1
Display T2
Display T3
DelLast T3 T1
DelLast T3 T1
DelLast T3 T1
DelLast T3 T1
DelLast T3 T1
DelLast T3 T1
DelLast T3 T1
DelLast T3 T1
DelLast T3 T1
Display T3
Union T1 T3
Union T2 T1
Trains
Display T2
Reverse T2
Display T2
 */

/* TEST.OUT
T1: W1 W2 W3
T2: W6 W5 W4
T2: W4 W5 W6
Trains: T2 T1
Trains: T1
T1: W1 W2 W3 W4 W5 W6
Train T2 does not exist
T1: W6 W5 W4 W3 W2 W1
T1: W1 W2 W3 W4 W5 W6
T2: W7 W8
Trains: T1
T1: W1 W2 W3 W4 W5 W6 W7 W8
T1: W0 W1 W2 W3 W4 W5 W6 W7 W8
T1: W0 W1 W2 W3 W4 W5 W6 W7 W8 W9
T1: W9 W8 W7 W6 W5 W4 W3 W2 W1 W0
T1: W0 W1 W2 W3 W4 W5 W6 W7 W8 W9
Trains: T3
T3: W10 W11 W12 W13 W0 W1 W2 W3 W4 W5 W6 W7 W8 W9
T3: W9 W8 W7 W6 W5 W4 W3 W2 W1 W0 W13 W12 W11 W10
Trains: T2 T1 T3
T1: W9
T2: W10
T3: W8 W7 W6 W5 W4 W3 W2 W1 W0 W13 W12 W11
Train T1 already exists
Train T1 already exists
Train T1 already exists
Train T1 already exists
Train T1 already exists
Train T1 already exists
Train T1 already exists
Train T1 already exists
Train T1 already exists
T3: W8 W7 W6 W5 W4 W3 W2 W1 W0 W13 W12 W11
Trains: T2
T2: W10 W9 W8 W7 W6 W5 W4 W3 W2 W1 W0 W13 W12 W11
T2: W11 W12 W13 W0 W1 W2 W3 W4 W5 W6 W7 W8 W9 W10
 */