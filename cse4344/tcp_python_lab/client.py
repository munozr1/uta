import socket

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
msg = "Hello, World!".encode('utf-8')
s.send(msg)
response = s.recv(1024)
print(response.decode('ascii'))
s.close()
