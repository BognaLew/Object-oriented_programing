#pragma once
#include "Animal.h"

class Antelope : public Animal {
public:
	Antelope(int  whenWasBorn, Coordinates coords, World* world);
	void specialColision(Organism* other, int x, int y, bool isAttacking) override;
	void GetOrganismName() override;
	~Antelope();
};