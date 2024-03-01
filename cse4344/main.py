from scapy.all import *
import pandas as pd

# Load the capture file
packets = rdpcap("/mnt/data/http-ethereal-trace-1")

# Function to extract and summarize data
def summarize_http_info(packets):
    requests = []
    responses = []
    for pkt in packets:
        if pkt.haslayer(TCP) and pkt.haslayer(Raw):
            # Attempt to decode as HTTP
            try:
                load = pkt[Raw].load.decode()
                if "HTTP" in load:
                    # Determine if request or response based on the presence of HTTP method
                    if any(method in load for method in ["GET ", "POST ", "HEAD ", "PUT ", "DELETE ", "OPTIONS "]):
                        requests.append(load)
                    else:
                        responses.append(load)
            except Exception as e:
                pass  # Ignore decode errors or non-HTTP packets

    # Analyze HTTP Requests
    http_versions_req = set()
    accepted_languages = set()
    for req in requests:
        lines = req.split("\r\n")
        for line in lines:
            if line.startswith("GET") or line.startswith("POST"):
                http_versions_req.add(line.split(" ")[-1])
            if line.startswith("Accept-Language"):
                accepted_languages.add(line.split(": ")[1])

    # Analyze HTTP Responses
    http_versions_res = set()
    status_codes = set()
    last_modified = set()
    content_lengths = set()
    for res in responses:
        lines = res.split("\r\n")
        for line in lines:
            if line.startswith("HTTP/"):
                http_versions_res.add(line.split(" ")[0])
                status_codes.add(line.split(" ")[1])
            if line.startswith("Last-Modified"):
                last_modified.add(line.split(": ")[1])
            if line.startswith("Content-Length"):
                content_lengths.add(line.split(": ")[1])
    
    # IP Addresses
    src_ips = set(pkt[IP].src for pkt in packets if pkt.haslayer(IP))
    dst_ips = set(pkt[IP].dst for pkt in packets if pkt.haslayer(IP))

    return {
        "http_versions_req": list(http_versions_req),
        "http_versions_res": list(http_versions_res),
        "accepted_languages": list(accepted_languages),
        "status_codes": list(status_codes),
        "last_modified": list(last_modified),
        "content_lengths": list(content_lengths),
        "src_ips": list(src_ips),
        "dst_ips": list(dst_ips)
    }

# Summarize information from the packets
summary = summarize_http_info(packets)
summary

