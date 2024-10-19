using lab10.Models;
using Microsoft.EntityFrameworkCore;


namespace lab10.Data
{
public class StoreContext : DbContext
{
    public StoreContext(DbContextOptions<StoreContext> options)
        : base(options)
    {
    }

    public DbSet<Article> Articles { get; set; }
    public DbSet<Category> Categories { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

          modelBuilder.Entity<Category>().HasData(
            new Category { Id = 1, Name = "Electronics" },
            new Category { Id = 2, Name = "Books" },
            new Category { Id = 3, Name = "Clothing" }
        );

        // Define relationships and any additional configuration here
        // For example, if you have specific requirements for table names or relationships
    }
}
}