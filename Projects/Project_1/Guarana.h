#pragma once
#include "Plant.h"

class Guarana : public Plant {
public:
	Guarana(int whenWasBorn, Coordinates coords, World* world);
	void specialColision(Organism* other, int x, int y, bool isAttacking) override;
	void GetOrganismName() override;
	~Guarana();
};
