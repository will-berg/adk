# Search for word in concordance
import sys
import time

# Open files created by concordance.py and korpus
lazy = open("lazy", "r", encoding="latin-1")
words = open("words", "r", encoding="latin-1")
indices = open("indices", "r", encoding="latin-1")
korpus = open("korpus", "r", encoding="latin-1")


def search(word):
	lazy_dict = create_lazy_dict()
	i, j = get_bounds(word, lazy_dict)
	pos, found = binary_search(i, j, word)

	if found == False:
		print("Ordet förekommer inte i texten")
	else:
		words.seek(pos)
		line = words.readline().split(" ")
		s, index = line[0], int(line[1].rstrip())
		indices.seek(index)

		if s == word:
			occurrences = int(indices.readline().split(" ")[-1])
			indices.seek(index)
			print(f"Det finns {occurrences} förekomster av ordet.")

			if occurrences > 25:
				print_out = input(f"Ordet förekommer {occurrences} gånger, vill du ha alla förekomster utskrivna på skärmen? ja/nej\n").lower()
				if print_out == "ja":
					output(occurrences)
				else:
					output(occurrences, 0)
			else:
				output(occurrences)
		else:
			print("Ordet förekommer inte i texten")


def create_lazy_dict():
	lazy_dict = {}
	prev_three_word = " "
	for line in lazy:
		three_word, i = line.split(" ")[0], (line.split(" ")[1]).rstrip()
		lazy_dict[three_word] = [i]
		if prev_three_word != " ":
			lazy_dict[prev_three_word].append(i)
		prev_three_word = three_word
	lazy_dict[three_word].append(i)
	return lazy_dict


def get_bounds(word, lazy_dict):
	search_word = word[0:3]
	# i (lower bound for binary search) = index in words file where the first word that starts with search_word is, j = upper bound for binary search
	[i, j] = lazy_dict[search_word]
	return int(i), int(j)


def binary_search(i, j, word, found=True):
	timeout = time.time() + 1
	while i < j:
		if time.time() > timeout:
			found = False
			break
		m = (i + j)/2
		words.seek(m)
		while True:
			if words.read(1) == "\n":
				break
		current = words.tell()
		s = words.readline().split(" ")[0]
		if s == word:
			i = current
		elif s < word:
			i = m
		else:
			j = m
	return i, found


def output(occurrences, counter = -1):
	for i in range(occurrences):
		current_pos = indices.tell()
		index_size = 0
		while True:
			if indices.read(1) == " ":
				break
			else:
				index_size += 1
		indices.seek(current_pos)
		pos = int(indices.read(index_size))
		indices.seek(indices.tell() + 1)
		if pos - 30 >= 0:
			start = pos - 30
		else:
			start = 0
		if start != 0:
			to_read = len(word) + 60
		else:
			to_read = len(word) + 30
		korpus.seek(start)
		print(korpus.read(to_read).replace("\n", " "))
		if counter != -1:
			counter += 1
			if counter == 25:
				break


if __name__ == "__main__":
	# Fetch input as command line argument, remove capitals
	word = sys.argv[1].lower()

	try:
		search(word)
	except:
		print("Ordet förekommer inte i texten")

	lazy.close()
	words.close()
	indices.close()
	korpus.close()


	"""
	while True:
		line = words.readline()
		w, index = line.split(" ")[0], (line.split(" ")[1]).rstrip()
		if w == word:
			indices.seek(int(index))
			i_line = indices.readline()
			positions = i_line.split(" ")[0:-1]
			print(positions)
			occurrences = len(positions)
			for pos in positions:
				pos = int(pos)
				if pos - 30 >= 0:
					start = pos - 30
				else:
					start = 0
				if start != 0:
					to_read = len(word) + 60
				else:
					to_read = len(word) + 30
				korpus.seek(start)
				context.append(korpus.read(to_read))
			break
	"""

	"""
	while True:
		I.seek(index)
		line = I.readline()
		w, i = line.split(" ")[0], (line.split(" ")[1]).rstrip()
		if calculate_hash(word) != calculate_hash(w):
			break
		i = int(i)
		if w == word:
			if i - 30 >= 0:
				start = i - 30
			else:
				start = 0
			if start != 0:
				to_read = len(word) + 60
			else:
				to_read = len(word) + 30
			L.seek(start)
			context.append(L.read(to_read))
			occurrences += 1
		index += len(line)
		"""

	# line = indices.readline()
	# word_indices = line.split(" ")
	# positions = word_indices[0:-1]
	# occurrences = int(word_indices[-1])

	"""
	while j - i > 11:
		m = (i + j)/2
		words.seek(m)
		while True:
			if words.read(1) == "\n":
				break
		s = words.readline().split(" ")[0]
		if s <= word:
			i = m
		else:
			j = m
	"""

			# output(positions)

			# output(positions, 0)

		# output(positions)



"""
def output(positions, counter = -1):
	for pos in positions:
		pos = int(pos)
		if pos - 30 >= 0:
			start = pos - 30
		else:
			start = 0
		if start != 0:
			to_read = len(word) + 60
		else:
			to_read = len(word) + 30
		korpus.seek(start)
		print(korpus.read(to_read).replace("\n", " "))
		if counter != -1:
			counter += 1
			if counter == 25:
				break
"""
