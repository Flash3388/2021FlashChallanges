#!/usr/bin/python3

import pathlib
from pathlib import Path
import random
import sys


CURRENT_DIR = pathlib.Path(__file__).parent.absolute()
BIN_PATH = CURRENT_DIR/'magic.bin'


def main():
	with BIN_PATH.open(mode='rb') as f:
		binary = f.read()

	print('Bin:', binary)

	for child in sys.argv[1:]:
		path = Path(child)
		size = path.stat().st_size

		print('At file', str(path))
		with path.open(mode='rb') as f:
			content = f.read()

		with path.open(mode='wb') as f:
			pos = random.randint(0, size-len(binary))
			f.write(content[0:pos] + binary + content[pos:])


if __name__ == '__main__':
	main()
