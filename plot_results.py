#!/usr/bin/env python
import pandas as pd
import numpy as np
import matplotlib.mlab as mlab
import matplotlib.pyplot as plt
import seaborn
import sys

# load the result file:
if len(sys.argv) > 1:
    result_df = pd.read_csv(sys.argv[1])
else:
    result_df = pd.read_csv("./result.txt")

s1_res_data = result_df["s1"].values
s2_res_data = result_df["s2"].values
seaborn.set()
# the histogram of the data
fig = plt.figure(1,figsize=(16,16))
axs = fig.subplots(3,1)
ax1 = axs[0]
ax2 = axs[1]
ax3 = axs[2]
def plot(ax, data, title):
    n, bins, patches = ax.hist(data, max(data), alpha=0.75)
    ax.set_xlabel('traial times')
    ax.set_ylabel('distribution')
    ax.set_title(title)
    # ax1.axis([40, 160, 0, 0.03])
    # ax.grid(True)
plot(ax1, s1_res_data, "result_s1")
plot(ax2, s2_res_data, "result_s2")
ax3.boxplot([s1_res_data, s2_res_data], labels=['s1','s2'])
plt.show()
fig.savefig("test_analysis_res.png")
print('mean s1: {}, mean s2: {}'.format(np.mean(s1_res_data), np.mean(s2_res_data)))
