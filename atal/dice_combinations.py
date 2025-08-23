#guloso lento time limit exceeded

call_cache = {}
global_counter = 0
def dice_combinations(target_sum, level=0, current_sum=0, options=[1,2,3,4,5,6]):
    global global_counter
    if len(options) == 0: return False
    if current_sum == target_sum:
        global_counter += 1
        return
    if current_sum > target_sum:
        return
    
    for option in options:
        dice_combinations(target_sum, level+1,current_sum+option, options)

input_target_sum = int(input().strip())
dice_combinations(input_target_sum)
print(global_counter)