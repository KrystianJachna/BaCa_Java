// Krystian Jachna - 7
/* Poczatkowo program po odczytaniu liczby zestawow danych wchodzi w petle w ktorej czyta ktora konwersje powinien wykonac oraz
czyta wyrazenie. Nastepnie w zaleznosci ktora operacje wybierze uzytkownik, poprawia wyrazenie do odpowiedniej postaci. To znaczy
miedzy innymi ze usuwa biale znaki i nieporzadane sybole takie jak ',','.' itd... Po wybraniu konwersji INF -> ONP algorytm
przechodzi przez wszystkie litery wyrazenia. W zaleznosci od tego na co natrafi wykonuje odpowiednie operacje tak jak w przy-
kladzie prezentowanym na wykladzie. Dodatkowo obslugiwany jest automat ktory sprawdza poprawnosc wejscia, liczba nawiasow i ich
kolejnosc.  Po wybraniu konwersji ONP -> INF nastepuje kolejne wczytywanie elementow wejscia i obsluga stosu
w przypadku odczytania operanda kladzie na stos, z kolei dla operatora sprawdza poczatkowo arnoscoperatora, w przypadku
operatorow unarnych, sciaga z stosu jedna wartosc. Jesli operator jest prawostronnie laczny dodaje nawiasy. Natomiast
gdy operator jest binarny zbiera ze stosu dwa wyrazenia prawe i lewe. Mozliwe jest kilka przypadkow gdy trzeba dodac nawiasy
gdy operator laczacy lewe wyrazenie jest mniejszy niz ten obecny trzeba dodac nawiasy do wyrazenia pierwszego, gdy
operator laczacy prawe wyrazenie jest mniejszy niz obecny trzeba dodac nawiasy do prawego wyrazenia. Lub gdy prawy i lewy
sa mniejsze badz rowne priorytetowi operatora obecnego, to drugie wyrazenie z racji wykonywania jako pierwsze bedzie wymagalo
nawiasow. Program dziala az do konca elementow wejsciowych. Po czym gdy nie wystapily bledy przy konwersji zwraca
skonwertowane wyrazenie.
*/
import java.util.Scanner;

class Result {                                          // klasa przechowujaca operator jaki scalil ostatnie dwa wyrazenia i samo wyrazenie
    public char oper;
    public String expr;

    public Result(char operc, String exprc) {           // konstruktor
        oper = operc;
        expr = exprc;
    }
}

class StackofResults {
    private int maxSize;                           // rozmiar tablicy zawierajacej stos
    private Result[] tab;                          // tablica zawierajaca wyniki poszczegolnych operacji
    private int top;                               // indeks szczytu stosu

    public StackofResults(int size) {                        // konstruktor
        maxSize = size;
        tab = new Result[size];
        for (int i = 0; i < size; i++)
            tab[i] = new Result(' ', "");

        top = -1;
    }

    public void push(String exprc, char operc) {                   // wstawia element na szczyt stosu
        if(top < (maxSize-1)) {                 // sprawdza czy jest miejsce
            top++;
            tab[top].expr = exprc;
            tab[top].oper = operc;
        }
    }

    public Result pop() {                             // zwraca i usuwa element ze szczytu stosu
        if (top < 0)                                  // sprawdza czy jest jakis element
            return new Result(' ',"");
        else
            return tab[top--];
    }

    public Result top() {                             // sprawdza wartosc na szczycie stosu
        if (top < 0)
            return new Result(' ', "");
        else
            return tab[top];
    }

    public boolean isEmpty() {                      // sprawdza czy stos jest pusty
        return (top == -1);
    }
}


class Stack {                                      // klasa stos
    private int maxSize;                           // rozmiar tablicy zawierajacej stos
    private char[] tab;                            // tablica zawierajaca stos
    private int top;                               // indeks szczytu stosu

    public Stack(int size) {                        // konstruktor
        maxSize = size;
        tab = new char[size];
        top = -1;
    }

    public void push(char elem) {                   // wstawia element na szczyt stosu
        if(top < (maxSize-1))                       // sprawdza czy jest miejsce
            tab[++top] = elem;
    }

    public char pop() {                             // zwraca i usuwa element ze szczytu stosu
        if (top < 0)                                // sprawdza czy jest jakis element
            return ' ';
        else
            return tab[top--];

    }

    public char top() {                             // sprawdza wartosc na szczycie stosu
        if (top < 0)
            return '0';
        else
            return tab[top];
    }

    public boolean isEmpty() {                      // sprawdza czy stos jest pusty
        return (top == -1);
    }
}

public class Source {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int sets  = 0;                      // liczba zestawow
        String expression = new String();   // wyrazenie
        String opperation = new String();   // operacja ONP lub INF
        char[] operatorsINF =
                {'(', ')', '!', '~', '^', '*', '/', '%', '+',
                        '-', '<', '>', '?', '&', '|', '='};         // mozliwe operatory dla INF
        char[] operatorsRPN =
                {'!', '~', '^', '*', '/', '%', '+',
                        '-', '<', '>', '?', '&', '|', '='};         // mozliwe operatory dla ONP
        char[] leftOperators =
                {'*', '/', '%', '+', '-', '<', '>', '?', '&', '|'}; // operatory lewostronne
        char[] rightOperators =
                {'!', '~', '^', '='};                               // operatory prawostronne
        char[] symbols =
                {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                        'o', 'p', 'q', 'r', 's', 't', 'u' ,'v', 'w', 'x', 'y', 'z'};    // mozliwe symbole (alfabet angielski)
        char [] unaryOperators = { '~' , '!'};                          // operatory jednoargumentowe
        char [] binaryOperators = { '^', '*', '/', '%', '+', '-', '<', '>', '?', '&', '|', '='};    // operatory dwuargumentowe

        sets = sc.nextInt();

        while(sets > 0) {                   // petla obslugujace pojedyncze polecenie (pojedyncza linie)
            opperation = sc.next();         // czytanie operacji
            expression = sc.nextLine();     // czytanie wyrazenia

            switch (opperation) {           // w zaleznosci operacji
                case "ONP:":                    // obsluga ONP
                    expression = deleteWhiteChars(operatorsRPN, symbols, expression);   // korekta wyrazenia
                    expression = RPNtoINF(expression, symbols, unaryOperators, rightOperators); // konwersja
                    displayRPN(expression);         // wyswietlanie skonwertowanego wyrazenia
                    break;
                case "INF:":                    // obsluga INF
                    expression = deleteWhiteChars(operatorsINF, symbols, expression);   // korekta wyrazenia
                    expression = INFtoRPN(expression, symbols, leftOperators, operatorsRPN, rightOperators);   // konwersja
                    displayONP(expression);     // wyswietlenie skonwertowanego wyrazenia
                    break;
            }
            sets--;
        }
        sc.close();
    }

    public static boolean isProperChar(char toCheck, char[] operators, char [] symbols) {       // sprawdzanie czy znak jest poprawny
        for (char ch : operators)                                                               // przejscie po wszystkich dostepnych operatorach
            if (ch == toCheck)
                return true;
        for (char ch : symbols)                                                                 // przejscie po wszystkich symbolach
            if (ch == toCheck)
                return true;
        return false;
        /*
            Przechodzi po wszystkich dostepnych operatorach i sybolach,
            w momencie gdy sprawdzany znak jest jednym z dostepnych
            zwraca prawde, w przeciwnym przypadku zwraca falsz.
        */
    }

    public static String deleteWhiteChars(char[] operators, char[] symbols, String expression) {    // usuwanie nieporzadanych znakow
        char temp = 0;

        for(int i = 0; i < expression.length(); ++i) {                                              // przejscie przez wszystkie elementy stringu
            temp = expression.charAt(i);
            if (isProperChar(temp, operators, symbols) == false) {                                  // jesli nie czytany znak nie zostatnie znalezionych w dozwolonych znakach
                expression = expression.substring(0, i) + expression.substring(i + 1);              // usuniety zostaje element nieporzadany, przez sklejenie stringu przed i po nim
                i--;
            }

        }
        return expression;
        /*
        Przechodzi po wszystkich znakach w wyrazeniu. Nastepnie
        sprawda czy dany znak jest jednym z dostepnych.
        Gdyby sie okazalo, ze nie jest nowy String zostaje sklejony
        z tego przed znakiem i za znakiem ktory byl nieprawidlowy.
        Celem jest pozbyciem sie bialych znakow i ',' '.' itd...
     */
    }

    public static int getPriority(char ch) {            // zwraca liczbe tym wyzsza im wyzszy jest priorytet operatora
        if (ch == '(' || ch == ')')
            return 10;
        else if (ch ==  '!' || ch == '~')
            return 9;
        else if (ch == '^')
            return 8;
        else if (ch == '*' || ch == '/' || ch == '%')
            return 7;
        else if (ch == '+' || ch == '-')
            return 6;
        else if (ch == '<' || ch == '>')
            return 5;
        else if (ch == '?')
            return 4;
        else if (ch == '&')
            return 3;
        else if (ch == '|')
            return 2;
        else if (ch == '=')
            return 1;
        else return 0;

        /*
            Ustala priorytet operatorow wedle wytycznych do zadania
         */
    }

    public static boolean isOperand (char toCheck, char [] symbols) {       // sprawdza czy jest operandem
        for (char ch : symbols)
            if (toCheck == ch)
                return true;
        return false;
        /*
            Sprawdza czy dany znak jest operandem. Przechodzac przez wszystkie operandy
         */
    }

    public static boolean isOperator (char toCheck, char [] operators) {       // sprawdza czy jest operandem
        for (char ch : operators)
            if (toCheck == ch)
                return true;
        return false;
        /*
            Sprawdza czy dany znak jest operatorem, przechodzacc przez wszystkie operandy.
         */
    }

    public static boolean rightOperator (char toCheck, char [] rightOperators) {       // sprawdza czy jest operatorem prawostronnym
        for (char ch : rightOperators)
            if (toCheck == ch)
                return true;
        return false;
        /*
            Sprawdza czy dany znak jest prawostronnie laczny, przechodzac przez wszystkie prawostronnie laczne operatory.
         */
    }

    public static boolean leftOperator (char toCheck, char [] leftOperators) {       // sprawdza czy jest operatorem lewostronnym
        for (char ch : leftOperators)
            if (toCheck == ch)
                return true;
        return false;
        /*
            Sprawdza czy dany znak jest lewostronnie laczny, przechodzac przez wszystkie lewostronnie laczne operatory.
         */
    }

    public static int automatState(int currentState, int symbol) {
        switch (currentState) {
            case 0:
            case 2:
                if (symbol == 5)
                    return -1;
                else if (symbol == 4)
                    return 0;
                else if (symbol == 3)
                    return -1;
                else if (symbol == 2)
                    return 2;
                else if (symbol == 1)
                    return 1;
                break;
            case 1:
                if (symbol == 5)
                    return 1;
                else if (symbol == 4)
                    return -1;
                else if (symbol == 3)
                    return 0;
                else if (symbol == 2)
                    return -1;
                else if (symbol == 1)
                    return -1;
                break;
        }
        return -1;
        /*
            do obslugi automatu sybole:
            operand = 1
            operator jednoargumentowy = 2
            operator dwuargumentowy = 3
            nawias ( = 4
            nawias ) = 5

            Obsluguje maszyne przedstawiona w tresci zadania, znajac jej stan oraz symbol na jaki trafilismy, zwraca
            stan w ktorym bedzie sie znajdowala maszyna po przeczytaniu danego elementu
     */

    }

    public static String INFtoRPN(String expression, char []symbols, char []leftOperators, char []operators, char[]rightOperators) {        // zamiana na ONP
        int automat = 0;                                                    // do sterowania automatem poczatkowy stan 0
        String RPNexp = new String();                                       // string wyjsciowy
        Stack stack = new Stack(expression.length());                       // stos
        int bracketsNumber = 0;                                             // do sprawdzania liczby i parowania nawiasow
        int operandNumber = 0;                                              // do sprawdzenia liczby operandow
        int operatorNumber = 0;                                             // do sprawdzenia liczby operandow


        for(int i = 0; i < expression.length(); ++i) {                      // przechodzi przez wszystkie znaki wyrazenia
            char ch = expression.charAt(i);

            if (isOperand(ch, symbols) == true) {                   // jesli jest operandem
                automat = automatState(automat, 1);          // zmiana stanu automatu
                RPNexp += ' ';
                RPNexp += ch;
                operandNumber++;
            }
            else if (ch == '(') {
                automat = automatState(automat, 4);
                stack.push(ch);                                     // dopisanie do stosu
                bracketsNumber++;
            }
            else if (leftOperator(ch, leftOperators) == true) {     // jesli jest operatorem lewostronnym
                automat = automatState(automat, 3);
                while (getPriority(ch) <= getPriority(stack.top()) && isOperator(stack.top(), operators) && stack.isEmpty() != true) {            // sciagam ze stostu wszystkie operatory o priorytecie wiekszym lub rownym
                    RPNexp += ' ';
                    RPNexp += stack.pop();                          // dopisuje na wyjscie
                }
                stack.push(ch);                                     // dopisuje na stos operaotr
                operatorNumber++;
            }
            else if (rightOperator(ch, rightOperators) == true) {   // jest operatorem lewostronnym
                if (ch == '!' || ch == '~')                         // obsluga automatu
                    automat = automatState(automat, 2);
                else {
                    automat = automatState(automat, 3);
                    operatorNumber++;
                }
                while (getPriority(ch) < getPriority(stack.top()) && isOperator(stack.top(), operators) && stack.isEmpty() != true) {            // sciagam ze stostu wszystkie operatory o priorytecie wiekszym
                    RPNexp += ' ';
                    RPNexp += stack.pop();                          // dopisuje na wyjscie
                }
                stack.push(ch);                                     // dopisuje na stos operaotr
            }
            else if (ch == ')') {
                automat = automatState(automat, 5);
                while (stack.isEmpty() != true && stack.top() != '(') {            // sciagam ze stostu wszystkie operatory do )
                    RPNexp += ' ';
                    RPNexp += stack.pop();                          // dopisuje na wyjscie
                }
                stack.pop();
                bracketsNumber--;
                if (bracketsNumber < 0)                             // sprawdzenie kolejnosci nawiasow
                    break;
            }
        }
        while (stack.isEmpty() == false) {
            RPNexp += ' ';
            RPNexp += stack.pop();
        }

        if (automat == 1 && bracketsNumber == 0 && operandNumber == (operatorNumber + 1))
            return RPNexp;
        else
            return "";


        /*
            Algorytm przechodzi przez wszystkie litery wyrazenia. W zaleznosci
            od tego na co natrafi wykonuje odpowiednie operacje tak jak w przy-
            kladzie prezentowanym na wykladzie. Dodatkowo obslugiwany jest aut-
            omat ktory sprawdza poprawnosc wejscia, liczba nawiasow i ich kole-
            jnosc.
         */
        /*
        do obslugi automatu sybole:
            operand = 1
            operator jednoargumentowy = 2
            operator dwuargumentowy = 3
            nawias ( = 4
            nawias ) = 5
     */
    }

    public static void displayONP(String expression) {
        if(expression.length() != 0)
            System.out.print("ONP:" + expression + "\n");
        else
            System.out.println("ONP: error");
        /*
        Wyswietla skonwertowane wyrazenie w odpowiedni sposob lub informacje o bledzie
        */
    }

    public static String RPNtoINF(String expression, char []symbols, char []unaryOperators, char []rightOperators) {
        StackofResults stack = new StackofResults(expression.length());     // stos do przechowywania wyrazen wraz ze znakiem ktore je polaczyl
        Result tmpResult = new Result(' ', "");
        Result tmpHigherResult = new Result(' ', "");
        String tmpString = new String();
        boolean flag = true;                                                // flaga sprawdzajaca poprawnosc wejscia


        for (int i = 0; i < expression.length(); ++i) {                 // ide po wszystkich elementach wyrazenia
            char ch = expression.charAt(i);

            if (isOperand(ch, symbols) == true) {                      // operandy wrzuca na stos
                stack.push(Character.toString(ch), ' ');
            }
            else {
                if (ch == '~' || ch == '!') {                           // obsluga operatorow unarnych
                    if (stack.isEmpty() == false) {                     // sprawdza poprawnosc wejscia
                        tmpResult = stack.pop();
                        if (isOperand(tmpResult.oper, rightOperators) == true || tmpResult.oper == ' ')   // jesli operator jest prawostronny bez nawiasow
                            tmpResult.expr =   ch + " " + tmpResult.expr;
                        else
                            tmpResult.expr = ch + " " + '(' + tmpResult.expr + ')';                       // z nawiasami
                        stack.push(tmpResult.expr, ch);                                                   // wrzuca wyrazenie na stos

                    } else {
                        flag = false;
                        break;
                    }
                }
                else {
                    if (stack.isEmpty() == false) {                                                         // sprawdza poprawnosc wejscia
                        tmpHigherResult = stack.pop();
                        if(stack.isEmpty() == false) {                                                      // sprawdza poprawnosc wejscia
                            tmpResult = stack.pop();

                            if (getPriority(tmpResult.oper) < getPriority(ch) && getPriority(tmpResult.oper) != 0) {        // gdy priotytet wyrazenia lewego jest nizszy i nie jest to pojedynczy symbol
                                tmpResult.expr = '(' + tmpResult.expr + ')';                                                // dodaje do wyrazenia nawiasy
                            }
                            if ((getPriority(tmpHigherResult.oper) < getPriority(ch)) && getPriority(tmpHigherResult.oper) != 0 || // gdy priorytet wyrazenia prawego jest nizszy i wyrazenie nie jest pojedynczym symbolem lub
                                    (getPriority(tmpHigherResult.oper) == getPriority(ch) && ch != '=' && ch!= '^')                     // priorytet wyrazenia prawego jest rowny lub wiekszy i znak rozny od = i &&
                                            &&  tmpHigherResult.oper != ' ') {         // i != 0
                                tmpHigherResult.expr = '(' + tmpHigherResult.expr + ')';                // dodaje nawiasy
                            }
                            tmpString =  " " + tmpResult.expr + " " + ch + " " + tmpHigherResult.expr + " ";    // skleja wyrazenie
                            stack.push(tmpString, ch);          // wrzuca na stos
                        }
                        else {
                            flag = false;
                            break;
                        }
                    }
                    else {
                        flag = false;
                        break;
                    }
                }


            }
        }

        tmpResult = stack.pop();            // czyta ze stosu
        tmpString = tmpResult.expr;

        if (flag == true && stack.isEmpty() == true)        // jest nie wystapil blad i stos zostal oprozniony
            return tmpString;                               // zwraca skonwertowane wyrazenie
        else
            return "";
        /*
             Kolejne wczytywanie elementow wejscia i obsluga stosu (wyrazen i operatora ktore je polaczylo)
             w przypadku odczytania operanda kladzie na stos, z kolei dla operatora sprawdza poczatkowo arnosc
             operatora, w przypadku operatorow unarnych, sciaga z stosu jedna wartosc. Jesli
             operator jest prawostronnie laczny dodaje nawiasy. Natomiast gdy operator jest binarny zbiera
             ze stosu dwa wyrazenia prawe i lewe. Mozliwe jest kilka przypadkow gdy trzeba dodac nawiasy
             gdy operator laczacy lewe wyrazenie jest mniejszy niz ten obecny trzeba dodac nawiasy do
             wyrazenia pierwszego, gdy operator laczacy prawe wyrazenie jest mniejszy niz obecny trzeba
             dodac nawiasy do prawego wyrazenia. Lub gdy prawy i lewy sa mniejsze badz rowne priorytetowi
             operatora obecnego, to drugie wyrazenie z racji wykonywania jako pierwsze bedzie wymagalo nawiasow.
             Program dziala az do konca elementow wejsciowych. Po czym gdy nie wystapily bledy przy konwersji
             zwraca skonwertowane wyrazenie.
         */
    }

    public static void displayRPN (String expression) {

        if(expression.length() != 0) {
            expression = " " + expression;
            while (expression.contains("  ") == true)
                expression = expression.replaceAll("  ", " "); // usuwanie ostatniej spacji
            System.out.print("INF:" + expression + "\n");
        }
        else
            System.out.println("INF: error");
        /*
            Wyswietla skonwertowane wyrazenie w odpowiedni sposob lub informacje o bledzie
        */
    }
}

/*
============= test0.in =============
Te prezentowane w tresci zadania
51
ONP: xabc**=
ONP: ab+a~a-+
INF: a+b+(~a-a)
INF: x=~~a+b*c
INF: t=~a<x<~b
INF: ( a,+ b)/..[c3
ONP: ( a,b,.).c;-,*
ONP: abc++def++g+++
INF: x=a=b=c
ONP: xabc===
INF: a+b*(c^d-e)^(f+g*h)-i
ONP: abcd^e-fgh*+^*+i-
INF: a~+b
ONP: +a+b
INF: a~~
ONP: a~~
INF: a+b~
INF: ()a+b
INF: (a+b)+()
INF: ~()a
INF: (a-b)*c/d^e)
INF: (a-b)*c~/~(d^e)
INF: ((a-b)*c/d^e
INF: (a-b)(*c/d^e
INF: (a)-b)*c/d^e
INF: )(a-b)*c/d^e
INF: )+(a-b)*c/d^e
INF: (a-b)*c/d^e(
INF: (a-b)*c/d^e+(
INF: a+@/&-d
ONP: abc-+de^/
ONP: -abc-+de^/
ONP: a
INF: a
INF: a*b+c*d+d*e+f*g
ONP: ab*cd*+de*+fg*+
ONP: abcd+++
ONP: ab+c+d+
INF: a+b+c+d
INF: a+(b+(c+d))
INF: a^b^c^d
INF: ((a^b)^c)^d

======================================
 */

/* ============= test0.out =============
INF: x = a * ( b * c )
INF: a + b + ( ~ a - a )
ONP: a b + a ~ a - +
ONP: x a ~ ~ b c * + =
ONP: t a ~ x < b ~ < =
ONP: a b + c /
INF: a * ( b - c )
INF: error
ONP: x a b c = = =
INF: x = a = b = c
ONP: a b c d ^ e - f g h * + ^ * + i -
INF: a + b * ( c ^ d - e ) ^ ( f + g * h ) - i
ONP: error
INF: error
ONP: error
INF: ~ ~ a
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
INF: ( a + ( b - c ) ) / d ^ e
INF: error
INF: a
ONP: a
ONP: a b * c d * + d e * + f g * +
INF: a * b + c * d + d * e + f * g
INF: a + ( b + ( c + d ) )
INF: a + b + c + d
ONP: a b + c + d +
ONP: a b c d + + +
ONP: a b c d ^ ^ ^
ONP: a b ^ c ^ d ^
======================================
 */