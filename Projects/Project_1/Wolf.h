#pragma once
#include "Animal.h"

class Wolf : public Animal {
public:
	Wolf(int  whenWasBorn, Coordinates coords, World* world);
	void GetOrganismName() override;
	~Wolf();
};
