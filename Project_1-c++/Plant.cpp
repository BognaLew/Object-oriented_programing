#include "Plant.h"

Plant::Plant(int strenght, int  whenWasBorn, char symbol, Coordinates coords, World* world, TypeOfOrganism type, bool hasSpecialColision) : Organism(strenght, 0, whenWasBorn, symbol, coords, world, type, hasSpecialColision, false) {

}

void Plant::action(){
	int n = rand() % 50;
	if (n >= 45) {
		spread();
	}
}

void Plant::colision(Organism* attacking, Organism* other, int x, int y){}

void Plant::specialColision(Organism* other, int x, int y, bool isAttacking){}

void Plant::spread(){
	SetFreeDirections(1);
	Direction d = randDirection();
	int x = GetCoordinates().GetX(), y = GetCoordinates().GetY();
	if (d == Direction::UP) {
		y -= 1;
	}else if (d == Direction::DOWN) {
		y += 1;
	}else if (d == Direction::LEFT) {
		x += 1;
	}else if (d == Direction::RIGHT) {
		x -= 1;
	}else {
		return;
	}
	Coordinates newCoordinates(x, y);
	Organism* newOrganism = world->createNewOrganism((int)type, newCoordinates);
	world->addOrganism(newOrganism);
	world->GetBoard()->SetOrganism(newOrganism);
}

Plant::~Plant(){}