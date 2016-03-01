// Name: Dennis Xiang
// Loginid: dxiang
// CSCI 455 PA5
// Spring 2015

// Table.cpp  Table class implementation


/*
 * Modified 11/22/11 by CMB
 *   changed name of constructor formal parameter to match .h file
 */

#include "Table.h"

#include <iostream>
#include <string>
#include <cassert>

//*************************************************************************
// Node class definition and member functions
//     You don't need to add or change anything in this section

// Note: we define the Node in the implementation file, because it's only
// used by the Table class; not by any Table client code.

struct Node {
  string key;
  int value;

  Node *next;

  Node(const string &theKey, int theValue);

  Node(const string &theKey, int theValue, Node *n);
};

Node::Node(const string &theKey, int theValue) {
  key = theKey;
  value = theValue;
  next = NULL;
}

Node::Node(const string &theKey, int theValue, Node *n) {
  key = theKey;
  value = theValue;
  next = n;
}

typedef Node * ListType;

//*************************************************************************
//LINKEDLIST HELPER FUNCTIONS - FWD DECLARATIONS

  //Inserts a new Node in the front of the list
  void insertFront(ListType &list, const string &theKey, int val);

  //Returns whether or not an entry exists given a key
  bool hasEntry(ListType list, const string &theKey);

  //Deletes a Node from the list
  bool deleteEntry(ListType &list, string theKey);

  //Traverses a list and prints out each entry (key and value)
  void printList(ListType list);

  //Counts number of nodes in the list
  int countNodes(ListType list);

  //Returns the address of the value of the given key, NULL if key not present
  int *addressOf(ListType list, const string &theKey);

//*************************************************************************



Table::Table() {
  hashSize = HASH_SIZE;
  hashTable = new ListType[hashSize];
  for(int i = 0; i < hashSize; i++) {
    hashTable[i] = NULL;
  }
}


Table::Table(unsigned int hSize) {
  hashSize = hSize;
  hashTable = new ListType[hashSize];
  for(int i = 0; i < hashSize; i++) {
    hashTable[i] = NULL;
  }
}


int * Table::lookup(const string &key) {

  return addressOf(hashTable[hashCode(key)],key);

}


bool Table::remove(const string &key) {

  return deleteEntry(hashTable[hashCode(key)],key);

}


bool Table::insert(const string &key, int value) {

  if(hasEntry(hashTable[hashCode(key)],key)) { 
    return false;
  }

  insertFront(hashTable[hashCode(key)],key,value);

  return true;
}

int Table::numEntries() const {

  int count = 0;
  for(int i = 0; i < hashSize; i++) {
    count = count + countNodes(hashTable[i]);
  }
  return count;

}


void Table::printAll() const {

  for(int i = 0; i < hashSize; i++) {
    printList(hashTable[i]);
  }

}

void Table::hashStats(ostream &out) const {
  
  out << "Number of hash buckets: " << hashSize << endl;
  out << "Number of entries: " << numEntries() << endl;

  int numBuckets = 0;
  int temp;
  int longestChain = 0;
  for(int i = 0; i < hashSize; i++){
    if(hashTable[i] != NULL) {
      numBuckets++;
      temp = countNodes(hashTable[i]);
      if(temp > longestChain) {
	longestChain = temp;
      }
    }
  }

  out << "Number of non-empty buckets: " << numBuckets << endl;
  out << "Longest chain: " << longestChain << endl;
  
}





//******************************************************
//LINKEDLIST HELPER FUNCTION DEFINITIONS


//Inserts a new Node in the front of the list
void insertFront(ListType &list, const string &theKey, int val) {

  list = new Node(theKey, val, list);
  
}

//Returns whether or not an entry exists given a key
bool hasEntry(ListType list, const string &theKey) {

  if(list == NULL) {
    return false;
  }

  while(list != NULL) {
    
    if(list->key == theKey) {
      return true;
    }
    list = list->next;
  }
  return false;
  
}


//Deletes a Node from the list
bool deleteEntry(ListType &list, string theKey) {

  if(hasEntry(list,theKey) != true) { 
    return false;
  }

  ListType p = list;
  
  //found entry is first node in list
  if(list->key == theKey) {

    list = list->next;
    delete p;

  }
  
  else {
    p = p->next;
    ListType q = list;

    while(p->key != theKey) {
      p = p->next;    
      q = q->next;
    }

    q->next = p->next;
    delete p;
  }

  return true;

}


//Traverses a list and prints out each entry (key and value)
void printList(ListType list) {

  while(list != NULL) {
    cout << list->key << " " << list->value << endl;
    list = list->next;
  }

}

//Counts number of nodes in the list
int countNodes(ListType list) {

  int count = 0;
  while(list != NULL) {
    count++;
    list = list->next;
  }
  return count;

}

//Returns the address of the value of the given key, NULL if key not present
int *addressOf(ListType list, const string &theKey) {

  while(list != NULL) {
    
    if(list->key == theKey) {
      return &(list->value);
    }
    list = list->next;
  }
  return NULL;
  
}
