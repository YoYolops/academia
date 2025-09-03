def is_sorted(arr):
    for i in range(1, len(arr)):
        if arr[i] < arr[i-1]:
            return False
    return True


def explore_tree(tree_arr):
    half = len(tree_arr) // 2
    left = tree_arr[:half]
    right = tree_arr[half:]

    if len(tree_arr) <= 1:
        return {
            "swap_counter": 0,
            "processed_tree": tree_arr
        }
    
    left_exploration = explore_tree(left)
    right_exploration = explore_tree(right)

    if is_sorted(left_exploration["processed_tree"]+right_exploration["processed_tree"]):
        return {
            "swap_counter": left_exploration["swap_counter"]+right_exploration["swap_counter"],
            "processed_tree": left_exploration["processed_tree"]+right_exploration["processed_tree"],
        }
    if is_sorted(right_exploration["processed_tree"]+left_exploration["processed_tree"]):
        return {
            "swap_counter": left_exploration["swap_counter"]+right_exploration["swap_counter"]+1,
            "processed_tree": right_exploration["processed_tree"]+left_exploration["processed_tree"],
        }
    return {
        "swap_counter": -1,
        "processed_tree": tree_arr
    }

rounds = int(input().strip())
for i in range(rounds):
    input()
    test_arr = [int(val) for val in input().split(" ")]
    print(explore_tree(test_arr)["swap_counter"])