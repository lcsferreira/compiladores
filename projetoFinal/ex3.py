#!/usr/bin python3

def main() -> None:
	idade = 18
	if idade > 18:
		permitido = True
		print(permitido)
	if idade < 18:
		permitido = False
		print(permitido)
	if idade == 18:
		permitido = True
		print(permitido)

if __name__ == "__main__":
	main()
