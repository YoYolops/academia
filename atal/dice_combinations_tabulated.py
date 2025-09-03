MOD = 10**9 + 7

def dice_combinations(n):
    dp = [0] * 7
    dp[0] = 1  # dp[0] = 1

    for i in range(1, n + 1):
        idx = i % 7
        dp[idx] = 0  # recompute fresh
        for j in range(1, 7):
            if i - j >= 0:
                dp[idx] = (dp[idx] + dp[(i - j) % 7]) % MOD

    return dp[n % 7]

input_value = int(input().strip())
print(dice_combinations(input_value))