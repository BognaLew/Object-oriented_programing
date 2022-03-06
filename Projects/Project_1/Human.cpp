#include "Human.h"

Human::Human(Coordinates coords, World* world) : Animal(5, 4, 0, 1, 'H', coords, world, Organism::TypeOfOrganism::HUMAN, false) {
	numberOfTurnsSkillWillBeActive = 0;
	skillIsActive = false;
	isAlive = true;
	d = Direction::NONE;
}

bool Human::GetIsAlive() {
	return isAlive;
}

bool Human::GetSkillIsActive() {
	return skillIsActive;
}

int Human::GetNumberOfTurnsSkillWillBeActive() {
	return numberOfTurnsSkillWillBeActive;
}

Organism::Direction Human::GetDirection() {
	return d;
}

void Human::GetOrganismName() {
	cout << "Human";
}

void Human::SetSkillIsActive(bool isActive) {
	this->skillIsActive = isActive;
}

void Human::SetIsAlive(bool isAlive) {
	this->isAlive = isAlive;
}

void Human::SetNumberOfTurnsSkillWillBeActive(int number) {
	this->numberOfTurnsSkillWillBeActive = number;
}

void Human::SetMoveRange(int moveRange){
	this->moveRange = moveRange;
}

void Human::SetDirection(Organism::Direction d) {
	this->d = d;
}

void Human::activateSkill() {
	SetMoveRange(2);
	SetNumberOfTurnsSkillWillBeActive(10);
	SetSkillIsActive(true);
}

void Human::deactivateSkill() {
	SetMoveRange(1);
	SetNumberOfTurnsSkillWillBeActive(5);
	SetSkillIsActive(false);
}

void Human::action() {
	if (skillIsActive) {
		if (numberOfTurnsSkillWillBeActive < 7) {
			int i = rand() % 100;
			if (i >= 50) {
				SetMoveRange(2);
			}
			else {
				SetMoveRange(1);
			}
		}
		numberOfTurnsSkillWillBeActive--;
		if (numberOfTurnsSkillWillBeActive == 5) {
			deactivateSkill();
		}
	}else if (numberOfTurnsSkillWillBeActive > 0) {
		numberOfTurnsSkillWillBeActive--;
	}
	if (d != Direction::NONE) {
		Coordinates c;
		int x = this->GetCoordinates().GetX();
		int y = this->GetCoordinates().GetY();
		if (d == Direction::UP && y - moveRange >= 0) {
			c.SetX(x);
			c.SetY(y - moveRange);
			y -= moveRange;
		}
		else if (d == Direction::DOWN && y + moveRange < world->GetBoard()->GetSizeY()) {
			c.SetX(x);
			c.SetY(y + moveRange);
			y += moveRange;
		}
		else if (d == Direction::LEFT && x - moveRange >= 0) {
			c.SetX(x - moveRange);
			c.SetY(y);
			x -= moveRange;
		}
		else if (d == Direction::RIGHT && x + moveRange < world->GetBoard()->GetSizeX()) {
			c.SetX(x + moveRange);
			c.SetY(y);
			x += moveRange;
		}
		Organism* tmp = world->GetBoard()->GetOrganism(c);
		if (tmp == nullptr) {
			Coordinates c1 = this->GetCoordinates();
			this->SetCoorinates(x, y);
			world->GetBoard()->moveOrganism(this, c1);
		}
		else {
			this->colision(this, tmp, x, y);
		}
	}
}

Human::~Human(){}