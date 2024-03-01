import socket

def request_file(addr,name,id,file_name):
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.connect(addr)
    try:
        # Send the file name to the server
        bytes_name = len(name).to_bytes(4,"big")
        client_socket.send(bytes_name)
        client_socket.send(name.encode())

        bytes_id = len(id).to_bytes(4, "big")
        client_socket.send(bytes_id)
        client_socket.send(id.encode())

        bytes_filename = len(file_name).to_bytes(4, "big")
        client_socket.send(bytes_filename)
        client_socket.send(file_name.encode())

        bytes_file_size = client_socket.recv(4)
        # Receive the file size from the server
        file_size = int.from_bytes(bytes_file_size,"big")
        print(f"\tFile size: {file_size} bytes")

        if file_size > 0:
            # Receive and save the file content
            received_data = b""
            while len(received_data) < file_size:
                data = client_socket.recv(1024)
                if not data:
                    break
                received_data += data

            # Save the file to the disk
            with open(file_name, 'wb') as file:
                file.write(received_data)

            print(f"\tFile '{file_name}' received successfully")
        else:
            print(f"\tError: {client_socket.recv(1024).decode()}")
    except Exception as e:
        print(f"\tError: {e}")
    finally:
        # Close the connection
        client_socket.close()

if __name__ == "__main__":
    file_to_request = "dns_query.py"
    name = ""
    id = ""
    request_file(("127.0.0.1",8889),name,id,file_to_request)