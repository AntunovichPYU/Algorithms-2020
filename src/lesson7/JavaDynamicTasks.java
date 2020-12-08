package lesson7;

import kotlin.NotImplementedError;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    //Сложность: O(nm), n и m - длины строк
    //Ресурсоемкость: O(nm)
    public static String longestCommonSubSequence(String first, String second) {
        int firstLength = first.length();
        int secondLength = second.length();
        int[][] table = new int[firstLength + 1][secondLength + 1];

        for (int i = 0; i < firstLength; i++) {
            for (int j = 0; j < secondLength; j++) {
                if (i == 0 || j == 0)
                    table[i][j] = 0;
                else if (first.charAt(i - 1) == second.charAt(j - 1))
                    table[i][j] = table[i - 1][j - 1] + 1;
                else
                    table[i][j] = max(table[i - 1][j], table[i][j - 1]);
            }
        }

        StringBuilder result = new StringBuilder();
        int i = firstLength;
        int j = secondLength;
        while (i > 0 && j > 0) {
            if (first.charAt(i - 1) == second.charAt(j - 1)) {
                result.append(first.charAt(i - 1));
                i--;
                j--;
            } else if (table[i - 1][j] > table[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return result.reverse().toString();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        throw new NotImplementedError();
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    //Сложность: O(n*m), где n*m - размер поля
    //Ресурсоемкость: O(n*m)
    public static int shortestPathOnField(String inputName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputName));
        String line;
        List<String[]> input = new ArrayList<>();
        int height = 0;
        int width = 0;
        while ((line = reader.readLine()) != null) {
            String[] toAdd = line.split(" ");
            input.add(toAdd);
            height++;
            if (width == 0)
                width = toAdd.length;
        }
        reader.close();

        int[][] table = new int[height][width];
        for (int i = 0; i < height; i++) {
            String[] el = input.get(i);
            for (int j = 0; j < width; j++) {
                table[i][j] = Integer.parseInt(el[j]);
            }
        }

        int[][] minLengths = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 && j == 0)
                    minLengths[i][j] = table[i][j];
                else if (i == 0)
                    minLengths[i][j] = minLengths[i][j - 1] + table[i][j];
                else if (j == 0)
                    minLengths[i][j] = minLengths[i - 1][j] + table[i][j];
                else
                    minLengths[i][j] = Integer.min(
                            minLengths[i][j - 1] + table[i][j],
                            min(
                                    minLengths[i - 1][j] + table[i][j],
                                    minLengths[i - 1][j - 1] + table[i][j]
                            )
                    );
            }
        }

        return minLengths[height - 1][width - 1];
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
