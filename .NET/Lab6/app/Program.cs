using System;
using System.Linq;

class Employee
{
    public string EmployeeName { get; set; }
    public string DepartmentName { get; set; }
    public decimal Salary { get; set; }

    public Employee(string employeeName, string departmentName, decimal salary)
    {
        EmployeeName = employeeName;
        DepartmentName = departmentName;
        Salary = salary;
    }

    public override string ToString()
    {
        return $"Name: {EmployeeName}, Department: {DepartmentName}, Salary: {Salary}";
    }
}

class Program
{
    static void Main(string[] args)
    {

        // Task 1
var personTuple = ("John", "Doe", 30, 60000);

// Method 1: Using a foreach loop
foreach (var item in personTuple.GetType().GetFields())
{
    Console.WriteLine(item.GetValue(personTuple));
}

// Method 2: Using tuple element names
Console.WriteLine($"{personTuple.Item1} {personTuple.Item2}, Age: {personTuple.Item3}, Salary: {personTuple.Item4}");





// Method 3: Item assignment
var (name, surname, age, salary) = personTuple;
Console.WriteLine($"Name: {name}, Surname: {surname}, Age: {age}, Salary: {salary}");


// Task 2
var @class = "Software Engineering";
Console.WriteLine(@class);


// Task 3
int[] array = { 1, 3, 2, 4, 5 };

Array.Sort(array);
Array.Reverse(array);
int indexOf3 = Array.IndexOf(array, 3);
int[] copiedArray = new int[5];
Array.Copy(array, copiedArray, array.Length);
bool exists = Array.Exists(array, element => element == 3);
Console.WriteLine("[{0}]", string.Join(", ", array));

// Task 4 
 // In C#, anonymous types work similarly to tuples for printing.


void DrawCard(string firstLine, string secondLine, char borderChar = 'X', int borderWidth = 2, int minWidth = 10)
{
    int maxLineLength = Math.Max(firstLine.Length, secondLine.Length);
    int cardWidth = Math.Max(minWidth, maxLineLength - 1); // plus 4 for the border 'XX' on each side
    string topBottomBorder = new string(borderChar, cardWidth);

    
    for (int i = 0; i < borderWidth; i++)
    {
        Console.WriteLine(topBottomBorder);
    }

    
    Console.WriteLine(new string(borderChar, borderWidth) + 
                      firstLine.PadLeft((cardWidth + firstLine.Length) / 2)
                               .PadRight(cardWidth - 2 * borderWidth) + 
                      new string(borderChar, borderWidth));

    Console.WriteLine(new string(borderChar, borderWidth) + 
                      secondLine.PadLeft((cardWidth + secondLine.Length) / 2)
                                .PadRight(cardWidth - 2 * borderWidth) + 
                      new string(borderChar, borderWidth));


    

    // Print the bottom border
    for (int i = 0; i < borderWidth; i++)
    {
        Console.WriteLine(topBottomBorder);
    }
}


DrawCard("James", "meow", minWidth: 20);






// Task 6
void CountMyTypes(params object[] items)
{
    int intCount = 0, doubleCount = 0, stringCount = 0;
    foreach (var item in items)
    {
     switch (item)
{
    case int i when i > 0: // Check for positive integers
        intCount++;
        break;
    case double d when d > 0: // Check for positive real numbers (doubles)
        doubleCount++;
        break;
    case string s when s.Length >= 5: // Check for strings with at least 5 characters
        stringCount++;
        break;
    // Additional cases can be added as needed
}

    }
    Console.WriteLine($"Integers: {intCount}, Doubles: {doubleCount}, Strings: {stringCount}");
}

CountMyTypes(1, 2.0, "hello", 3, 4.5, "world");
        // Your existing code for tasks 1 to 6 goes here.

        // Task 7: Employee array and sorting
        Employee[] employees = new Employee[10]
        {
            new Employee("Alice", "IT", 50000),
            new Employee("Bob", "HR", 45000),
            new Employee("Charlie", "IT", 70000),
            new Employee("Dave", "Finance", 55000),
            new Employee("Eve", "HR", 48000),
            new Employee("Frank", "Marketing", 52000),
            new Employee("Grace", "Finance", 53000),
            new Employee("Henry", "IT", 60000),
            new Employee("Isabel", "Marketing", 47000),
            new Employee("Jack", "HR", 51000)
        };

        // Sort by name and then by salary
        var sortedEmployees = employees.OrderBy(e => e.EmployeeName).ThenBy(e => e.Salary).ToArray();

        foreach (var emp in sortedEmployees)
        {
            Console.WriteLine(emp);
        }
    }
}
