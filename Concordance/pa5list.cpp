// Name: Dennis Xiang
// loginid: dxiang
// CS 455 Lab 13
// a program to test the linked list code necessary for a hash table chain

#include <iostream>
#include <string>
#include <cassert>

using namespace std;


// this is the same Node type and ListType from PA5 Table.cpp
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




//LINKEDLIST FUNCTIONS

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
  cout << endl;

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




//TEST HELPER FUNCTIONS - FWD DECLARATIONS

void testInsertFront(ListType &list);

void testHasEntry(const ListType &list);

void testDeleteEntry(ListType &list);



//MAIN

int main() {

  Node * linkedList = NULL;

  testInsertFront(linkedList);
  testHasEntry(linkedList);
  cout << "Counting total number of Nodes: " << countNodes(linkedList) << endl << endl;
  testDeleteEntry(linkedList);
  cout << "Counting total number of Nodes: " << countNodes(linkedList) << endl << endl;
  testInsertFront(linkedList);

  cout << "Address of value for Steve: " << addressOf(linkedList,"Steve") << endl;

  return 0;
}


//function for testing insertFront method
void testInsertFront(ListType &list) {

  cout << "Added the following entries to the list: Dennis(100), Dennis(90), Steve(99), Jenny(83), Eric(34)" << endl;
  insertFront(list,"Dennis",100);
  insertFront(list,"David",90);
  insertFront(list,"Steve",99);
  insertFront(list,"Jenny", 83);
  insertFront(list,"Eric", 34);
  cout << "Printing the list: " << endl;
  printList(list);
 
}

//function for testing containsEntry method
void testHasEntry(const ListType &list) {

  cout << "Searching for Dennis in the linkedlist: " << hasEntry(list,"Dennis") << endl;
  cout << "Searching for David in the linkedlist: " << hasEntry(list,"David") << endl;
  cout << "Searching for Steve in the linkedlist: " << hasEntry(list,"Steve") << endl;
  cout << "Searching for Jenny in the linkedlist: " << hasEntry(list,"Jenny") << endl;
  cout << "Searching for Jacob in the linkedlist: " << hasEntry(list,"Jacob") << endl;
  cout << endl;

}

//function for testing deleteEntry method
void testDeleteEntry(ListType &list) {

  if(deleteEntry(list,"Dennis") == true) {
    cout << "Deleted Dennis from the list. Reprinting list:" << endl;
    printList(list);
  }
  else {
    cout << "Failed. Dennis not found in list." << endl;
  }

  if(deleteEntry(list,"Steve") == true) {
    cout << "Deleted Steve from the list. Reprinting list:" << endl;
    printList(list);
  }
  else {
    cout << "Failed. Steve not found in list." << endl;
  }

  if(deleteEntry(list,"Eric") == true) {
    cout << "Deleted Eric from the list. Reprinting list:" << endl;
    printList(list);
  }
  else {
    cout << "Failed.Eric not found in vlist." << endl;
  }

  if(deleteEntry(list,"John") == true) {
    cout << "Deleted John from the list. Reprinting list:" << endl;
    printList(list);
  }
  else {
    cout << "Failed.John not found in list." << endl;
  }

  if(deleteEntry(list,"Jenny") == true) {
    cout << "Deleted Jenny from the list. Reprinting list:" << endl;
    printList(list);
  }
  else {
    cout << "Failed.Jenny not found in list." << endl;
  }

  if(deleteEntry(list,"David") == true) {
    cout << "Deleted David from the list. Reprinting list:" << endl;
    printList(list);
  }
  else {
    cout << "Failed.David not found in list." << endl;
  }


}
