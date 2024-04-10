Name and UTA ID of the student.
Rodrigo Munoz
ID: 1001847694

python 3.10.9

validate_command_line_arguments: Checks if the correct number of command-line arguments have been provided, and validates the player, color, and game version choices.

calculate_score:  Calculates the score of a given game state, taking into account red and blue marbles remaining and the game version (standard or misere).

is_terminal_state:  Determines if the game is over by checking if there are no red or no blue marbles remaining.

generate_moves: Yields all valid moves possible from the current game state.

move: Simulates a player's move, updates the game state, and switches the current player.

minmax_alpha_beta_pruning: The core AI function that implements the minimax algorithm with alpha-beta pruning to determine the best move.

generate_computer_move: Uses the minimax function to evaluate possible moves and chooses the move with the highest predicted score for the computer.

main: The main function that handles command-line arguments, initializes the game, and controls the game loop.


All game logic is used int he main() function
