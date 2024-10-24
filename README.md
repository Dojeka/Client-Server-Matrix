### Part 2: Matrix Multiplication Using Strassen's Algorithm with Multithreading
In Part 2, the project shifts from simple text communication to a more complex computational task—matrix multiplication using Strassen’s algorithm, where clients send matrices to the server for parallel computation.

Here's a breakdown of Part 2:

1. **Client-Side (Matrix Generation & Transmission)**:
   - Clients will generate matrices of integers with dimensions that are powers of 2 (e.g., 2x2, 4x4, 8x8, etc.).
   - Each client sends these matrices to the server. The number of matrices to multiply in each request is also a power of 2 (2 matrices, 4 matrices, etc.).

2. **Server-Side (Multithreaded Matrix Multiplication)**:
   - **Thread Management**: The server will spawn threads (or use cores) to multiply these matrices using Strassen's algorithm, which reduces the complexity of matrix multiplication compared to the standard method.
   - **Binary Tree Model**: The multiplication will follow a binary tree model, where each pair of matrices is assigned to a thread/core. The results are propagated up the tree until a final product is computed at the root node.
   - **Thread/Core Configuration**: You will run tests using varying numbers of threads/cores (1, 3, 7, 15, and 31) and varying numbers of matrices (2, 4, 8, 16, 32) to see how performance scales with concurrency.

3. **Matrix Multiplication Algorithm (Strassen's Algorithm)**:
   - Strassen's algorithm is more efficient than the standard matrix multiplication algorithm, with a time complexity of \( O(n^{\log_2 7}) \) or roughly \( O(n^{2.81}) \).
   - You will decompose each matrix into smaller \( N/2 \times N/2 \) submatrices, perform multiplications in parallel, and combine the sub-results to obtain the final matrix.

4. **Performance Metrics**:
   - You need to collect timing data from the server for each test case (matrix size and thread/core count). 
   - Using the timing data, compute and tabulate the following performance metrics:
     - **Parallel Execution Time**: Time taken to complete the multiplication with multiple threads.
     - **Speedup**: How much faster the parallel execution is compared to a single-threaded (serial) execution. Formula: \( \text{Speedup} = \frac{\text{Time for 1 core}}{\text{Time for } p \text{ cores}} \).
     - **Efficiency**: How effectively the cores are utilized. Formula: \( \text{Efficiency} = \frac{\text{Speedup}}{p} \).

5. **Running the Simulation**:
   - Run the simulation for 25 scenarios based on varying matrix sizes (1k x 1k to 16k x 16k) and thread/core counts.
   - Collect data for each scenario and compute the metrics for each test run.

6. **Final Deliverables**:
   - **Report**: A detailed 10–15-page report in IEEE-style format, containing:
     - Tables of timing data for parallel execution time, speedup, and efficiency.
     - Graphs for performance comparisons across the different configurations.
     - Code snippets, screenshots of simulation runs, and discussions on the performance results.
   - **Demo Video**: A short (3–5-minute) screen capture showing your program in action—compiling, running, and producing the desired results.
   - **Source Code**: Submit the Java source code for the client-server and matrix multiplication system.

This part of the project is designed to showcase how parallel computing can accelerate matrix operations, and how efficiently your server can handle multiple concurrent clients with multithreading.
