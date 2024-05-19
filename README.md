# Puzzles-MaxDoubleSliceSum
Find the maximal sum of any double slice.

## Background

The lesson task hints that the maximum double slice sum is another implementation
of the maximum slice problem.

Instead of finding on max slice value, rather it finds all max sums ending at i,
in both directions of the input array (ascending, descending), and searches for
the pair of adjacent sums that, when themselves are summed, are the maximum sum.

## Description

A non-empty array A consisting of N integers is given.

A triplet (X, Y, Z), such that 0 <= X < Y < Z < N, is called a **double** **slice**.

The **sum** of double slice (X, Y, Z) is the total of

A[x + 1] + A[x + 2] + ... + A[Y - 2] + A[Y - 1] + A[Y + 1] + A[Y + 2] + ... + A[Z - 2] + A[Z - 1]

Note: the double slice sums exclude the values at A[X], A[Y], and A[Z].

For example, the array A such that:

```
A[0] = 3
A[1] = 2
A[2] = 6
A[3] = -1
A[4] = 4
A[5] = 5
A[6] = -1
A[7] = 2
```

contains the following example double slices:

- double slice (0, 3, 6)
  - sum is A[1] + A[2] + A[4] + A[5]
  - sum is 2 + 6 + 4 + 5 = 17
  - A[0] = 3, A[3] = -1, A[6] = -1 are excluded
  - A[7] = 2 is outside the double slice

- double slice (0, 3, 7)
  - sum is A[1] + A[2] + A[4] + A[5] + A[6]
  - sum is 2 + 6 + 4 + 5 + -1 = 16
  - A[0] = 3, A[3] = -1, A[7] = 2 are excluded

- double slice (3, 4, 5)
  - sum is 0
  - When X = 3, then X + 1 is 4. However Y is 4 and not summed
  - When Y = 4, then Y + 1 is 5. However Z is 5 and not summed
 
The goal is to find the maximal slice of any double slice.

Write a function:

```
    class Solution { public int solution(int[] A); }
```

that, given a non-empty array A consisting of N integers, returns the maximal sum of any double slice.

For example, given:

```
A[0] = 3
A[1] = 2
A[2] = 6
A[3] = -1
A[4] = 4
A[5] = 5
A[6] = -1
A[7] = 2
```

the function should return 17, because no double slice of array A has a sum of greater than 17.

Write an efficient algorithm for the following assumptions:
- N is an integer within the range [3..100,000];
- each element of array A is an integer within the range [-10,000..10,000].

In the MaxSlice study guide it walked us through the progression to the Kadane algorithm.

The small abstract thought leap required is that, instead of tracking one max slice sum,
the maximum double slice sum must track all sums of the two slices.

Since the sum must be maximum, then the slices will always be adjacent.
All values will be in at least one sum. Utilize the max sum logic of the Kadane algorithm.

Logic on paper is as such.

For the sample array:

Note the length of array A as N = 8.

Define two Kadane sum arrays.

One array sums values by traversing the array in ascending order.

The other array sums values by traversing the array in descending order.

The final step is to traverse the Kadane sum arrays together.

The result is the maximum sum of one pair of slice sums.

K1 = {0, 0, 0, 0, 0, 0, 0, 0}

loop from 1 while **i** is less than N - 1 = 7

The loop is computing max(K1[i - 1] + A[i], 0) for every element.

When i = 1, then K[1] = max(K1[1 - 1] + A[1], 0) = max(K[0] + A[1], 0) = max(0 + 2, 0) = 2

K1 = {0, 2, 0, 0, 0, 0, 0, 0}

When i = 2, then K[2] = max(K1[2 - 1] + A[2], 0) = max(K[1] + A[2], 0) = max(2 + 6, 0) = 8

K1 = {0, 2, 8, 0, 0, 0, 0, 0}

When i = 3, then K[3] = max(K1[3 - 1] + A[3], 0) = max(K[2] + A[3], 0) = max(8 + -1, 0) = 7

K1 = {0, 2, 8, 7, 0, 0, 0, 0}

When i = 4, then K[4] = max(K1[4 - 1] + A[4], 0) = max(K[3] + A[4], 0) = max(7 + 4, 0) = 11

K1 = {0, 2, 8, 7, 11, 0, 0, 0}

When i = 5, then K[5] = max(K1[5 - 1] + A[5], 0) = max(K[4] + A[5], 0) = max(11 + 5, 0) = 16

K1 = {0, 2, 8, 7, 11, 16, 0, 0}

When i = 6, then K[6] = max(K1[6 - 1] + A[6], 0) = max(K[5] + A[6], 0) = max(16 + -1, 0) = 15

K1 = {0, 2, 8, 7, 11, 16, 15, 0}

When i = 7, stop

K2 = {0, 0, 0, 0, 0, 0, 0, 0}

loop from N - 2 = 6 while **i** is greater than 0

The loop is computing max(K2[i + 1] + A[i], 0) for every element.

When i = 6, then K[6] = max(K1[6 + 1] + A[6], 0) = max(K2[7] + A[6], 0) = max(0 + -1, 0) = 0

K2 = {0, 0, 0, 0, 0, 0, 0, 0}

When i = 5, then K[5] = max(K1[5 + 1] + A[5], 0) = max(K2[6] + A[5], 0) = max(0 + 5, 0) = 5

K2 = {0, 0, 0, 0, 0, 5, 0, 0}

When i = 4, then K[4] = max(K1[4 + 1] + A[4], 0) = max(K2[5] + A[4], 0) = max(5 + 4, 0) = 9

K2 = {0, 0, 0, 0, 9, 5, 0, 0}

When i = 3, then K[3] = max(K1[3 + 1] + A[3], 0) = max(K2[4] + A[3], 0) = max(9 + -1, 0) = 8

K2 = {0, 0, 0, 8, 9, 5, 0, 0}

When i = 2, then K[2] = max(K1[2 + 1] + A[2], 0) = max(K2[3] + A[2], 0) = max(8 + 6, 0) = 14

K2 = {0, 0, 14, 8, 9, 5, 0, 0}

When i = 1, then K[1] = max(K1[1 + 1] + A[1], 0) = max(K2[2] + A[1], 0) = max(14 + 2, 0) = 16

K2 = {0, 16, 14, 8, 9, 5, 0, 0}

When i = 0, stop

Now that the two kadane sum arrays are populated, pairs of values are summed to find the maximum double slice sum.

The pairs are chosen in a specific way.

Remember that the value at Y of the triplet is not summed.

The indices of the kadane sums are always 2 index positions apart to simulate skipping the value at Y.

max = 0

loop from 1 while **i** is less than N - 1 = 7

The loop is computing max(max, K1[i - 1] + K2[i + 1]) for every element.

When i = 1, then max = max(max, K1[1 - 1] + K2[1 + 1]) = max(0, K1[0] + K2[2]) = max(0, 0 + 14) = 14

max = 14

When i = 2, then max = max(max, K1[2 - 1] + K2[2 + 1]) = max(14, K1[1] + K2[3]) = max(14, 2 + 8) = 14

max = 14

When i = 3, then max = max(max, K1[3 - 1] + K2[3 + 1]) = max(14, K1[2] + K2[4]) = max(14, 8 + 9) = 17

max = 17

When i = 4, then max = max(max, K1[4 - 1] + K2[4 + 1]) = max(17, K1[3] + K2[5]) = max(17, 7 + 5) = 17

max = 17

When i = 5, then max = max(max, K1[5 - 1] + K2[5 + 1]) = max(17, K1[4] + K2[6]) = max(17, 11 + 0) = 17

max = 17

When i = 6, then max = max(max, K1[6 - 1] + K2[6 + 1]) = max(17, K1[5] + K2[7]) = max(17, 15 + 0) = 17

max = 17

When i = 7, stop

The result of the solution is 17.
