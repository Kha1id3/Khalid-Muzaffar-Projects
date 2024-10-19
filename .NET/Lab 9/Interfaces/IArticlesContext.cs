using System.Collections.Generic;
using Lab9.Models;


namespace Lab9.Interfaces
{
public interface IArticlesContext
{
    IEnumerable<Article> GetAll();
    Article Get(int id);
    void Add(Article article);
    void Update(Article article);
    void Delete(int id);
}

}
