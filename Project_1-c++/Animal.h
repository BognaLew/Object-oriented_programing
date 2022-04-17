#pragma once
#include "Organism.h"


class Animal : public Organism {
protected:
	int moveRange;
	virtual void reproduction(Animal* moving, Animal* other);
public:
	Animal(int strenght, int initiative, int  whenWasBorn, int moveRange, char symbol, Coordinates coords, World* world, TypeOfOrganism type, bool hasSpecialColision);

	virtual void action() override;
	virtual void colision(Organism* attacking, Organism* other, int x, int y) override;
	virtual void specialColision(Organism* other, int x, int y, bool isAttacking);

	int GetMoveRange();

	~Animal();
	
};
