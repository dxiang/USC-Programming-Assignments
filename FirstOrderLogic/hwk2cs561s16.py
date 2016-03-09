# CSCI561 Homework #2
# Author: Dennis Xiang
# First order logic and backward chaining


import sys
import re
import getopt

#global vars
KB = {}
stand_var = 0

def is_var(symbol):
    '''Checks if the given symbol is a variable by first checking if its an atomic sentence, then if its a list, then
    if it is lowercase'''
    #print 'Checking if', symbol, 'is an atomic sentence: ', isinstance(symbol, AtomicSent)
    return (not isinstance(symbol, AtomicSent)) and (not isinstance(symbol, list)) and symbol[0].islower()


class AtomicSent:
    'Class for holding an atomic sentence. Contains the predicate string and its list of arguments (strings)'
    def __init__(self, obj_pred=None, obj_args=None):
        if isinstance(obj_pred, str):
            self.pred = obj_pred
        else:
            print 'ERROR: Predicate should be a string'
        if isinstance(obj_args, list):
            self.args = obj_args
        else:
            print 'ERROR: Arguments should be a list'

    def replace_var(self, old_var, new_var):
        for i in range(len(self.args)):
            if self.args[i] == old_var:
                self.args[i] = new_var
            else:
                print "The variable to replace doesn't exist!"

    def print_sentence(self, theta=None):
        'Prints the atomic sentence in standard form. If argument is variable, print an underscore'
        temp_sentence = self.pred + '('
        for arg in self.args:
            if is_var(arg):
                if not (theta == None) and theta.has_key(arg):
                    temp_sentence += (theta[arg] + ', ')
                else:
                    temp_sentence += ('_, ')
            else:
                temp_sentence += (arg + ', ')
        temp_sentence = list(temp_sentence)
        temp_sentence[-2:] = ')'
        return "".join(temp_sentence)

    def islower(self):
        return False

class Rule:
    '''Class for representing a rule in the KB. Has a LHS and a RHS for implications. Otherwise LHS is empty.
    Both LHS and RHS are lists of Atomic Sentences'''
    def __init__(self, obj_lhs, obj_rhs):
        self.lhs = obj_lhs
        self.rhs = obj_rhs


def subst(theta, atomic_sentence):
    'Substitutes all entries in theta into the given atomic sentence, return the atomic sentence'
    for i in range(len(atomic_sentence.args)):
        if theta.has_key(atomic_sentence.args[i]):
            atomic_sentence.args[i] = theta[atomic_sentence.args[i]]
    return atomic_sentence


def fol_bc_or(KB, goal, theta):
    print 'Ask:', goal.print_sentence(theta)
    for i in range(len(KB[goal.pred])):
        standardize_vars(goal, KB[goal.pred][i], theta)
        for theta_prime in fol_bc_and(KB, KB[goal.pred][i].lhs, unify(KB[goal.pred][i].rhs[0], goal, theta)):
            if not (theta_prime == None):
                print 'True:', goal.print_sentence(theta_prime)
            else:
                print 'False:', goal.print_sentence(theta_prime)
            yield theta_prime


def fol_bc_and(KB, goals, theta):
    #print 'Called fol_bc_AND'
    #print theta
    if theta == None:
        #print 'theta is null'
        yield theta
    elif len(goals) == 0:
        yield theta
    else:
        for theta_prime in fol_bc_or(KB, subst(theta, goals[0]), theta):
            for theta_dprime in fol_bc_and(KB, goals[1:], theta_prime):
                yield theta_dprime


def fol_bc_ask(KB, query):
    '''Backward Chaining algorithm
    KB (Knowledge Base): a dictionary of rules with the rhs predicate as the key and the list of rules associated
                        with that predicate as the value
    query: the atomic sentence goal question

    returns: a valid set of substitutions, otherwise, None
    '''

    for theta in fol_bc_or(KB, query, {}):
        if theta == None:
            print 'False'
            return False
        else:
            print 'True'
            return True

def standardize_vars(goal, rule, theta):
    global stand_var
    stand_var += 1
    for i in range(len(rule.rhs)):
        for j in range(len(rule.rhs[i].args)):
            for goal_arg in goal.args:
                if is_var(rule.rhs[i].args[j]) and (rule.rhs[i].args[j] == goal_arg or theta.has_key(rule.rhs[i].args[j])):
                    rule.rhs[i].args[j] += str(stand_var)

    for i in range(len(rule.lhs)):
        for j in range(len(rule.lhs[i].args)):
            for goal_arg in goal.args:
                if is_var(rule.lhs[i].args[j]) and (rule.lhs[i].args[j] == goal_arg or theta.has_key(rule.lhs[i].args[j])):
                    rule.lhs[i].args[j] += str(stand_var)


def unify(a, b, theta):
    'Arguments a and b can be either variables, constants, a list, or an atomic sentence'
    if isinstance(a, AtomicSent) and isinstance(b, AtomicSent):
        #print 'Unifying', a.print_sentence(), 'with', b.print_sentence()
        pass
    else:
        #print 'Unifying', a, 'with', b
        pass
    #print theta

    if theta == None:
        return None
    elif a == b:
        return theta
    elif is_var(a):
        return unify_var(a, b, theta)
    elif is_var(b):
        return unify_var(b, a, theta)
    elif isinstance(a, AtomicSent) and isinstance(b, AtomicSent):
        return unify(a.args, b.args, unify(a.pred, b.pred, theta))
    elif isinstance(a, list) and isinstance(b, list) and len(a) == len(b):
        return unify(a[1:], b[1:], unify(a[0], b[0], theta))
    else:
        return None


def unify_var(var, x, theta):
    if theta.has_key(var):
        return unify(theta[var], x, theta)
    else:
        theta[var] = x
        return theta


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
print 'Stored following goal(s):'
for i in range(len(goals)):
    print goals[i].pred, goals[i].args

print '\nStored following KB:'
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

        if KB.has_key(rhs[0].pred):
            KB[rhs[0].pred].append(Rule(lhs, rhs))
        else:
            KB[rhs[0].pred] = [Rule(lhs, rhs)]
    else:
        atm_rule = parse_atm_sent(rule_sent)
        print atm_rule[0].pred, atm_rule[0].args

        if KB.has_key(atm_rule[0].pred):
            KB[atm_rule[0].pred].append(Rule([],atm_rule))
        else:
            KB[atm_rule[0].pred] = [Rule([],atm_rule)]


print '\nOutput text:'

for goal in goals:
    fol_bc_ask(KB, goal)

#unify(goals[0], KB['Secret'][0].rhs[0], theta)

#standardize_vars(goals[0], KB['Traitor'][0], theta)

#print KB['Traitor'][0].rhs[0].args
#print KB['Traitor'][0].lhs[0].args