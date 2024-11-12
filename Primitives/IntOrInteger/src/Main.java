public class Main {
    public static void main(String[] args) {
        Container container = new Container();
        container.addCount(5672);
        System.out.println(container.getCount());

        // TODO: ниже напишите код для выполнения задания:
        //  С помощью цикла и преобразования чисел в символы найдите все коды
        //  букв русского алфавита — заглавных и строчных, в том числе буквы Ё.

        final String rusAlphabet = "абвгдеёжзийклмнопрстуфхцчшщьыъэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЬЫЪЭЮЯ";
        final char[] rusLetters = rusAlphabet.toCharArray();

        // самый простой способ преобразуя символы в код ...
        System.out.println("\nСпособ 1 - перевод символов в код\n");
        for (char rusLetter : rusLetters) {
            System.out.println("Код буквы " + rusLetter + " = " + (int) rusLetter);
        }
        // но в задании говорится о преобразования чисел в символы
        // наверное имеется ввиду так:
        System.out.println("\nСпособ 2 - перебором всех возможных кодов\n");
        for (int i = 0; i < Character.MAX_VALUE; i++) {
            for (char rusLetter : rusLetters) {
                if ((char) i == rusLetter) {
                    System.out.println("Код буквы " + rusLetter + " = " + i);
                }
            }
        }
    }
}
