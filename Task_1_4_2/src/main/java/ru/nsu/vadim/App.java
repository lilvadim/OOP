package ru.nsu.vadim;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import ru.nsu.vadim.notebook.JsonNotebook;
import ru.nsu.vadim.notebook.Note;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Usage:
 * notebook --add "title" -t "text"
 * notebook --remove "title"
 * notebook --show --from "27 Oct 2002 00:00" --to "d MMM yyyy HH:mm"
 * notebook --show
 */

public class App {
    public static void main(String[] args) {
        JsonNotebook jsonNotebook = new JsonNotebook();
        LongOpt[] longOpts = new LongOpt[5];
        longOpts[0] = new LongOpt("add", LongOpt.REQUIRED_ARGUMENT, null, 'a');
        longOpts[1] = new LongOpt("rm", LongOpt.REQUIRED_ARGUMENT, null, 'r');
        longOpts[2] = new LongOpt("show", LongOpt.NO_ARGUMENT, null, 's');
        longOpts[3] = new LongOpt("from", LongOpt.REQUIRED_ARGUMENT, null, 0);
        longOpts[4] = new LongOpt("to", LongOpt.REQUIRED_ARGUMENT, null, 1);
        Getopt getopt = new Getopt("notebook", args, "t:r:a:s", longOpts);

        Action action = Action.SHOW;
        String title = "New note";
        String text = "";
        LocalDateTime from = LocalDateTime.MIN;
        LocalDateTime to = LocalDateTime.MAX;
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_RESET = "\u001B[0m";


        int c;
        while ((c = getopt.getopt()) != -1) {
            switch (c) {
                case 'a' -> {
                    title = getopt.getOptarg();
                    action = Action.ADD;
                }
                case 't' -> text = getopt.getOptarg();
                case 'r' -> {
                    title = getopt.getOptarg();
                    action = Action.REMOVE;
                }
                case 's' -> action = Action.SHOW;
                case 0 -> {
                    try {
                        from = LocalDateTime.parse(getopt.getOptarg());
                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 1 -> {
                    try {
                        to = LocalDateTime.parse(getopt.getOptarg());
                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                }
                default -> System.out.println("Wrong input!");
            }
        }

        switch (action) {
            case ADD -> {
                try {
                    jsonNotebook.add(LocalDateTime.now(), title, text);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            case REMOVE -> {
                try {
                    jsonNotebook.remove(title);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            case SHOW -> {
                try {
                    jsonNotebook.getNoteListByInterval(from, to).forEach(n ->
                            System.out.println(
                                    ANSI_BLUE + "\"" + n.getTitle() + "\" [" + Note.DATE_TIME_FORMATTER.format(n.getDateTime()) + "]\n" + ANSI_RESET
                                            + n.getText()
                                            + "\n"));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }

    enum Action {ADD, REMOVE, SHOW}
}
