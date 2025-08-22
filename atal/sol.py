#guloso

global_counter = 0
def dice_combinations(target_sum, current_sum=0, options=[1,2,3,4,5,6]):
    global global_counter
    if len(options) == 0: return False
    if current_sum == target_sum:
        global_counter += 1
        return True
    if current_sum > target_sum:
        return False
    
    for option in options:
        result = dice_combinations(target_sum, current_sum+option, options)
        if not result:
            return False
        
    return True    

input_target_sum = int(input().strip())
dice_combinations(input_target_sum)
print(global_counter)