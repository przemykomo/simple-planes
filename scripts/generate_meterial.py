folder1 = r"../src/main/resources"
from os import walk
from os import listdir
from os.path import isfile, join
print(listdir(folder1))


f = []
for (dirpath, dirnames, filenames) in walk(folder1):
    f.extend(filenames)
print(f)


