import socket
import threading
import os

def handle_client(client_socket, address):
    print(f"Accepted connection from {address}")
    file_list = [
        "dns_query.py",
        "echo_client.py",
        "echo_server.py",
        "example.txt",
        "file_client.py",
        "file_server.py",
        "get.py"
    ]
    try:
        bytes_name = client_socket.recv(4)
        len_name = int.from_bytes(bytes_name,"big")
        name = client_socket.recv(len_name).decode()

        bytes_id = client_socket.recv(4)
        len_id = int.from_bytes(bytes_id, "big")
        id = client_socket.recv(len_id).decode()

        bytes_filename = client_socket.recv(4)
        len_filename = int.from_bytes(bytes_filename, "big")
        file_name = client_socket.recv(len_filename).decode()

        print(name,id,file_name)
        print(f"\tReceived request for file: {file_name}")
        if file_name in file_list:
            file_size = os.path.getsize("./"+file_name)
            client_socket.send(file_size.to_bytes(4,"big"))

            with open(file_name, 'rb') as file:
                data = file.read(1024)
                while data:
                    client_socket.send(data)
                    data = file.read(1024)
            print(f"\tFile sent successfully to {address}")
        else:
            error_message = "File not found"
            client_socket.send(error_message.encode())
    except Exception as e:
        print(f"\tError handling client {address}: {e}")
    finally:
        client_socket.close()
        print(f"\tConnection closed with {address}")

def start_server(addr):

    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server_socket.bind(addr)
    server_socket.listen(5)

    print(f"Server listening on {addr[0]}:{addr[1]}")

    try:
        while True:
            client_socket, address = server_socket.accept()
            client_handler = threading.Thread(target=handle_client, args=(client_socket, address))
            client_handler.start()

    except KeyboardInterrupt:
        print("Server shutting down.")
        server_socket.close()

if __name__ == "__main__":
    start_server(("127.0.0.1",8889))