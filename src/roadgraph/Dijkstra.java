package roadgraph;

import java.util.List;
import util.*; // for here GeographicLoader
import geography.*;

/**
 * @author UCSD MOOC Development Team
* Arjun Singh and Royali Bahri
 */
public class Dijkstra implements Runnable {
    public int correct;

    private static final int TESTS = 4;
    public String getfeedback;



    /** Format readable feedback */
    public static String printOutput(double score, String feedback) {
        return "Score: " + score + "\nFeedback: " + feedback;
    }

    /** Format test number and description */
    public static String appendFeedback(int num, String test) {
        return "\n** Test #" + num + ": " + test + "...";
    }

    public static void main(String[] args) {
        Dijkstra grader = new Dijkstra();

        // In case of Infinite loop detection
        Thread thread = new Thread(grader);
        thread.start();
        long endTime = System.currentTimeMillis() + 10000;
        boolean infinite = false;
        while(thread.isAlive()) {
            // Stop thread after every 10 seconds
            if (System.currentTimeMillis() > endTime) {
                thread.stop();
                infinite = true;
                break;
            }
        }
        if (infinite) {
            System.out.println(printOutput((double)grader.correct / TESTS, grader.getfeedback + "\nYour program entered an infinite loop."));
        }
    }

// run the test cases with map provided by the ucsd team for adj list and matrix //
    public void runTest(int i, String file, String desc, GeographicPoint start, GeographicPoint end) {
    	mainDatastructure graph = new mainDatastructure();

        getfeedback += "\n\n" + desc;

        GraphLoader.loadRoadMap("data/graders/mod3/" + file, graph);
        CorrectAnswer corr = new CorrectAnswer("data/graders/mod3/" + file + ".answer", false);

        judge(i, graph, corr, start, end);
    }
//Compare the user's result with the right answer.
    // where end - ending point and start- starting point 
    // corr - correct answer
    // i - is the graph number 
    // result - is the final user graph 
   
    public void judge(int i, mainDatastructure result, CorrectAnswer corr, GeographicPoint start, GeographicPoint end) {
        // Correct if paths are same length and have the same elements
        getfeedback += appendFeedback(i, "Running Dijkstra's algorithm from (" + start.getX() + ", " + start.getY() + ") to (" + end.getX() + ", " + end.getY() + ")");
        Path graderObject = result.dijkstra(start, end);
        List<GeographicPoint> path = graderObject.getPath();
        if (path == null) {
            if (corr.path == null) {
                getfeedback += "PASSED.";
                correct++;
            } else {
                getfeedback += "FAILED. Your implementation returned null; expected \n" + printPath(corr.path) + ".";
            }
        } else if (path.size() != corr.path.size() || !corr.path.containsAll(path)) {
            getfeedback += "FAILED. Expected: \n" + printPath(corr.path) + "Got: \n" + printPath(path);
            if (path.size() != corr.path.size()) {
                getfeedback += "Your result has size " + path.size() + "; expected " + corr.path.size() + ".";
            } else {
                getfeedback += "Correct size, but incorrect path.";
            }
        } else {
            getfeedback += "PASSED.";
            correct++;
        }
    }

    /** Print a search path in readable form */
    public String printPath(List<GeographicPoint> path) {
        String ret = "";
        for (GeographicPoint point : path) {
            ret += point + "\n";
        }
        return ret;
    }

    /** Run the grader */
    @Override
    public void run() {
        getfeedback = "";

        correct = 0;

        try {
            runTest(1, "map1.txt", "MAP: Straight line (-3 <- -2 <- -1 <- 0 -> 1 -> 2-> 3 ->...)", new GeographicPoint(0, 0), new GeographicPoint(6, 6));

            runTest(2, "map2.txt", "MAP: Example map from the writeup", new GeographicPoint(7, 3), new GeographicPoint(4, -1));

            runTest(3, "map3.txt", "MAP: Right triangle (with a little detour)", new GeographicPoint(0, 0), new GeographicPoint(0, 4));

            runTest(4, "ucsd.map", "UCSD MAP: Intersections around UCSD", new GeographicPoint(32.8709815, -117.2434254), new GeographicPoint(32.8742087, -117.2381344));

            if (correct == TESTS)
                getfeedback = "All tests passed. Great job!" + getfeedback;
            else
                getfeedback = "Some tests failed. Check your code for errors, then try again:" + getfeedback;

        } catch (Exception e) {
            getfeedback += "\nError during runtime: " + e;
            e.printStackTrace();
        }
            
        System.out.println(printOutput((double)correct / TESTS, getfeedback));
    }
}
