package Main;

import java.util.*;
import java.io.*;

/**
 * This class is used to save game progress to a file and read saved progress.
 *
 * <h2>Course Info: </h2>
 * ICS4U0 with Ms. Krasteva
 *
 * @author Rohan Krishna
 * @version 4.0 June 10, 2019
 * <p>
 * <b> Total time spent writing file: </b> 3 hours
 * <p>
 * <b> Instance Variables: </b>
 * <p>
 * <b> names </b> The ArrayList storing all the names of players with saved games.
 * <p>
 * <b> levels </b> The ArrayList storing all the levels of players with saved games.
 * <p>
 * <b> maxSaves </b> The maximum number of games that are to be saved.
 */
public class LevelLoader {
    private ArrayList<String> names;
    private ArrayList<Integer> levels;
    private int maxSaves;

    /**
     * This constructor creates the names and levels ArrayLists and sets the maximum number of games that can be saved.
     *
     * @param max The maximum number of games that can be saved.
     */
    public LevelLoader(int max) {
        names = new ArrayList<String>();
        levels = new ArrayList<Integer>();
        maxSaves = max;
    }


    /**
     * This method assigns indexes of the names and levels ArrayLists with values read from the saved progress file.
     * <p>
     * <b> input </b> The BufferedReader object used to read the file.
     * <p>
     * <b> line </b> Stores a line read from the saved progress file.
     */
    private void getData() {
        BufferedReader input;
        String line;
        try {
            input = new BufferedReader(new FileReader("Save Progress.txt"));
            line = input.readLine();
            names.clear();
            levels.clear();
            while (line != null) {
                names.add(line);
                try {
                    levels.add(Integer.parseInt(input.readLine()));
                } catch (NumberFormatException e) {
                }
                line = input.readLine();
                line = input.readLine();
            }
        } catch (IOException e) {
        }
    }


    /**
     * This method calls writeToFile() to create a new saved game. Data is added to the file in descending order by
     * level and alphabetical order within each level.
     *
     * @param name  The name to be added to the file.
     * @param level The level of the game to be saved.
     *              <p>
     *              <b> start </b> The index of the first occurence of a level in the levels ArrayList.
     *              <b> end </b> The index of the last occurence of a level in the levels ArrayList.
     */
    public void saveProgress(String name, int level) {
        int start;
        int end;

        getData();
        if (names.contains(name)) {
            levels.remove(names.indexOf(name));
            names.remove(names.indexOf(name));
        }

        if (level == 3) {
            start = 0;
            end = levels.lastIndexOf(level);
            if (end == -1) //this is the first level 3 entry
            {
                names.add(0, name);
                levels.add(0, level);
                writeToFile();
                return;
            }
        } else if (level == 2) {
            start = levels.indexOf(2);
            end = levels.lastIndexOf(2);
            if (start == -1) //this is the first level 2 entry
            {
                names.add(levels.lastIndexOf(3) + 1, name);
                levels.add(levels.lastIndexOf(3) + 1, level);
                writeToFile();
                return;
            }
        } else {
            start = levels.indexOf(1);
            end = levels.size() - 1;
            if (start == -1) //this is the first level 1 entry
            {
                names.add(name);
                levels.add(level);
                writeToFile();
                return;
            }
        }
        names.add(findInsertionIndex(start, end, name), name);
        levels.add(findInsertionIndex(start, end, name), level);
        writeToFile();
    }


    /**
     * This method returns the index of the ArrayList at which to add new data.
     *
     * @param start The index of the names ArrayList to start searching from.
     * @param end   The index of the names ArrayList after which to stop searching.
     * @param name  The name that is being added and inserted in alphabetical order.
     *              <p>
     *              <b> Local Variables: </b>
     *              <p>
     *              <b> middle </b> Stores the index in between the start and end indexes.
     */
    private int findInsertionIndex(int start, int end, String name) {
        if (end <= start) {
            if ((name.compareTo(names.get(start)) < 0))
                return start;
            else
                return start + 1;
        } else {
            int middle = (start + end) / 2;
            if (name.compareTo(names.get(middle)) < 0)
                return findInsertionIndex(start, middle - 1, name);
            else
                return findInsertionIndex(middle + 1, end, name);
        }
    }


    /**
     * This method is used to write new data to the save progress file.
     * <p>
     * <b> Local Variables: </b>
     * <p>
     * <b> output </b> The PrintWriter object used to write to the file.
     * <p>
     * <b> count </b> Keeps track of the number of games saved to prevent exceeding the desired limit of saved games.
     */
    private void writeToFile() {
        PrintWriter output;
        int count = 0;

        try {
            output = new PrintWriter(new FileWriter("Save Progress.txt"));
            while (count < maxSaves && count < names.size()) {
                output.println(names.get(count));
                output.println(levels.get(count));
                output.println();
                count++;
            }
            output.close();
        } catch (IOException e) {
        }
    }


    /**
     * This method clears the file containing the save progress.
     * <p>
     * <b> Local Variables: </b>
     * <p>
     * <b> output </b> The PrintWriter object used to overwrite the existing file.
     */
    public void clearFile() {
        PrintWriter output;

        try {
            output = new PrintWriter(new FileWriter("Save Progress.txt"));
            output.close();
        } catch (IOException e) {
        }
    }

    /**
     * This method returns the current level of a player with a saved game.
     *
     * @param name The name of the player whose data is to be loaded.
     * @return The level at which the game is saved. If the player does not have a saved file, returns -1.
     */
    public int getPlayerData(String name) {
        if (names.indexOf(name) == -1) return -1;
        return levels.get(names.indexOf(name));
    }
}