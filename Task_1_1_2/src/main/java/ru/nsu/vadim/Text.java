package ru.nsu.vadim;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Text {
    public static int[] searchPattern(String pattern, String fileName) throws Exception {
        int[] res = new int[1024];
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

        }

    }

//    private static void getZarr(String str, int[] Z) {
//
//        int n = str.length();
//
//        int L = 0, R = 0;
//
//        for (int i = 1; i < n; ++i) {
//
//            if (i > R) {
//
//                L = R = i;
//
//                while (R < n && str.charAt(R - L) == str.charAt(R)) {
//                    R++;
//                }
//
//                Z[i] = R - L;
//                R--;
//
//            } else {
//
//                int k = i - L;
//
//                if (Z[k] < R - i + 1) {
//                    Z[i] = Z[k];
//                } else {
//                    L = i;
//
//                    while (R < n && str.charAt(R - L) == str.charAt(R)) {
//                        R++;
//                    }
//
//                    Z[i] = R - L;
//                    R--;
//                }
//            }
//        }
//    }
}
