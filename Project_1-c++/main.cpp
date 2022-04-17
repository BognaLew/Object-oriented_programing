#include <iostream>
#include <cstdlib>
#include <ctime>
using namespace std;

#include "World.h"
#include "Organism.h"
#include "Animal.h"
#include "Board.h"
#include "Coordinates.h"
#include "Human.h"


int main() {
	srand(time(NULL));
	char input;
	World* world;
	while (true) {
		cout << "N - new game, L - load game\n";
		cin >> input;
		if (input == 'N' || input == 'n') {
			int length, width;
			cout << "Give dimensions of the world (x, y): ";
			cin >> length >> width;
			double fill = 0;
			bool fillIsCorrect = false;
			while (!fillIsCorrect) {
				cout << "Give the fill of the world (0-1): ";
				cin >> fill;
				if (fill > 1) {
					cout << "Wrong fill. It cannot be bigger than 1. Try again.\n";
				}else {
					fillIsCorrect = true;
				}
			}
			int number = floor(length * width * fill);
			world = new World(number, length, width);
			break;
		}else if (input == 'L' || input == 'l') {
			world = World::loadWorld();
			break;
		}else {
			cout << "Wrong input. Try again.\n";
		}
	}

	cout << "\nBogna Lew 184757\n";
	cout << "Arrows - controling human; space - activating special skill (\"Antelope's speed\")\n\n";

	world->drawWorld();
	while (!world->GetIsEndOfGame()) {
		world->makeTurn();
	}

	delete world;
	return 0;
}