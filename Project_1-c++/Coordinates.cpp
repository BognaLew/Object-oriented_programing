#include "Coordinates.h"

Coordinates::Coordinates(int x, int y) {
	this->x = x;
	this->y = y;
}

Coordinates::Coordinates() {
	x = 0;
	y = 0;
}

int Coordinates::GetX() {
	return x;
}

int Coordinates::GetY() {
	return y;
}

void Coordinates::SetX(int x) {
	this->x = x;
}

void Coordinates::SetY(int y) {
	this->y = y;
}

Coordinates::~Coordinates() {}