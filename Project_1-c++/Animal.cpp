#include "Animal.h"
#include "Human.h"

Animal::Animal(int strenght, int initiative, int  whenWasBorn, int moveRange, char symbol, Coordinates coords, World* world, TypeOfOrganism type, bool hasSpecialColision) : Organism(strenght, initiative, whenWasBorn, symbol, coords, world, type, hasSpecialColision, true){
	this->moveRange = moveRange;
}

void Animal::reproduction(Animal* moving, Animal* other) {
	if (moving->reproduced || other->reproduced) {
		return;
	}else{
		Coordinates newCoords;
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
		newCoords.SetX(x);
		newCoords.SetY(y);
		Organism* newOrganism = world->createNewOrganism((int)moving->type, newCoords);
		moving->GetWorld()->addOrganism(newOrganism);
		moving->GetWorld()->GetBoard()->SetOrganism(newOrganism);
		moving->SetReproduced(true);
		other->SetReproduced(true);
		world->printMessage(newOrganism, other, false, false);
	}
}

void Animal::colision(Organism* attacking, Organism* other, int x, int y) {
	if (other->GetType() == type) {
		this->reproduction(this, dynamic_cast<Animal*>(other));
	}else if (other->GetHasSpecialColision()) {
		other->specialColision(attacking, x, y, false);
	}else if (this->GetHasSpecialColision()) {
		attacking->specialColision(other, x, y, true);
	}else if(attacking->GetStrenght() >= other->GetStrenght()) {
		world->printMessage(attacking, other, true, false);
		if (dynamic_cast<Human*>(other)) {
			world->SetIsHumanAlive(false);
		}
		attacking->GetWorld()->deleteOrganism(other);
		Coordinates c1 = this->GetCoordinates();
		attacking->SetCoorinates(x, y);
		world->GetBoard()->moveOrganism(attacking, c1);
		delete other;
	}else{
		world->printMessage(other, attacking, true, false);
		if (dynamic_cast<Human*>(attacking)) {
			world->SetIsHumanAlive(false);
		}
		world->GetBoard()->removeOrganism(attacking);
		other->GetWorld()->deleteOrganism(attacking);
		delete attacking;
	}
}

void Animal::specialColision(Organism* other, int x, int y, bool isAttacking) {}

void Animal::action() {
	Coordinates c;
	int x = this->GetCoordinates().GetX();
	int y = this->GetCoordinates().GetY();
	this->SetDirections(moveRange);
	Direction d = this->randDirection();
	if (d == Direction::UP) {
		y -= moveRange;
	}else if (d == Direction::DOWN) {
		y += moveRange;
	}else if(d == Direction::LEFT){
		x += moveRange;
	}else if (d == Direction::RIGHT) {
		x -= moveRange;
	}else {
		return;
	}
	c.SetX(x);
	c.SetY(y);
	Organism* tmp = world->GetBoard()->GetOrganism(c);
	if (tmp == nullptr) {
		Coordinates c1 = this->GetCoordinates();
		this->SetCoorinates(x, y);
		world->GetBoard()->moveOrganism(this, c1);
	}else {
		this->colision(this, tmp, x, y);
	}
};

int Animal::GetMoveRange() {
	return moveRange;
}

Animal::~Animal() {}

