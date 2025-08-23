def f(n):
    output = 0
    for i in range(1, 7):
        output += sum(f(n-i), i)
        

while True:
    inpt = int(input().strip())
    print(f(inpt))