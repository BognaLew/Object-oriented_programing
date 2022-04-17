#pragma once
#include "Animal.h"

class Turtle : public Animal {
public:
	Turtle(int  whenWasBorn, Coordinates coords, World* world);
	void action() override;
	void specialColision(Organism* other, int x, int y, bool isAttacking) override;
	void GetOrganismName() override;
	~Turtle();
};
