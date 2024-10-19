using System;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using lab10.Models;
using lab10.Data;
using Microsoft.AspNetCore.Mvc.Rendering;

namespace LAB10.Controllers
{
public class ArticlesController : Controller
{
    private readonly StoreContext _context;
    private readonly IWebHostEnvironment _webHostEnvironment;

    public ArticlesController(StoreContext context, IWebHostEnvironment webHostEnvironment)
    {
        _context = context;
        _webHostEnvironment = webHostEnvironment;
    }

    // GET: Articles
    public async Task<IActionResult> Index()
    {
        var articles = await _context.Articles.Include(a => a.Category).ToListAsync();
        return View(articles);
    }

    // GET: Articles/Details/5
    public async Task<IActionResult> Details(int? id)
    {
        if (id == null)
        {
            return NotFound();
        }

        var article = await _context.Articles
            .Include(a => a.Category)
            .FirstOrDefaultAsync(m => m.Id == id);

        if (article == null)
        {
            return NotFound();
        }

        return View(article);
    }

    // GET: Articles/Create
    public IActionResult Create()
    {
        ViewBag.Categories = new Microsoft.AspNetCore.Mvc.Rendering.SelectList(_context.Categories, "Id", "Name");
      return View();
       
    }

        // POST: Articles/Create
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Name,Price,ImageFile,CategoryId")] ArticleViewModel model)
        {
  
                string? uniqueFileName = null;
                if (model.ImageFile != null)
                {
                    string uploadsFolder = Path.Combine(_webHostEnvironment.WebRootPath, "images");
                    uniqueFileName = Guid.NewGuid().ToString() + "_" + model.ImageFile.FileName;
                    string filePath = Path.Combine(uploadsFolder, uniqueFileName);
                    using (var fileStream = new FileStream(filePath, FileMode.Create))
                    {
                        model.ImageFile.CopyTo(fileStream);
                    }
                }

                Article article = new Article
                {
                    Name = model.Name,
                    Price = model.Price,
                    ImagePath = uniqueFileName,
                    CategoryId = model.CategoryId
                };

                try
                {
                    _context.Add(article);
                    await _context.SaveChangesAsync();
                    return RedirectToAction(nameof(Index));
                }
                catch (Exception ex)
                {
                   
                    ModelState.AddModelError("", "An error occurred while saving the article. Please try again.");

                    
                    return View(model);
                }
            

          
         
        }


        // GET: Articles/Edit/5
public async Task<IActionResult> Edit(int? id)
{
    if (id == null)
    {
        return NotFound();
    }

    var article = await _context.Articles.FindAsync(id);
    if (article == null)
    {
        return NotFound();
    }

    var articleViewModel = new ArticleViewModel
    {
        Id = article.Id,
        Name = article.Name,
        Price = article.Price,
        ImagePath = article.ImagePath, // Only set the ImagePath, ImageFile is for uploads
        CategoryId = article.CategoryId
    };

    // Populate the ViewBag with categories for the dropdown.
    ViewBag.CategoryId = new SelectList(_context.Categories, "Id", "Name", article.CategoryId);

    return View(articleViewModel);
}



[HttpPost]
[ValidateAntiForgeryToken]
public async Task<IActionResult> Edit(int id, [Bind("Id,Name,Price,ImageFile,CategoryId")] ArticleViewModel model)
{
    if (id != model.Id)
    {
        return NotFound();
    }

    if (ModelState.IsValid)
    {
        try
        {
            // Find the existing article from the database
            var articleToUpdate = await _context.Articles.FindAsync(id);

            // Update the article with the values from the model
            if (articleToUpdate != null)
            {
                articleToUpdate.Name = model.Name;
                articleToUpdate.Price = model.Price;
                // Handle image file update if necessary
                // ...
                articleToUpdate.CategoryId = model.CategoryId;

                _context.Update(articleToUpdate);
                await _context.SaveChangesAsync();
            }
        }
        catch (DbUpdateConcurrencyException)
        {
            if (!ArticleExists(model.Id))
            {
                return NotFound();
            }
            else
            {
                throw;
            }
        }
        return RedirectToAction(nameof(Index));
    }

    // If we got this far, something failed; redisplay the form
    ViewBag.Categories = new Microsoft.AspNetCore.Mvc.Rendering.SelectList(_context.Categories, "Id", "Name", model.CategoryId);
    return View(model);
}


    // GET: Articles/Delete/5
    public async Task<IActionResult> Delete(int? id)
    {
        if (id == null)
        {
            return NotFound();
        }

        var article = await _context.Articles
            .Include(a => a.Category)
            .FirstOrDefaultAsync(m => m.Id == id);

        if (article == null)
        {
            return NotFound();
        }

        return View(article);
    }

    // POST: Articles/Delete/5
    [HttpPost, ActionName("Delete")]
    [ValidateAntiForgeryToken]
    public async Task<IActionResult> DeleteConfirmed(int id)
    {
        var article = await _context.Articles.FindAsync(id);
        if (article.ImagePath != null)
        {
            var imagePath = Path.Combine(_webHostEnvironment.WebRootPath, "images", article.ImagePath);
            if (System.IO.File.Exists(imagePath))
            {
                System.IO.File.Delete(imagePath);
            }
        }
        _context.Articles.Remove(article);
        await _context.SaveChangesAsync();
        return RedirectToAction(nameof(Index));
    }

    private bool ArticleExists(int id)
    {
        return _context.Articles.Any(e => e.Id == id);
    }
}
}