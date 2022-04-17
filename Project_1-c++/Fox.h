#pragma once
#include "Animal.h"

class Fox : public Animal{
public:
	Fox(int  whenWasBorn, Coordinates coords, World* world);
	void action() override;
	void GetOrganismName() override;
	~Fox();

};
