#pragma once
#include "Organism.h"
#include <vector>
using namespace std;

class Organism;
class Human;
class Coordinates;
class Board;

class World {
private:
	int turn, numberOfOrganisms;
	bool isHumanAlive, isEndOfGame;
	vector<Organism*> organisms;
	Human* human;
	Board* board;
	
public:
	World(int sizeX, int sizeY);
	World(int numberOfOrganisms, int sizeX, int sizeY);
	World(World&& other);

	int GetTurn();
	Board* GetBoard();
	bool GetIsHumanAlive();
	bool GetIsEndOfGame();
	
	void SetIsHumanAlive(bool isAlive);
	void SetIsEndOfGame(bool isEndOfGame);
	void SetTurn(int turn);
	void SetHuman(Human* human);
	void SetNumberOfOrganisms(int number);
	
	static World* loadWorld();
	void saveWorld();
	void printMessage(Organism* moving, Organism* other, bool ifAttack, bool specialAction);
	void drawWorld();
	void makeTurn();
	void input();

	Organism* createNewOrganism(int type, Coordinates coords);
	void addOrganism(Organism* newOrganism);
	void deleteOrganism(Organism* organism);

	~World();
};