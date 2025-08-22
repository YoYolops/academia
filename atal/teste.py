def normalize(s: str) -> str:
    n = len(s)
    if n % 2 == 1:  # ímpar → não pode dividir
        return s
    mid = n // 2
    left = normalize(s[:mid])
    right = normalize(s[mid:])
    # força ordem determinística
    if left < right:
        return left + right
    else:
        return right + left

s1 = input().strip()
s2 = input().strip()

print("YES" if normalize(s1) == normalize(s2) else "NO")

print(normalize(s1))