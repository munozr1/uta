import numpy as np
import scipy as sp

A = np.array([[1, 0, 4], [-2, 3, -2], [-2, 0, 6]])
Q, R = sp.linalg.qr(A)

print(f"Q ={Q}\n \nR ={R}")
