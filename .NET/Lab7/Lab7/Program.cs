using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;

namespace LectureLINQ
{
    // Task 1: Define the Person class and Topic class
    class Person
    {
        public string Name { get; set; }
        public int Age { get; set; }
        public bool IsActive { get; set; }
        public List<string> Topics { get; set; }
        public string Gender { get; set; }

        public Person(string name, int age, bool isActive, List<string> topics, string gender)
        {
            Name = name;
            Age = age;
            IsActive = isActive;
            Topics = topics;
            Gender = gender;
        }
    }

    class Topic
    {
        public int ID { get; set; }
        public string Name { get; set; }

        public Topic(int id, string name)
        {
            ID = id;
            Name = name;
        }
    }

    // Task 3: Define the Student class to store topic IDs
    class Student
    {
        public int ID { get; set; }
        public string Name { get; set; }
        public int Age { get; set; }
        public List<int> TopicIDs { get; set; }

        public Student(int id, string name, int age, List<int> topicIDs)
        {
            ID = id;
            Name = name;
            Age = age;
            TopicIDs = topicIDs;
        }
    }

    // Task 2: Define the StudentWithTopics class
    class StudentWithTopics
    {
        public int ID { get; set; }
        public string Name { get; set; }
        public int Age { get; set; }
        public List<string> Topics { get; set; }

        public StudentWithTopics(int id, string name, int age, List<string> topics)
        {
            ID = id;
            Name = name;
            Age = age;
            Topics = topics;
        }
    }

    class Program
    {
        // Sample data for Persons (Task 1)
        static List<Person> Persons { get; set; } = new List<Person>()
        {
            new Person("Muzaffar", 22, true, new List<string>{"Mathamatics", "DSA"}, "Male"),
            new Person("Marym", 40, false, new List<string>{"DSA", "Logic"}, "Female"),
            new Person("Ahmed", 30, true, new List<string>{"Mathamatics", "English"}, "Male"),
            new Person("Alex", 12, true, new List<string>{"Logic", "English"}, "Male"),
            new Person("Marta", 55, false, new List<string>{"Mathamatics", "DSA"}, "Female")
        };

        // Sample data for StudentsWithTopics (Task 2)
        static List<StudentWithTopics> StudentsWithTopics { get; set; } = new List<StudentWithTopics>
        {
            new StudentWithTopics(1, "Khalid", 22, new List<string> { "Mathamatics", "DSA" }),
            new StudentWithTopics(2, "Ziad", 20, new List<string> { "DSA", "Logic" }),
            new StudentWithTopics(3, "Mert", 24, new List<string> { "Mathamatics", "English" }),
          
        };

        // Task 1: Group students by last name and index
        static void GroupStudents(int n)
        {
            var groupedStudents = StudentsWithTopics
                .OrderBy(student => student.Name)
                .ThenBy(student => student.Age)
                .Select((student, index) => new { student, index })
                .GroupBy(x => x.index / n, x => x.student);

            Console.WriteLine($"Students grouped into {n} elements per group:");
            foreach (var group in groupedStudents)
            {
                Console.WriteLine($"Group {group.Key + 1}:");
                foreach (var student in group)
                {
                    Console.WriteLine($"{student.Name} ({student.Age})");
                }
                Console.WriteLine();
            }
        }

        // Task 2: Sort topics by frequency
        static void SortTopicsByFrequency()
        {
            var allTopics = StudentsWithTopics
                .SelectMany(student => student.Topics)
                .GroupBy(topic => topic)
                .OrderByDescending(topicGroup => topicGroup.Count())
                .Select(topicGroup => new { Topic = topicGroup.Key, Count = topicGroup.Count() });

            Console.WriteLine("Topics sorted by frequency of occurrence:");
            foreach (var topicInfo in allTopics)
            {
                Console.WriteLine($"{topicInfo.Topic}: {topicInfo.Count} times");
            }
        }

        // Task 3: Transform StudentWithTopics objects into Student objects (Minimalist solution)
        static void TransformStudentsWithTopicsToStudents()
        {
            // Predefined list of topics
            List<Topic> topics = new List<Topic>
            {
                new Topic(1, "Mathamatics"),
                new Topic(2, "DSA"),
                new Topic(3, "Logic"),
                new Topic(4, "English"),
                // Add more topics as needed
            };

            List<Student> students = StudentsWithTopics.Select(studentWithTopics =>
                new Student(studentWithTopics.ID, studentWithTopics.Name, studentWithTopics.Age,
                    studentWithTopics.Topics.Select(topicName =>
                        topics.First(t => t.Name == topicName).ID).ToList())).ToList();

            Console.WriteLine("Students with topic IDs:");
            foreach (var student in students)
            {
                Console.WriteLine($"{student.Name} ({student.Age}), Topics: [{string.Join(", ", student.TopicIDs)}]");
            }
        }

        // Task 4: Invoke a method using reflection
        static void InvokeMethodWithReflection()
        {
            // Step 1: Create an instance of the MyClass class using reflection
            Type myClassType = typeof(MyClass);
            object myClassInstance1 = Activator.CreateInstance(myClassType);
            object myClassInstance2 = Activator.CreateInstance(myClassType);

            // Step 2: Get the MethodInfo for the Add method
            MethodInfo addMethod = myClassType.GetMethod("Add");

            if (addMethod != null)
            {
                // Step 3: Invoke the Add method on the first instance with parameters
                object[] parameters = { 5, 7 };
                int result1 = (int)addMethod.Invoke(myClassInstance1, parameters);
                Console.WriteLine($"Result from instance 1: {result1}");

                // Step 4: Invoke the Add method on the second instance with different parameters
                object[] parameters2 = { 10, 20 };
                int result2 = (int)addMethod.Invoke(myClassInstance2, parameters2);
                Console.WriteLine($"Result from instance 2: {result2}");
            }
            else
            {
                Console.WriteLine("Method not found.");
            }
        }

     static void Main()
{
    int n = 2; // Adjust 'n' as needed

    // Task 1: Group students by last name and index
    Console.WriteLine("Task 1: Group students by last name and index");
    GroupStudents(n);

    // Task 2: Sort topics by frequency
    Console.WriteLine("\nTask 2: Sort topics by frequency");
    SortTopicsByFrequency();

    // Task 3: Transform StudentWithTopics objects into Student objects (Minimalist solution)
    Console.WriteLine("\nTask 3: Transform StudentWithTopics objects into Student objects (Minimalist solution)");
    TransformStudentsWithTopicsToStudents();

    // Task 4: Invoke a method using reflection
    Console.WriteLine("\nTask 4: Invoke a method using reflection");
    InvokeMethodWithReflection();
}

    }
}

// Additional class used for Task 4
public class MyClass
{
    public int Add(int a, int b)
    {
        return a + b;
    }
}
