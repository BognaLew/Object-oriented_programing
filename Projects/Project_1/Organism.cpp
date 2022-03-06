#include "Organism.h"

Organism::Organism(int strenght, int initiative, int  whenWasBorn, char symbol, Coordinates coords, World* world, TypeOfOrganism type, bool hasSpecialColision, bool isAnimal) {
	this->strenght = strenght;
	this->initiative = initiative;
	this->whenWasBorn = whenWasBorn;
	this->coords = coords;
	this->world = world;
	this->type = type;
	this->symbol = symbol;
	this->hasSpecialColision = hasSpecialColision;
	this->isAnimal = isAnimal;
	reproduced = false;
}


int Organism::GetStrenght() {
	return strenght;
}

int Organism::GetInitiative() {
	return initiative;
}

int Organism::GetWhenWasBorn() {
	return whenWasBorn;
}

char Organism::GetSymbol() {
	return symbol;
}

bool Organism::GetReproduced() {
	return reproduced;
}

Coordinates Organism::GetCoordinates() {
	return Coordinates(coords.GetX(), coords.GetY());
}

World* Organism::GetWorld() {
	return world;
}

Organism::TypeOfOrganism Organism::GetType() {
	return type;
}

bool Organism::GetHasSpecialColision() {
	return hasSpecialColision;
}

bool Organism::GetIsAnimal() {
	return isAnimal;
}

void Organism::SetDirections(int moveRange) {
	directions[(int)Direction::LEFT] = true;
	directions[(int)Direction::RIGHT] = true;
	directions[(int)Direction::UP] = true;
	directions[(int)Direction::DOWN] = true;
	Coordinates left(coords.GetX() + moveRange, coords.GetY()), right(coords.GetX() - moveRange, coords.GetY()), up(coords.GetX(), coords.GetY()- moveRange), down(coords.GetX(), coords.GetY() + moveRange);
	if (left.GetX() >= world->GetBoard()->GetSizeX()) {
		directions[(int)Direction::LEFT] = false;
	}
	if (right.GetX() < 0 ) {
		directions[(int)Direction::RIGHT] = false;
	}
	if (up.GetY() < 0) {
		directions[(int)Direction::UP] = false;
	}
	if (down.GetY() >= world->GetBoard()->GetSizeY()) {
		directions[(int)Direction::DOWN] = false;
	}
}

void Organism::SetFreeDirections(int moveRange) {
	directions[(int)Direction::LEFT] = true;
	directions[(int)Direction::RIGHT] = true;
	directions[(int)Direction::UP] = true;
	directions[(int)Direction::DOWN] = true;
	Coordinates left(coords.GetX() + moveRange, coords.GetY()), right(coords.GetX() - moveRange, coords.GetY()), up(coords.GetX(), coords.GetY() - moveRange), down(coords.GetX(), coords.GetY() + moveRange);
	
	if (left.GetX() >= world->GetBoard()->GetSizeX()) {
		directions[(int)Direction::LEFT] = false;
	}else if (world->GetBoard()->GetOrganism(left) != nullptr && world->GetBoard()->GetOrganism(left)->GetStrenght() > this->strenght) {
		directions[(int)Direction::LEFT] = false;
	}
	if (right.GetX() < 0) {
		directions[(int)Direction::RIGHT] = false;
	}else if (world->GetBoard()->GetOrganism(right) != nullptr && world->GetBoard()->GetOrganism(right)->GetStrenght() > this->strenght) {
		directions[(int)Direction::RIGHT] = false;
	}
	if (up.GetY() < 0) {
		directions[(int)Direction::UP] = false;
	}else if (world->GetBoard()->GetOrganism(up) != nullptr && world->GetBoard()->GetOrganism(up)->GetStrenght() > this->strenght) {
		directions[(int)Direction::UP] = false;
	}
	if (down.GetY() >= world->GetBoard()->GetSizeY()) {
		directions[(int)Direction::DOWN] = false;
	}else if (world->GetBoard()->GetOrganism(down) != nullptr && world->GetBoard()->GetOrganism(down)->GetStrenght() > this->strenght) {
		directions[(int)Direction::DOWN] = false;
	}
}

void Organism::SetCoorinates(int x, int y) {
	coords.SetX(x);
	coords.SetY(y);
}

void Organism::SetReproduced(bool reproduced) {
	this->reproduced = reproduced;
}

void Organism::SetStrenght(int strenght) {
	this->strenght = strenght;
}

void Organism::SetWorld(World* world) {
	this->world = world;
}

void Organism::SetWhenWasBorn(int whenWasBorn) {
	this->whenWasBorn = whenWasBorn;
}


Organism::TypeOfOrganism Organism::randType() {
	int n = rand() % 10;
	switch (n) {
	case 0: return TypeOfOrganism::WOLF;
	case 1: return TypeOfOrganism::SHEEP;
	case 2: return TypeOfOrganism::FOX;
	case 3: return TypeOfOrganism::TURTLE;
	case 4: return TypeOfOrganism::ANTELOPE;
	case 5: return TypeOfOrganism::GRASS;
	case 6: return TypeOfOrganism::MILT;
	case 7: return TypeOfOrganism::GUARANA;
	case 8: return TypeOfOrganism::BELLADONNA;
	case 9: return TypeOfOrganism::HOGWEED;
	}
}

Organism::Direction Organism::randDirection() {
	if (directions[0] == false && directions[1] == false && directions[2] == false && directions[3] == false) {
		return Direction::NONE;
	}else {
		while (true) {
			int n = rand() % 4;
			if (directions[n]) {
				return (Direction)n;
			}
		}
	}
}

