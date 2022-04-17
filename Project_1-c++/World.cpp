#include "World.h"

#include "Human.h"
#include "Wolf.h"
#include "Turtle.h"
#include "Sheep.h"
#include "Fox.h"
#include "Antelope.h"
#include "Board.h"

#include "Grass.h"
#include "Milt.h"
#include "Guarana.h"
#include "Belladonna.h"
#include "Hogweed.h"

#include <algorithm>
#include <iostream>
#include <fstream>
#include <conio.h>
using namespace std;

#define MAX_DIRECTIONS 4

enum inputs {
	if_arrows = 224,	//sterowanie
	arrow_up = 72,
	arrow_down = 80,
	arrow_left = 75,
	arrow_right = 77,
	space = 32,		//aktywacja umiejêtnoœci
	esc = 27,		//zamykanie gry
	enter = 13,		//pzechodzenie do kolejnej tury
	s = 115,		//ma³e s  <- do zapisywania
	S = 83			//du¿e s
};

World::World(int sizeX, int sizeY) {
	numberOfOrganisms = 0;
	board = new Board(sizeX, sizeY);
	turn = 0;
	isHumanAlive = true;
	organisms.reserve(0);
}

World::World(int numberOfOrganisms, int sizeX, int sizeY) {
	this->numberOfOrganisms = numberOfOrganisms;
	board = new Board(sizeX, sizeY);
	Coordinates c = board->randEmptyArea();
	Organism* human = new Human(c, this);
	this->human = dynamic_cast<Human*>(human);
	addOrganism(human);
	for (int k = 0; k < numberOfOrganisms - 1; k++) {
		Coordinates coords = board->randEmptyArea();
		Organism::TypeOfOrganism type = human->randType();
		Organism* o = createNewOrganism((int)type, coords);
		addOrganism(o);
	}
	isHumanAlive = true;
	isEndOfGame = false;
}

World::World(World&& other)
{
	this->turn = other.turn;
	this->board = other.board;
	this->isHumanAlive = other.isHumanAlive;
	this->isEndOfGame = other.isEndOfGame;
	this->organisms = other.organisms;
	this->human = other.human;

	for (size_t i = 0; i < organisms.size(); i++) {
		organisms[i]->SetWorld(this);
	}

	other.turn = 0;
	other.board = nullptr;
	other.isHumanAlive = false;
	other.isEndOfGame = false;
	other.human = nullptr;
}

int World::GetTurn() {
	return turn;
}

Board* World::GetBoard() {
	return board;
}

bool World::GetIsHumanAlive() {
	return isHumanAlive;
}

bool World::GetIsEndOfGame() {
	return isEndOfGame;
}

void World::SetIsHumanAlive(bool isAlive) {
	this->isHumanAlive = isAlive;
}

void World::SetIsEndOfGame(bool isEndOfGame) {
	this->isEndOfGame = isEndOfGame;
}

void World::SetTurn(int turn) {
	this->turn = turn;
}

void World::SetHuman(Human* human) {
	this->human = human;
}

void World::SetNumberOfOrganisms(int number) {
	this->numberOfOrganisms = number;
}

World* World::loadWorld() {
	fstream file;
	string fileName;
	cout << "Loading world from text file.\n";
	bool isFileGood = false;
	cout << "Give file name (add .txt at the end): ";
	while (!isFileGood) {
		cin >> fileName;
		file.open(fileName, fstream::in);
		if (file.good()) {
			cout << "Opening file...\n";
			isFileGood = true;
		}
		else {
			cout << "Wrong file. Try again.\n";
		}
	}
	if (file.is_open()) {
		int x, y, numberOfOrganisms, turn, isHumanAlive;
		file >> x >> y >> numberOfOrganisms >> turn >> isHumanAlive;
		World* tmpWorld = new World(x, y);
		tmpWorld->SetNumberOfOrganisms(numberOfOrganisms);
		tmpWorld->SetIsHumanAlive(isHumanAlive);
		tmpWorld->SetTurn(turn);
		int type, strenght, initiative, whenWasBorn, skillIsActive, numberOfTurnsSkillWillBeActive;
		Organism* tmpOrganism = nullptr;
		for (int i = 0; i < numberOfOrganisms; i++) {
			file >> type >> strenght >> initiative >> whenWasBorn >> x >> y;
			if (type == (int)Organism::TypeOfOrganism::HUMAN) {
				file >> skillIsActive >> numberOfTurnsSkillWillBeActive;
				tmpOrganism = new Human(Coordinates(x, y), tmpWorld);
				tmpOrganism->SetStrenght(strenght);
				Human* human = dynamic_cast<Human*>(tmpOrganism);
				human->SetSkillIsActive(skillIsActive);
				human->SetNumberOfTurnsSkillWillBeActive(numberOfTurnsSkillWillBeActive);
				tmpWorld->SetHuman(human);
			}else {
				tmpOrganism = tmpWorld->createNewOrganism(type, Coordinates(x, y));
				tmpOrganism->SetStrenght(strenght);
				tmpOrganism->SetWhenWasBorn(whenWasBorn);
			}
			tmpWorld->addOrganism(tmpOrganism);
		}
		return tmpWorld;
	}
	file.close();
	return nullptr;
}

void World::saveWorld() {
	fstream file;
	string fileName;
	cout << "Saving world to text file.\n";
	bool isFileGood = false;
	cout << "Give file name (add .txt at the end): ";
	while (!isFileGood) {
		cin >> fileName;
		file.open(fileName, fstream::out);
		if (file.good()) {
			cout << "Opening file...\n";
			isFileGood = true;
		}
		else {
			cout << "Wrong file. Try again.\n";
		}
	}
	file << board->GetSizeX() << " " << board->GetSizeY() << " " << organisms.size() << " " << turn << " " << (int)isHumanAlive << endl;
	for (int i = 0; i < organisms.size(); i++) {
		file << (int)organisms[i]->GetType() << " " << organisms[i]->GetStrenght() << " " << organisms[i]->GetInitiative() << " " << organisms[i]->GetWhenWasBorn() << " " << organisms[i]->GetCoordinates().GetX() << " " << organisms[i]->GetCoordinates().GetY();
		if (dynamic_cast<Human*>(organisms[i])) {
			Human* tmp = dynamic_cast<Human*>(organisms[i]);
			file << " " << tmp->GetSkillIsActive() << " " << tmp->GetNumberOfTurnsSkillWillBeActive();
		}
		file << endl;
	}
	file.close();
	cout << "World saved.\n";

}

void World::drawWorld() {
	board->drawBoard();
	
	cout << endl;
}

void World::printMessage(Organism* winning, Organism* other, bool ifAttack, bool specialAction) {
	if (ifAttack && !specialAction) {
		winning->GetOrganismName();
		cout << " [ x = " << winning->GetCoordinates().GetX() << " ; y = " << winning->GetCoordinates().GetY();
		if (other->GetIsAnimal()){
			cout << " ] kills ";
		}else {
			cout << " ] eats ";
		}
		other->GetOrganismName();
		cout << " [ x = " << other->GetCoordinates().GetX() << " ; y = " << other->GetCoordinates().GetY() << " ]\n";
	}
	else if (specialAction && dynamic_cast<Turtle*>(winning)) {
		winning->GetOrganismName();
		cout << " [ x = " << winning->GetCoordinates().GetX() << " ; y = " << winning->GetCoordinates().GetY() << " ] expels ";
		other->GetOrganismName();
		cout << " [ x = " << other->GetCoordinates().GetX() << " ; y = " << other->GetCoordinates().GetY() << " ]\n";
	}
	else if (specialAction && dynamic_cast<Antelope*>(winning)) {
		winning->GetOrganismName();
		cout << " [ x = " << winning->GetCoordinates().GetX() << " ; y = " << winning->GetCoordinates().GetY() << " ] escapes from ";
		other->GetOrganismName();
		cout << " [ x = " << other->GetCoordinates().GetX() << " ; y = " << other->GetCoordinates().GetY() << " ]\n";
	}
	else {
		cout << "New organism was born. ";
		winning->GetOrganismName();
		cout << " [ x = " << winning->GetCoordinates().GetX() << " ; y = " << winning->GetCoordinates().GetY() << " ]\n";
	}
}

void World::makeTurn() {
	input();
	if (isEndOfGame) {
		return;
	}
	sort(organisms.begin(), organisms.end(), [](Organism* first, Organism* second) {
		if (first->GetInitiative() > second->GetInitiative()) return true;
		else if (first->GetInitiative() == second->GetInitiative()) {
			if (first->GetWhenWasBorn() > second->GetWhenWasBorn()) return true;
			else return false;
		}
		else return false;
		});
	system("cls");
	cout << "Bogna Lew 184757\n";
	switch(isHumanAlive) {
	case 0: cout << "Human is dead. Press enter to move to the next turn.\n";
	case 1: cout << "Arrows - controling human; space - activating special skill (\"Antelope's speed\")\n";
	}
	cout << "S - save world; ESC - quit\n\n";
	
	for (int i = 0; i < organisms.size(); i++) {
		organisms.at(i)->action();
	}
	cout << endl;
	drawWorld();
	turn++;
}

void World::input() {
	while (true) {
		int znak = _getch();
		if (GetIsHumanAlive() && znak == if_arrows) {
			znak = _getch();
			if (znak == arrow_up) {
				human->SetDirection(Organism::Direction::UP);
				return;
			}else if (znak == arrow_down) {
				human->SetDirection(Organism::Direction::DOWN);
				return;
			}else if(znak == arrow_left) {
				human->SetDirection(Organism::Direction::LEFT);
				return;
			}else if(znak == arrow_right) {
				human->SetDirection(Organism::Direction::RIGHT);
				return;
			}
		}else if (GetIsHumanAlive() && znak == space) {
			if (human->GetSkillIsActive()) {
				cout << "Special skill is active. It will be deactivated after: " << human->GetNumberOfTurnsSkillWillBeActive() - 5 << " turns.\n";
			}else if (!human->GetSkillIsActive() && human->GetNumberOfTurnsSkillWillBeActive() > 0) {
				cout << "You cannot activate special skill yet. You will be able to do that after: " << human->GetNumberOfTurnsSkillWillBeActive() << "turns.\n";
			}else {
				human->activateSkill();
				human->SetDirection(Organism::Direction::NONE);
				cout << "Special skill activated: \"Antelope's speed\". The skill will be active for 5 turns.\n";
				return;
			}
		}else if (!GetIsHumanAlive() && (znak == if_arrows || znak == space)) {
			cout << "Human is dead. You can no longer control him.\n";
		}else if (znak == enter && !isHumanAlive) {
			human->SetDirection(Organism::Direction::NONE);
			return;
		}
		else if (znak == s || znak == S) {
			saveWorld();
		}
		else if (znak == esc) {
			SetIsEndOfGame(true);
			return;
		}
	}
}

Organism* World::createNewOrganism(int type, Coordinates coords) {
	switch (type) {
	case (int)Organism::TypeOfOrganism::WOLF: return new Wolf(this->GetTurn(), coords, this);
	case (int)Organism::TypeOfOrganism::SHEEP : return new Sheep(this->GetTurn(), coords, this);
	case (int)Organism::TypeOfOrganism::FOX : return new Fox(this->GetTurn(), coords, this);
	case (int)Organism::TypeOfOrganism::TURTLE : return new Turtle(this->GetTurn(), coords, this);
	case (int)Organism::TypeOfOrganism::ANTELOPE : return new Antelope(this->GetTurn(), coords, this);
	case (int)Organism::TypeOfOrganism::GRASS: return new Grass(this->GetTurn(), coords, this);
	case (int)Organism::TypeOfOrganism::MILT: return new Milt(this->GetTurn(), coords, this);
	case (int)Organism::TypeOfOrganism::GUARANA: return new Guarana(this->GetTurn(), coords, this);
	case (int)Organism::TypeOfOrganism::BELLADONNA: return new Belladonna(this->GetTurn(), coords, this);
	case (int)Organism::TypeOfOrganism::HOGWEED: return new Hogweed(this->GetTurn(), coords, this);
	}
}

void World::addOrganism(Organism* organism) {
	board->SetOrganism(organism);
	organisms.push_back(organism);
}

void World::deleteOrganism(Organism* organism) {
	int number = 0;
	for (int i = 0; i < organisms.size(); i++) {
		if (organism == organisms.at(i)) {
			number = i;
			break;
		}
	}
	organisms.erase(organisms.begin() + number);
	Organism* tmp = board->GetOrganism(organism->GetCoordinates());
	tmp = nullptr;
}


World::~World() {}