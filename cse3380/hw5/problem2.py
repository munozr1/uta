import numpy as np
import matplotlib.pyplot as plt

A = np.array([[1, -2], [4, 1]])
eigenvalues, eigenvectors = np.linalg.eig(A)

plt.quiver([0, 0], [0, 0], [1, 0], [0, 1], angles='xy', scale_units='xy', scale=1, color=['b', 'b'], label=['i', 'j'])
plt.quiver([0, 0], [0, 0], A[:, 0], A[:, 1], angles='xy', scale_units='xy', scale=1, color=['g', 'g'], label=['a1', 'a2'])
plt.quiver([0, 0], [0, 0], eigenvectors[0].real, eigenvectors[1].real, angles='xy', scale_units='xy', scale=1, color=['r', 'r'], label=['v1', 'v2'])

plt.xlim(-3, 3)
plt.ylim(-3, 3)
plt.grid()
plt.legend()
plt.show()
