import socket
import threading

def handle_client(client_socket, address, clients):
    try:
        while True:
            message = client_socket.recv(1024).decode()
            if not message:
                break

            print(f"Received message from {address}: {message}")

            # Broadcast the message to all connected clients
            for client in clients:
                if client != client_socket:
                    try:
                        client.send(message.encode())
                    except:
                        # Remove the client if unable to send
                        clients.remove(client)

    except Exception as e:
        print(f"Error handling client {address}: {e}")

    finally:
        # Close the connection and remove the client from the list
        client_socket.close()
        clients.remove(client_socket)
        print(f"Connection closed with {address}")

def start_chat_server():
    host = '127.0.0.1'
    port = 8888

    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server_socket.bind((host, port))
    server_socket.listen(5)

    print(f"Chat server listening on {host}:{port}")

    clients = []

    try:
        while True:
            client_socket, address = server_socket.accept()
            clients.append(client_socket)

            # Start a new thread to handle the client
            client_handler = threading.Thread(target=handle_client, args=(client_socket, address, clients))
            client_handler.start()

    except KeyboardInterrupt:
        print("Server shutting down.")
        for client in clients:
            client.close()
        server_socket.close()

if __name__ == "__main__":
    start_chat_server()