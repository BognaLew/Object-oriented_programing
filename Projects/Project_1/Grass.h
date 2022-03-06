#pragma once
#include "Plant.h"

class Grass : public Plant {
public:
	Grass(int whenWasBorn, Coordinates coords, World* world);
	void GetOrganismName() override;
	~Grass();
};
