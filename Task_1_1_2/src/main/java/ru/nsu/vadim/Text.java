package ru.nsu.vadim;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Text {
    public static void searchPattern(String pattern, String fileName) throws Exception {
        int[] zArr;
        int[] res = new int[1024];
        int patternLen = pattern.length();
        FileReader reader = new FileReader(fileName);
        char[] buffer = new char[1024];
        for (int i = 0; i < patternLen; i++) {
            buffer[i] = pattern.charAt(i);
        }
        buffer[patternLen] = '$';
        int eofCheck = 0;
        int resCnt = 0;
        for (int i = 0; true; i++) {
            eofCheck = reader.read(buffer, patternLen + 1, 1024 - patternLen - 1);
            if (eofCheck == -1) {
                break;
            }
            zArr = calculateZ(buffer);
            for (int j = patternLen + 1; j < 1024; j++) {
                if (zArr[j] == patternLen) {
                    System.out.println(j + i * 1024 - patternLen - 1);
                }
            }
        }
    }

    private static int[] calculateZ(char[] str) {
        int len = str.length;
        int[] zArr = new int[len];
        int left = 0;
        int right = 0;
        for (int k = 1; k < len; k++) {
            if (k > right) {
                left = right = k;
                while (right < len && str[right] == str[right - left]) {
                    right++;
                }
                zArr[k] = right - left;
                right--;
            } else {
                int k1 = k - left;
                if (zArr[k1] < right - k + 1) {
                    zArr[k] = zArr[k1];
                } else {
                    left = k;
                    while (right < len && str[right] == str[right - left]) {
                        right++;
                    }
                    zArr[k] = right - left;
                    right--;
                }
            }
        }
        return zArr;
    }
}
