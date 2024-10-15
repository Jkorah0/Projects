from pynput import keyboard
import os
import time
import pygetwindow as gw

#List of unallowed sites (as part of window titles)
unallowed_sites = ["Facebook", "YouTube"]

def check_active_window():
    #Check if the current active window title matches any unallowed site.
    active_window = gw.getActiveWindowTitle()
    if active_window and any(site in active_window for site in unallowed_sites):
        print(f"Unallowed site accessed: {active_window}. Shutting down...")
        os.system("shutdown /s /t 1")  # Windows shutdown
        return True
    return False

def on_press(key):
    try:
        if check_active_window():
            # If an unallowed site is detected, stop the keylogger.
            return False  # This will stop the listener
    except Exception as e:
        print(f"Error: {e}")

# Setup listener for key press events
listener = keyboard.Listener(on_press=on_press)

# Start the listener in the background
listener.start()

# Continuous loop to keep checking the active window
while True:
    if check_active_window():
        break  # Break the loop if an unallowed site is detected
    time.sleep(5)  # Check every 5 
