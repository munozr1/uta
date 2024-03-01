import socket
import threading

def receive_messages(client_socket):
    try:
        while True:
            message = client_socket.recv(1024).decode()
            print(message)
    except Exception as e:
        print(f"Error receiving messages: {e}")

def send_messages(client_socket):
    try:
        while True:
            message = input()
            client_socket.send(message.encode())
    except Exception as e:
        print(f"Error sending messages: {e}")

def start_chat_client():
    host = '127.0.0.1'  # Change to the server's IP address or hostname
    port = 8888

    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.connect((host, port))

    print("Connected to the chat server.")

    # Start separate threads for receiving and sending messages
    receive_thread = threading.Thread(target=receive_messages, args=(client_socket,))
    send_thread = threading.Thread(target=send_messages, args=(client_socket,))

    receive_thread.start()
    send_thread.start()

if __name__ == "__main__":
    start_chat_client()