using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;
using Lab9.Services;
using Lab9.Interfaces; // Replace with the actual namespace of your IArticlesContext implementations

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddControllersWithViews();

// Register your IArticlesContext implementation
builder.Services.AddSingleton<IArticlesContext, ArticlesContextList>(); // or ArticlesContextDictionary

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseRouting();

app.UseAuthorization();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");

app.Run();
