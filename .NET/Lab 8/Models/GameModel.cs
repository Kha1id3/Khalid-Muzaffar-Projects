using System;

public class GameModel
{
    public int Range { get; private set; }
    public int DrawnNumber { get; private set; }

    public void SetRange(int range)
    {
        Range = range;
        DrawnNumber = new Random().Next(0, range);
    }

    public void DrawNumber()
    {
        DrawnNumber = new Random().Next(0, Range);
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
            return "Correct!";
        }
    }
}
