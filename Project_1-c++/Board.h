#pragma once
#include "Organism.h"

class Coordinates;
class Organism;

class Board{
private:
	int sizeX, sizeY;
	Organism*** board;
public:
	Board(int x, int y);
	Board(Board&& other);
	Organism* GetOrganism(Coordinates coords);

	void SetOrganism(Organism* organism);

	void moveOrganism(Organism* organism, Coordinates newCoords);
	void removeOrganism(Organism* organism);

	void drawBoard();

	int GetSizeX();
	int GetSizeY();

	Coordinates randEmptyArea();
};
