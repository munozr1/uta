import numpy as np
import matplotlib.pyplot as plt

def SolveLeastSquares(filename):
# Load the data from the file
  data = np.loadtxt(filename)
  x = data[:, 0]
  y = data[:, 1]

  A = np.column_stack((x, np.ones_like(x)))
  b = y

  leastsquare= np.linalg.lstsq(A, b, rcond=None)[0]

  print('Least squares solution: y = {:.2f}x + {:.2f}'.format(leastsquare[0], leastsquare[1]))

  # Plot data andleastsquare 
  plt.scatter(x, y, label='Data')
  plt.plot(x, leastsquare[0]*x + leastsquare[1], 'r', label='Least Squares Solution')
  plt.xlabel('x')
  plt.ylabel('y')
  plt.legend()
  plt.show()

SolveLeastSquares('dataset1.txt')
SolveLeastSquares('dataset2-1.txt')
