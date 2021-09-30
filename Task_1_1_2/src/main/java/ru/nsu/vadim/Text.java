package ru.nsu.vadim;

import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

public class Text {
    public static ArrayList<Integer> searchPatternFile(String pattern, String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        return searchPattern(pattern, reader);
    }

    public static ArrayList<Integer> searchPattern(String pattern, String string) throws Exception {
        StringReader reader = new StringReader(string);
        return searchPattern(pattern, reader);
    }

    public static ArrayList<Integer> searchPattern(String pattern, Reader reader) throws Exception {
        ArrayList<Integer> res = new ArrayList<>(0);

        if (pattern.length() == 0) {
            return res;
        }

        final char sentinel = '\uFFFD';
        final int patternLen = pattern.length();
        final int buffLen = patternLen * 16;
        char[] buffer = new char[buffLen];
        for (int i = 0; i < patternLen; i++) {
            buffer[i] = pattern.charAt(i);
        }
        buffer[patternLen] = sentinel;

        int readLenFirst = buffLen - patternLen - 1;
        int readLen = buffLen - patternLen * 2;
        for (int i = 0; true; i++) {
            int charRead;
            if (i == 0) {
                charRead = reader.read(buffer, patternLen + 1, readLenFirst);
            } else {
                charRead = reader.read(buffer, patternLen * 2, readLen);
            }
            if (charRead == -1) {
                break;
            }

            int[] zArr = calculateZ(buffer);
            for (int j = patternLen + 1; j < patternLen + 1 + charRead; j++) {
                if (zArr[j] == patternLen) {
                    int entryIndex = j + i * (buffLen - patternLen - 1) - patternLen - 1 - (patternLen - 1) * i;
                    res.add(entryIndex);
                }
            }
            if ((charRead < readLenFirst && i == 0) || (charRead < readLen)) {
                break;
            }
            System.arraycopy(buffer, buffLen - patternLen + 1, buffer, patternLen + 1, patternLen - 1);
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
