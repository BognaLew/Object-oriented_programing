#include "Fox.h"

Fox::Fox(int  whenWasBorn, Coordinates coords, World* world) : Animal(3, 7, whenWasBorn, 1, 'F', coords, world, Organism::TypeOfOrganism::FOX, false) {

}

void Fox::action() {
	int x = this->GetCoordinates().GetX();
	int y = this->GetCoordinates().GetY();
	this->SetFreeDirections(moveRange);
	Direction d = this->randDirection();
	if (d == Direction::UP) {
		y -= moveRange;
	}
	else if (d == Direction::DOWN) {
		y += moveRange;
	}
	else if (d == Direction::LEFT) {
		x += moveRange;
	}
	else if (d == Direction::RIGHT) {
		x -= moveRange;
	}
	Coordinates c1 = this->GetCoordinates();
	this->SetCoorinates(x, y);
	world->GetBoard()->moveOrganism(this, c1);
}

void Fox::GetOrganismName() {
	cout << "Fox";
}

Fox::~Fox(){}