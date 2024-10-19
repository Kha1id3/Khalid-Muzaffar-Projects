
import sys
import time
import math


from board import Board
from tile import Tile


class Halma():

    def __init__(self, b_size=8, t_limit=60, c_player=Tile.P_black):
        self.b_size = b_size
        self.t_limit = t_limit
        self.c_player = c_player
        self.current_player = Tile.P_white
        self.board = [[Tile() for _ in range(b_size)] for _ in range(b_size)]
        self.set_predefined_board()
        self.board_view = Board(self.board)
        self.selected_tile = None
        self.valid_moves = []
        self.computing = False
        self.total_plies = 0
        self.ply_depth = 3
        self.ab_enabled = True
        self.start_time = time.time()  
        self.total_boards = 0
        self.total_prunes = 0

        self.board_view.set_status_color("#E50000" if self.current_player == Tile.P_black else "#007F00")

        if self.c_player == self.current_player:
            self.ai_move()

        self.board_view.add_click_handler(self.tile_clicked)
        self.board_view.draw_tiles()  

        print("Halma Solver Basic Information")
        print("==============================")
        print("AI opponent enabled:", "no" if self.c_player is None else "yes")
        print("A-B pruning enabled:", "yes" if self.ab_enabled else "no")
        print("Turn time limit:", self.t_limit)
        print("Max ply depth:", self.ply_depth)
        print()

        self.board_view.mainloop()  

    def set_predefined_board(self):
        predefined_setup = [
            [2, 2, 2, 2, 0, 0, 0, 0],
            [2, 2, 2, 0, 0, 0, 0, 0],
            [2, 2, 0, 0, 0, 0, 0, 0],
            [2, 0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0, 0, 1],
            [0, 0, 0, 0, 0, 0, 1, 1],
            [0, 0, 0, 0, 0, 1, 1, 1],
            [0, 0, 0, 0, 1, 1, 1, 1]
        ]
        for i in range(self.b_size):
            for j in range(self.b_size):
                self.board[i][j] = Tile(tile=0, piece=predefined_setup[i][j], row=i, col=j)
        self.initialize_goals()
    

    def initialize_goals(self):
        self.r_goals = [tile for row in self.board for tile in row if tile.piece == Tile.P_black]
        self.g_goals = [tile for row in self.board for tile in row if tile.piece == Tile.P_white]

    def tile_clicked(self, row, col):

        if self.computing:  
            return

        new_tile = self.board[row][col]

        
        if new_tile.piece == self.current_player:

            self.outline_tiles(None)  

            # Outline the new and valid move tiles
            new_tile.outline = Tile.O_MOVED
            self.valid_moves = self.get_moves_at_tile(new_tile,
                self.current_player)
            self.outline_tiles(self.valid_moves)

        
            self.board_view.set_status("Tile `" + str(new_tile) + "` selected")
            self.selected_tile = new_tile

            self.board_view.draw_tiles(board=self.board)  

        # If we already had a piece selected and we are moving a piece
        elif self.selected_tile and new_tile in self.valid_moves:

            self.outline_tiles(None)  
            self.move_piece(self.selected_tile, new_tile)  

            # Update status and reset tracking variables
            self.selected_tile = None
            self.valid_moves = []
            self.current_player = (Tile.P_black
                if self.current_player == Tile.P_white else Tile.P_white)

            self.board_view.draw_tiles(board=self.board)  

            # If there is a winner to the game
            winner = self.find_winner()
            if winner:
                self.board_view.set_status("The " + ("white"
                    if winner == Tile.P_white else "black") + " player has won!")
                self.current_player = None
                self.finalize_game()
            elif self.c_player is not None:
                self.ai_move()

        else:
            self.board_view.set_status("Invalid move attempted")


    def evaluate_proximity(self, player):
        target_row = 0 if player == Tile.P_white else self.b_size - 1
        score = 0
        for row in range(self.b_size):
            for col in range(self.b_size):
                tile = self.board[row][col]
                if tile.piece == player:
                    score += abs(target_row - row)  # smaller is better
        return -score  # Negative because closEer is better

    def evaluate_mobility(self, player):
        total_moves = sum(len(self.get_moves_at_tile(self.board[row][col], player))
                        for row in range(self.b_size) for col in range(self.b_size)
                        if self.board[row][col].piece == player)
        return total_moves  # More moves is better

    def evaluate_safety(self, player):
        safety_score = 0
        directions = [(1, 0), (0, 1), (-1, 0), (0, -1)]
        for row in range(self.b_size):
            for col in range(self.b_size):
                tile = self.board[row][col]
                if tile.piece == player:
                    # Count friendly neighbors
                    safety_score += sum(1 for dr, dc in directions
                                        if 0 <= row + dr < self.b_size and 0 <= col + dc < self.b_size
                                        and self.board[row + dr][col + dc].piece == player)
        return safety_score
    
    def utility(self, player):
    # Weighted combination of heuristics
        return (self.evaluate_proximity(player) * 0.5 +
                self.evaluate_mobility(player) * 0.3 +
                self.evaluate_safety(player) * 0.2)

    def minimax(self, depth, player_to_max, max_time, a=float("-inf"),
                b=float("inf"), maxing=True, prunes=0, boards=0):

        # Bottomed out base case
        if depth == 0 or self.find_winner() or time.time() > max_time:
            return self.utility_distance(player_to_max), None, prunes, boards

        # Setup initial variables and find moves
        best_move = None
        if maxing:
            best_val = float("-inf")
            moves = self.get_next_moves(player_to_max)
        else:
            best_val = float("inf")
            moves = self.get_next_moves((Tile.P_black
                    if player_to_max == Tile.P_white else Tile.P_white))

        # For each move
        for move in moves:
            for to in move["to"]:

                # Bail out when we're out of time
                if time.time() > max_time:
                    return best_val, best_move, prunes, boards

                # Move piece to the move outlined
                piece = move["from"].piece
                move["from"].piece = Tile.P_NONE
                to.piece = piece
                boards += 1

                # Recursively call self
                val, _, new_prunes, new_boards = self.minimax(depth - 1,
                    player_to_max, max_time, a, b, not maxing, prunes, boards)
                prunes = new_prunes
                boards = new_boards

                # Move the piece back
                to.piece = Tile.P_NONE
                move["from"].piece = piece

                if maxing and val > best_val:
                    best_val = val
                    best_move = (move["from"].loc, to.loc)
                    a = max(a, val)

                if not maxing and val < best_val:
                    best_val = val
                    best_move = (move["from"].loc, to.loc)
                    b = min(b, val)

                if self.ab_enabled and b <= a:
                    return best_val, best_move, prunes + 1, boards

        return best_val, best_move, prunes, boards

    def ai_move(self):

        # Print out search information
        current_turn = (self.total_plies // 2) + 1
        print("Turn", current_turn, "Computation")
        print("=================" + ("=" * len(str(current_turn))))
        print("Executing search ...", end=" ")
        sys.stdout.flush()
        
        boards = 0
        prunes = 0

        # self.board_view.set_status("Computing next move...")
        self.computing = True
        self.board_view.update()
        max_time = time.time() + self.t_limit
        

        # Execute minimax search
        start = time.time()
        _, move, prunes, boards = self.minimax(self.ply_depth,
            self.c_player, max_time)
        end = time.time()

        self.total_boards += boards
        self.total_prunes += prunes

        
        print("complete")
        print("Time to compute:", round(end - start, 4))
        print("Total boards generated:", boards)
        print("Total prune events:", prunes)

        if move:
         self.move_piece(self.board[move[0][0]][move[0][1]], self.board[move[1][0]][move[1][1]])
         self.board_view.draw_tiles()

        
        self.outline_tiles(None)  
        move_from = self.board[move[0][0]][move[0][1]]
        move_to = self.board[move[1][0]][move[1][1]]
        self.move_piece(move_from, move_to)

        self.board_view.draw_tiles(board=self.board)  

        winner = self.find_winner()
        if winner:
            self.board_view.set_status("The " + ("white"
                if winner == Tile.P_white else "black") + " player has won!")
            self.board_view.set_status_color("#212121")
            self.current_player = None
            self.current_player = None

            self.finalize_game()

            print()
            print("Final Stats")
            print("===========")
            print("Final winner:", "white"
                if winner == Tile.P_white else "black")
            print("Total # of plies:", self.total_plies)

        else:  
            self.current_player = (Tile.P_black
                if self.current_player == Tile.P_white else Tile.P_white)

        self.computing = False
        print()

    def get_next_moves(self, player=1):

        moves = []  
        for col in range(self.b_size):
            for row in range(self.b_size):

                curr_tile = self.board[row][col]

                # Skip board elements that are not the current player
                if curr_tile.piece != player:
                    continue

                move = {
                    "from": curr_tile,
                    "to": self.get_moves_at_tile(curr_tile, player)
                }
                moves.append(move)

        return moves

    def get_moves_at_tile(self, tile, player, moves=None, adj=True):

        if moves is None:
            moves = []

        row = tile.loc[0]
        col = tile.loc[1]

        valid_tiles = [Tile.T_NONE, Tile.T_white, Tile.T_black]
        if tile.tile != player:
            valid_tiles.remove(player)  # Moving back into your own goal
        if tile.tile != Tile.T_NONE and tile.tile != player:
            valid_tiles.remove(Tile.T_NONE)  # Moving out of the enemy's goal

        # Find and save immediately adjacent moves
        for col_delta in range(-1, 2):
            for row_delta in range(-1, 2):

               

                new_row = row + row_delta
                new_col = col + col_delta

                # Skip checking degenerate values
                if ((new_row == row and new_col == col) or
                    new_row < 0 or new_col < 0 or
                    new_row >= self.b_size or new_col >= self.b_size):
                    continue

                # Handle moves out of/in to goals
                new_tile = self.board[new_row][new_col]
                if new_tile.tile not in valid_tiles:
                    continue

                if new_tile.piece == Tile.P_NONE:
                    if adj:  
                        moves.append(new_tile)
                    continue

                

                new_row = new_row + row_delta
                new_col = new_col + col_delta

                # Skip checking degenerate values
                if (new_row < 0 or new_col < 0 or
                    new_row >= self.b_size or new_col >= self.b_size):
                    continue

                # Handle returning moves and moves out of/in to goals
                new_tile = self.board[new_row][new_col]
                if new_tile in moves or (new_tile.tile not in valid_tiles):
                    continue

                if new_tile.piece == Tile.P_NONE:
                    moves.insert(0, new_tile)  # PrioritizeING jumps
                    self.get_moves_at_tile(new_tile, player, moves, False)

        return moves

    def move_piece(self, from_tile, to_tile):

        # Handle trying to move a non-existant piece and moving into a piece
        if from_tile.piece == Tile.P_NONE or to_tile.piece != Tile.P_NONE:
            self.board_view.set_status("")
            return

        # Move piece
        to_tile.piece = from_tile.piece
        from_tile.piece = Tile.P_NONE

        # Update outline
        to_tile.outline = Tile.O_MOVED
        from_tile.outline = Tile.O_MOVED

        self.total_plies += 1

        self.board_view.set_status_color("#007F00" if
            self.current_player == Tile.P_black else "#E50000")
        self.board_view.set_status("Piece moved from `" + str(from_tile) +
            "` to `" + str(to_tile) + "`, " + ("white's" if
            self.current_player == Tile.P_black else "black's") + " turn...")

    def find_winner(self):

        if all(g.piece == Tile.P_white for g in self.r_goals):
            return Tile.P_white
        elif all(g.piece == Tile.P_black for g in self.g_goals):
            return Tile.P_black
        else:
            return None

    def outline_tiles(self, tiles=[], outline_type=Tile.O_SELECT):

        if tiles is None:
            tiles = [j for i in self.board for j in i]
            outline_type = Tile.O_NONE

        for tile in tiles:
            tile.outline = outline_type

    def utility_distance(self, player):

        def point_distance(p0, p1):
            return math.sqrt((p1[0] - p0[0])**2 + (p1[1] - p0[1])**2)

        value = 0

        for col in range(self.b_size):
            for row in range(self.b_size):

                tile = self.board[row][col]

                if tile.piece == Tile.P_white:
                    distances = [point_distance(tile.loc, g.loc) for g in
                                 self.r_goals if g.piece != Tile.P_white]
                    value -= max(distances) if len(distances) else -50

                elif tile.piece == Tile.P_black:
                    distances = [point_distance(tile.loc, g.loc) for g in
                                 self.g_goals if g.piece != Tile.P_black]
                    value += max(distances) if len(distances) else -50

        if player == Tile.P_black:
            value *= -1

        return value
    
    def finalize_game(self):
        game_duration = time.time() - self.start_time
        print("Game Over")
        self.print_board()
        winner = self.find_winner()
        print(f"Winner: {'white' if winner == Tile.P_white else 'black'}")
        print(f"Total Runtime: {game_duration:.2f} seconds")
        print(f"Total Evaluated Nodes: {self.total_boards}")
        print(f"Total Prune Events: {self.total_prunes}")

    def print_board(self):
        for row in self.board:
            print(' '.join(str(tile.piece) for tile in row))



if __name__ == "__main__":

    halma = Halma()
