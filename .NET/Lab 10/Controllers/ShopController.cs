using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using lab10.Models; // Replace with your actual namespace
using System.Linq;
using System.Threading.Tasks;
using lab10.Data;

namespace LAB10.Controllers
{
    public class ShopController : Controller
    {
        private readonly StoreContext _context;

        public ShopController(StoreContext context)
        {
            _context = context;
        }

    // GET: Shop
    public async Task<IActionResult> Index(int? categoryId)
    {
        var categories = await _context.Categories.ToListAsync();
        ViewBag.Categories = categories;

        var articlesQuery = _context.Articles.Include(a => a.Category).AsQueryable();
        if (categoryId.HasValue)
        {
            articlesQuery = articlesQuery.Where(a => a.CategoryId == categoryId.Value);
        }

        var articles = await articlesQuery.ToListAsync();
        return View(articles);
    }

    // GET: Shop/Details/5
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

     public IActionResult AddToCart(int id)
        {
            string cartKey = $"article{id}";

            // Retrieve the current count from the cookie
            int currentCount = int.Parse(Request.Cookies[cartKey] ?? "0");
            currentCount++;

            // Set the updated count in the cookie
            CookieOptions options = new CookieOptions
            {
                Expires = DateTime.Now.AddDays(7) // Expires in 1 week
            };
            Response.Cookies.Append(cartKey, currentCount.ToString(), options);

            return RedirectToAction("Index");
        }
}
}