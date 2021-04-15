import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A data structure for movie.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 11 2021
 */
class Movie
{
    private String name;
    private int year;
    private String rating;
    private int duration;
    private String genre;
    private double score;

    public Movie()
    {
    }

    public Movie(String name, int year, String rating, int duration, String genre, double score)
    {
        this.name = name;
        this.year = year;
        this.rating = rating;
        this.duration = duration;
        this.genre = genre;
        this.score = score;
    }

    public static Movie readLine(String line)
    {
        String[] values = line.split(",");
        assert values.length == 6;
        String name = values[0];
        int year = Integer.parseInt(values[1]);
        String rating = values[2];
        int duration = Integer.parseInt(values[3]);
        String genre = values[4];
        double score = Double.parseDouble(values[5]);
        return new Movie(name, year, rating, duration, genre, score);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getScore()
    {
        return score;
    }

    public void setScore(double score)
    {
        this.score = score;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public String getRating()
    {
        return rating;
    }

    public void setRating(String rating)
    {
        this.rating = rating;
    }
}


/**
 * A program that filters films.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 11 2021
 */
public class Movies
{

    private static final String INVALID_RATING = "Rating must be one of the following: PG, G, PG-13, NR, R";
    private static final String INVALID_SCORE = "Score must be between 0 and 10";
    private static final String INVALID_DURATION = "Duration must be between 0 and 300";
    private static final String INVALID_YEAR = "Year must be before 2022";


    public ArrayList<Movie> moviesList = new ArrayList<>();

    public Movies()
    {
        try
        {
            readFile();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        Movies movies = new Movies();

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter Movie Rating:");

        String rating = scan.nextLine();

        System.out.println("Enter Movie Duration:");

        int duration = scan.nextInt();

        System.out.println("Enter Movie Score:");

        double score = scan.nextDouble();

        System.out.println("Will the filter be greater or less than?");

        boolean greaterThan = scan.nextBoolean();

        System.out.println("Enter Movie Year:");

        int year = scan.nextInt();

        try
        {
            movies.validateInput(rating, score, duration, year);
        } catch (InvalidInputException e)
        {
            e.printStackTrace();
        }
        movies.makeRatingFile(rating);
        movies.makeDurationFile(duration, greaterThan);
        movies.makeScoreFile(score, greaterThan);
        movies.makeYearFile(year);
    }

    public void readFile() throws FileNotFoundException
    {
        String filename = "movieData.txt";
//        URL url = getClass().getResource(filename);
//        File f = new File(url.getPath());
        File f = new File(filename);
        if (f.exists())
        {
            FileReader fr = new FileReader(f);

            try (BufferedReader bfr = new BufferedReader(fr))
            {

                String line = bfr.readLine();
                while (line != null)
                {
                    moviesList.add(Movie.readLine(line));
                    line = bfr.readLine();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        } else
        {
            throw new FileNotFoundException(filename);
        }
    }

    public void makeRatingFile(String rating)
    {
        ArrayList<String> lines = new ArrayList<>();
        for (Movie movie : moviesList)
        {
            if (movie.getRating().equals(rating))
            {
                lines.add(movie.getName() + " | " + movie.getGenre());

            }
        }
        String filename = "ratings.txt";
        writeFile(lines, filename);
    }

    private void writeFile(ArrayList<String> lines, String filename)
    {
        File f = new File(filename);
        try
        {
//            f.delete();
            f.createNewFile();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
//        {
            FileOutputStream fos = new FileOutputStream(f, false);
            PrintWriter pw = new PrintWriter(fos);
            // For an ArrayList named `names`
            for (String line : lines)
            {
                pw.println(line);
            }

            pw.close();
//            fos.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void makeScoreFile(double score, boolean greaterThan)
    {
        ArrayList<String> lines = new ArrayList<>();
        for (Movie movie : moviesList)
        {
            if (movie.getScore() > score == greaterThan)
            {
                lines.add(movie.getName() + " | " + movie.getGenre());
            }
        }
        String filename = "scores.txt";
        writeFile(lines, filename);
    }

    public void makeDurationFile(int duration, boolean greaterThan)
    {
        ArrayList<String> lines = new ArrayList<>();
        for (Movie movie : moviesList)
        {
            if (movie.getDuration() > duration == greaterThan)
            {
                lines.add(movie.getName() + " | " + movie.getGenre());
            }
        }
        String filename = "durations.txt";
        writeFile(lines, filename);
    }

    public void makeYearFile(int year)
    {
        ArrayList<String> lines = new ArrayList<>();
        for (Movie movie : moviesList)
        {
            if (movie.getYear() == year)
            {
                lines.add(movie.getName() + " | " + movie.getGenre());
            }
        }
        String filename = "years.txt";
        writeFile(lines, filename);
    }

    public void validateInput(String rating, double score, int duration, int year) throws InvalidInputException
    {
        switch (rating)
        {
            case "PG", "G", "PG-13", "NR", "R":
                break;
            default:
                throw new InvalidInputException(INVALID_RATING);
        }
        if (score < 0 || score > 10)
        {
            throw new InvalidInputException(INVALID_SCORE);
        }
        if (duration < 0 || duration > 300)
        {
            throw new InvalidInputException(INVALID_DURATION);
        }
        if (year >= 2022 || year < 1000)
        {
            throw new InvalidInputException(INVALID_YEAR);
        }


    }


}