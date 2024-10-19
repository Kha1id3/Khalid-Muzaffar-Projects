import re
import sys
import os
from datetime import datetime
from typing import List, Dict
import ipaddress


def read_config_file(config_file: str) -> Dict[str, Dict[str, str]]:
    if not os.path.exists(config_file):
        print(f"Config file {config_file} not found. Exiting.")
        sys.exit(1)

    with open(config_file, "r") as file:
        content = file.readlines()

    sections = {}

    for line in content:
        line = line.strip()
        if re.match(r"\[(\w+)\]", line):
            section = re.findall(r"\[(\w+)\]", line)[0]
            sections[section] = {}
        elif re.match(r"(\w+)=(.*)", line):
            key, value = re.findall(r"(\w+)=(.*)", line)[0]
            sections[section][key] = value

    display_defaults = {"lines": "6", "separator": "|", "filter": "GET"}
    for key, value in display_defaults.items():
        if key not in sections["Display"]:
            sections["Display"][key] = value

    return sections


def read_log_file(log_file: str) -> List[str]:
    if not os.path.exists(log_file):
        print(f"Log file {log_file} not found. Exiting.")
        sys.exit(1)

    with open(log_file, "r") as file:
        content = file.readlines()

    return content


def parse_log_line(log_line: str) -> Dict[str, str]:
    pattern = r'(?P<ip>\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}) - - \[(?P<timestamp>.*?)\] "(?P<method>\w+) (?P<path>.*?) HTTP/1\.\d" ' \
              r'(?P<status>\d{3}) (?P<size>\d+) ".+?" "(?P<browser>.*?)"'

    match = re.match(pattern, log_line)

    if not match:
        return {}

    log_entry = match.groupdict()
    log_entry["timestamp"] = datetime.strptime(log_entry["timestamp"], "%d/%b/%Y:%H:%M:%S %z")
    log_entry["status"] = int(log_entry["status"])
    log_entry["size"] = int(log_entry["size"])

    return log_entry


def analyse_log_lines(log_lines: List[str]) -> List[Dict[str, str]]:
    log_entries = []
    for line in log_lines:
        parsed_line = parse_log_line(line)
        if parsed_line:
            log_entries.append(parsed_line)
    return log_entries


def ip_belongs_to_subnet(ip_address, subnet):
    try:
        ip = ipaddress.IPv4Address(ip_address)
        subnet_network = ipaddress.ip_network(subnet, strict=False)
        return ip in subnet_network
    except ValueError:
        return False


def filter_requests_by_subnet(requests, subnet):
    filtered_requests = []
    subnet_network = ipaddress.ip_network(subnet)

    for request in requests:
        ip = ipaddress.ip_address(request['ip'])
        if ip in subnet_network:
            filtered_requests.append(request)

    return filtered_requests


def print_requests_from_subnet(requests, subnet, lines_to_pause):
    printed_lines = 0
    for request in requests:
        if ip_belongs_to_subnet(request['ip'], subnet):
            print(f"{request['ip']} - {request['timestamp']} - {request['method']} {request['path']} - "
                  f"{request['status']} - {request['size']} - {request['browser']}")

            printed_lines += 1

            if printed_lines % lines_to_pause == 0:
                input("Press Enter to continue...")


def print_requests_by_browser(log_entries: List[Dict[str, str]], browser: str, lines_per_page: int):
    browser_regex = re.compile(browser, re.IGNORECASE)
    count = 0
    for entry in log_entries:
        if browser_regex.search(entry["browser"]):
            print(
                f"{entry['ip']} - {entry['timestamp']} - {entry['method']} {entry['path']} - {entry['status']} - {entry['size']} - {entry['browser']}")

            count += 1
            if count % lines_per_page == 0:
                input("Press Enter to continue...")


def print_total_bytes_sent(log_entries: List[Dict[str, str]], request_type: str, separator: str):
    total_bytes = 0
    for entry in log_entries:
        if entry["method"] == request_type:
            total_bytes += entry["size"]
    print(f"{request_type}{separator}{total_bytes}")


def main():
    config_file = "lab.config"
    sections = read_config_file(config_file)
    config, log_file_name, display = sections["Config"], sections["LogFile"]["name"], sections["Display"]
    log_lines = read_log_file(log_file_name)
    log_entries = analyse_log_lines(log_lines)

    student_index = 269553
    mask_length = student_index % 16 + 8
    subnet = f"152.0.0.0/{mask_length}"

    filtered_requests = filter_requests_by_subnet(log_entries, subnet)

    print("\n-------- Requests from subnet: --------")
    print_requests_from_subnet(filtered_requests, subnet, int(display["lines"]))

    print("\n-------- Requests by browser: --------")
    print_requests_by_browser(log_entries, "Safari", int(display["lines"]))

    print("\n------- Total bytes sent: --------")
    print_total_bytes_sent(log_entries, display["filter"], display["separator"])


if __name__ == "__main__":
    main()

# initial
# app7.py:48:80: E501 line too long (186 > 79 characters)
# app7.py:55:80: E501 line too long (94 > 79 characters)
# app7.py:97:80: E501 line too long (165 > 79 characters)
# app7.py:105:80: E501 line too long (100 > 79 characters)
# app7.py:111:80: E501 line too long (151 > 79 characters)
# app7.py:118:80: E501 line too long (97 > 79 characters)
# app7.py:129:80: E501 line too long (105 > 79 characters)
# app7.py:135:80: E501 line too long (98 > 79 characters)
# app7.py:140:80: E501 line too long (80 > 79 characters)
# app7.py:144:80: E501 line too long (103 > 79 characters)
# app7.py:147:80: E501 line too long (80 > 79 characters)

# Final
# app7.py:48:80: E501 line too long (132 > 79 characters)
# app7.py:57:80: E501 line too long (94 > 79 characters)
# app7.py:98:80: E501 line too long (104 > 79 characters)
# app7.py:99:80: E501 line too long (84 > 79 characters)
# app7.py:107:80: E501 line too long (100 > 79 characters)
# app7.py:113:80: E501 line too long (151 > 79 characters)
# app7.py:120:80: E501 line too long (97 > 79 characters)
# app7.py:131:80: E501 line too long (105 > 79 characters)
# app7.py:142:80: E501 line too long (80 > 79 characters)
# app7.py:148:80: E501 line too long (80 > 79 character)
