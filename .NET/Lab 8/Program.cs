var builder = WebApplication.CreateBuilder(args);
builder.Services.AddControllersWithViews();
var app = builder.Build();

if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();
app.UseRouting();
app.UseAuthorization();

app.MapControllerRoute(name: "solve", pattern: "Tool/Solve/{a}/{b}/{c}", defaults: new { controller = "Tool", action = "Solve" });
app.MapControllerRoute(name: "set", pattern: "Game/Set/{n}", defaults: new { controller = "Game", action = "Set" });
app.MapControllerRoute(name: "draw", pattern: "Game/Draw", defaults: new { controller = "Game", action = "Draw" });
app.MapControllerRoute(name: "guess", pattern: "Game/Guess/{number}", defaults: new { controller = "Game", action = "Guess" });
app.MapControllerRoute(name: "default", pattern: "{controller=Home}/{action=Index}/{id?}");

app.Run();
