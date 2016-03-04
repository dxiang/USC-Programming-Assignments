# CSCI561 Homework #2
# Author: Dennis Xiang
# First order logic and backward chaining


import sys
import re
import getopt

#global vars
KB = {}

class AtomicSent:
    'Class for holding an atomic sentence. Contains the predicate and its list of arguments'
    def __init__(self, obj_pred=None, obj_args=None):
        self.pred = obj_pred
        self.args = obj_args

    def set_data(self, obj_pred, obj_args):
        self.pred = obj_pred
        self.args = obj_args

class Rule:
    '''Class for representing a rule in the KB. Has a LHS and a RHS for implications. Otherwise LHS is empty.
    Both LHS and RHS are lists of Atomic Sentences'''
    def __init__(self, obj_lhs, obj_rhs):
        self.lhs = obj_lhs
        self.rhs = obj_rhs


def is_var(symbol):
    return len(symbol) == 1 and symbol[0].islower()

#def is_comp(expr):


def unify(a, b, theta):
    'Arguments a and b can be either variables, constants, '
    if theta == None:
        return None
    elif a == b:
        return theta
    elif is_var(a):
        return unify_var(a, b, theta)
    elif is_var(b):
        return unify_var(b, a, theta)

def unify_var(a, b, theta):
    return

def parse_atm_sent(string):
    'This function takes a string of input and returns an array of atomic sentences'
    temp_list = re.findall('([A-Z]\w*)\(([A-Za-z, ]+)\)', string)

    atomic_list = []
    arg_list = []

    for tup in temp_list:
        match_obj = re.search(',', tup[1])

        if match_obj:
            arg_list = tup[1].split(', ')
        else:
            arg_list.append(tup[1])

        atomic_list.append(AtomicSent(tup[0], arg_list))
        arg_list = []

    return atomic_list


################################## Main starts here #####################################

# parse command line input
try:
    optlist, args = getopt.getopt(sys.argv[1:], 'i:')
except getopt.GetoptError as err:
    print str(err)
    sys.exit(2)

filename = ''

for opt, arg in optlist:
    if opt == '-i':
        filename = arg

print '\n' + 'Reading input from file: ' + filename + '\n'
infile = open(filename)

goals = parse_atm_sent(infile.readline())
print 'Goal(s):'
for i in range(len(goals)):
    print goals[i].pred, goals[i].args

print '\nKB:'
num_sentences = int(infile.readline())
for i in range(num_sentences):
    rule_sent = infile.readline()
    match_obj = re.search('=>', rule_sent)
    if match_obj:
        divided_rule = rule_sent.split('=')
        lhs = parse_atm_sent(divided_rule[0])
        rhs = parse_atm_sent(divided_rule[1])
        for i in range(len(lhs)):
            print lhs[i].pred, lhs[i].args,
        print ' => ', rhs[0].pred, rhs[0].args

        KB[rhs[0].pred] = Rule(lhs, rhs)
    else:
        atm_rule = parse_atm_sent(rule_sent)
        print atm_rule[0].pred, atm_rule[0].args

        KB[atm_rule[0].pred] = Rule([],atm_rule)
