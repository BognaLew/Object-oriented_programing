#pragma once
#include "Organism.h"

class Plant : public Organism {
protected:
	virtual void spread();

public:
	Plant(int strenght, int  whenWasBorn, char symbol, Coordinates coords, World* world, TypeOfOrganism type, bool hasSpecialColision);

	virtual void action() override;
	virtual void colision(Organism* attacking, Organism* other, int x, int y) override;
	virtual void specialColision(Organism* other, int x, int y, bool isAttacking) override;

	~Plant();
};
