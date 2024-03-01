import socket

try:
    msg = input("message:")

    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.connect(("127.0.0.1",50000))

    len_msg = len(msg)
    bytes_len_msg = len_msg.to_bytes(4, "big")
    sock.send(bytes_len_msg)
    sock.send(msg.encode("ascii"))

    bytes_msg_len = sock.recv(4)
    len_msg = int.from_bytes(bytes_msg_len,"big")
    bytes_msg = sock.recv(len_msg)
    msg = bytes_msg.decode("ascii")

    print("echo: ",msg)
    sock.close()

except KeyboardInterrupt as e:
    sock.close()
    sock = None
finally:
    if sock is not None:
        sock.close()
