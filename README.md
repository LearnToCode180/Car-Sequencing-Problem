# Car-Sequencing-Problem
The car sequencing problem has been first described by Parello et al. in 1986 \[PKW86]. This
problem involves scheduling cars along an assembly line, in order to install options (e.g., sunroof, radio, or air-conditioning) on them. Each option is installed by a different station, designed
to handle at most a certain percentage of the cars passing along the assembly line, and the cars
requiring this option must be spaced such that the capacity of every station is never exceeded.
This requirement may be formalized by p/q ratio constraints: each option is associated with
a p/q ratio constraint that states that any subsequence of q vehicles may comprise at most p
vehicles requiring this option.

# Solution:
### Variables:
We have two different variables, 
1. One dimensional array: **\[NumberOfCar]** which contains the category type of each car (in our example, we have 12 category).
2. Two demensional arrray: **\[Options\*NumberOfCar]** which tell us if an option exist on a car or not.
### Domains:
1. Each cell will contains a value between **1** and **12**.
2. Each cell will take **0** or **1** as value.
### Constraints:
1. The number of car of each category should be the same of the number of car we have with this category in our array.
2. The number of options of each car should be the same of the number of options of its category (We will take the values from the array which we had initialised).
3. This constraint is the very important one, it ensure that in each sequence of q\[i] car which have the option i, we should have a maximum of 
p\[i] car.
