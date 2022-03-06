#include "Milt.h"

Milt::Milt(int whenWasBorn, Coordinates coords, World* world) : Plant(0, whenWasBorn, 'm', coords, world, Organism::TypeOfOrganism::MILT, false) {

}

void Milt::action() {
	int n = 0;
	for (int i = 0; i < 3; i++) {
		n = rand() % 50;
		if (n >= 45) {
			spread();
		}
	}
}

void Milt::GetOrganismName() {
	cout << "Milt";
}

Milt::~Milt() {}