package board;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private static final int SIZE = 3; // Tamanho do tabuleiro (3x3)
    private JButton[][] buttons;      // Botões do tabuleiro
    private char currentPlayer;       // Jogador atual (X ou O)
    private char[][] board;           // Representação do tabuleiro
    private JLabel turnLabel;         // Rótulo para indicar o turno do jogador

    public GameFrame() {
        setTitle("Jogo da Velha");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450); // Altura maior para incluir o rótulo
        setLayout(new BorderLayout()); // Layout com áreas definidas (Norte, Centro, etc.)

        currentPlayer = 'X'; // Jogador inicial
        buttons = new JButton[SIZE][SIZE];
        board = new char[SIZE][SIZE];

        initializeTurnLabel(); // Inicializa o rótulo de turno
        initializeBoard();     // Inicializa o tabuleiro
    }

    private void initializeTurnLabel() {
        // Rótulo para exibir o jogador atual
        turnLabel = new JLabel("Vez do jogador: X");
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        turnLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Margem
        add(turnLabel, BorderLayout.NORTH); // Adiciona o rótulo na parte superior
    }

    private void initializeBoard() {
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(SIZE, SIZE)); // Layout de grade

        // Inicializa os botões e o tabuleiro
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 40)); // Fonte grande para X e O
                buttons[i][j].setFocusPainted(false); // Remove o foco no botão
                final int row = i;
                final int col = j;

                // Adiciona ação para capturar o clique no botão
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        makeMove(row, col);
                    }
                });

                boardPanel.add(buttons[i][j]);
                board[i][j] = ' '; // Inicializa o tabuleiro vazio
            }
        }

        add(boardPanel, BorderLayout.CENTER); // Adiciona o tabuleiro ao centro da janela
    }

    private void makeMove(int row, int col) {
        if (board[row][col] == ' ') { // Se a célula está vazia
            board[row][col] = currentPlayer;
            buttons[row][col].setText(String.valueOf(currentPlayer));

            // Verifica vitória ou empate
            if (checkWin(currentPlayer)) {
                JOptionPane.showMessageDialog(this, "Jogador " + currentPlayer + " venceu!");
                resetGame();
            } else if (isFull()) {
                JOptionPane.showMessageDialog(this, "Empate!");
                resetGame();
            } else {
                // Alterna o jogador
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                updateTurnLabel();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Movimento inválido!");
        }
    }

    private boolean checkWin(char player) {
        // Verifica linhas, colunas e diagonais
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }

    private boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == ' ') return false;
            }
        }
        return true;
    }

    private void updateTurnLabel() {
        // Atualiza o rótulo com o jogador atual
        turnLabel.setText("Vez do jogador: " + currentPlayer);
    }

    private void resetGame() {
        // Reinicia o jogo e reseta o tabuleiro
        currentPlayer = 'X';
        updateTurnLabel();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = ' ';
                buttons[i][j].setText("");
            }
        }
    }
}
