#include "Antelope.h"
#include "Human.h"

Antelope::Antelope(int  whenWasBorn, Coordinates coords, World* world) : Animal(4, 4, whenWasBorn, 2, 'A', coords, world, Organism::TypeOfOrganism::ANTELOPE, true) {

}

void Antelope::specialColision(Organism* other, int x, int y, bool isAttacking) {
	if (dynamic_cast<Antelope*>(other)) {
		this->reproduction(this, dynamic_cast<Animal*>(other));
	}else{
		int chance = rand() % 100;
		if (chance >= 50 && other->GetIsAnimal()) {
			world->printMessage(this, other, true, true);
			if (isAttacking) {
				this->SetCoorinates(x, y);
			}
			this->SetFreeDirections(this->moveRange);
			Organism::Direction d = this->randDirection();
			Coordinates c;
			Coordinates c1 = this->GetCoordinates();
			int x1 = c1.GetX(), y1 = c1.GetY();
			if (d == Direction::UP) {
				y1 -= moveRange;
			}else if (d == Direction::DOWN) {
				y1 += moveRange;
			}else if (d == Direction::LEFT) {
				x1 += moveRange;
			}else if (d == Direction::RIGHT) {
				x1 -= moveRange;
			}
			c.SetX(x1);
			c.SetY(y1);
			this->SetCoorinates(x1, y1);
			world->GetBoard()->moveOrganism(this, c1);
			if (!isAttacking) {
				Coordinates c2 = other->GetCoordinates();
				other->SetCoorinates(x, y);
				world->GetBoard()->moveOrganism(other, c2);
			}
		}else {
			if (this->strenght >= other->GetStrenght()) {
				if (dynamic_cast<Human*>(other)) {
					world->SetIsHumanAlive(false);
				}
				if (isAttacking) {
					Coordinates c1 = other->GetCoordinates();
					this->SetCoorinates(x, y);
					world->GetBoard()->moveOrganism(other, c1);
				}
				this->GetWorld()->deleteOrganism(other);
				world->GetBoard()->removeOrganism(this);
				world->printMessage(this, other, true, false);
				delete other;
			}
			else {
				world->printMessage(other, this, true, false);
				world->GetBoard()->removeOrganism(this);
				other->GetWorld()->deleteOrganism(this);
				if (!isAttacking) {
					Coordinates c1 = other->GetCoordinates();
					other->SetCoorinates(x, y);
					world->GetBoard()->moveOrganism(other, c1);
				}
				delete this;
				
			}
		}
	}
}

void Antelope::GetOrganismName() {
	cout << "Antelope";
}

Antelope::~Antelope(){}