import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Parser {

    public Puzzle parse()  {
        Puzzle puzzle = new Puzzle();
        Gson gson = new Gson();
        String file = "src/problem-1.json";
        try
        {
            puzzle = gson.fromJson(new FileReader(file), Puzzle.class);
        }
        catch(FileNotFoundException exception) {
            System.out.println("File not found.");
        }
        return puzzle;
    }
}
