import tkinter as tk
from tkinter import messagebox, ttk
from PIL import ImageTk, Image
import requests
import sqlite3
import json
import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

conn = sqlite3.connect('database.db')
c = conn.cursor()

c.execute("""
    CREATE TABLE IF NOT EXISTS posts (
        id integer,
        title text,
        price real,
        description text,
        category text,
        image text
    )
""")

root = tk.Tk()
root.title('Product Data Viewer')
root.configure(bg='black')

style = ttk.Style()
style.configure("TButton",
                foreground="black",
                background="light blue",
                font=("Arial", 12, "bold"),
                borderwidth="1")

status_line = tk.Label(root, bg='white', fg='black', font=("Arial", 25))
status_line.pack(side=tk.BOTTOM, fill=tk.X)

# Frame to hold the clear button
clear_frame = tk.Frame(root, bg='black')
clear_frame.pack(side=tk.TOP, anchor=tk.NE, padx=10, pady=10)

graph_displayed = None
cat_image_label = None


def clear_db():
    c.execute("DROP TABLE IF EXISTS posts")
    c.execute("""
        CREATE TABLE IF NOT EXISTS posts (
            id integer,
            title text,
            price real,
            description text,
            category text,
            image text
        )
    """)
    conn.commit()
    status_line['text'] = 'Database cleared ✅'


def download_data():
    try:
        response = requests.get('https://fakestoreapi.com/products')
        data = json.loads(response.text)

        for post in data:
            c.execute("INSERT INTO posts VALUES (:id, :title, :price, :description, :category, :image)",
                      {'id': post['id'], 'title': post['title'], 'price': post['price'],
                       'description': post['description'], 'category': post['category'], 'image': post['image']})

        conn.commit()
        status_line['text'] = 'Data downloaded ✅'
    except Exception as e:
        messagebox.showerror("Error", f"An error occurred while downloading data: {str(e)}")


def display_data():
    try:
        c.execute("SELECT AVG(price) FROM posts")
        avg_price = c.fetchone()[0]

        fig, ax = plt.subplots(figsize=(10, 5))
        ax.set_facecolor('gray')
        fig.patch.set_facecolor('black')

        c.execute("SELECT title, price FROM posts")
        rows = c.fetchall()

        titles = [row[0] for row in rows]
        prices = [row[1] for row in rows]

        truncated_titles = [title.split(' ', 2)[:2] for title in titles]
        truncated_titles = [' '.join(words) for words in truncated_titles]

        ax.bar(truncated_titles, prices, color='blue')
        ax.set_xlabel('Product', fontsize=10, color='white')
        ax.set_ylabel('Price', fontsize=10, color='white')
        ax.set_title('Product Prices', fontsize=15, color='white')
        ax.tick_params(axis='x', rotation=60, colors='white')
        ax.tick_params(axis='y', colors='white')

        plt.tight_layout()

        canvas = FigureCanvasTkAgg(fig, master=root)
        canvas.draw()
        canvas.get_tk_widget().pack()

        global graph_displayed
        graph_displayed = True

        status_line['text'] = f'Data displayed ✅. Average price: {avg_price:.2f}'

    except Exception as e:
        messagebox.showerror("Error", f"An error occurred while displaying data: {str(e)}")


def display_cat_image():
    global cat_image_label

    try:
        response = requests.get('https://api.thecatapi.com/v1/images/search')
        data = json.loads(response.text)

        image_url = data[0]['url']
        image_response = requests.get(image_url)

        with open('cat_image.jpg', 'wb') as image_file:
            image_file.write(image_response.content)

        image = Image.open('cat_image.jpg')
        image = image.resize((250, 120), Image.LANCZOS)
        cat_image = ImageTk.PhotoImage(image)

        if cat_image_label is not None:
            cat_image_label.destroy()

        cat_image = ImageTk.PhotoImage(image)

        cat_image_label = tk.Label(root, image=cat_image)
        cat_image_label.image = cat_image
        cat_image_label.pack(side=tk.BOTTOM, padx=10, pady=10)

        status_line['text'] = 'Cat image displayed ✅'

    except Exception as e:
        messagebox.showerror("Error", f"An error occurred while downloading a cat image: {str(e)}")


clear_button = ttk.Button(clear_frame, text='Clear Database \U0001F6AE', command=clear_db)
download_button = ttk.Button(root, text='Download Data \U0001F4E2', command=download_data)
display_button = ttk.Button(root, text='Display Data \U0001F4B8', command=display_data)
cat_image_button = ttk.Button(root, text='Display Cat Image \U0001F431', command=display_cat_image)

clear_button.pack(side=tk.RIGHT)
download_button.pack(side=tk.LEFT, anchor=tk.NW, padx=10, pady=10)
display_button.pack(pady=10)
cat_image_button.pack(side=tk.BOTTOM, anchor=tk.SE, padx=10, pady=10)

root.mainloop()

conn.close()
