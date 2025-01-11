package main;

import board.GameFrame;

public class main {
    public static void main(String[] args) {
        // Garante que o código gráfico roda na Event Dispatch Thread (EDT)
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameFrame gameFrame = new GameFrame();
                gameFrame.setVisible(true); // Exibe a janela
            }
        });
    }
}
