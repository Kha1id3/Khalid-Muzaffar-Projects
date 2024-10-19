using Microsoft.AspNetCore.Http;
using System.ComponentModel.DataAnnotations;


namespace lab10.Models
{
public class ArticleViewModel
{
    public int Id { get; set; }

    [Required(ErrorMessage = "Name is required")]
    public string Name { get; set; }

    [Required(ErrorMessage = "Price is required")]
    [Range(0.01, double.MaxValue, ErrorMessage = "Price must be greater than 0")]
    public decimal Price { get; set; }

    // For uploading an image
    [Display(Name = "Image File")]
    public  IFormFile ImageFile { get; set; }

    // To display the current image (used in Edit view)
    public  string ImagePath { get; set; }

    [Required(ErrorMessage = "Category is required")]
    [Display(Name = "Category")]
    public int CategoryId { get; set; }

    // You can add additional properties as needed for your application
}
}