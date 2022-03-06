#pragma once
#include "Plant.h"

class Belladonna : public Plant {
public:
	Belladonna(int whenWasBorn, Coordinates coords, World* world);
	void GetOrganismName() override;
	void specialColision(Organism* other, int x, int y, bool isAttacking) override;
	~Belladonna();
};
