#include "Guarana.h"

Guarana::Guarana(int whenWasBorn, Coordinates coords, World* world) : Plant(0, whenWasBorn, 'u', coords, world, Organism::TypeOfOrganism::GUARANA, true) {

}

void Guarana::specialColision(Organism* other, int x, int y, bool isAttacking) {
	other->SetStrenght(other->GetStrenght() + 3);
	world->printMessage(other, this, true, false);
	other->GetWorld()->deleteOrganism(this);
	Coordinates c1 = other->GetCoordinates();
	other->SetCoorinates(x, y);
	world->GetBoard()->moveOrganism(other, c1);
	delete this;
}

void Guarana::GetOrganismName() {
	cout << "Guarana";
}

Guarana::~Guarana() {}