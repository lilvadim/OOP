package ru.nsu.vadim;

import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Text {
    public static List<Integer> searchPatternFile(String pattern, String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        return searchPattern(pattern, reader);
    }

    public static List<Integer> searchPattern(String pattern, String string) throws Exception {
        StringReader reader = new StringReader(string);
        return searchPattern(pattern, reader);
    }

    public static List<Integer> searchPattern(String pattern, Reader reader) throws Exception {
        int buffSize = 20;
        char sentinel = '\uFFFD';
        int patternLen = pattern.length();
        char[] buffer = new char[buffSize];
        for (int i = 0; i < patternLen; i++) {
            buffer[i] = pattern.charAt(i);
        }
        buffer[patternLen] = sentinel;

        int[] zArr;
        ArrayList<Integer> res = new ArrayList<>(0);

        int charRead;
        for (int i = 0; true; i++) {
            if (i == 0) {
                charRead = reader.read(buffer, patternLen + 1, buffSize - patternLen - 1);
            } else {
                charRead = reader.read(buffer, patternLen * 2, buffSize - patternLen * 2);
            }
            if (charRead == -1) {
                break;
            }
            if (charRead < buffSize - patternLen * 2) {
                for (int c = patternLen * 2 + charRead; c < buffSize; c++) {
                    buffer[c] = sentinel;
                }
            }
            zArr = calculateZ(buffer);
            for (int j = patternLen + 1; j < buffSize; j++) {
                if (zArr[j] == patternLen) {
//                    System.out.println(j + i * (buffSize - patternLen - 1) - patternLen - 1 - (patternLen - 1) * i);
                    res.add(j + i * (buffSize - patternLen - 1) - patternLen - 1 - (patternLen - 1) * i);
                }
            }
            if (charRead < buffSize - patternLen * 2) {
                break;
            }
            System.arraycopy(buffer, buffSize - patternLen + 1, buffer, patternLen + 1, patternLen - 1);
        }
        reader.close();
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
