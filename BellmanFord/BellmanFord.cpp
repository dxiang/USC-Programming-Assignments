/*
Dennis Xiang
dxiang
EE450 Lab1

This assignment utilizes the Bellman Ford algorithm to determine the best path from a source to
a destination node given an adjacency matrix input file.
*/

#include <iostream>
#include <fstream>
#include <string>
#include <stdlib.h>
using namespace std;

const int INF = 1000; //for purposes of this lab, infinite is just a large number

//forward declarations
bool genSolution(int** nodeArr, int numNodes);
void findPathRec(int* pred, int index, int target, ofstream &outFile);

/*
Main function - provides error checking for the command arguments and attempts to read the input file into a 2D array
*/
int main(int argc, char* argv[]) {

	if (argc > 1) {
		cout << "Reading from file: " << argv[1] << endl;
	}
	else {
		cout << "Missing input file name, please run again with input file in command line." << endl;
		return 1;
	}

	string str;
	int numNodes;

	ifstream inFile(argv[1]);
	if (inFile.is_open())
	{
		getline(inFile, str); //get first line of text file
		numNodes = atoi(str.c_str()); //convert to integer
		cout << "Number of Nodes: " << numNodes << endl;

		int** arrNode = new int*[numNodes]; //initialize dynamic 2D array
		for (int i = 0; i < numNodes; i++) {
			arrNode[i] = new int[numNodes];
		}

		for (int i = 0; i < numNodes; i++) { //stores values of textfile into array. if '*', replace with large number INF
			for (int j = 0; j < numNodes; j++) {
				if (j == numNodes - 1) {
					getline(inFile, str);
				}
				else {
					getline(inFile, str, ',');
				}
				if (str[0] == '*') {
					arrNode[i][j] = INF;
				}
				else {
					arrNode[i][j] = atoi(str.c_str());
				}
			}
		}

		inFile.close();

		if(genSolution(arrNode, numNodes)) {
		  cout << "Done!" << endl;
		}
		else {
		  cout << "Write to file failed..." << endl;
		}

		for (int i = 0; i < numNodes; i++) { //free memory from 2D array
			delete[] arrNode[i];
		}
		delete[] arrNode;

	}


	else {
		cout << "Unable to open file. Closing program.";
	}

	return 0;
}//end main function


/*
Generate Solution function - performs BellmanFord iterations on the provided dataset and generates an output file for the
solution.
*/
bool genSolution(int** nodeArr, int numNodes) {

	bool printed = true;

	int* dSource = new int[numNodes]; //initilialize array of distances from source
	for (int i = 1; i < numNodes; i++) {
		dSource[i] = INF;
	}
	dSource[0] = 0;

	int* pred = new int[numNodes]; //initilialize array of distances from source
	for (int i = 0; i < numNodes; i++) {
		pred[i] = NULL;
	}

	//perform iterations
	for (int iter = 1; iter < numNodes; iter++) {
		for (int node1 = 0; node1 < numNodes; node1++) {
			for (int node2 = 0; node2 < numNodes; node2++) {
				if (nodeArr[node1][node2] + dSource[node1] < dSource[node2]) {
					dSource[node2] = nodeArr[node1][node2] + dSource[node1];
					pred[node2] = node1;
				}
			}
		}
	}

	string outFileName;
	cout << "Please input a filename for the generated solution: ";
	cin >> outFileName;

	ofstream outFile(outFileName.c_str());

	if (outFile.is_open()) {

		for (int i = 0; i < numNodes-1; i++) { //print shortest distances from source to each node 
			outFile << dSource[i] << ",";
		}
		outFile << dSource[numNodes-1] << endl;
		
		for (int i = 0; i < numNodes; i++) {
			findPathRec(pred, i, i, outFile);
			outFile << endl;
		}

	}

	else {
		printed = false;
	}

	outFile.close();

	delete[] dSource;

	delete[] pred;

	return printed;
}//end genSolution function


/*
Recursive function for listing out the path of shortest cost
*/
void findPathRec(int* pred, int index, int target, ofstream &outFile) {
	if (index == 0) {
		outFile << 0;
	}
	else if (pred[index] == 0) {
		outFile << "0->" << index;
	}
	else {
		findPathRec(pred, pred[index], target, outFile);
		if (index != target) {
			outFile << "->" << index;
		}
		else {
			outFile << "->" << index;
		}
	}
}//end findPathRec
