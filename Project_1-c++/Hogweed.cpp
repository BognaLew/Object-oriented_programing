#include "Hogweed.h"
#include "Human.h"

Hogweed::Hogweed(int whenWasBorn, Coordinates coords, World* world) : Plant(10, whenWasBorn, 'h', coords, world, Organism::TypeOfOrganism::HOGWEED, true) {

}
void Hogweed::GetOrganismName() {
	cout << "Hogweed";
}

void Hogweed::action() {
	this->SetDirections(1);
	int x = GetCoordinates().GetX(), y = GetCoordinates().GetY();
	Coordinates c[4] = { Coordinates(x + 1,y), Coordinates(x - 1, y), Coordinates(x, y - 1), Coordinates(x, y + 1) };
	for (int i = 0; i < 4; i++) {
		if (directions[i] == true) {
			Organism* tmp = world->GetBoard()->GetOrganism(c[i]);
			if (tmp != nullptr && tmp->GetIsAnimal()) {
				world->printMessage(this, tmp, true, false);
				if (dynamic_cast<Human*>(tmp)) {
					world->SetIsHumanAlive(false);
				}
				world->GetBoard()->removeOrganism(tmp);
				this->GetWorld()->deleteOrganism(tmp);
				delete tmp;
			}
		}
	}
}

void Hogweed::specialColision(Organism* other, int x, int y, bool isAttacking) {
	world->printMessage(this, other, true, false);
	if (dynamic_cast<Human*>(other)) {
		world->SetIsHumanAlive(false);
	}
	world->GetBoard()->removeOrganism(other);
	this->GetWorld()->deleteOrganism(other);
	delete other;
}

Hogweed::~Hogweed() {}