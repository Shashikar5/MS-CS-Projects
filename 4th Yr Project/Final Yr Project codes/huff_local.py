num_images = 100
opfile = open('huff_local' + str(num_images) + '.txt', 'a')
inputfile = '100_seq.txt'

import numpy as np
import time

import math
import json
from operator import add

def arrayToString(arr):
    string = ""
    for i in range(arr.shape[0]):
        for j in range(arr.shape[1]):
            if j != 0:
                string = string + " "
            string = string + str(arr[i][j])
        string = string + ";"
    return string[:-1]

def stringToArray(string):
    arr = [[int(y) for y in x.split(" ")] for x in string.split(";")]
    return np.array(arr, dtype=np.uint8)

class NLNode:

    def __init__(self, left, right):

        self.left = left
        self.right = right
class LNode:

    def __init__(self, data):
        self.data = data

def fill(huffTree, huffDict, path = ''):
    if hasattr(huffTree, 'data'):
        huffDict[path] = huffTree.data
    else:
        fill(huffTree.left, huffDict, path + '0')
        fill(huffTree.right, huffDict, path + '1')

def huffmanEncode(arr):
    freq = dict()
    for i in arr:
        if i in freq.keys():
            freq[i] += 1
        else:
            freq[i] = 1
    huff = [(LNode(i), freq[i]) for i in freq.keys()]
    huff = sorted(huff, key=lambda x: x[1], reverse=True)
    while len(huff) != 1:
        a = huff[-1]
        b = huff[-2]
        huff.pop()
        huff.pop()
        t = NLNode(a[0], b[0])
        ind = -1
        while ind >= -len(huff) and huff[ind][1] < a[1] + b[1]:
            ind -= 1

        huff.insert(ind + 1, (t, a[1] + b[1]))
    huffTree = huff[0][0]
    huffDict = dict()
    fill(huffTree, huffDict)
    invDict = {v: k for k, v in huffDict.items()}

    op = ''
    for i in arr:
        op += invDict[i]
    while len(op) % 8 != 0:
        op += '0'
    result = json.dumps(huffDict) + "HENCDELIM" + str(len(arr)) + "HENCDELIM" + op
    return result

def huffmanDecode(enc):
    arr = enc.split('HENCDELIM', maxsplit = 2)
    huffDict = json.loads(arr[0])
    encstr = arr[2]
    ol = int(arr[1])
    end = 0
    cstr = ''
    op = []
    while end != len(encstr):
        cstr += encstr[end]
        if cstr in huffDict.keys():
            op.append(huffDict[cstr])
            cstr = ''
        end += 1
    op = op[:ol]
    return op
    
s2 = time.time()
file1 = open(inputfile, 'r')
file2 = open(str(num_images) + '_compressed.txt', 'a')

while True:
    line = file1.readline()
    if not line:
        break
    line = line[:-1]
    arr = line.split(maxsplit=1)
    compressed = huffmanEncode(stringToArray(arr[1]).flatten().tolist())
    print(arr[0] + "\t" + compressed, file = file2)

file1.close()
file2.close()

e2 = time.time()
t2 = e2 - s2

print("Compression(Job 1):", t2 // 60, t2 % 60, file = opfile)

s2 = time.time()
file1 = open(str(num_images) + '_compressed.txt', 'r')
file2 = open(str(num_images) + '_compratio.txt', 'a')

compsum = 0
while True:
    line = file1.readline()
    if not line:
        break
    line = line[:-1]
    arr = line.split(maxsplit=1)
    parts = arr[1].split('HENCDELIM', maxsplit = 2)
    compsum += len(parts[0]) + len(parts[1]) + len(parts[2]) / 8
compsum /= num_images
compsum = 32*32*8/compsum
print("Average Compression ratio : ", compsum, file = opfile)

file1.close()
file2.close()

e2 = time.time()
t2 = e2 - s2

print("Compratio(Job 2):", t2 // 60, t2 % 60, file = opfile)

s2 = time.time()
file1 = open(str(num_images) + '_compressed.txt', 'r')
file2 = open(str(num_images) + '_decomp.txt', 'a')

while True:
    line = file1.readline()
    if not line:
        break
    line = line[:-1]
    arr = line.split(maxsplit=1)
    decomp = arrayToString(np.array(huffmanDecode(arr[1])).reshape((32, 32)))
    print(arr[0] + "\t" + decomp, file = file2)

file1.close()
file2.close()

e2 = time.time()
t2 = e2 - s2

print("Decompression(Job 3):", t2 // 60, t2 % 60, file = opfile)

def mean_square_error(im1, im2):
    diff = np.subtract(im1, im2, dtype = np.float32)
    return (np.sum(np.square(diff)))/(diff.shape[0]*diff.shape[1])

def peak_signal_to_noise_ratio(rms):
    return 20*math.log10(255/rms)

def peek_signal_to_noise_ratio(im1, im2):
    rms = rms_error(im1, im2)
    return 20*math.log10(255/rms)

def rms_error(im1, im2):
    return np.sqrt(mean_square_error(im1, im2))

s2 = time.time()
file1 = open(inputfile, 'r')
file2 = open(str(num_images) + '_decomp.txt', 'r')

rms = 0
psnr = 0
while True:
    line1 = file1.readline()
    line2 = file2.readline()
    if not line1:
        break
    line1 = line1[:-1]
    line2 = line2[:-1]
    x = stringToArray(line1.split(maxsplit=1)[1])
    y = stringToArray(line2.split(maxsplit=1)[1])
    rms += rms_error(x, y)
    psnr += peek_signal_to_noise_ratio(x, y)
rms /= num_images
psnr /= num_images
print("Average RMS : ", rms, file = opfile)
print("Average PSNR : ", psnr, file = opfile)
file1.close()
file2.close()

e2 = time.time()
t2 = e2 - s2

print("Stats(Job 5):", t2 // 60, t2 % 60, file = opfile)

