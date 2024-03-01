import numpy as np
import sympy as sp
print(np.__version__)

A = np.array([[3, 8, -5], [3, -6, -7], [3, 4, 2]])
b = np.array([-1, -1, 3]).reshape(-1, 1)  # reshape to column vector

# a) RREF A
A_rref = sp.Matrix(A).rref()[0].tolist()
A_rref_np = np.array(A_rref)
# b) Col(A) 
column_space = np.linalg.svd(A)[0][:, :np.linalg.matrix_rank(A)]
# (c) Solve the matrix equation Ax = b
x = np.linalg.solve(A, b)
# (d) Nul(A)
nulA= sp.Matrix(A).nullspace()
null_space_np = np.array(nulA).squeeze()
print("RREF of A:\n", A_rref_np)
print("Col(A) :\n", column_space)
print("Ax = b :\n", x)
print("Nul(A) :\n", null_space_np)
