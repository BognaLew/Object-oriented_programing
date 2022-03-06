#pragma once
#include "Plant.h"

class Hogweed : public Plant {
public:
	Hogweed(int whenWasBorn, Coordinates coords, World* world);
	void GetOrganismName() override;
	void action() override;
	void specialColision(Organism* other, int x, int y, bool isAttacking) override;
	~Hogweed();
};
