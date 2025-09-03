
def dice_combinations(n, memoization={}):
    if n == 0:
        return 1
    soma = 0
    for i in range(1, 7):
        if (n - i) >= 0:
            if n-i in memoization:
                soma += memoization[n-i]
            else:
                memoization[n-i] = dice_combinations(n-i, memoization)
                soma += memoization[n-i]
        else:
            break
    return soma

input_val = int(input().strip())
print(dice_combinations(input_val))