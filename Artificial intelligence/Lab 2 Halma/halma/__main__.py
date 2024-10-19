
import sys


from halma import Halma
from tile import Tile

BOARD_SIZES = ["8", "10", "16"]
PTIONS = ["r", "re", "black"]
white_OPTIONS = ["g", "ge", "white"]


if __name__ == "__main__":

   
    if len(sys.argv) < 3:
        print("Usage: halma <b-size> <t-limit> [<h-player>]")
        sys.exit(-1)

    b_size = int(sys.argv[1])
    t_limit = int(sys.argv[2])
    h_player = sys.argv[3] if len(sys.argv) == 4 else None

    # Assuming h_player indicates color
    c_player = None
    if h_player:
        h_player = h_player.lower()
        c_player = Tile.P_white if h_player == 'white' else Tile.P_black

    
    halma = Halma(b_size=b_size, t_limit=t_limit, c_player=c_player)
