#pragma once
#include <iostream>
#include "World.h"
#include "Board.h"
#include "Coordinates.h"

class World;
class Coordinates;

class Organism {
public:
	enum class Direction {
		LEFT,
		RIGHT,
		UP,
		DOWN,
		NONE
	};

	enum class TypeOfOrganism {
		HUMAN,		//cz³owiek
		WOLF,		//wilk
		SHEEP,		//owca
		FOX,		//lis
		TURTLE,		//¿ó³w
		ANTELOPE,	//antylopa
		GRASS,		//trawa
		MILT,		//mlecz
		GUARANA,	//guarana
		BELLADONNA,	//wilcze jagody
		HOGWEED		//barsz Sosnowskiego
	};

	virtual void GetOrganismName() = 0;
	virtual void action() = 0;
	virtual void colision(Organism* attacking, Organism* other, int x, int y) = 0;
	virtual void specialColision(Organism* other, int x, int y, bool isAttacking) = 0;

	int GetStrenght();
	int GetInitiative();
	int GetWhenWasBorn();
	char GetSymbol();
	bool GetReproduced();
	Coordinates GetCoordinates();
	World* GetWorld();
	TypeOfOrganism GetType();
	bool GetHasSpecialColision();
	bool GetIsAnimal();

	void SetDirections(int moveRange);
	void SetFreeDirections(int moveRange);
	void SetCoorinates(int x, int y);
	void SetReproduced(bool reproduced);
	void SetStrenght(int strenght);
	void SetWorld(World* world);
	void SetWhenWasBorn(int whenWasBorn);

	Direction randDirection();

	TypeOfOrganism randType();
	


protected:
	int strenght, initiative, whenWasBorn;
	char symbol;
	bool reproduced, hasSpecialColision, isAnimal;
	bool directions[4];
	Coordinates coords;
	World* world;
	TypeOfOrganism type;

	

	Organism(int strenght, int initiative, int  whenWasBorn, char symbol, Coordinates coords, World* world, TypeOfOrganism type, bool hasSpecialColision, bool isAnimal);
};
