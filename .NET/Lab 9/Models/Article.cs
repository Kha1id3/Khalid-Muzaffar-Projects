using System;

namespace Lab9.Models
{
   public class Article
{
    public int Id { get; set; }
    public string? Name { get; set; }
    public decimal Price { get; set; }
    public DateTime ExpirationDate { get; set; }
    public string? Category { get; set; }
}

}
