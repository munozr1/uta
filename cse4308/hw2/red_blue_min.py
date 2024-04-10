from collections import namedtuple
import sys

players = ["human", "computer"]
colors = ["red", "blue"]
versions = ["standard", "misere"]

State = namedtuple("State", ["num_red", "num_blue", "current_player", "version"])

def custom_max(iterable, key_function):
    return max(iterable, key=key_function)

def validate_command_line_arguments():
    if len(sys.argv) < 5:
        return -1
    if sys.argv[4] not in players:
        return -1
    if sys.argv[3] not in colors or sys.argv[2] not in colors:
        return -1
    if sys.argv[3] not in versions:
        return -1

def calculate_score(game_state):
    score = game_state.num_red + game_state.num_blue
    if game_state.num_red == 0 or game_state.num_blue == 0:
        score = 0
    if game_state.version == "misere":
        score *= -1  # Invert for misère
    return score

def is_terminal_state(game_state):
    return game_state.num_red == 0 or game_state.num_blue == 0

def generate_moves(game_state):
    if game_state.num_red >= 2:
        yield "red", 2
    if game_state.num_red >= 1:
        yield "red", 1
    if game_state.num_blue >= 2:
        yield "blue", 2
    if game_state.num_blue >= 1:
        yield "blue", 1

def move(game_state, m):
  current_red = game_state.num_red
  current_blue = game_state.num_blue

  if m[0] == "red":
      current_red -= m[1]
  else:
      current_blue -= m[1]

  new_state = game_state._replace(num_red=current_red, num_blue=current_blue)
  return new_state._replace(current_player=players[(players.index(game_state.current_player) + 1) % 2])



def minmax_alpha_beta_pruning(state): #TODO: add depth for extra credit
    actions = generate_moves(state)

    def max_value(state, alpha, beta):
        if is_terminal_state(state):
            return calculate_score(state)
        value = -float("inf")
        for a in actions:
            value = max(value, min_value(move(state, a), alpha, beta))
            if value >= beta:
                return value
            alpha = max(alpha, value)
        return value

    def min_value(state, alpha, beta):
        if is_terminal_state(state):
            return calculate_score(state)
        value = float("inf")
        for a in actions:
            value = min(value, max_value(move(state, a), alpha, beta))
            if value <= alpha:
                return value
            beta = min(beta, value)
        return value

    return custom_max(generate_moves(state),lambda a: min_value(move(state, a), -float("inf"), float("inf")))



def generate_computer_move(game_state, heuristic):
    best_move = None
    best_value = -float("inf")

    for color, amount in heuristic:
        if color == "red":
            if game_state.num_red < amount:
                print("invalid move")
                continue
        else:
            if game_state.num_blue < amount:
                print("invalid move")
                continue

        new_state = move(game_state, (color, amount))
        value = minmax_alpha_beta_pruning(new_state)
        if best_move is None or best_move[1] < value[1]:
            best_value = value[1]
            best_move = (color, amount)
        print("Best Move: ", best_move, "Best Value: ", best_value)
    if best_move is None:
            best_move = ("red", 1)

    return best_move




def main():

    move_heuristic = [("blue", 2), ("red", 2), ("red", 1), ("blue", 1)]
    valid = validate_command_line_arguments()
    if not valid:
        print("red_blue_nim.py <num-red> <num-blue> <version> <first-player> <depth>")
        sys.exit(1)

    num_red = int(sys.argv[1])
    num_blue = int(sys.argv[2])

    version = sys.argv[3] if len(sys.argv) >= 4 else "standard"
    if version == "misere":
        move_heuristic.reverse()

    first_player = sys.argv[4] if len(sys.argv) >= 5 else "computer"
    #TODO: add depth for extra credit

    game_state = State(num_red, num_blue, first_player, version)

    while game_state.num_blue > 0 and game_state.num_red > 0:
        print("Game state: ", game_state)

        if game_state.current_player == "human":
            # Get human move
            print("Your Turn...")
            color = input("Enter color (red/blue): ")
            amount = int(input("Enter amount (1/2): "))
            game_state = move(game_state, (color, amount))

        else:
            print("Computer's turn...")
            color, amount = generate_computer_move(game_state, move_heuristic) #TODO: change depth for extra credit
            print(f"Computer removes {amount} {color} marbles")
            game_state = move(game_state, (color, amount))

    print("Game Over")
    player1 = game_state.current_player
    player2 = players[(players.index(game_state.current_player) + 1) % 2]
    if version == "standard":
        print(f"{player1} wins!")
    else:
        print(f"{player2} wins!")


main()
