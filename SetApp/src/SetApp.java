import java.util.*;

class SetArray {
    public int [] set;
    private int size;

    public SetArray(int s){     // konstruktor
        set = new int [s];
        size = s;
    }

    public int getSize() {
        return size;
    }       // do pobierania rozmiaru

    public void readSet(Scanner cin) {          // wypelnienie zbioru
        System.out.printf("Podaj " + size + " elementów zbioru: \n") ;
        for (int i = 0; i < size; i++) {
            set[i] = cin.nextInt();
        }
    }

    public boolean find(int elem) {             // sprawdza czy element jest w zbiorze
        for (int i = 0; i < size; i++) {
            if (set[i] == elem)
                return true;
        }
        return false;
    }

    public void display() {                     // wypisanie wszystkich elementow zbiory
        for (int i = 0; i < size; i++) {
            System.out.print(set[i] + " ");
        }
        if (size == 0) {
            System.out.println("Zbior pusty");
        }
        System.out.println("");
    }

    public void setsSum(SetArray s1, SetArray s2) {     // suma zbiorow
        set = new int[s1.size+s2.size];                 // stworzenie nowej tablicy
        int i = 0;
        size = 0;

        for(i = 0; i < s1.size; i++) {                  // przepisuje do nowej tablicy wszystkie elementy pierwszego zbioru
            set[i] = s1.set[i];
        }
        size = s1.size;                                 // ustawiwa rozmiar na rozmiar pierwszego zbioru

        for (int j = 0; j < s2.size; j++) {             // przechodzi po wszystkich elementach drugiego zbiboru
            if(s1.find(s2.set[j]) == false) {           // jesli nie ma w pierwszym zbiorze danego elementu to dodaje do nowego zbioru
                set[i] = s2.set[j];
                i++;
                size++;
                }
        }
    }

    public void setsDiff(SetArray s1, SetArray s2) {
        set = new int[s1.size+s2.size];   // nowa tablica na zbior wynikowy
        int i = 0;
        int j = 0;
        size = 0;

        for(i = 0; i < s1.size; i++) {     // przechodzi po wszystkich elementach pierwszego zbioru
            if(s2.find(s1.set[i]) == false) {           // jesli nie znajdzie elementu w drugim zbiorze to dodaje do trzeciego
                set[j] = s1.set[i];
                j++;
                size++;
                }
        }
    }

    public void commonPart(SetArray s1, SetArray s2) {          // czesc wspolna
        set = new int[s1.size+s2.size];                         // tworzenie nowego zbioru
        size = 0;
        int j = 0;

        for (int i = 0; i < s1.size; i++) {                 // przechodzi po wszystkich elementach pierwszego zbioru
            if(s2.find(s1.set[i]) == true) {                // jesli dany element znajdzie tez w drugim zbiorze
                set[j] = s1.set[i];                         // dodaje do trzeciego
                j++;
                size++;
            }
        }
    }
}



public class SetApp {
    public static void main(String args[])  {
        Scanner cin = new Scanner(System.in);
        int s = 0;
        int o = 0;


        System.out.println("Tworzenie pierwszego zbioru");
        System.out.print("Podaj rozmiar pierwszego zbioru: " );
        s = cin.nextInt();
        SetArray s1 = new SetArray(s);
        s1.readSet(cin);
        System.out.println("Tworzenie drugiego zbioru");
        System.out.print("Podaj rozmiar drugiego zbioru: " );
        s = cin.nextInt();
        SetArray s2 = new SetArray(s);
        s2.readSet(cin);

        SetArray s3;

        do {
            menu();                                     // wyswieltanie menu
            o = cin.nextInt();

            switch (o) {                                // wybor opcji
                case 1: {
                    s3 = new SetArray(s1.getSize()+s2.getSize());
                    s3.setsSum(s1, s2);
                    s3.display();
                    break;

                }
                case 2: {
                    s3 = new SetArray(s1.getSize()+s2.getSize());
                    s3.setsDiff(s1, s2);
                    s3.display();
                    break;
                }
                case 3: {
                    s3 = new SetArray(s1.getSize());
                    s3.commonPart(s1, s2);
                    s3.display();
                    break;
                }
                case 4: {
                    System.out.print("Podaj rozmiar pierwszego zbioru: " );
                    s = cin.nextInt();
                    s1 = new SetArray(s);
                    s1.readSet(cin);
                }
                case 5: {
                    System.out.print("Podaj rozmiar drugiego zbioru: " );
                    s = cin.nextInt();
                    s2 = new SetArray(s);
                    s2.readSet(cin);
                }
                case 0: {      
                    System.out.println("Koniec");
                    break;
                }
                default:        // w przypadku zlego wyboru
                    System.out.println("Zla opcja!");
            }
        }while (o!= 0);




    }
    public static void  menu(){             // wypisanie menu
        System.out.println("\n_____________________________");
        System.out.println(" Operacje ");
        System.out.println("_______________________________");
        System.out.println(" 1. Suma zbiorów");
        System.out.println(" 2. Roznica zbiorow");
        System.out.println(" 3. Iloraz zbiorów");
        System.out.println(" 4. Zmień zawartość pierwszego zbioru");
        System.out.println(" 5. Zmień zawartość drugiego zbioru");
        System.out.println(" 0. KONIEC");
        System.out.println("===============================");
    }
}
