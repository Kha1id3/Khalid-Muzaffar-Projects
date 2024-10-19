using lab10.Models;



namespace lab10.Models
{
public class Category
{
    public int Id { get; set; }
    public string Name { get; set; }

    // Navigation property
    public List<Article> Articles { get; set; }
}
}