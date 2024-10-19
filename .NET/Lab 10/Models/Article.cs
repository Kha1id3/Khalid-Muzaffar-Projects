using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace lab10.Models
{

public class Article
{
    public int Id { get; set; }
    public string Name { get; set; }
    public decimal Price { get; set; }
    public string ImagePath { get; set; } // Nullable for cases where no image is provided
    public int CategoryId { get; set; }   // Foreign key reference to Category

    // Navigation property
    public Category Category { get; set; }
}
}