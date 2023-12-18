#include "cse30life.h"
#include "board.h"
#include <stdio.h>
#include <stdlib.h>

Board* create_board(const char* filename) {
    FILE* file = fopen(filename, "r");
    if (!file) return NULL;

    Board* new_board = (Board*)malloc(sizeof(*new_board));
    if (!new_board) {
        fclose(file);
        return NULL;
    }

    fscanf(file, "%lu %lu", &(new_board->num_rows), &(new_board->num_cols));

    size_t buffer_size = new_board->num_rows * new_board->num_cols * sizeof(Cell);
    new_board->buffer_a = (Cell*)calloc(buffer_size, 1);
    new_board->buffer_b = (Cell*)malloc(buffer_size);

    if (!(new_board->buffer_a || new_board->buffer_b)) {
        free(new_board);
        free(new_board->buffer_a);
        free(new_board->buffer_b);
        fclose(file);
        return NULL;
    }
    new_board->gen = 0;
    new_board->current_buffer = new_board->buffer_a;
    new_board->next_buffer = new_board->buffer_b;


    for (size_t row, col; fscanf(file, "%lu %lu", &row, &col) == 2;) {
        size_t index = get_index(new_board->num_cols, row, col);
        new_board->current_buffer[index] = 1;
    }

    fclose(file);
    return new_board;
}

void delete_board(Board** bpp) {
    if (*bpp) {
        free((*bpp)->buffer_a);
        free((*bpp)->buffer_b);
        free(*bpp);
        *bpp = NULL;
    }
}

void clear_board(Board* board) {
    size_t buffer_size = board->num_rows * board->num_cols;
    memset(board->buffer_a, 0, buffer_size * sizeof(Cell)); 
    memset(board->buffer_b, 0, buffer_size * sizeof(Cell));
}

void swap_buffers(Board* board) {
    Cell* i = board->current_buffer;
    board->current_buffer = board->next_buffer;
    board->next_buffer = i;
}

size_t get_index(size_t num_cols, size_t row, size_t col) {
    return row * num_cols + col;
}
