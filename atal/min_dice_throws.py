import math

def min_dice_throws(n, faces):
    dp = [math.inf] * (n + 1)
    dp[0] = 0  # 0 moves to reach sum 0

    for i in range(1, n + 1):
        for face in faces:
            if i - face >= 0:
                dp[i] = min(dp[i], 1 + dp[i - face])

    return dp[n] if dp[n] != math.inf else -1  # -1 means impossible

# Example
faces = [10, 17]
print(min_dice_throws(1, faces))  # → 1 (just 20)