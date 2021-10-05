package ru.nsu.vadim;

import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.ArrayList;

public class Text {
    public static ArrayList<Integer> searchPattern(String pattern, Path filePath) throws Exception {
        FileReader reader = new FileReader(String.valueOf(filePath));
        return searchPattern(pattern, reader);
    }

    public static ArrayList<Integer> searchPattern(String pattern, String string) throws Exception {
        StringReader reader = new StringReader(string);
        return searchPattern(pattern, reader);
    }

    public static ArrayList<Integer> searchPattern(String pattern, Reader reader) throws Exception {
        ArrayList<Integer> res = new ArrayList<>(0);
        final int patternLen = pattern.length();

        if (patternLen == 0) {
            return res;
        }

        final char sentinel = '\uFFFD';
        final int buffLen = patternLen * 16;
        char[] buffer = new char[buffLen];
//        final char[] patternArr = pattern.toCharArray();
        System.arraycopy(pattern.toCharArray(), 0, buffer, 0, patternLen);
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
    private static int[] calculateZ(char[] string) {
        int len = string.length;
        int[] zArr = new int[len];
        int left = 0;
        int right = 0;
        for (int i = 1; i < len; i++) {
            zArr[i] = right > i ? Math.min(zArr[i - left], right - i) : 0;
            while (i + zArr[i] < len && string[zArr[i]] == string[i+zArr[i]]) {
                zArr[i]++;
            }
            if (i + zArr[i] > right) {
                left = i;
                right = i + zArr[i];
            }
        }
        return zArr;
    }
}
