import numpy as np
#1 compute the inverse of a matrix
#2 multiply matrix x_B by the inverse of B to get x
#3 return x
def changeOfBasis(B, x_B):
    P = np.linalg.inv(B)
    x = P @ xB
    return x

B = np.array([[0, -4, 6], [-1, 0, 6], [-1, 0, 3]])
xB= np.array([[-2], [6], [1]])

x = change_of_basis(B, xB)

print("x = :\n",x)
