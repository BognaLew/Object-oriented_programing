#pragma once

class Coordinates {
private:
	int x, y;
public:
	Coordinates(int x, int y);
	Coordinates();

	int GetX();
	int GetY();

	void SetX(int X);
	void SetY(int y);

	~Coordinates();
};