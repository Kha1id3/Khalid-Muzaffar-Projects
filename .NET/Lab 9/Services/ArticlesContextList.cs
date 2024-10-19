using System.Collections.Generic;
using System.Linq;
using Lab9.Interfaces;
using Lab9.Models;


namespace Lab9.Services
{
    public class ArticlesContextList : IArticlesContext
{
    private List<Article> articles = new List<Article>();
    private int nextId = 1;

    public IEnumerable<Article> GetAll() => articles;

    public Article Get(int id) => articles.FirstOrDefault(a => a.Id == id);

    public void Add(Article article)
    {
        article.Id = nextId++;
        articles.Add(article);
    }

    public void Update(Article article)
    {
        var index = articles.FindIndex(a => a.Id == article.Id);
        if (index != -1)
        {
            articles[index] = article;
        }
    }

    public void Delete(int id) => articles.RemoveAll(a => a.Id == id);
}

}
