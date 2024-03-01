import numpy as np
import matplotlib.pyplot as plt
#
#1 normalize the X rows to unit length using the norm() function from the numpy library.
#2 compute the product of each pair of rows of X and divide it by the product of their lengths to get the cosine similarity value.
#3 return the cosine similarity values ​​represented in the M-by-M matrix S . 
def cosine_similarity_matrix(X):
    norm = np.linalg.norm(X, axis=1)
    X_normalized = X / norm[:, None]
    S = X_normalized @ X_normalized.T
    return S

# Generate a random matrix X
M = 4 
N = 10
X = np.random.rand(M, N)

S = cosine_similarity_matrix(X)

plt.matshow(S)
plt.colorbar()
plt.show()
