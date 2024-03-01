import socket

def request_file(add, name, id, file_name):
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.connect(add)

    try:
        bytes_name = len(name).to_bytes(4, 'big')
        client_socket.send(bytes_name)
        client_socket.send(name.encode())
        bytes_id = len(id).to_bytes(4, 'big')
        client_socket.send(bytes_id)

        client_socket.send(id.encode())


        bytes_filename = len(file_name).to_bytes(4, 'big')
        client_socket.send(bytes_filename)
        client_socket.send(file_name.encode())

        bytes_file_size = client_socket.recv(4)
        file_size = int.from_bytes(bytes_file_size, 'big')
        print(f"File size: {file_size} bytes")

        if file_size > 0:
            received_data = b''
            while len(received_data) < file_size:
                data = client_socket.recv(1024)
                if not data:
                    break
                received_data += data

            with open(file_name, 'wb') as file:
                file.write(received_data)

            print(f"File {file_name} received successfully")

        else:
            print(f"\tError: {client_socket.recv(1024).decode()}")
    except Exception as e:
        print(f"\tError: {e}")
    finally:
        client_socket.close()
if __name__ == "__main__":
    file_to_request = "dns.query.py"
    name = "Rodrigo Munoz"
    id = "1001847694"
    request_file(('192.168.50.227', 8889), name, id, file_to_request)
    request_file(('192.168.50.227', 8889), name,  id, 'echo_client.py')
    request_file(('192.168.50.227', 8889), name,  id, 'echo_server.py')
    request_file(('192.168.50.227', 8889), name,  id, 'example.txt')
    request_file(('192.168.50.227', 8889), name, id, 'file_client.py' )
    request_file(('192.168.50.227', 8889), name, id, 'file_server.py')
    request_file(('192.168.50.227', 8889), name, id,  'get.py')
