# Daily Question Streak System

A console-based productivity system built using Core Java.

## Problem It Solves
Helps students maintain daily learning discipline by rewarding activity and penalizing inactivity.

## Features
- Add daily questions
- Coin reward system
- Daily inactivity penalty
- Streak tracking
- Rank progression:
  BEGINNER → LEARNER → CONSISTENT → FOCUSED → DISCIPLINED → UNBREAKABLE

## Tech Stack
- Core Java
- OOP Principles
- File Handling
- LocalDate & ChronoUnit

## How It Works
Each valid question increases coins.  
Missing days reduce coins.  
Daily activity increases streak.  
Rank is calculated using coins and streak.

## How to Run
1. Install JDK
2. Open terminal in project folder
3. Compile:
   javac QuestionRewardApp.java
4. Run:
   java QuestionRewardApp
