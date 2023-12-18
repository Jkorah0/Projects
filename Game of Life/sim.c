
#include "sim.h"
#include "cse30life.h"
#include "board.h"

#define C_IMPL
extern void asm_do_row(Cell*, Cell*, size_t, size_t, size_t);

size_t mod(int x, size_t N) {
    return (x + x / N * N) % N;
}

static void do_row(Cell* dest, Cell* src, size_t row, size_t rows, size_t cols) {
    for (size_t col = 0; col < cols; col++) {
        int neighbor = 0;

        
        int offsets[3] = { -1, 0, 1 };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (offsets[i] == 0 && offsets[j] == 0) continue;

                size_t neighbor_row = mod(row + offsets[i], rows);
                size_t neighbor_col = mod(col + offsets[j], cols);
                neighbor += src[get_index(cols, neighbor_row, neighbor_col)];
            }
        }

        Cell current_cell = src[get_index(cols, row, col)];

        if (current_cell) {
            if (neighbor < 2 || neighbor > 3) {
                dest[get_index(cols, row, col)] = 0;
            } else {
                dest[get_index(cols, row, col)] = 1;
            }
        } else {
            if (neighbor == 3) {
                dest[get_index(cols, row, col)] = 1;
            } else {
                dest[get_index(cols, row, col)] = 0;
            }
        }
    }
}

void sim_loop(Board* board, unsigned int steps) {
    unsigned int i = 0;
    while (i < steps) {
        size_t row = 0;
        while (row < board->num_rows) {
            do_row(board->next_buffer, board->current_buffer, row, board->num_rows, board->num_cols);
            row++;
        }
        swap_buffers(board);
        i++;
    }
}
