#include "Wolf.h"

Wolf::Wolf(int whenWasBorn, Coordinates coords, World* world) : Animal(9, 5, whenWasBorn, 1, 'W', coords, world, Organism::TypeOfOrganism::WOLF, false) {

}

void Wolf::GetOrganismName() {
	cout << "Wolf";
}

Wolf::~Wolf(){}