import sys
import logging


def read_log():
    lines = sys.stdin.readlines()
    entries = []

    for line in lines:
        if line.strip():
            path, result_code, bytes_sent, processing_time = line.split()
            entries.append((
                path,
                int(result_code),
                int(bytes_sent),
                int(processing_time)
            ))

    logging.debug(f"Number of lines read: {len(lines)}")
    logging.debug(f"Number of entries in the list: {len(entries)}")

    return entries


def successful_reads(log_entries):
    successful_entries = []

    for entry in log_entries:
        path, result_code, bytes_sent, processing_time = entry
        if 200 <= result_code < 300:
            successful_entries.append(entry)

    logging.info(f"Number of successful reads: {len(successful_entries)}")
    return successful_entries


def failed_reads(log_entries):
    entries_4xx = []
    entries_5xx = []

    for entry in log_entries:
        path, result_code, bytes_sent, processing_time = entry
        if 400 <= result_code < 500:
            entries_4xx.append(entry)
        elif 500 <= result_code < 600:
            entries_5xx.append(entry)

    logging.info(f"Number of entries with 4xx result codes: {len(entries_4xx)}")
    logging.info(f"Number of entries with 5xx result codes: {len(entries_5xx)}")

    return entries_4xx + entries_5xx


def html_entries(log_entries):
    successful_html_entries = []

    successful_entries = successful_reads(log_entries)

    for entry in successful_entries:
        path, result_code, bytes_sent, processing_time = entry
        if path.endswith('.html'):
            successful_html_entries.append(entry)

    return successful_html_entries


def print_html_entries(log_entries):
    successful_html_entries = html_entries(log_entries)

    print("Successfully retrieved HTML pages:")
    print(successful_html_entries)


def run():
    logging.basicConfig(level=logging.DEBUG, format='%(levelname)s: %(message)s')

    logging.info("Start")
    log_entries = read_log()

    print("-----Log file content-----")
    for entry in log_entries:
        path, result_code, bytes_sent, processing_time = entry
        print(f"{path} {result_code} {bytes_sent} {processing_time}")

    successful_entries = successful_reads(log_entries)
    print("Successful reads:")
    for entry in successful_entries:
        print(entry)

    failed_entries = failed_reads(log_entries)
    for entry in failed_entries:
        print(entry)

    successful_html_entries = html_entries(log_entries)
    for entry in successful_html_entries:
        print(entry)

    print_html_entries(log_entries)

    failed_requests = 0
    total_bytes_sent = 0
    total_processing_time = 0
    total_requests = 0
    largest_resource = None
    largest_processing_time = -1

    for entry in log_entries:
        path, result_code, bytes_sent, processing_time = entry
        total_requests += 1
        total_bytes_sent += bytes_sent
        total_processing_time += processing_time

        if result_code == 404:
            failed_requests += 1

        if processing_time > largest_processing_time:
            largest_processing_time = processing_time
            largest_resource = path

    logging.info("----------Summary---------------")
    print(
        f"The path and processing time of the largest resource: {largest_resource} ({largest_processing_time} ms)")
    print(f"Number of failed requests (non-existent resources): {failed_requests}")
    print(f"Total number of bytes sent to a user: {total_bytes_sent}")
    print(f"Total number of kilobytes sent to a user: {total_bytes_sent / 1024:.2f} KB")
    if total_requests > 0:
        print(f"Average processing time of a request: {total_processing_time / total_requests:.2f} ms")
    else:
        print("No requests found in the log.")

    logging.info("-------------End----------------")


if __name__ == "__main__":
    run()
