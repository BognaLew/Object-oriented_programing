#pragma once
#include "Animal.h"

class Sheep : public Animal {
public:
	Sheep(int  whenWasBorn, Coordinates coords, World* world);
	void GetOrganismName() override;
	~Sheep();
};