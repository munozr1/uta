import numpy as np



def changOfCoordintes(P, B):
    invP = np.linalg.inv(P)
    x = invP @ B
    return x 



P = np.array([[4,-9, 5], [-3,-1,6], [9, -2,-6]])
B = np.array([[0, 4, 3], [-1,5, 3], [3,-4,-6]])
x = changOfCoordintes(P, B)
print(x)
