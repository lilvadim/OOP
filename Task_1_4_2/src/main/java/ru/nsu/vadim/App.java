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
    private enum Action {ADD, REMOVE, SHOW}

    private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BOLD = "\033[0;1m";

    public static void main(String[] args) {
        JsonNotebook jsonNotebook = new JsonNotebook();
        LongOpt[] longOpts = new LongOpt[7];
        longOpts[0] = new LongOpt("add", LongOpt.REQUIRED_ARGUMENT, null, 'a');
        longOpts[1] = new LongOpt("remove", LongOpt.REQUIRED_ARGUMENT, null, 'r');
        longOpts[2] = new LongOpt("show", LongOpt.NO_ARGUMENT, null, 's');
        longOpts[3] = new LongOpt("from", LongOpt.REQUIRED_ARGUMENT, null, 0);
        longOpts[4] = new LongOpt("to", LongOpt.REQUIRED_ARGUMENT, null, 1);
        longOpts[5] = new LongOpt("help", LongOpt.REQUIRED_ARGUMENT, null, 'h');
        longOpts[6] = new LongOpt("text", LongOpt.REQUIRED_ARGUMENT, null, 't');

        Getopt getopt = new Getopt("notebook", args, "t:r:a:sh", longOpts);
        String HELP_MESSAGE = """
                Options:
                    --help (-h)           - print this help message
                    --add (-a) <title>    - add note with title (if note already exists, it will be overwritten)
                    --text (-t) <text>    - add text to note. Use this with '--add' option
                    --remove (-r) <title> - remove note with such title
                    --show (-s)           - show notes sorted by last edit date. Equivalent to no options.
                    --from <date>         - set lower bound to filter notes shown by '--show' option
                    --to <date>           - set upper bound to filter notes shown by '--show' option
                Date format: d MMM yyyy HH:mm
                    Example: 27 Oct 2002 23:59""";

        Action action = Action.SHOW;
        String title = "New note";
        String text = ANSI_RED_BACKGROUND + "no text" + ANSI_RESET;
        LocalDateTime from = LocalDateTime.MIN;
        LocalDateTime to = LocalDateTime.MAX;


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
                        from = LocalDateTime.parse(getopt.getOptarg(), Note.DATE_TIME_FORMATTER);
                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 1 -> {
                    try {
                        to = LocalDateTime.parse(getopt.getOptarg(), Note.DATE_TIME_FORMATTER);
                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 'h' -> {
                    System.out.println(HELP_MESSAGE);
                    return;
                }
                default -> {
                    return;
                }
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
                                    ANSI_BLUE + Note.DATE_TIME_FORMATTER.format(n.getDateTime()) + ANSI_BOLD + ANSI_BLUE + "\n\"" + n.getTitle() + "\"\n" + ANSI_RESET
                                            + n.getText()
                                            + "\n"));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }
}
