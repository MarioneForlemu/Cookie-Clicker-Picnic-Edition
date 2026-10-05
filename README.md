# Cookie Clicker: Picnic Edition 🐜🍪

A casual desktop clicking game built in Java using Princeton's `StdDraw` library. I made this for a computer science assignment but decided to add a custom picnic theme with an animated trail of marching ants for visual flair.

## Inspiration 💡

This project draws heavy inspiration from classic incremental games like *Cookie Clicker*, reimagined with a chaotic picnic theme. 

Instead of buying normal corporate grandmas or factory upgrades, your growing cookie count automatically attracts a continuous stream of ants. The more cookies you earn, the faster they work!

## Mechanics 🕹️

The core loop focuses on tapping the cookie, building up your score, and watching the ant trail react:

Click Cookie → Score Rises → Auto-Unlock Upgrades → Ants Move Faster 🏃‍♂️💨

### Controls

| Action | Control | Visual Outcome |
| :--- | :--- | :--- |
| **Click Cookie** | Left Mouse Click | Increments your score based on active click power |
| **Idle Earning** | Wait / Do Nothing | Earns 1 cookie automatically every single second |
| **Auto-Upgrade** | Reach 20 / 60 Cookies | Deducts cost, bumps stats, and scales next tier cost |

### The Ant Army Behavior 🐜

- **The Entry Trail:** Ants spawn in the bottom-left corner and walk along a wavy path toward the cookie as a visual aesthetic.
- **The Visual Turnaround:** Once they touch the cookie radius, they switch to the exit path and spawn a tiny circle in their mouths to look like they are carrying a crumb away. 
- **Adaptive Speed:** The ants track your upgrades. At 0 cookies, the animation crawls incredibly slow. As your click power and passive income speed up, the animation loop accelerates to match your pace, serving as a visual indicator of your progress without affecting your actual score or gameplay.

## 🎨 Color Palette

I swapped the standard boring backgrounds for a custom picnic blanket checkerboard grid:

- **Main Board:** Dark Picnic Red (`#BD3E49`) & Crisp White (`#FFFBFA`)
- **The Pastry:** Classic Cookie Brown (`#B3793B`) with Chocolate Chips (`#44260B`)

## 🏗️ How it Works (Under the Hood)

- **Flicker-Free Rendering:** Uses double-buffering (`StdDraw.enableDoubleBuffering()`) to clear and redraw the entire screen invisibly in the background before flashing the frame. This stops the screen from blinking white every time the score changes.
- **Dynamic Head Tracking:** Uses `Math.atan2` to calculate the exact direction the ants are traveling along their wavy sine-wave path, spinning their artwork so they always face forward instead of sliding sideways.
- **Real-Time Clocking:** Uses `System.currentTimeMillis()` to count out exactly 1000 milliseconds for a precise passive income loop.

## 🛠️ Get Started

1. Grab the library file your class uses (`stdlib-package.jar`) and add it to your project classpath.
2. Compile the game:
   ```bash
   javac assignment4/CookieClicker.java
   ```
3. Run the game:
   ```bash
   java assignment4/CookieClicker
   ```

**Made with 🐜 and Java**
