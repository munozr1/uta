import socket

try:
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server_socket.bind(("127.0.0.1", 50000))
    server_socket.listen(5)


    cli,_ = server_socket.accept()

    bytes_msg_len = cli.recv(4)
    len_msg = int.from_bytes(bytes_msg_len,"big")
    bytes_msg = cli.recv(len_msg)
    msg = bytes_msg.decode("ascii")

    len_msg = len(msg)
    bytes_len_msg = len_msg.to_bytes(4,"big")
    cli.send(bytes_len_msg)
    cli.send(msg.encode("ascii"))

    cli.close()
    server_socket.close()
    server_socket = None
except KeyboardInterrupt as e:
    server_socket.close()
    server_socket = None
finally:
    if server_socket is not None:
        server_socket.close()
