import socket

try:
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind(('127.0.0.1', 50000))
    server_socket.listen(5)

    cli,_ = server_socket.accept()

    cli.send(cli.recv(1024))
    cli.close()

except KeyboardInterrupt as e:
    server_socket.close()
    server_socket = None
finally:
    if server_socket is not None:
        server_socket.close()
