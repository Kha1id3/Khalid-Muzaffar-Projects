using Microsoft.AspNetCore.Mvc;
using QuadraticEquation.Models;

public class ToolController : Controller
{
    public IActionResult Solve(double a, double b, double c)
    {
        double discriminant = b * b - 4 * a * c;
        string message;
        string cssClass;

        if (a == 0 && b == 0 && c == 0)
        {
            message = "The equation is an identity.";
            cssClass = "identity";
        }
        else if (discriminant < 0)
        {
            message = "No real solutions.";
            cssClass = "no-solution";
        }
        else if (discriminant == 0)
        {
            double x = -b / (2 * a);
            message = $"One real solution: x = {x}";
            cssClass = "one-solution";
        }
        else
        {
            double x1 = (-b + Math.Sqrt(discriminant)) / (2 * a);
            double x2 = (-b - Math.Sqrt(discriminant)) / (2 * a);
            message = $"Two real solutions: x1 = {x1}, x2 = {x2}";
            cssClass = "two-solutions";
        }

        var resultModel = new QuadraticEquationResult
        {
            Message = message,
            CssClass = cssClass
        };

        return View(resultModel);
    }
}
