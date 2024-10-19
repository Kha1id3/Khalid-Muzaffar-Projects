import argparse
import datetime
import json
import random
import smtplib
import time
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText

import requests
from bs4 import BeautifulSoup
from rich import print


def send_email(msg_content):
    with open('config.json', 'r') as f:
        config = json.load(f)
    msg = MIMEMultipart()
    msg['From'] = config['email']
    msg['To'] = 'wojciech.thomas@pwr.edu.pl'
    nice_words = ['amazing', 'awesome', 'brilliant', 'fantastic', 'terrific']
    nice_word = random.choice(nice_words)
    msg[
        'Subject'] = f'A {nice_word} mail for the best professor in the world. Lab message sent on {datetime.datetime.now()}'

    msg.attach(MIMEText(msg_content, 'plain'))

    server = smtplib.SMTP('smtp.gmail.com', 587)
    server.starttls()
    server.login(config['email'], config['password'])
    server.send_message(msg)
    server.quit()


def cat_facts(number):
    response = requests.get(f'https://cat-fact.herokuapp.com/facts/random?amount={number}')
    facts = response.json()
    for i, fact in enumerate(facts):
        print(f"😺 [yellow]Cat Fact [cyan]{i + 1}[yellow]: [white]{fact['text']}")
        time.sleep(0.4)


def list_teachers(letter):
    response = requests.get(f'https://wit.pwr.edu.pl/wydzial/struktura-organizacyjna/pracownicy?letter={letter}')
    soup = BeautifulSoup(response.text, 'html.parser')

    teachers = soup.find_all('div', class_='col-text text-content')

    if not teachers:
        print(f"No teachers with last name starting with {letter}")
        return

    print(f"[green]---------------The list of researchers - {letter}--------------------")

    for teacher in teachers:

        name_tag = teacher.find('a', class_='title')
        if name_tag:
            name = name_tag.text.strip()
        else:
            name = "Name not found"

        email_tag = teacher.find('p')
        if email_tag:

            email = email_tag.text.replace('Email: ', '').strip()
        else:
            email = "Email not found"

        print(f"[yellow]{name} - [cyan]{email}")
        time.sleep(0.4)


def main():
    parser = argparse.ArgumentParser(description="Lab 9 Tasks ")
    parser.add_argument('--mail', type=str, help="Content of the mail to be sent")
    parser.add_argument('--cat-facts', type=int, help="Number of beautiful cat facts are below ps.I love cats")
    parser.add_argument('--teachers', type=str, help="Initial letter of teachers' last name")

    args = parser.parse_args()

    if args.mail:
        send_email(args.mail)

    if args.cat_facts:
        cat_facts(args.cat_facts)

    if args.teachers:
        list_teachers(args.teachers)


if __name__ == '__main__':
    main()
