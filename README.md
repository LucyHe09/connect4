# Connect Four - Strategy Game

Connect Four is a classic two-player strategy game where players take turns dropping colored tokens into a suspended vertical grid. The objective is to be the first player to connect four of your own tokens in a row — horizontally, vertically, or diagonally.

---

## Game Overview

- **Players:** 2
- **Grid:** 6 Rows × 7 Columns
- **Goal:** Connect four of your tokens before your opponent does!
- **Win Conditions:**
  - Four in a row **horizontally**
  - Four in a row **vertically**
  - Four in a row **diagonally**

---

## Features

- Object-oriented design using a custom `AbstractStrategyGame` class
- Modular and easily extendable for future enhancements
- Clear win detection in all directions
- Turn-based gameplay logic
- Basic game loop and input handling (if CLI version included)

---

## Class Structure

```plaintext
AbstractStrategyGame (abstract)
        ⬇
    ConnectFour

