package com.company;

public class Converter//специальны класс для конвертации чисел в арабсский и в римские виды
{
    public static int ToArabic(String romanNum) throws Exception
    {
        int res = 0;//возвращение
            if(!romanNum.matches("(X?|IX$)|(V?|IV$)(I{0,3})"))//проверка на валидность римского числа
                throw new Exception("No such roman numbers or numbers out of boundaries");
            char[] num = romanNum.toCharArray();//переводим в массив символов дабы посимвольно читать
        //первый символ обрабатывается особенно т.к. в зависимости положения I с ней производятся разные операции
        //поскольку диапазон достаточно узок и известен, можно позволить делать все проверки немного грубо
            if (num[0] == 'I' && !romanNum.matches("I{0,3}"))
                res -= 1;
            else if (num[0] == 'X')
                res += 10;
            else if (num[0] == 'V')
                res += 5;
            else if(num[0] == 'I')
                res += 1;
            for (int i = 1; i < num.length; i++)
            {//дальше посимвольно смотрим что у нас идет и в зависимости от того что видим добавляем число
                if (num[i] == 'I')
                    res += 1;
                if (num[i] == 'V')
                    res += 5;
                if (num[i] == 'X')
                    res += 10;
            }
        return res;//возвращаем
    }

    public static String ToRoman(int arabicNum) throws Exception
    {
        //с обратно конвертацией проще т.к. просто необходимо отнимать максимальнуй доступный разряд пока можно
            String res = "";
        while (arabicNum - 100 >= -10)
        {
            if (arabicNum - 100 < 0)
                res += "XС";//но предпоследние числа имеют особенное написание так что надо учитывать это
            else
                res += "C";
            arabicNum -= 100;
        }
            while (arabicNum - 50 >= -10)
            {
                if (arabicNum - 50 < 0)
                    res += "XL";//но предпоследние числа имеют особенное написание так что надо учитывать это
                else
                    res += "L";
                arabicNum -= 50;
            }
            while (arabicNum - 10 >= -1)
            {
                if (arabicNum - 10 == -1)
                    res += "IX";//но предпоследние числа имеют особенное написание так что надо учитывать это
                else
                    res += "X";
                arabicNum -= 10;
            }
            while (arabicNum - 5 >= -1)
            {
                if (arabicNum - 5 == -1)
                    res += "IV";
                else
                    res += "V";
                arabicNum -= 5;
            }
            for (int i = 0; i < arabicNum; i++)
                res += "I";
            return res;
    }
}
