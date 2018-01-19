import sys
from collections import OrderedDict
import numpy as np
import matplotlib.pyplot as plt

#list sample sizes
if len(sys.argv) != 2:
    sizes = [10, 120, 160, 1, 20, 40, 5, 80]
else:
    sizes = sys.argv[1].split(" ")
    sizes = list(map(int, sizes))
sizes.sort()

stats = OrderedDict()
avg = []
err = []

for sample in sizes:
    fileName = 'bench/bench_' + str(sample) + '.res'
    values = []
    with open(fileName, 'r') as file:
        values = file.read().split(" ")
        values.pop()
        values = list(map(int, values))
    stats[sample] = (np.mean(values), np.std(values))

for k, v in stats.items():
    print(str(k) + " -> " + str(v[0]) + " , " + str(v[1]))

x = sizes
y = np.array([x[0] for x in stats.values()])
e = np.array([x[1] for x in stats.values()])

plt.errorbar(x, y, e, linestyle='-', marker='+')

plt.savefig('bench.png')
