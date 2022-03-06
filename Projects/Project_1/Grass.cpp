#include "Grass.h"

Grass::Grass(int whenWasBorn, Coordinates coords, World* world) : Plant(0, whenWasBorn, 'g', coords, world, Organism::TypeOfOrganism::GRASS, false) {

}

void Grass::GetOrganismName() {
	cout << "Grass";
}

Grass::~Grass(){}