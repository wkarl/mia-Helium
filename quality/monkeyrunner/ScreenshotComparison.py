import sys
import os
import os.path
import subprocess

from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice

PACKAGE  = 'de.prosiebensat1digital.seventv'
ACTIVITY = '.activity.MainActivity'

SETTLE_DELAY   = 1
SCREENSHOT_DIR = '../../build/reports/ScreenshotComparison'
REFERENCE      = SCREENSHOT_DIR + '/reference.png'
SCREENSHOT     = SCREENSHOT_DIR + '/screenshot.png'
COMPARISON     = SCREENSHOT_DIR + '/comparison.png'
ACCEPTANCE     = 0.95

print "=== MonkeyRunner - Screenshot comparison ==="

# Make sure apk path is passed as argument
if len(sys.argv) != 2:
    print "usage: monkeyrunner SCRIPT PATH_TO_APK"
    sys.exit(1)

apkPath = sys.argv[1]
# Check if file exists
if not os.path.isfile(apkPath):
    print "No file found at " + apkPath
    sys.exit(1)

# Create screenshots directory
if not os.path.exists(SCREENSHOT_DIR):
    os.makedirs(SCREENSHOT_DIR)

print "Connecting to device"
device = MonkeyRunner.waitForConnection(10)
if device is None:
    print "Failed to connect"
    exit(1)

print "Installing apk"
if not device.installPackage(apkPath):
    sys.exit(1)

print "Starting " + ACTIVITY
device.startActivity(component=PACKAGE + '/' + ACTIVITY)

print "Waiting " + str(SETTLE_DELAY) + "s for UI to settle"
MonkeyRunner.sleep(5)

print "Taking screenshot"
screenshot = device.takeSnapshot()
screenshot.writeToFile(SCREENSHOT)
#screenshot = MonkeyRunner.loadImageFromFile(SCREENSHOT)

print "Comparing screenshot to reference image"
if os.path.isfile(REFERENCE):
    reference = MonkeyRunner.loadImageFromFile(REFERENCE)
else:
    # Create reference on first run
    reference = screenshot
    
if screenshot.sameAs(reference, ACCEPTANCE):
    print "Images match"
    # Set screenshot as new reference
    os.rename(SCREENSHOT, REFERENCE)
else:
    print "Images differ, generating diff"
    subprocess.call(["/usr/bin/compare", REFERENCE, SCREENSHOT, COMPARISON])
    print os.path.realpath(COMPARISON)
    sys.exit(1)

