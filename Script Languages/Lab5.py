


def run():
    file_path = "C:/Users/Khalid/PycharmProjects/Lab5/access_log.txt"
    with open(file_path, "r") as f:
        log_data = read_log(f)
    ip_requests = ip_requests_number(log_data)
    most_active_ips = ip_find(ip_requests)
    least_active_ips = ip_find(ip_requests, most_active=False)
    longest_req = longest_request(log_data)
    non_existent_pages = non_existent(log_data)

    print("----------------------------------")
    print("Most Active IPs:", most_active_ips)
    print("----------------------------------")
    print("Least Active IPs:", least_active_ips)
    print("----------------------------------")
    print("Longest Request:", longest_req)
    print("----------------------------------")
    print("Non-existent Pages:", non_existent_pages)
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
            "request": " ".join(parts[5:8]), #method reequested source and http version
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


if __name__ == "__main__":
    run()
