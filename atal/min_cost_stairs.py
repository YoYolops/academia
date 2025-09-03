import math

def min_cost_stairs(cost, moves):
    n = len(cost) - 1  # last step index
    dp = [math.inf] * (n + 1)
    dp[0] = cost[0]  # or 0, depending on problem statement

    for i in range(1, n + 1):
        for jump in moves:
            if i - jump >= 0:
                dp[i] = min(dp[i], cost[i] + dp[i - jump])

    return dp[n]

# Example
costs = [1, 5, 2, 3, 20]   # cost[i] = cost of step i
moves = [1, 2]             # can climb 1 or 2 steps at a time
print(min_cost_stairs(costs, moves))  # min cost to reach last step