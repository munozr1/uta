import socket

def http_get_request(host, port, path="/"):
    # Create a socket object
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Connect to the server
    client_socket.connect((host, port))

    # Send the HTTP GET request
    request = f"GET {path} HTTP/1.1\r\nHost: {host}\r\nConnection: close\r\n\r\n"
    client_socket.send(request.encode())

    # Receive and print the response
    response = b""
    while True:
        data = client_socket.recv(1024)
        if not data:
            break
        response += data

    start_of_content = response.find(b"\r\n\r\n") + 4
    html_content = response[start_of_content:]

    # Print the HTML content
    print(html_content.decode())

    # Save the HTML content to a file if specified

    with open("index.html", 'wb') as file:
        file.write(html_content)
    # Close the socket
    client_socket.close()

if __name__ == "__main__":
    host_to_request = "www.google.com"
    port_to_request = 80  # HTTP default port

    http_get_request(host_to_request, port_to_request)