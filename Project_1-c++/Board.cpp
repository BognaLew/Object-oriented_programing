#include "Board.h"
#include <iostream>

Board::Board(int x, int y) {
	this->sizeX = x;
	this->sizeY = y;

	board = new Organism * *[y];
	for (int i = 0; i < x; i++) {
		board[i] = new Organism * [x];
	}
	for (int i = 0; i < x; i++) {
		for (int j = 0; j < y; j++) {
			board[i][j] = nullptr;
		}
	}
}
Board::Board(Board&& other) {
	this->sizeX = other.sizeX;
	this->sizeY = other.sizeY;

	this->board = new Organism * *[sizeY];
	for (int i = 0; i < sizeX; i++) {
		this->board[i] = new Organism * [sizeX];
	}
	for (int i = 0; i < sizeX; i++) {
		for (int j = 0; j < sizeY; j++) {
			this->board[i][j] = other.board[i][j];
		}
	}
}

Organism* Board::GetOrganism(Coordinates coords) {
	return board[coords.GetX()][coords.GetY()];
}

void Board::SetOrganism(Organism* organism) {
	board[organism->GetCoordinates().GetX()][organism->GetCoordinates().GetY()] = organism;
}

void Board::moveOrganism(Organism* organism, Coordinates prevCoords) {
	
	board[prevCoords.GetX()][prevCoords.GetY()] = nullptr;
	SetOrganism(organism);
}

void Board::removeOrganism(Organism* organism) {

	board[organism->GetCoordinates().GetX()][organism->GetCoordinates().GetY()] = nullptr;
}

void Board::drawBoard() {

	for (int i = -1; i < sizeX; i++) {
		if (i >= 0) {
			cout << i%10 << " ";
		}
		else {
			cout << " ";
		}
	}
	cout << endl;
	for (int i = 0; i < sizeY; i++) {
		cout << i%10;
		for (int j = 0; j < sizeX; j++) {
			Organism* o = GetOrganism(Coordinates(j, i));
			if (o == nullptr) {
				cout << "  ";
			}
			else {
				cout << o->GetSymbol() << " ";
			}
		}
		cout << i%10 << endl;
	}
	for (int i = -1; i < sizeX; i++) {
		if (i >= 0) {
			cout << i%10 << " ";
		}
		else {
			cout << " ";
		}
	}
	cout << endl;
}

int Board::GetSizeX() {
	return sizeX;
}

int Board::GetSizeY() {
	return sizeY;
}

Coordinates Board::randEmptyArea() {
	while (true) {
		int x = rand() % sizeX;
		int y = rand() % sizeY;
		if (board[x][y] == nullptr) {
			return Coordinates(x, y);
		}else {
			continue;
		}
	}
}