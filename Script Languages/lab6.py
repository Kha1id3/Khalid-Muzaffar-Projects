import json
import logging
import sys


def run():
    file_path = "C:/Users/Khalid/PycharmProjects/Lab5/access_log.txt"
    with open(file_path, "r") as f:
        log_data = read_log(f)
    ip_requests = ip_requests_number(log_data)
    most_active_ips = ip_find(ip_requests)
    least_active_ips = ip_find(ip_requests, most_active=False)
    longest_req = longest_request(log_data)
    non_existent_pages = non_existent(log_data)

    try:
        config = load_config()
    except FileNotFoundError:
        logging.info("Configuration file not found, using default values")
        config = {
            "log_name": "access_log.txt",
            "ip_address": "127.0.0.1",
            "logging_level": "INFO",
            "lines_to_display": 5,
            "custom_param": ""
        }
    except json.JSONDecodeError:
        logging.error("Invalid JSON format in configuration file")
        sys.exit(1)
    except KeyError as e:
        logging.info(f"Missing key in configuration file: {e}")

    logging.basicConfig(level=config["logging_level"])

    config["custom_status"] = 404

    try:
        with open(config["log_name"], "r") as f:
            log_data = read_log(f)
    except FileNotFoundError:
        logging.error("Log file not found")
        sys.exit(1)

    print("----------------------------------")
    print("Most Active IPs:", most_active_ips)
    print("----------------------------------")
    print("Least Active IPs:", least_active_ips)
    print("----------------------------------")
    print("Longest Request:", longest_req)
    print("----------------------------------")
    print("Non-existent Pages:", non_existent_pages)
    print("----------------------------------")

    # Call the custom function
    custom_function(log_data, config)

    print("----------------------------------")
    print("Requests from IP:", config["ip_address"])
    print_requests_from_ip(log_data, config["ip_address"])
    print("----------------------------------")

    method = input("Enter the request method to filter: ")
    print("Requests with method:", method)
    print_requests_by_method(log_data, method, config["lines_to_display"])
    print("----------------------------------")




def read_log(f):
    log_data = []
    for line in f:
        parts = line.strip().split(" ")
        try:
            status = int(parts[8])
        except ValueError:
            status = None
        try:
            response_size = int(parts[9])
        except ValueError:
            response_size = 0
        entry = {
            "ip": parts[0],
            "client_id": parts[1],
            "user_id": parts[2],
            "timestamp": " ".join(parts[3:5]).strip("[]"),
            "request": " ".join(parts[5:8]),  # method reequested source and http version
            "status": status,
            "response_size": response_size
        }
        log_data.append(entry)
    return log_data


def ip_requests_number(log_data):
    ip_requests = {}
    for entry in log_data:
        ip = entry["ip"]
        if ip in ip_requests:
            ip_requests[ip] += 1
        else:
            ip_requests[ip] = 1
    return ip_requests


def ip_find(ip_requests, most_active=True):
    assert isinstance(most_active, bool), "most_active should be a boolean"
    count = max(ip_requests.values()) if most_active else min(ip_requests.values())
    return [ip for ip, num in ip_requests.items() if num == count]


def longest_request(log_data):
    max_length = 0
    longest_req = ""
    ip_address = ""

    for entry in log_data:
        request = entry["request"]
        if len(request) > max_length:
            max_length = len(request)
            longest_req = request
            ip_address = entry["ip"]

    return ip_address, longest_req


def non_existent(log_data):
    non_existent_pages = set()
    for entry in log_data:
        if entry["status"] == 404:
            request = entry["request"]
            non_existent_pages.add(request)

    return list(non_existent_pages)


def load_config():
    with open("config.json", "r", encoding="utf-8") as f:
        return json.load(f)


def print_requests_from_ip(log_data, ip_address):
    filtered_data = [entry for entry in log_data if entry["ip"] == ip_address]
    for entry in filtered_data:
        print(entry)


def print_requests_by_method(log_data, method, lines_limit):
    counter = 0
    for entry in log_data:
        if method in entry["request"]:
            print(entry)
            counter += 1

            if counter == lines_limit:
                input("Press any key to continue...")
                counter = 0


def custom_function(log_data, config):
    custom_status = config.get("custom_status")
    if custom_status:
        assert isinstance(custom_status, int), "custom_status should be an integer"
        filtered_log_data = [entry for entry in log_data if entry["status"] == custom_status]
        print(f"--- Requests with status {custom_status} ---")
        for entry in filtered_log_data:
            print(f"{entry['ip']} - {entry['request']} - {entry['status']}")


if __name__ == "__main__":
    run()
