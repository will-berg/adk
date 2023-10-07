# Open korpus file
L = open("korpus", "r", encoding="latin-1")
I = open("rawindex.txt", "r", encoding="latin-1")

# Create index, concordance, and lazy-hashfile
A = open("index", "r+", encoding="latin-1")		# Stores key and value on line, key is hash representation of three starting letters and value is the position of the first word that starts with those letters in rawindex
""" concordance = open("concordance", "r+", encoding="latin-1")
lazy = open("lazy", "w", encoding="latin-1") """


# ord('a') = 97, we start from 1
def calculate_hash(word):
	if len(word) >= 3:
		return (ord(word[0]) - 96) * 900 + (ord(word[1]) - 96) * 30 + ord(word[2]) - 96
	if len(word) == 2:
		return (ord(word[0]) - 96) * 900 + ord(word[1]) - 96
	if len(word) == 1:
		return ord(word[0]) - 96


lazy = {}
position = 0

for line in I:
	word = line.split(" ")[0]
	hash_value = calculate_hash(word)
	if hash_value not in lazy.keys():
		lazy[hash_value] = position
	position += len(line)

for key, value in lazy.items():
	A.write(f"{key} {value}\n")

""" test = []
test2 = []
for word in words:
	if calculate_hash(word) not in test:
		test.append(calculate_hash(word))
	test2.append(calculate_hash(word))

print(len(words))
print()
print(words)
print()
print(len(test))
print()
print(test)
print()
print(len(test2))
print()
print(test2) """


""" print(len(lazy.keys()))
print(len(words))

test = []
for word in words:
	if calculate_hash(word) not in lazy.keys():
		print(word)


counter = 0
for word in words:
	if calculate_hash(word) in lazy.keys():
		print(word)
		counter += 1

print(counter) """
