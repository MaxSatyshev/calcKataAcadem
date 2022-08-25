import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws CalcException {
        Scanner sc = new Scanner(System.in);
        String c = sc.next(); //Ввод операции
        System.out.print(calc(c));
    }
    public static String calc(String input) throws CalcException {
        char[] ch = input.toCharArray(); //Разбивает строку на символы
        String result = new String();
        int oper = 0;
        for (int i = 0; i < ch.length; i++) { //Находим индекс оператора
            if (ch[i] == '+' || ch[i] == '-' || ch[i] == '*' || ch[i] == '/') {
                oper += i;
            }
        }
        char[] numChar1 = new char[oper];
        char[] numChar2 = new char[ch.length - (oper + 1)];
        for (int i = 0, x = 0; i < oper; i++) {
            numChar1[x] = ch[i]; //Первое число в символьный массив
            x++;
        }
        for (int i = oper + 1, y = 0; i < ch.length; i++) {
            numChar2[y] = ch[i]; //Второе число в символьный массив
            y++;
        }
        String num1 = new String(numChar1); //Перевод числа из массива в строку
        String num2 = new String(numChar2);

        // Исключения:
        int operand = 0;
        int rom = 0;
        int arab = 0;
        for (int i = 0; i < ch.length; i++) { //Находим количество рим цифр для исключений
            if (ch[i] == 'I' || ch[i] == 'V' || ch[i] == 'X') {
                rom++;
            } else if (ch[i] == '0' || ch[i] == '1' || ch[i] == '2' || ch[i] == '3' || ch[i] == '4' || ch[i] == '5' || ch[i] == '6' || ch[i] == '7' || ch[i] == '8' || ch[i] == '9') {
                arab++;
            } else if (ch[i] == '+' || ch[i] == '-' || ch[i] == '*' || ch[i] == '/') {
                operand++;
            } else {
                throw new CalcException("throws Exception //т.к. в числах допущена ошибка или оператор не соответсвует условию");
            }
            if (rom > 0 && arab > 0) {
                throw new CalcException("throws Exception //т.к. используются одновременно разные системы счисления");
            }
        }
        if (operand != 1 ||ch[0] == '+' ||ch[0] == '-' ||ch[0] == '*' ||ch[0] == '/' ||ch[ch.length-1] == '+' ||ch[ch.length-1] == '-' ||ch[ch.length-1] == '*' ||ch[ch.length-1] == '/') {
            throw new CalcException("throws Exception //т.к. строка не является математической операцией или формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        int x = 0, y = 0;
        if (rom == 0 && arab > 0) {
            x = Integer.parseInt(num1); // Первое число
            y = Integer.parseInt(num2); // Второе число
            if (x < 1 || x > 10 || y < 1 || y > 10) {
                throw new CalcException("throws Exception //т.к. числа не удовлетворяют условию - от 1 до 10");
            }
        } else if (rom > 0 && arab == 0) {
            Rom rumToArab1 = Rom.valueOf(num1);
            Rom rumToArab2 = Rom.valueOf(num2);
            int a = rumToArab1.getValue();
            int b = rumToArab2.getValue();
            if (a < 1 || a > 10 || b < 1 || b > 10) {
                throw new CalcException("throws Exception //т.к. числа не удовлетворяют условию - от I до X");
            }
        }
        if (rom == 0 && arab > 0) {
            switch (ch[oper]) {
                case ('+'):
                    result = String.valueOf(x + y);
                    break;
                case ('-'):
                    result = String.valueOf(x - y);
                    break;
                case ('*'):
                    result = String.valueOf(x * y);
                    break;
                case ('/'):
                    result = String.valueOf(x / y);
                    break;
            }
        } else if (rom > 0 && arab == 0) {
            Rom rumToArab1 = Rom.valueOf(num1);
            Rom rumToArab2 = Rom.valueOf(num2);
            int a = rumToArab1.getValue();
            int b = rumToArab2.getValue();
            int res = 0;
            switch (ch[oper]) {
                case ('+'):
                    res = a + b;
                    break;
                case ('-'):
                    res = a - b;
                    break;
                case ('*'):
                    res = a * b;
                    break;
                case ('/'):
                    res = a / b;
                    break;
            }
            if (res > 0) {
                result = String.valueOf(Rom.values()[res - 1]);
            }
            else throw new CalcException("throws Exception //т.к. римские числа не могут быть отрицательными");

        }
        return result;
    }
}