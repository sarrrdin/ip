/**
 * Project Duke CS2103
 * Done by Hong Jin.
 */
package duke;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.DeadlineCommand;
import duke.command.DeleteCommand;
import duke.command.EventCommand;
import duke.command.FindCommand;
import duke.command.HiCommand;
import duke.command.ListCommand;
import duke.command.MarkCommand;
import duke.command.PriorityCommand;
import duke.command.TodoCommand;
import duke.command.UnmarkCommand;

/**
 * class Parser to parse through commands input by user.
 */
public class Parser {
    //formatter for date.
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" d/MM/uuuu");

    /**
     * public static method parse that parse input from user and returns Command for Duke to operate.
     * @param fullCommand full command from user.
     * @return Command that user input.
     * @throws DukeException
     */
    public static Command parse(String fullCommand) throws DukeException {

        String[] splitString = fullCommand.split(" ", 2);

        CommandCases cs;

        try {
            cs = CommandCases.valueOf(splitString[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DukeException("No such command exist...");
        }

        try {
            if (splitString.length == 1) {
                switch (cs) {
                case HI:
                    return new HiCommand();

                case BYE:
                    return new ByeCommand();

                case LIST:
                    return new ListCommand();

                case MARK:
                    throw new DukeException("The index to mark cannot be left empty");

                case UNMARK:
                    throw new DukeException("The index to unmark cannot be left empty");

                case TODO:
                    throw new DukeException("The description of todo cannot be left empty");

                case DEADLINE:
                    throw new DukeException("The description of deadline cannot be left empty");

                case EVENT:
                    throw new DukeException("The description of event cannot be left empty");

                case DELETE:
                    throw new DukeException("The index to delete cannot be left empty");

                case FIND:
                    throw new DukeException("The keyword to find cannot be left empty");

                case PRIORITY:
                    throw new DukeException("The index to prioritize cannot be left empty");

                default:
                    throw new DukeException("Invalid argument, please re enter a valid command...");
                }
            } else {
                switch (cs) {
                case HI:
                    return new HiCommand();

                case BYE:
                    return new ByeCommand();

                case LIST:
                    return new ListCommand();

                case MARK:
                    return new MarkCommand(Integer.parseInt(splitString[1]));

                case UNMARK:
                    return new UnmarkCommand(Integer.parseInt(splitString[1]));

                case TODO:
                    return new TodoCommand(splitString[1]);

                case DEADLINE:
                    String[] parse = splitString[1].split(" /by", 2);
                    return new DeadlineCommand(parse[0], LocalDate.parse(parse[1], formatter));

                case EVENT:
                    String[] parse1 = splitString[1].split(" /at", 2);
                    return new EventCommand(parse1[0], LocalDate.parse(parse1[1], formatter));

                case DELETE:
                    return new DeleteCommand(Integer.parseInt(splitString[1]));

                case FIND:
                    String[] keywordSplit = splitString[1].split("\\s");
                    return new FindCommand(keywordSplit);

                case PRIORITY:
                    //command line will be priority <priority height> /for <task index>
                    String[] parse2 = splitString[1].split(" /for ", 2);
                    int priority = 0;
                    int index = 0;

                    try {
                        priority = Integer.parseInt(parse2[0]);
                        index = Integer.parseInt(parse2[1]);
                    } catch (NumberFormatException e) {
                        throw new DukeException("Invalid argument, please re enter a valid command...");
                    }
                    return new PriorityCommand(priority, index);

                default:
                    throw new DukeException("No such command exist... please try again");
                }
            }
        } catch (DateTimeParseException e) {
            throw new DukeException("hihi please provide date in dd/mm/yyyy format :)");
        }
    }
}
