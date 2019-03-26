#pragma once
#include "pch.h"

void CheckInput::CheckIfInt(int &input)
{
	try {
		cin >> input;
		if (cin.fail())
			throw 0;
		else if (input < 0)
			throw 1;
	}
	catch (int check)
	{
		if (check == 0)
		{
			cout << "You gave an invalid character! The value is changed to 0. If you want to change it, go to edition options" << endl;
			cin.clear();
			cin.ignore();
			input = 0;
		}
		else if (check == 1)
		{
			cout << "You gave a number lower than 0! The value is changed to 0. If you want to change it, go to edition options." << endl;
			input = 0;
		}
	}
}

void CheckInput::Check_Watched_Status(int &input)
{
	try {
		cin >> input;
		if (cin.fail())
			throw 0;
		else if (input < 0 || input > 2)
			throw 1;
	}
	catch (int check)
	{
		if (check == 0)
		{
			cout << "You gave an invalid character! The value is changed to 0 (NOT WATCHED)." << endl;
			cin.clear();
			cin.ignore();
			input = 0;
		}
		else if (check == 1)
		{
			cout << "You gave an invalid number! The value is changed to 0(NOT WATCHED)." << endl;
			input = 0;
		}
	}
}
void CheckInput::CheckIfTime(int &input, int unit, int month, int year)
{
	try {
		cin >> input;
		if (cin.fail())
			throw 0;
		switch (unit)
		{
		case 0:
			if (input < 0 || input>23) throw 1;
			break;
		case 1: 
			if (input < 0 || input>59) throw 1;
			break;
		case 2:
			if (input < 0) throw 3;
			break;
		case 3:
			if (input < 1 || input>12) throw 2;
			break;
		case 4:
			if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
			{
				if (input < 1 || input > 31) throw 2;
			}
			else if (month == 4 || month == 6 || month == 9 || month == 11)
			{
				if (input < 1 || input > 30) throw 2;
			}
			else if (month == 2)
			{
				if (year%4==0) if (input < 1 || input > 29) throw 2;
				else if (year % 4 != 0) if (input < 1 || input > 28) throw 2;
			}
			break;
		}
	}
	catch (int check)
	{
		if (check == 0)
		{
			cout << "You gave an invalid character! If you want to change it go to edition options." << endl;
			cin.clear();
			cin.ignore();
			input = 1;
		}
		else if (check == 1)
		{
			cout << "You gave an invalid number! The value is changed to 0. If you want to change it go to edition options." << endl;
			input = 0;
		}
		else if (check == 2)
		{
			cout << "You gave an invalid number! The value is changed to 1. If you want to change it go to edition options." << endl;
			input = 1;
		}
		else if (check == 3)
		{
			cout << "You gave an invalid number! The value is changed to 2018. If you want to change it go to edition options." << endl;
			input = 2018;
		}
	}
}