
words = open("words", "r", encoding="latin-1")

words.seek(4)
# words.__next__()
# add = words.readline().find("\n")
print(words.tell())
line = words.readline()

print(repr(line))
