from os import chdir
from os.path import expanduser
from subprocess import check_output
from pyevolve import G1DList
from pyevolve import GSimpleGA
from pyevolve import Selectors
from pyevolve import Statistics
from pyevolve import DBAdapters
import pyevolve

ROBOCODE_DIRECTORY = expanduser('~') + '/robocode'




def eval_func(chromosome):
	
   
	
	#print ['java', '-DNOSECURITY=true', '-classpath', 				'libs/robocode.jar:robots', 'evolve.Scorer'] + [repr(value) for value in chromosome]
   	chdir(ROBOCODE_DIRECTORY)
	output = check_output(['java', '-DNOSECURITY=true', '-classpath', 				'libs/robocode.jar:robots', 'evolve.Scorer'] + [repr(value) for value in chromosome])
	wins = int(output.split('\n')[-2])
	return wins
		
# Enable the pyevolve logging system
pyevolve.logEnable()

# Genome instance, 1D List of 50 elements
genome = G1DList.G1DList(6)

# Sets the range max and min of the 1D List
genome.setParams(rangemin=0, rangemax=100)


# The evaluator function (evaluation function)
genome.evaluator.set(eval_func)

# Genetic Algorithm Instance
ga = GSimpleGA.GSimpleGA(genome)

# Sets the population size
pop = ga.getPopulation()
pop.setPopulationSize(10)

# Set the Roulette Wheel selector method, the number of generations and
# the termination criteria
ga.selector.set(Selectors.GRouletteWheel)
ga.setGenerations(2)
ga.terminationCriteria.set(GSimpleGA.ConvergenceCriteria)

# Sets the DB Adapter, the resetDB flag will make the Adapter recreate
# the database and erase all data every run, you should use this flag
# just in the first time, after the pyevolve.db was created, you can
# omit it.
sqlite_adapter = DBAdapters.DBSQLite(identify="ex1", resetDB=True)
ga.setDBAdapter(sqlite_adapter)

# Do the evolution, with stats dump
# frequency of 20 generations
ga.evolve(freq_stats=20)


# Best individual
print ga.bestIndividual()

print wins
