#!/usr/bin python3

def main() -> None:
	num = 5
	result = factorial(num)
	print(result)

def factorial(n: int) -> int:
	result = 1
	while n > 1:
		result = result * n
		n = n - 1
	return result

if __name__ == "__main__":
	main()
