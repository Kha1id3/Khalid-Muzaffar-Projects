using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

public class GameController : Controller
{
    private const string SessionKeyRange = "Game_Range";
    private const string SessionKeyNumber = "Game_Number";
    private const string SessionKeyGuessCount = "Game_GuessCount";

    public IActionResult Set(int n)
    {
        HttpContext.Session.SetInt32(SessionKeyRange, n);
        HttpContext.Session.SetInt32(SessionKeyGuessCount, 0); // Reset guess count
        DrawNumber(); // Draw a new number within the range
        return RedirectToAction("Index");
    }

    public IActionResult Draw()
    {
        DrawNumber();
        return RedirectToAction("Index");
    }

    public IActionResult Guess(int guess)
    {
        int? range = HttpContext.Session.GetInt32(SessionKeyRange);
        if (!range.HasValue)
        {
            // If no range is set, redirect to a page or show a message to set the range first
            return RedirectToAction("Index", new { message = "Please set the range first." });
        }

        int? number = HttpContext.Session.GetInt32(SessionKeyNumber);
        int guessCount = HttpContext.Session.GetInt32(SessionKeyGuessCount) ?? 0;
        guessCount++; // Increment the guess count
        HttpContext.Session.SetInt32(SessionKeyGuessCount, guessCount);

        string message;
        if (guess < number)
        {
            message = $"Too small! Attempt #{guessCount}";
        }
        else if (guess > number)
        {
            message = $"Too high! Attempt #{guessCount}";
        }
        else
        {
            message = $"Correct! It took you {guessCount} attempts.";
            DrawNumber(); // Draw a new number for the next game
        }

        return RedirectToAction("Index", new { message });
    }

    private void DrawNumber()
    {
        int range = HttpContext.Session.GetInt32(SessionKeyRange) ?? 100; // Default to 100 if not set
        int drawnNumber = new Random().Next(0, range);
        HttpContext.Session.SetInt32(SessionKeyNumber, drawnNumber);
        HttpContext.Session.SetInt32(SessionKeyGuessCount, 0); // Reset guess count after drawing a new number
    }

    public IActionResult Index(string? message = null)
    {
        ViewBag.Message = message ?? "Make a guess!";
        ViewBag.GuessCount = HttpContext.Session.GetInt32(SessionKeyGuessCount) ?? 0;
        return View();
    }
}
