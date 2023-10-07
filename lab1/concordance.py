# Create the concordance
from collections import defaultdict

# Open rawindex.txt created by tokenizer
raw_index = open("rawindex.txt", "r", encoding="latin-1")

# Create indices file, words file (reference to indices file), and lazy-hashfile (reference to words file)
indices = open("indices", "w", encoding="latin-1")
words = open("words", "w", encoding="latin-1")
lazy = open("lazy", "w", encoding="latin-1")


def create_word_index():
	word_index = defaultdict(list)
	for line in raw_index:
		line_list = line.split(" ")
		word, i = line_list[0], str((line_list[1])).rstrip() + " "
		word_index[word].append(i)
	return word_index


def create_files(word_index):
	written = set()
	characters = 0
	index = 0

	for word, i in word_index.items():
		words.write(word + " " + str(index) + "\n")
		three_word = word[0:3]
		if three_word not in written:
			lazy.write(three_word + " " + str(characters) + "\n")
		written.add(three_word)
		characters += len(word) + len(str(index)) + 2

		occurences = str(len(i))
		i.append(occurences)
		indices.writelines(i)
		indices.write("\n")
		len_i = 0
		for word in i:
			len_i += len(word)
		index += len_i + 1


if __name__ == "__main__":
	word_index = create_word_index()
	raw_index.close()
	create_files(word_index)

	indices.close()
	words.close()
	lazy.close()
