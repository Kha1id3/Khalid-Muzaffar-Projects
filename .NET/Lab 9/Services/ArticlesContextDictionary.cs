using Lab9.Interfaces;
using Lab9.Models;

namespace Lab9.Services
{
    public class ArticlesContextDictionary : IArticlesContext
{
    private Dictionary<int, Article> articles = new Dictionary<int, Article>();
    private int nextId = 1;

    public IEnumerable<Article> GetAll() => articles.Values;

    public Article Get(int id)
    {
        articles.TryGetValue(id, out var article);
        return article;
    }

    public void Add(Article article)
    {
        article.Id = nextId++;
        articles[article.Id] = article;
    }

    public void Update(Article article)
    {
        if (articles.ContainsKey(article.Id))
        {
            articles[article.Id] = article;
        }
    }

    public void Delete(int id) => articles.Remove(id);
}

}
