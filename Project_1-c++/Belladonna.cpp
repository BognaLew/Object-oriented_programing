#include "Belladonna.h"
#include "Human.h"

Belladonna::Belladonna(int whenWasBorn, Coordinates coords, World* world) : Plant(99, whenWasBorn, 'b', coords, world, Organism::TypeOfOrganism::BELLADONNA, true) {

}
void Belladonna::GetOrganismName(){
	cout << "Belladonna";
}

void Belladonna::specialColision(Organism* other, int x, int y, bool isAttacking){
	world->printMessage(this, other, true, false);
	if (dynamic_cast<Human*>(other)) {
		world->SetIsHumanAlive(false);
	}
	world->GetBoard()->removeOrganism(other);
	other->GetWorld()->deleteOrganism(other);
	delete other;
}

Belladonna::~Belladonna(){}