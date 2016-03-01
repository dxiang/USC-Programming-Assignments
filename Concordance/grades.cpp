// Name: Dennis Xiang
// Loginid: dxiang
// CSCI 455 PA5
// Spring 2015

/*
 * grades.cpp
 * A program to test the Table class.
 * How to run it:
 *      grades [hashSize]
 * 
 * the optional argument hashSize is the size of hash table to use.
 * if it's not given, the program uses default size (Table::HASH_SIZE)
 *
 */

#include "Table.h"

// cstdlib needed for call to atoi
#include <cstdlib>

using namespace std;

//FORWARD DECLARATIONS**************
void printCmds();
bool processCmds(Table * &grades, const string &cmd, string &name, int &val);
//**********************************



int main(int argc, char * argv[]) {

  // gets the hash table size from the command line

  int hashSize = Table::HASH_SIZE;

  Table * grades;  // Table is dynamically allocated below, so we can call
                   // different constructors depending on input from the user.

  if (argc > 1) {
    hashSize = atoi(argv[1]);  // atoi converts c-string to int

    if (hashSize < 1) {
      cout << "Command line argument (hashSize) must be a positive number" 
	   << endl;
      return 1;
    }

    grades = new Table(hashSize);

  }
  else {   // no command line args given -- use default table size
    grades = new Table();
  }


  grades->hashStats(cout);

  // add more code here
  // Reminder: use -> when calling Table methods, since grades is type Table*

  string cmd;
  bool keepGoing = true;
  string name;
  int val;

  do {

    cout << "cmd> ";
    cin >> cmd;
    keepGoing = processCmds(grades,cmd,name,val);

  }while(keepGoing);

  delete grades;

  return 0;
}


//Prints out all valid commands 
void printCmds() {

  cout << "List of acceptable commands and corresponding [arguments]:" << endl;
  cout << "- insert [name] [score]" << endl;
  cout << "- change [name] [newscore]" << endl;
  cout << "- lookup [name]" << endl;
  cout << "- remove [name]" << endl;
  cout << "- print" << endl;
  cout << "- size" << endl;
  cout << "- stats" << endl;
  cout << "- help" << endl;
  cout << "- quit" << endl;

}

//process user input commands
bool processCmds(Table * &grades, const string &cmd, string &name, int &val) {

  bool keepGoing = true;

    if(cmd == "insert") {
      
      cin >> name >> val;
      if(grades->insert(name,val) == false) {
	cout << "Student already exists in database, insert failed." << endl;
      }
      else {
	cout << name << " with score of " << val << " added to database." << endl;
      }

    }
    else if(cmd == "change") {

      cin >> name >> val;
      if(grades->lookup(name) == NULL) {
	cout << "Student not found in the database." << endl;
      }
      else {
	cout << name << "'s score changed to " << val << endl;
	*grades->lookup(name) = val;
      }

    }
    else if(cmd == "lookup") {

      cin >> name;

      if(grades->lookup(name) == NULL) {
	cout << "Student not found in the database." << endl;
      }
      else {
	cout << name << " has a score  of: " << *grades->lookup(name) << endl;
      }

    }
    else if(cmd == "remove") {

      cin >> name;
      if(grades->remove(name)) {
	cout << name << " removed from the database." << endl;
      }
      else {
	cout << "Student not found in the database." << endl;
      }

    }
    else if(cmd == "print") {
      
      grades->printAll();

    }
    else if(cmd == "size") {

      cout << "Number of entries in the table: " << grades->numEntries() << endl;

    }
    else if(cmd == "stats") {

      grades->hashStats(cout);

    }
    else if(cmd == "help") {
      printCmds();
    }
    else if(cmd == "quit") {
      keepGoing = false;
    }
    else {
      cout << "ERROR: Invalid Command" << endl;
      printCmds();
    }

  return keepGoing;
}
