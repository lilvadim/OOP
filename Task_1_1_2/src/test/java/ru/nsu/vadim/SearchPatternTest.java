package ru.nsu.vadim;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class SearchPatternTest {
    private ArrayList<Integer> searchPattern_reference(String substring, String string) {
        ArrayList<Integer> res = new ArrayList<>(0);
        if (substring.length() == 0 || string.length() == 0) {
            return res;
        }

        int index;
        for (int i = 0; i < string.length(); i = index + 1) {
            index = string.indexOf(substring, i);
            if (index != -1) {
                res.add(index);
            } else {
                break;
            }
        }
        return res;
    }

    @Test
    public void searchPattern_file_testEng() throws Exception {
        String pattern = "free";
        Path filePath = Path.of("src/test/resources/GPL.txt");
        ArrayList<Integer> res = Text.searchPattern(pattern, filePath);
        ArrayList<Integer> ans = searchPattern_reference(pattern, Files.readString(filePath));
        Assertions.assertEquals(ans, res);
    }

    @Test
    public void searchPattern_file_testRu() throws Exception {
        String pattern = "иценз";
        Path filePath = Path.of("src/test/resources/GPL_RU.txt");
        ArrayList<Integer> res = Text.searchPattern(pattern, filePath);
        ArrayList<Integer> ans = searchPattern_reference(pattern, Files.readString(filePath));
        Assertions.assertEquals(ans, res);
    }

    @Test
    public void searchPattern_file_testEmptyPattern() throws Exception {
        String pattern = "";
        Path filePath = Path.of("src/test/resources/GPL.txt");
        ArrayList<Integer> ans = searchPattern_reference(pattern, Files.readString(filePath));
        ArrayList<Integer> res = Text.searchPattern(pattern, filePath);
        Assertions.assertEquals(ans, res);
    }

    @Test
    public void searchPattern_string_testEmptyText() throws Exception {
        String pattern = "pattern";
        String string = "";
        ArrayList<Integer> ans = searchPattern_reference(pattern, string);
        ArrayList<Integer> res = Text.searchPattern(pattern, string);
        Assertions.assertEquals(ans, res);
    }

    @Test
    public void searchPattern_string_testMultiplePattern() throws Exception {
        String pattern = "pattern";
        String string = "patternpatternpatternpatternpatternpatternpatternpatternpatternpatternpatternpatternpatternpatternpatternpatternpatternpatternpatternpatternpattern";
        ArrayList<Integer> ans = searchPattern_reference(pattern, string);
        ArrayList<Integer> res = Text.searchPattern(pattern, string);
        Assertions.assertEquals(ans, res);
    }

    @Test
    public void searchPattern_string_testEmptyAll() throws Exception {
        String pattern = "";
        String string = "";
        ArrayList<Integer> ans = searchPattern_reference(pattern, string);
        ArrayList<Integer> res = Text.searchPattern(pattern, string);
        Assertions.assertEquals(ans, res);
    }

    @Test
    public void searchPattern_string_testSmallText() throws Exception {
        String pattern = "pattern";
        String string = "p";
        ArrayList<Integer> ans = searchPattern_reference(pattern, string);
        ArrayList<Integer> res = Text.searchPattern(pattern, string);
        Assertions.assertEquals(ans, res);
    }

    @Test
    public void searchPattern_string_testSameChars() throws Exception {
        String pattern = "p";
        String string = "pppppppppppppppppppp";
        ArrayList<Integer> ans = searchPattern_reference(pattern, string);
        ArrayList<Integer> res = Text.searchPattern(pattern, string);
        Assertions.assertEquals(ans, res);
    }

    @Test
    public void searchPattern_string_testMatch1() throws Exception {
        String pattern = "p";
        String string = "p";
        ArrayList<Integer> ans = searchPattern_reference(pattern, string);
        ArrayList<Integer> res = Text.searchPattern(pattern, string);
        Assertions.assertEquals(ans, res);
    }

    @Test
    public void searchPattern_file_testMatchLarge() throws Exception {
        Path filePath = Path.of("src/test/resources/GPL.txt");
        String pattern = Files.readString(filePath);
        ArrayList<Integer> ans = searchPattern_reference(pattern, Files.readString(filePath));
        ArrayList<Integer> res = Text.searchPattern(pattern, filePath);
        Assertions.assertEquals(ans, res);
    }
}
