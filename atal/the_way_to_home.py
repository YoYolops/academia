[n, d] = [int(i) for i in input().strip().split(" ")]
path = input().strip()
step_count = 0
current_position = 0
best_candidate = 0
i=0
while i < len(path):
    if (i-current_position) > d:
        if best_candidate == current_position:
            print("-1")
            exit(0)
        else:
            current_position = best_candidate
            i = current_position+1
            step_count += 1
            continue
    if path[i] == "1" and i > 0:
        best_candidate = i
    i+=1
print(step_count+1)