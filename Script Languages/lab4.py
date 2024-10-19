from datetime import datetime
from ipaddress import IPv4Address, IPv4Network
import sys


def timestamp_log(timestamp_str):
    parts = timestamp_str.split(':')
    day_str, month_str, year_str = parts[0].split('/')
    hour_str, minute_str, second_str = parts[1], parts[2], parts[3]
    year = int(year_str)
    month = datetime.strptime(month_str, '%b').month
    day = int(day_str)
    hour = int(hour_str)
    minute = int(minute_str)
    second = int(second_str)
    return datetime(year, month, day, hour, minute, second)


class LogRecord:
    def __init__(self, ip, timestamp, request, status_code):
        self.ip = ip
        self.timestamp = timestamp
        self.request = request
        self.status_code = status_code

    def __str__(self):
        return f"{self.ip} | {self.timestamp} | {self.request} | {self.status_code}"

    def __repr__(self):
        return f"LogRecord({self.ip!r}, {self.timestamp!r}, {self.request!r}, {self.status_code!r})"

    def is_in_network(self, network):
        return self.ip in IPv4Network(network)


def log_entry_line(entry_line):
    components = entry_line.split()
    ip = IPv4Address(components[0])
    timestamp = timestamp_log(components[1])
    request = components[2]
    status_code = int(components[3])
    return LogRecord(ip, timestamp, request, status_code)


def load_log_entries(entry_lines):
    return [log_entry_line(line) for line in entry_lines]


def show_requests_in_time_range(log_records, start, end):
    if end < start:
        print("Warning: The end time is earlier than the start time.")
        return

    for record in log_records:
        if start <= record.timestamp <= end:
            print(record)


def filter_by_status_code(log_records, status_code):
    return [record for record in log_records if record.status_code == status_code]


if __name__ == '__main__':
    input_lines = sys.stdin.readlines()
    log_records = load_log_entries(input_lines)

start_datetime = datetime(2020, 10, 18, 0, 0, 0)
end_datetime = datetime(2020, 10, 18, 4, 59, 59)

formal_representation = repr(log_records)
print(formal_representation)

network_range = "152.32.65.99"
print(f"\nChecking if IP addresses belong to network {network_range}:")
for record in log_records:
    result = record.is_in_network(network_range)
    print(f"{record.ip} belongs to {network_range}: {result}")


print("\n----Displaying requests between times:-----")
show_requests_in_time_range(log_records, start_datetime, end_datetime)


for record in log_records:
    result = record.is_in_network(network_range)
    print(f"{record.ip} belongs to {network_range}: {result}")

print("\nFiltering log entries by status code:")
status_code_to_filter = 200
filtered_records = filter_by_status_code(log_records, status_code_to_filter)
for record in filtered_records:
    print(record)


#https://docs.python.org/3.11/library/ipaddress.html#ipaddress.IPv4Network
#https://docs.python.org/3.11/library/datetime.html#datetime.datetime.strptime