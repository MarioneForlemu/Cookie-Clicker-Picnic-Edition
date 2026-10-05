package assignment4;

import java.awt.Color;
import java.awt.Font;
import edu.princeton.cs.introcs.StdDraw;

public class CookieClicker {

	public static void main(String[] args) {

		// ---------------------------------------------------------------------
		// AI PROMPT LOG (For Evaluation Requirement):
		// "How to prevent the white screen flash when redrawing the cookie clicker game and its animation?"
		// Besides this I only used a youtube video to learn how to use StdDraw for my ant animation, and the rest of the code is my own work.
		// ---------------------------------------------------------------------

		// Core Score / Mechanics
		int score = 0;
		int clickPower = 1;            // Starts w/ 1 cookie per click
		int idleIncomePerSecond = 0;   // Starts w/ 0 passive cookies per second

		// Power-Up Thresholds and Pricing Costs
		int clickUpgradeCost = 20;     // Unlocks @ 20 cookies
		int idleUpgradeCost = 60;      // Idle upgrade unlocks @ 60 cookies
		double costMultiplier = 1.5;   // Multiplies the cost threshold by 1.5 after each auto-purchase

		// Timing Variables (millisecond matching)
		long lastIdleHarvestTime = System.currentTimeMillis();

		// FIXED ANIMATION TRACKER
		double antTimer = 0.0; // Global position tracker for the entire marching stream

		// Takes care of buffering
		StdDraw.enableDoubleBuffering();

		boolean wasPressed = false;

		// Game rendering
		drawGame(score, antTimer);

		// Cookie was clicked
		while (true) {
			long currentTime = System.currentTimeMillis();

			// --- CODE ADDED: DYNAMIC ANIMATION SPEED CALCULATION ---
			// Sets a slow starting speed, then accelerates based on upgrades
			double baseSpeed = 0.0003;
			double speedMultiplier = 0.00015 * (clickPower - 1) + 0.00025 * idleIncomePerSecond;
			double currentSpeed = baseSpeed + speedMultiplier;
			
			// Cap the speed so it doesn't get completely chaotic at crazy high scores
			if (currentSpeed > 0.004) {
				currentSpeed = 0.004;
			}

			// Apply the dynamic speed factor to the animation timeline
			antTimer += currentSpeed; 
			if (antTimer > 0.04) {
				antTimer = 0.0; // Clean, seamless loop point matching our small ant spacing
			}

			// Idle income (every 1000 ms)
			if (currentTime - lastIdleHarvestTime >= 1000) {
				score += idleIncomePerSecond; // Earn passive points
				lastIdleHarvestTime = currentTime; // Reset time anchor for next second

				// Checks for automatic Click Power-up purchase
				if (score >= clickUpgradeCost) {
					score -= clickUpgradeCost;
					clickPower += 1;
					clickUpgradeCost = (int) (clickUpgradeCost * costMultiplier);
				}

				// Checks for automatic Idle Power-up purchase
				if (score >= idleUpgradeCost) {
					score -= idleUpgradeCost;
					idleIncomePerSecond += 1;
					idleUpgradeCost = (int) (idleUpgradeCost * costMultiplier);
				}

				// Updates score display
				drawGame(score, antTimer);
			}

			// Detecting mouse clicks
			boolean isPressed = StdDraw.isMousePressed();

			if (isPressed && !wasPressed) {
				double dx = StdDraw.mouseX() - 0.5;
				double dy = StdDraw.mouseY() - 0.5;

				if (Math.sqrt(dx * dx + dy * dy) <= 0.25) {
					score += clickPower; // Increments score by current click power modifier

					// Runs upgrade check immediately after a click for responsive buying
					if (score >= clickUpgradeCost) {
						score -= clickUpgradeCost;
						clickPower += 1;
						clickUpgradeCost = (int) (clickUpgradeCost * costMultiplier);
					}

					if (score >= idleUpgradeCost) {
						score -= idleUpgradeCost;
						idleIncomePerSecond += 1;
						idleUpgradeCost = (int) (idleUpgradeCost * costMultiplier);
					}

					drawGame(score, antTimer);
				}
			}

			// Keep screen rendering so movement feels seamless
			drawGame(score, antTimer);

			wasPressed = isPressed;
			StdDraw.pause(10);
		}
	}

	public static void drawGame(int score, double antTimer) {
		
		StdDraw.clear();

		// My custom checkerboard background
		int rows = 5;
		int cols = 5;
		double halfWidth = 0.1;

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				double x = 0.1 + col * 0.2;
				double y = 0.1 + row * 0.2;

				if ((row + col) % 2 == 0) {
					StdDraw.setPenColor(189, 62, 73);
				} else {
					StdDraw.setPenColor(255, 251, 250);
				}

				StdDraw.filledSquare(x, y, halfWidth);
			}
		}

		// Ants entering frame (bottom left)
		for (double offset = -0.04; offset < 0.45; offset += 0.04) {
			double progress = antTimer + offset;
			
			// Stop drawing once they reach the edge of the cookie
			if (progress >= 0.0 && progress < 0.32) {
				double antX = progress;
				double waveFrequency = 40.0; 
				double waveAmplitude = 0.015; 
				
				double antY = progress + (waveAmplitude * Math.sin(antX * waveFrequency));
				
				double changeInX = 1.0;
				double changeInY = 1.0 + (waveAmplitude * waveFrequency * Math.cos(antX * waveFrequency));
				double angleInDegrees = Math.toDegrees(Math.atan2(changeInY, changeInX));
				double correctedAngle = angleInDegrees - 90.0;
				
				double walkFactor = 0.006 * Math.sin(progress * 350.0);
				double antW = 0.035 + walkFactor;
				double antH = 0.035 - walkFactor;
				
				StdDraw.picture(antX, antY, "ant.png", antW, antH, correctedAngle);
			}
		}

		// Ants leaving w/ crumbs (top right)
		for (double offset = -0.04; offset < 0.45; offset += 0.04) {
			double progress = antTimer + offset;
			
			double exitX = 0.65 + progress;
			double exitY = 0.65 + progress;
			
			if (exitX >= 0.65 && exitX <= 1.05) {
				double waveFrequency = 40.0;
				double waveAmplitude = 0.015;
				
				double antY = exitY + (waveAmplitude * Math.sin(exitX * waveFrequency));
				
				double changeInX = 1.0;
				double changeInY = 1.0 + (waveAmplitude * waveFrequency * Math.cos(exitX * waveFrequency));
				double angleInDegrees = Math.toDegrees(Math.atan2(changeInY, changeInX));
				double correctedAngle = angleInDegrees - 90.0;
				
				double walkFactor = 0.006 * Math.sin(progress * 350.0);
				double antW = 0.035 + walkFactor;
				double antH = 0.035 - walkFactor;
				
				// Draw the leaving ant
				StdDraw.picture(exitX, antY, "ant.png", antW, antH, correctedAngle);
				
				// Draw a realistic crumb size (radius 0.004) right @ mouth level
				StdDraw.setPenColor(179, 121, 59);
				StdDraw.filledCircle(exitX + 0.012, antY + 0.012, 0.004);
			}
		}

		// Cookie Base
		StdDraw.setPenColor(179, 121, 59);
		StdDraw.filledCircle(0.5, 0.5, 0.25);

		// Cookie Chips
		StdDraw.setPenColor(68, 38, 11);
		StdDraw.filledCircle(0.4, 0.6, 0.04);
		StdDraw.filledCircle(0.5, 0.5, 0.04);
		StdDraw.filledCircle(0.6, 0.6, 0.04);
		StdDraw.filledCircle(0.4, 0.4, 0.04);
		StdDraw.filledCircle(0.6, 0.4, 0.04);

		// Game text elements
		StdDraw.setPenColor(StdDraw.BLACK);
		Font font = new Font("Times New Roman", Font.BOLD, 22);
		StdDraw.setFont(font);
		StdDraw.text(0.5, 0.9, "Cookie Clicker");

      StdDraw.setPenColor(StdDraw.BLACK);
		Font picnic = new Font("Times New Roman", Font.ITALIC, 20);
		StdDraw.setFont(picnic);
		StdDraw.text(0.5, 0.86, "Picnic Edition");
		
		Font ScoreFont = new Font("Times New Roman", Font.PLAIN, 15);
		StdDraw.setFont(ScoreFont);
		StdDraw.text(0.5, 0.78, "Score: " + score);

		StdDraw.show();
	}
}
