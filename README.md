# Spriteviewer
Spriteviewer is a simple tool to view animated spritesheets.
The most important feature of Spriteviewer is the ability to
automatically load changes. You can keep it open next to your
favorite editor and see the changes whenever you save your file.

This is by no means a full-fledged spriting tool - it's merely a
program you can use to instantly view changes you make, with
basic settings. This is also by no means well-coded. :)

## Usage

    java -jar <jarfile> <imagefile> <frame width> <frame height>

### Example

This opens the viewer for an image named 'player.png' which has
frames that are sized 16x24.

    java -jar spriteview.jar player.png 16 24
    
### Commands

- *Arrow UP / DOWN*: Change the current row in the sheet
- *Arrow LEFT / RIGHT*: Adjust the frame count
- *Plus / Minus*: Adjust the frame speed (seconds per frame)
- *X*: Swap between white and black backgrounds

## Features

- Automatically reload changes
- Adjust frame speed, frame count and row
- Zoom between 1:1 and 6:1 zoom levels
- Resizable window that always centers the sprite
- White and black backgrounds

## License

This is licensed under MIT license. Feel free to make pull requests!
