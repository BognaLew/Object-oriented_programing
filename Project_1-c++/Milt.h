#pragma once
#include "Plant.h"

class Milt : public Plant {
public:
	Milt(int whenWasBorn, Coordinates coords, World* world);
	void action() override;
	void GetOrganismName() override;
	~Milt();
};