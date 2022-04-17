#include "Sheep.h"

Sheep::Sheep(int  whenWasBorn, Coordinates coords, World* world) : Animal(4, 4, whenWasBorn, 1, 'S', coords, world, Organism::TypeOfOrganism::SHEEP, false) {

}

void Sheep::GetOrganismName() {
	cout << "Sheep";
}

Sheep::~Sheep(){}
