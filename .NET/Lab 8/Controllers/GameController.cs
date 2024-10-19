using Microsoft.AspNetCore.Mvc;
using System;
namespace QuadraticEquation.Controllers
{
public class GameController : Controller
{
    private readonly GameModel _gameModel = new GameModel();

    public IActionResult Set(int range)
    {
        _gameModel.SetRange(range);
        return RedirectToAction("Index");
    }

    public IActionResult Draw()
    {
        _gameModel.DrawNumber();
        return RedirectToAction("Index");
    }

    public IActionResult Guess(int guess)
    {
        string message = _gameModel.MakeGuess(guess);
        return RedirectToAction("Index", new { message });
    }

    public IActionResult Index(string? message = null)
    {
        ViewBag.Message = message;
        ViewBag.CssClass = GetMessageCssClass(message);
        return View(_gameModel);
    }

    private string GetMessageCssClass(string message)
    {
        if (message == "Too small!")
        {
            return "too-small";
        }
        else if (message == "Too high!")
        {
            return "too-high";
        }
        else if (message == "Correct!")
        {
            return "correct";
        }
        else
        {
            return "";
        }
    }
}
}