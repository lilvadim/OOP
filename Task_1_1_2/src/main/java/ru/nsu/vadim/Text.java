package ru.nsu.vadim;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Text {
    public static ArrayList<Integer> searchPattern(String pattern, String fileName) throws Exception {
        ArrayList<Integer> zArrList = new ArrayList(0);
        int patternLen = pattern.length();
        FileReader reader = new FileReader(fileName);
        char[] buffer = new char[1024];
        for (int i = 0; i < patternLen; i++) {
            buffer[i] = pattern.charAt(i);
        }
        buffer[patternLen] = '$';
        int eofCheck = 0;
        for (int i = 0; eofCheck != -1; i++) {
            eofCheck = reader.read(buffer, patternLen, 1024 - patternLen - 1);
            zArrList.addAll(Arrays.stream(calculateZ(buffer)).boxed().collect(Collectors.toList()));
        }
        ArrayList<Integer> res = new ArrayList<>(1);
        for (int i = 0; i < zArrList.size(); i++) {
            if (zArrList.get(i) == patternLen) {
                res.add(i - patternLen - 1);
            }
        }
        return res;
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
