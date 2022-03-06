#pragma once
#include "Animal.h"


class Human : public Animal {
private:
	int numberOfTurnsSkillWillBeActive;
	bool skillIsActive, isAlive;
	Direction d;
public:
	Human(Coordinates coords, World* world);

	bool GetIsAlive();
	bool GetSkillIsActive();
	int GetNumberOfTurnsSkillWillBeActive();
	Direction GetDirection();
	void GetOrganismName() override;

	void SetSkillIsActive(bool isActive);
	void SetIsAlive(bool isAlive);
	void SetNumberOfTurnsSkillWillBeActive(int number);
	void SetDirection(Direction d);

	void SetMoveRange(int moveRange);

	void activateSkill();
	void deactivateSkill();

	void action() override;

	~Human();
};