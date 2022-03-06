#include "Turtle.h"

Turtle::Turtle(int  whenWasBorn, Coordinates coords, World* world) : Animal(2, 1, whenWasBorn, 1, 'T', coords, world, Organism::TypeOfOrganism::TURTLE, true) {

}

void Turtle::action() {
	int ifMoves = rand() % 100;
	if (ifMoves < 25) {
		Coordinates c;
		int x = this->GetCoordinates().GetX();
		int y = this->GetCoordinates().GetY();
		this->SetDirections(moveRange);
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
		c.SetX(x);
		c.SetY(y);
		Organism* tmp = world->GetBoard()->GetOrganism(c);
		if (tmp == nullptr) {
			Coordinates c1 = this->GetCoordinates();
			this->SetCoorinates(x, y);
			world->GetBoard()->moveOrganism(this, c1);
		}
		else {
			this->colision(this, tmp, x, y);
		}
	}
}

void Turtle::specialColision(Organism* other, int x, int y, bool isAttacking) {
	if (other->GetType() == type) {
		this->reproduction(this, (Animal*)other);
	}else if (other->GetStrenght() >= 5) {
		world->printMessage(other, this, true, false);
		other->GetWorld()->deleteOrganism(this);
		Coordinates c1 = other->GetCoordinates();
		other->SetCoorinates(x, y);
		world->GetBoard()->moveOrganism(other, c1);
		delete this;
	}else if(other->GetIsAnimal()){
		world->printMessage(this, other, true, true);
		if (isAttacking) {
			Coordinates c = other->GetCoordinates();
			this->SetCoorinates(x, y);
			world->GetBoard()->moveOrganism(this, c);
			other->SetFreeDirections(((Animal*)other)->GetMoveRange());
			Direction d = this->randDirection();
			int x1 = other->GetCoordinates().GetX(), y1 = other->GetCoordinates().GetY();
			if (d == Direction::UP) {
				y1 -= moveRange;
			}
			else if (d == Direction::DOWN) {
				y1 += moveRange;
			}
			else if (d == Direction::LEFT) {
				x1 += moveRange;
			}
			else if (d == Direction::RIGHT) {
				x1 -= moveRange;
			}
			Coordinates c1 = other->GetCoordinates();
			other->SetCoorinates(x1, y1);
			world->GetBoard()->moveOrganism(other, c1);
		}
	}
}

void Turtle::GetOrganismName() {
	cout << "Turtle";
}

Turtle::~Turtle(){}