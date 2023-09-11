#!/usr/bin python3

def main() -> None:
	a = 0
	b = 1
	c = 0
	print(a)
	print(b)
	n = 2
	while n < 10:
		c = a + b
		print(c)
		a = b
		b = c
		n = n + 1

if __name__ == "__main__":
	main()
