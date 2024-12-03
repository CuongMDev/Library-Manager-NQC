package com.example.librarymanagernqc.Objects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class FuzzySearch {
    // Private constructor to prevent instantiation
    private FuzzySearch() {
    }

    /**
     * search book by title, limit = 0 mean no limit
     */
    public static<T> List<T> search(List<T> list, String word, String methodName, int maxDistance, int limit) {
        if (limit == 0) {
            limit = list.size();
        }

        List<T> results = new LinkedList<>();
        for (T item : list) {
            // Lấy phương thức được chỉ định từ lớp T
            try {
                Method method = item.getClass().getMethod(methodName);
                String itemWord = (String) method.invoke(item);

                if (wordDistance(word, itemWord) <= maxDistance) {
                    results.add(item);
                    if (results.size() >= limit) {
                        break;
                    }
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    private static int wordDistance(String findWord, String word) {
        for (int i = 0, j = 0; i < findWord.length(); i++) {
            while (j < word.length() && Character.toLowerCase(findWord.charAt(i)) != Character.toLowerCase(word.charAt(j))) {
                j++;
            }
            if (j == word.length()) {
                return findWord.length() - i;
            }

            //findWord[i] = word[j] so increase j
            j++;
        }

        return 0;
    }
}
