import socket
import struct

def dns_query(domain, dns_server="8.8.8.8"):
    dns_server_address = (dns_server, 53)
    s = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)

    query_message = (
        b"\x00\x00"  # Transaction ID
        b"\x01\x00"  # Flags: Standard query
        b"\x00\x01"  # Questions: 1
        b"\x00\x00"  # Answers: 0
        b"\x00\x00"  # Authority RR: 0
        b"\x00\x00"  # Additional RR: 0
        + bytes.fromhex("".join(format(len(label), "02x") + label.encode("utf-8").hex() for label in domain.split(".")))
        + b"\x00"  # Null-terminator
        b"\x00\x01"  # Type: A (IPv4 address)
        b"\x00\x01"  # Class: IN (Internet)
    )

    s.sendto(query_message, dns_server_address)

    response, _ = s.recvfrom(1024)

    ip_addresses = [".".join(str(byte) for byte in response[-4:])]
    print([str(byte) for byte in response[-5:-4]])

    return ip_addresses


domain_to_query = "google.com"
result = dns_query(domain_to_query)

print(f"The IP address of {domain_to_query} is {result}")