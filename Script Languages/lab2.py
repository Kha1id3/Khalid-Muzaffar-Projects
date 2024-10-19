import sys
import re
import logging


logging.basicConfig(level=logging.INFO, format='%(levelname)s: %(message)s')


logging.info("Start")

failed_requests = 0
total_bytes_sent = 0
total_processing_time = 0
total_requests = 0
largest_resource = None
largest_processing_time = -1


for line in sys.stdin:
    logging.debug(f"Processing line: {line.strip()}")


    match = re.search(r'\s(\d{3})\s(\d+)\s(\d+)\s', line)
    if match:
        result_code, processing_time, bytes_sent = map(int, match.groups())
        path = re.search(r'\s(\S+)\s', line).group(1)

        total_requests += 1
        total_bytes_sent += bytes_sent
        total_processing_time += processing_time

        if result_code == 404:
            failed_requests += 1
            print(f'! {line.strip()}')
        else:
            if processing_time > largest_processing_time:
                largest_processing_time = processing_time
                largest_resource = path
            print(line.strip())
    else:
        print(line.strip())

logging.info("----------Summary-----------------")
print(f"The path and processing time of the largest resource: {largest_resource} ({largest_processing_time} ms)")
print(f"Number of failed requests (non-existent resources): {failed_requests}")
print(f"Total number of bytes sent to a user: {total_bytes_sent}")
print(f"Total number of kilobytes sent to a user: {total_bytes_sent / 1024:.2f} KB")
print(f"Average processing time of a request: {total_processing_time / total_requests:.2f} ms")

logging.info("-------------End----------------")
