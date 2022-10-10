num_images = 100
opfile = open('lzw_local' + str(num_images) + '.txt', 'a')
inputfile = '100_seq.txt'

import numpy as np
import time

import math
import json
from operator import add

def arrayToString(arr):
    string = ""
    for i in range(arr.shape[0]):
        if i != 0:
            string = string + " "
        string = string + str(arr[i])
    return string

def stringToArray(string):
    arr = [int(x) for x in string.split(" ")]
    return np.array(arr)

def arrayToString2D(arr):
    string = ""
    for i in range(arr.shape[0]):
        for j in range(arr.shape[1]):
            if j != 0:
                string = string + " "
            string = string + str(arr[i][j])
        string = string + ";"
    return string[:-1]

def stringToArray2D(string):
    arr = [[int(y) for y in x.split(" ")] for x in string.split(";")]
    return np.array(arr, dtype=np.uint8)
    
def lzwEncode(arr):
    op = []
    dictionary_size = 256
    lzwDict = {repr([i]): i for i in range(dictionary_size)}
    seq = []
    for i in arr:                     
        seqsym = seq + [i]
        if repr(seqsym) in lzwDict: 
            seq = seqsym
        else:
            op.append(lzwDict[repr(seq)])
            if(len(lzwDict) <= 65535):
                lzwDict[repr(seqsym)] = dictionary_size
                dictionary_size += 1
            seq = [i]

    if repr(seq) in lzwDict:
        op.append(lzwDict[repr(seq)])
    return op
    
def lzwDecode(enc):
    op = []
    dictionary_size = 256
    lzwDict = {i: [i] for i in range(dictionary_size)}
    seq = []
    for i in enc:
        if not (i in lzwDict):
            lzwDict[i] = seq + [seq[0]]
        op += lzwDict[i]
        if not(len(seq) == 0):
            lzwDict[dictionary_size] = seq + [lzwDict[i][0]]
            dictionary_size += 1
        seq = lzwDict[i]
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
    compressed = arrayToString(np.array(lzwEncode(stringToArray2D(arr[1]).flatten().tolist())))
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
    compsum += len(stringToArray(arr[1]))
compsum /= num_images
compsum = 32*32/compsum
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
    decomp = arrayToString2D(np.array(lzwDecode(stringToArray(arr[1]))).reshape((32, 32)))
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
    x = stringToArray2D(line1.split(maxsplit=1)[1])
    y = stringToArray2D(line2.split(maxsplit=1)[1])
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

