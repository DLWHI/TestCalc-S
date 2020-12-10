package com.company;

import java.io.Console;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main
{
    //сама программа
    //ниже все переменные которые нам нужны почти везде
    private static Pattern operation = Pattern.compile("[\\+\\-\\*\\/]");
    private static int[] num = new int[2];
    private static String expression = "";
    private static Matcher matcher;

    public static void main(String[] args) throws Exception
    {
        Scanner input = new Scanner(System.in);//для ввода
        while (expression != "exit")//и по кругу опрашиваем юзера пока он не ошибется или не захочет выйти
        {
            expression = input.nextLine();
            if(expression.matches("(\\d{1,2})\s?[\\+\\-\\*\\/]\s?(\\d{1,2})"))
                //в случае если юзер ввел арабские цифры(а мы это проверили) обратабываем как арабские цифры
                Arabic(expression);
            else if(expression.matches("[((X?|IX$)|(V?|IV$)(I{0,3}))]+\s?[\\+\\-\\*\\/]\s?[((X?|IX$)|(V?|IV$)(I{0,3}))]+"))
                //и римские как римские
                Roman(expression);
            else if(expression.matches("exit"))
                //команда для выхода
                return;
            else
                //на случай если юзверь ввел что то нечитабельное
                throw new Exception("Invalid input");
        }
    }

    private static void Arabic(String expression) throws Exception
    {
        Pattern arabic = Pattern.compile("\\d{1,2}");//регех для поиска чисел
        matcher = arabic.matcher(expression);
        for (int i = 0; i < num.length; i++)
        {
            //циклично ищем по строке числа нужного нам вида
            //если нам не будет каким то образом хватать числа matcher.group() кинет исключение
            matcher.find();
            num[i] = Integer.parseInt(matcher.group());
            if(num[i] < 1 || num[i] > 10)//если вышли из диапазона
                throw new Exception("Numbers out of boundaries");
        }
        matcher = operation.matcher(expression);//находим символ оператора в команде
        matcher.find();
        System.out.print(" = ");
        System.out.print(Operate(num[0], num[1], matcher.group()));//и обрабатываем попутно выводя в консоль
        System.out.print("\n");
    }
    private static void Roman(String expression) throws Exception
    {
        //римский метод работает так же как и арабский
        // за исключением конвертаций чисел при помощи Converter в начале и в конце
        Pattern roman = Pattern.compile("[(X?|IX$)|(V?|IV$))?(I{0,3})]+");
        matcher = roman.matcher(expression);
        for (int i = 0; i < num.length; i++)
        {
            matcher.find();
            num[i] = Converter.ToArabic(matcher.group());
        }
        matcher = operation.matcher(expression);
        matcher.find();
        System.out.print(" = ");
        System.out.print(Converter.ToRoman(Operate(num[0], num[1], matcher.group())));
        System.out.print("\n");
    }

    private static int Operate(int a, int b, String op) throws Exception
    {
        //а тут в зависимости от полученной оперции мы вызываем нужный метод из Calculator'а
        switch (op)
        {
            case "+":
                return Calculator.Add(a, b);
            case "-":
                return Calculator.Subtract(a, b);
            case "*":
                return Calculator.Multiply(a, b);
            case "/":
                return Calculator.IntDivide(a, b);
            default:
                throw new Exception("No such operation");
        }
    }
}
//как то так...