using Microsoft.AspNetCore.Mvc;
using Lab9.Models; // Replace with your actual namespace
using System.Linq;
using Lab9.Interfaces;

public class ArticlesController : Controller
{
    private readonly IArticlesContext _context;

    public ArticlesController(IArticlesContext context)
    {
        _context = context;
    }

    // GET: Articles
    public IActionResult Index()
    {
        var articles = _context.GetAll();
        return View(articles);
    }

    // GET: Articles/Create
    public IActionResult Create()
    {
        return View();
    }

    // POST: Articles/Create
    [HttpPost]
    [ValidateAntiForgeryToken]
    public IActionResult Create(Article article)
    {
        if (ModelState.IsValid)
        {
            _context.Add(article);
            return RedirectToAction(nameof(Index));
        }
        return View(article);
    }

    // GET: Articles/Edit/5
    public IActionResult Edit(int id)
    {
        var article = _context.Get(id);
        if (article == null)
        {
            return NotFound();
        }
        return View(article);
    }

    // POST: Articles/Edit/5
    [HttpPost]
    [ValidateAntiForgeryToken]
    public IActionResult Edit(int id, Article article)
    {
        if (id != article.Id)
        {
            return NotFound();
        }

        if (ModelState.IsValid)
        {
            _context.Update(article);
            return RedirectToAction(nameof(Index));
        }
        return View(article);
    }

    // GET: Articles/Delete/5
    public IActionResult Delete(int id)
    {
        var article = _context.Get(id);
        if (article == null)
        {
            return NotFound();
        }
        return View(article);
    }

    // POST: Articles/Delete/5
    [HttpPost, ActionName("Delete")]
    [ValidateAntiForgeryToken]
    public IActionResult DeleteConfirmed(int id)
    {
        _context.Delete(id);
        return RedirectToAction(nameof(Index));
    }
}
