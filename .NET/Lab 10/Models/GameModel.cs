using System;

public class GameModel
{
    public int Range { get; set; }
    public int DrawnNumber { get; set; }

    private static Random random = new Random();

    public int DrawNumber(int range)
    {
        Range = range;
        DrawnNumber = random.Next(0, range);
        return DrawnNumber;
    }

    public string MakeGuess(int guess)
    {
        if (guess < DrawnNumber)
        {
            return "Too small!";
        }
        else if (guess > DrawnNumber)
        {
            return "Too high!";
        }
        else
        {
            // Redraw a number for a new game
            DrawnNumber = random.Next(0, Range);
            return "Correct!";
        }
    }
}
